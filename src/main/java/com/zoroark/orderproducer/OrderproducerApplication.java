package com.zoroark.orderproducer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.zoroark.orderproducer.util.CsvReaderUtil;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class OrderproducerApplication {

    public static void main(String[] args) throws Exception {
        String directoryPath = "/path/to/your/directory"; // Specify your directory path here
        String queueName = "orders";

        // Set up RabbitMQ connection
        ConnectionFactory factory = new ConnectionFactory();
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);

        // Set up RabbitMQ queue
        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
            channel.queueDeclare(queueName, false, false, false, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Process existing files in the directory and watch for new ones
        CsvReaderUtil csvReaderUtil = new CsvReaderUtil();
        csvReaderUtil.processExistingFiles(directoryPath);
        csvReaderUtil.watchDirectory(directoryPath);

        // Close the application context
        SpringApplication.run(OrderproducerApplication.class, args);
    }
}
