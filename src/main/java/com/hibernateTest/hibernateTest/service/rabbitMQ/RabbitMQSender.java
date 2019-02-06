package com.hibernateTest.hibernateTest.service.rabbitMQ;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hibernateTest.hibernateTest.model.ChangeRow;
import org.apache.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service("rabbitMQSender")
public class RabbitMQSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Value("rabbitmq.exchange")
    private String exchange;

    @Value("rabbitmq.bindingkey")
    private String key;

    private Logger logger = Logger.getLogger(RabbitMQSender.class);

    public void sendMessage(ChangeRow changeRow) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            String message = mapper.writeValueAsString(changeRow);
            rabbitTemplate.convertAndSend(exchange, key, message);
        }catch (IOException ex){
            logger.error("Can`t to convert changeRow to the JSON");
        }
    }
}
