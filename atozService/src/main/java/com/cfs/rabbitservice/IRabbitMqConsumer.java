package com.cfs.rabbitservice;

/**
 * @author chopra
 * 18/01/18
 */
public interface IRabbitMqConsumer {
    void receivedMessage(String message);
}
