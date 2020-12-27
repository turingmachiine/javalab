package ru.itis.bukarev.homework2;

import com.itextpdf.html2pdf.HtmlConverter;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.*;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

public class TransferConsumer {
    private final static String QUEUE = "transfer_queue";

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.basicQos(3);
            channel.basicConsume(QUEUE, false, (consumerTag, message) -> {
                try {
                    // read user
                    ByteArrayInputStream bis = new ByteArrayInputStream(message.getBody());
                    ObjectInputStream is = new ObjectInputStream(bis);
                    Player player = (Player) is.readObject();
                    // generate pdf
                    String html = Render.render(player, "templates/transfer.ftl");
                    OutputStream out = new FileOutputStream("pdf/trasnfer" + UUID.randomUUID() + ".pdf");
                    HtmlConverter.convertToPdf(html, out);
                    System.out.println("new pdf generated");
                    // ack
                    channel.basicAck(message.getEnvelope().getDeliveryTag(), false);

                } catch (ClassNotFoundException e) {
                    // reject
                    System.out.println("FAILED");
                    channel.basicReject(message.getEnvelope().getDeliveryTag(), false);
                }
            }, consumerTag -> {});
        } catch (IOException | TimeoutException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
