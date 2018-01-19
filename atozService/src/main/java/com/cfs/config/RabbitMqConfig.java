package com.cfs.config;

import com.bettercloud.vault.Vault;
import com.bettercloud.vault.VaultException;
import com.cfs.core.vault.VaultConstants;
import com.cfs.core.vault.VaultProperties;
import com.cfs.rabbitservice.IRabbitMqConsumer;
import com.cfs.rabbitservice.impl.RabbitMqConsumerImpl;
import org.apache.log4j.Logger;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.annotation.PostConstruct;

/**
 * @author chopra
 * 18/01/18
 */
@DependsOn("vaultSetup") @Configuration("rabbitMqConfig")
public class RabbitMqConfig {

    @Autowired
    private Vault vault;


    private static final Logger LOGGER = Logger.getLogger(RedisConfig.class);

    private RabbitCredentials rabbitCredentials;

    @Bean
    TopicExchange rubeExchange() {
        return new TopicExchange(rabbitCredentials.exchange, false, true);
    }

    @Bean
    public Queue rubeQueue() {
        return new Queue(rabbitCredentials.queue, false);
    }

    @Bean
    Binding rubeExchangeBinding(TopicExchange rubeExchange, Queue rubeQueue) {
        return BindingBuilder.bind(rubeQueue).to(rubeExchange).with(rabbitCredentials.key);
    }

    @Bean
    public MessageListenerAdapter listenerAdapter(IRabbitMqConsumer rabbitMqConsumer) {
        return new MessageListenerAdapter(rabbitMqConsumer, "receivedMessage");
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitCredentials.host);
        return connectionFactory;
    }

    @Bean
    public AmqpTemplate getAmqpTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setExchange(rabbitCredentials.exchange);
        return rabbitTemplate;
    }

    @PostConstruct
    private void readCredentialsFromVault() {
        this.rabbitCredentials = new RabbitCredentials();
        LOGGER.info("loading RabitMq Configuration");

        try {
            rabbitCredentials.host = vault.logical()
                    .read(VaultConstants.VAULT_APPLICATION_PATH + VaultProperties.VAULT_PROFILE)
                    .getData().get(VaultConstants.VAULT_RABBIT_HOST_URL);

            rabbitCredentials.exchange = vault.logical()
                    .read(VaultConstants.VAULT_APPLICATION_PATH + VaultProperties.VAULT_PROFILE)
                    .getData().get(VaultConstants.VAULT_RABBIT_EXCHANGE);

            rabbitCredentials.key = vault.logical()
                    .read(VaultConstants.VAULT_APPLICATION_PATH + VaultProperties.VAULT_PROFILE)
                    .getData().get(VaultConstants.VAULT_RABBIT_KEY);

            rabbitCredentials.queue = vault.logical()
                    .read(VaultConstants.VAULT_APPLICATION_PATH + VaultProperties.VAULT_PROFILE)
                    .getData().get(VaultConstants.VAULT_RABBIT_QUEUE);


        } catch (VaultException e) {
            throw new Error("Failed to read RabbitMq creds from Vault", e);
        }

        LOGGER.info("loaded RabitMq Configuration");


    }

    private class RabbitCredentials {
        protected String host;
        protected String user;
        protected String pass;
        protected String exchange;
        protected String key;
        protected String queue;
    }
}
