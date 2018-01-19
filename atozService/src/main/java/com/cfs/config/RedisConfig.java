package com.cfs.config;

import com.bettercloud.vault.Vault;
import com.bettercloud.vault.VaultException;
import com.cfs.core.vault.VaultConstants;
import com.cfs.core.vault.VaultProperties;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;


/**
 * @author chopra
 * 12/11/17
 */

@DependsOn("vaultSetup") @Configuration("redisConfig") public class RedisConfig {
    @Autowired private Vault vault;

    private RedisCredentials redisCredentials;
    private static final Logger LOGGER = Logger.getLogger(RedisConfig.class);

    @Bean public Jedis jedis() {
        Jedis jedis = new Jedis(redisCredentials.host);
        return jedis;
    }

    @Bean public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(Integer.parseInt(redisCredentials.maxTotal));
        jedisPoolConfig.setMaxIdle(Integer.parseInt(redisCredentials.maxIdle));
        jedisPoolConfig.setTestOnBorrow(Boolean.parseBoolean(redisCredentials.testOnBorrow));
        jedisPoolConfig.setMaxWaitMillis(Integer.parseInt(redisCredentials.maxWaitMillis));
        return jedisPoolConfig;
    }


    @Bean public JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConnectionFactory =
            new JedisConnectionFactory(jedisPoolConfig());
        jedisConnectionFactory.setHostName(redisCredentials.host);
        jedisConnectionFactory.setPassword(redisCredentials.pass);
        jedisConnectionFactory.setUsePool(true);
        return jedisConnectionFactory;
    }

    @Bean public StringRedisTemplate stringRedisTemplate() {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(jedisConnectionFactory());
        return stringRedisTemplate;
    }

    @Bean public StringRedisSerializer stringRedisSerializer() {
        return new StringRedisSerializer();
    }

    @Bean public RedisTemplate redisTemplate() {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        redisTemplate.setKeySerializer(stringRedisSerializer());
        redisTemplate.setValueSerializer(stringRedisSerializer());
        return redisTemplate;
    }

    @PostConstruct private void readCredentialsFromVault() {
        this.redisCredentials = new RedisCredentials();
        LOGGER.info("loading Redis Configuration");
        try {
            // todo remove this code
//            try {
//                redisCredentials.host = "localhost";
//                redisCredentials.pass = "";
//                redisCredentials.maxTotal = "1024";
//                redisCredentials.maxIdle = "200";
//                redisCredentials.maxWaitMillis = "2000";
//                redisCredentials.testOnBorrow = "false";
//            }
//            catch (Exception e){
//                throw new VaultException("exception");
//            }

            redisCredentials.host = vault.logical()
                .read(VaultConstants.VAULT_APPLICATION_PATH + VaultProperties.VAULT_PROFILE)
                .getData().get(VaultConstants.VAULT_REDIS_HOST);
            LOGGER.info("Redis URl received from vault = " + redisCredentials.host);
            redisCredentials.pass = vault.logical()
                .read(VaultConstants.VAULT_APPLICATION_PATH + VaultProperties.VAULT_PROFILE)
                .getData().get(VaultConstants.VAULT_REDIS_SERVER_PASS);
            redisCredentials.maxTotal = vault.logical()
                .read(VaultConstants.VAULT_APPLICATION_PATH + VaultProperties.VAULT_PROFILE)
                .getData().get(VaultConstants.VAULT_REDIS_MAX_TOTAL);
            redisCredentials.maxIdle = vault.logical()
                .read(VaultConstants.VAULT_APPLICATION_PATH + VaultProperties.VAULT_PROFILE)
                .getData().get(VaultConstants.VAULT_REDIS_MAX_IDLE);
            redisCredentials.maxWaitMillis = vault.logical()
                .read(VaultConstants.VAULT_APPLICATION_PATH + VaultProperties.VAULT_PROFILE)
                .getData().get(VaultConstants.VAULT_REDIS_MAX_WAIT);
            redisCredentials.testOnBorrow = vault.logical()
                .read(VaultConstants.VAULT_APPLICATION_PATH + VaultProperties.VAULT_PROFILE)
                .getData().get(VaultConstants.VAULT_REDIS_TEST_ON_BORROW);

            LOGGER.info("loaded Redis Configuration");

        } catch (VaultException e) {
            throw new Error("Failed to read Redis creds from Vault", e);
        }
    }

    private class RedisCredentials {
        protected String host;
        protected String user;
        protected String pass;
        protected String maxTotal;
        protected String maxWaitMillis;
        protected String testOnBorrow;
        protected String maxIdle;
    }
}
