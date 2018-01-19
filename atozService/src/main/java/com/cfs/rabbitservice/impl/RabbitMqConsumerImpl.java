package com.cfs.rabbitservice.impl;

import com.cfs.rabbitservice.IRabbitMqConsumer;
import org.springframework.stereotype.Service;

/**
 * @author chopra
 * 18/01/18
 */
@Service
public class RabbitMqConsumerImpl implements IRabbitMqConsumer {

    @Override
    public void receivedMessage(String message){
        System.out.println("Recieved Message: " + message);
    }
}
