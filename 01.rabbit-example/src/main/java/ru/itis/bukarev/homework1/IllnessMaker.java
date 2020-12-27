package ru.itis.bukarev.homework1;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

public class IllnessMaker {

    private final static String EXCHANGE_NAME = "users";
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
                    User user = (User) is.readObject();
                    // generate pdf
                    Document document = new Document();
                    PdfWriter.getInstance(document, new FileOutputStream("pdf/illness_" +
                            UUID.randomUUID() + ".pdf"));
                    document.open();
                    // #blacklivesmatter
                    Font font = FontFactory.getFont(FontFactory.HELVETICA, 14, BaseColor.BLACK);
                    Chunk chunk = new Chunk("User " + user.getFirstName() + " " + user.getLastName() +
                            " is feeling bad and can't work next week", font);
                    document.add(chunk);
                    document.close();
                    System.out.println("new pdf generated");
                    // ack
                    channel.basicAck(message.getEnvelope().getDeliveryTag(), false);

                } catch (ClassNotFoundException | DocumentException e) {
                    // reject
                    System.out.println("FAILED");
                    channel.basicReject(message.getEnvelope().getDeliveryTag(), false);
                }
            };

            channel.basicConsume(queue, false, deliverCallback, consumerTag -> {});
        } catch (IOException | TimeoutException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
