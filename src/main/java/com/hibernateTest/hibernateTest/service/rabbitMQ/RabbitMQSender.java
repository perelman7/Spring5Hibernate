package com.hibernateTest.hibernateTest.service.rabbitMQ;

import com.hibernateTest.hibernateTest.model.ChangeRow;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("rabbitMQSender")
public class RabbitMQSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Value("rabbitmq.exchange")
    private String exchange;

    @Value("rabbitmq.bindingkey")
    private String key;

    public void sendMessage(ChangeRow changeRow){
        MessageProperties properties = new MessageProperties();
        properties.setContentType("application/json");
        properties.setType("java.util.String");
        properties.setHeader("__TypeId__", String.class);
        Message message = MessageBuilder.withBody(changeRow.toString().getBytes()).build();
        rabbitTemplate.convertAndSend(exchange, key, message);
    }
}
