package ru.itis.bukarev.homework2;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.*;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class PlayerDatabaseAdder {

    private final static String EXCHANGE_NAME = "players";
    private final static String EXCHANGE_TYPE = "fanout";

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.basicQos(3);

            channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE);
            String queue = channel.queueDeclare().getQueue();

            channel.queueBind(queue, EXCHANGE_NAME, "");

            DeliverCallback deliverCallback = (consumerTag, message) -> {
                try {
                    // read user
                    ByteArrayInputStream bis = new ByteArrayInputStream(message.getBody());
                    ObjectInputStream is = new ObjectInputStream(bis);
                    Player player = (Player) is.readObject();
                    // add to list
                    System.out.println("Поступил новый игрок: " + player.getFirstName() + " " + player.getLastName());
                    System.out.println("Введите 1 если хотите добавить его в трансферный список \nВведите 2 если " +
                            "хотите добавить его в арендный список \nВведите что-то другое если хотите " +
                            "отказаться от игрока");
                    Scanner sc = new Scanner(System.in);
                    int type = sc.nextInt();
                    if (type == 1) {
                        //add to transfers.txt
                        PrintWriter out = new PrintWriter(new BufferedWriter(
                                new FileWriter("transfers.txt", true)), true);
                        out.println(player.getFirstName() + " " + player.getLastName());
                        out.close();
                        channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
                    } else if (type == 2) {
                        // add to loans.txt
                        PrintWriter out = new PrintWriter(new BufferedWriter(
                                new FileWriter("loans.txt", true)), true);
                        out.println(player.getFirstName() + " " + player.getLastName());
                        out.close();
                        channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
                    } else {
                        //reject
                        channel.basicReject(message.getEnvelope().getDeliveryTag(), false);
                    }
                    // ack

                } catch (ClassNotFoundException e) {
                    // reject
                    System.out.println("FAILED");
                    channel.basicReject(message.getEnvelope().getDeliveryTag(), false);
                }
            };

            channel.basicConsume(queue, false, deliverCallback, consumerTag -> {
            });
        } catch (IOException | TimeoutException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
