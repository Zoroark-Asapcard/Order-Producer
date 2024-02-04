package com.zoroark.orderproducer.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQProducer {

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(String queueName, String message) {
        rabbitTemplate.convertAndSend(queueName, message);
        System.out.println("Mensagem enviada para a fila '" + queueName + "': " + message);
    }
}