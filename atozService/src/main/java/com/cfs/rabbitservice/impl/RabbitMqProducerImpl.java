package com.cfs.rabbitservice.impl;

import com.cfs.rabbitservice.IRabbitMqProducer;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author chopra
 * 18/01/18
 */
@Service
public class RabbitMqProducerImpl implements IRabbitMqProducer {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public void createMessage(String message){
        amqpTemplate.convertAndSend("test_sms",message);
        System.out.println("message =" +message +" is send");
    }
}
