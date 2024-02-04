package com.zoroark.orderproducer;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.zoroark.orderproducer.util.CsvReaderUtil;
import com.zoroark.orderproducer.producer.RabbitMQProducer;

@SpringBootApplication
public class OrderproducerApplication implements CommandLineRunner {

	@Autowired
	private RabbitMQProducer rabbitMQProducer;

	public static void main(String[] args) {
		SpringApplication.run(OrderproducerApplication.class, args);
		
		// Set the RabbitMQ broker connection details
        String host = "localhost"; // Replace with your RabbitMQ server host
        int port = 5672; // Replace with your RabbitMQ server port
        String username = "guest"; // Replace with your RabbitMQ server username
        String password = "guest"; // Replace with your RabbitMQ server password

        // Create a CachingConnectionFactory and set the connection details
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);

        // Create a RabbitTemplate instance and set the connection factory
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        
      //Teste da Leitura de Arquivo CSV
      		String filePath = "input-data.csv";
      		CsvReaderUtil.readCSVFile(filePath, rabbitTemplate);
	}

	@Override
	public void run(String... args) throws Exception {
		rabbitMQProducer.send("orders", "Olá, RabbitMQ!");
	}

}
