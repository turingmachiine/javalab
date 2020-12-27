package ru.itis.bukarev.homework2;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.*;
import java.util.concurrent.TimeoutException;

public class DocumentProducer {

    private final static String LOAN_QUEUE = "loan_queue";
    private final static String TRANSFER_QUEUE = "transfer_queue";

    private final static String LOAN_ROUTING_KEY = "loan";
    private final static String TRANSFER_ROUTING_KEY = "transfer";
    private final static String DOCUMENTS_EXCHANGE = "documents";
    private final static String EXCHANGE_TYPE = "direct";

    public static void produce() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(DOCUMENTS_EXCHANGE, EXCHANGE_TYPE);
            channel.queueBind(LOAN_QUEUE, DOCUMENTS_EXCHANGE, LOAN_ROUTING_KEY);
            channel.queueBind(TRANSFER_QUEUE, DOCUMENTS_EXCHANGE, TRANSFER_ROUTING_KEY);
            File file = new File("transfers.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String currentFile;
            while ((currentFile = reader.readLine()) != null) {
                String[] data = currentFile.split(" ");
                Player player = Player.builder()
                        .firstName(data[0]).lastName(data[1]).build();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream os = new ObjectOutputStream(bos);
                os.writeObject(player);
                os.flush();
                bos.close();
                channel.basicPublish(DOCUMENTS_EXCHANGE, "transfer", null, bos.toByteArray());
            }
            file = new File("loans.txt");

            reader = new BufferedReader(new FileReader(file));
            while ((currentFile = reader.readLine()) != null) {
                String[] data = currentFile.split(" ");
                Player player = Player.builder()
                        .firstName(data[0]).lastName(data[1]).build();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream os = new ObjectOutputStream(bos);
                os.writeObject(player);
                os.flush();
                bos.close();
                channel.basicPublish(DOCUMENTS_EXCHANGE, "loan", null, bos.toByteArray());
            }
        } catch (IOException | TimeoutException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
