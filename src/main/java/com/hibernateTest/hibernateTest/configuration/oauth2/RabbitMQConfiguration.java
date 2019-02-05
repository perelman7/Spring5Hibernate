package com.hibernateTest.hibernateTest.configuration.oauth2;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.ClassMapper;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfiguration {

    @Value("rabbitmq.queue")
    private String queue;
    @Value("rabbitmq.exchange")
    private String exchange;
    @Value("rabbitmq.bindingkey")
    private String key;

    @Bean
    public Queue queue(){
        return new Queue(queue, false);
    }

    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(exchange);
    }

    @Bean
    public Binding binding(){
        return BindingBuilder.bind(queue()).to(directExchange()).with(key);
    }

    @Bean
    public MessageConverter messageConverter(){
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        converter.setClassMapper(new ClassMapper() {
            @Override
            public void fromClass(Class<?> clazz, MessageProperties properties) {

            }

            @Override
            public Class<?> toClass(MessageProperties properties) {
                return String.class;
            }
        });
        return converter;
    }

/*    @Bean
    public DefaultClassMapper classMapper() {
        DefaultClassMapper classMapper = new DefaultClassMapper();
        Map<String, Class<?>> idClassMapping = new HashMap<>();
        idClassMapping.put("string", String.class);
        classMapper.setIdClassMapping(idClassMapping);
        return classMapper;
    }*/


    @Bean
    public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

}
