package com.zoroark.orderproducer;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.zoroark.orderproducer.util.*;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.rabbitmq.client.Channel;

@SpringBootApplication
public class OrderproducerApplication {
	private final static String QUEUE_NAME = "orders";

	public static void main(String[] argv) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		String filePath = "input-data.csv";

        // Create a CachingConnectionFactory and set the connection details
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();

        // Create a RabbitTemplate instance and set the connection factory
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);

		try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
			
			// Create a Queue
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			
			// Function that reads and sends messages in Json format
			CsvReaderUtil.readCSVFile(filePath, rabbitTemplate);
			
		}
	}
}