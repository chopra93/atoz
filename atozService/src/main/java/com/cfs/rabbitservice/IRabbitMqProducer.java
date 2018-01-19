package com.cfs.rabbitservice;

/**
 * @author chopra
 * 18/01/18
 */
public interface IRabbitMqProducer {
    void createMessage(String message);
}
