package com.cfs.service.impl;

import com.cfs.service.IRedisService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Created by Chopra on 30/11/17.
 */
@Service
public class RedisServiceImpl implements IRedisService {
    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final Logger LOGGER = Logger.getLogger(RedisServiceImpl.class);

    @Override
    public String getValue(String key) {
        LOGGER.debug("key received - " + key);
        String value = redisTemplate.opsForValue().get(key);
        LOGGER.debug("value returned - " + value);
        return value;
    }

    @Override
    public void setValue(String key, String value) {
        LOGGER.debug("key received - " + key + "data received -" + value);
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void setValue(String key, String value, int ttl) {
        LOGGER.debug("key received - " + key + "data received -" + value + " TTL -" + ttl);
        redisTemplate.opsForValue().set(key, value, ttl, TimeUnit.MILLISECONDS);
    }

    @Override
    public void deleteValue(String key) {
        LOGGER.debug("key received for delete operation - " + key);
        redisTemplate.delete(key);
    }

    @Override
    public void setValueIfAbsent(String key, String value) {
        LOGGER.debug("key received - " + key + "data received -" + value);
        boolean absentFlag = redisTemplate.opsForValue().setIfAbsent(key, value);
        LOGGER.debug("value updated - " + absentFlag);
    }

    @Override
    public void putInMap(String mapname, String key, String value) {
        LOGGER.debug("data received for map : " + mapname + "- " + key + "data received -" + value);
        redisTemplate.opsForHash().put(mapname, key, value);
    }

    @Override
    public String getFromMap(String mapname, String key) {
        LOGGER.debug("Getting value from map : " + mapname + " for key : " + key);
        Object value = redisTemplate.opsForHash().get(mapname, key);
        return value == null ? null : String.valueOf(value);
    }
}
