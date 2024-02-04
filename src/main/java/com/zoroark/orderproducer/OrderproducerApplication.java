package com.zoroark.orderproducer;

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

		//Teste da Leitura de Arquivo CSV
		String filePath = "input-data.csv";
		CsvReaderUtil.readCSVFile(filePath);
	}

	@Override
	public void run(String... args) throws Exception {
		rabbitMQProducer.send("orders", "Olá, RabbitMQ!");
	}

}
