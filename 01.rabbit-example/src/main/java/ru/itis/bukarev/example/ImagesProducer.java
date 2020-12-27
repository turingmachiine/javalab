package ru.itis.bukarev.example;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ImagesProducer {
    // есть EXCHANGE - images НЕ ОЧЕРЕДЬ
    private final static String EXCHANGE_NAME = "images";
    // тип FANOUT
    private final static String EXCHANGE_TYPE = "fanout";

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            // создаем exchange
            channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE);
            // открываем файл с картинками
            File file = new File("images.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String currentFile;
            // считываем файл URL
            while ((currentFile = reader.readLine()) != null) {
                // публикую в exchange
                channel.basicPublish(EXCHANGE_NAME, "",null, currentFile.getBytes());
            }
        } catch (IOException | TimeoutException e) {
            throw new IllegalArgumentException(e);
        }
    }

}
