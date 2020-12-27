package ru.itis.bukarev.homework1;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeoutException;

public class UserProducer {

    private final static String EXCHANGE_NAME = "users";
    private final static String EXCHANGE_TYPE = "fanout";

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE);
            File file = new File("users.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String currentFile;
            while ((currentFile = reader.readLine()) != null) {
                String[] data = currentFile.split("\t");
                // to parse date
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                User user = new User(data[0], data[1], data[2],Integer.parseInt(data[3]), format.parse(data[4]));
                // serialize user
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream os = new ObjectOutputStream(bos);
                os.writeObject(user);
                os.flush();
                bos.close();
                // publish
                channel.basicPublish(EXCHANGE_NAME, "",null, bos.toByteArray());
            }
        } catch (IOException | TimeoutException | ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

}
