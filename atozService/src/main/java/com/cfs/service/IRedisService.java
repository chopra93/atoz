package com.cfs.service;

/**
 * Created by Chopra on 30/11/17.
 */
public interface IRedisService {
    String getValue(String key);

    void setValue(String key , String value);

    void setValue(String key , String value, int ttl);

    void deleteValue(String key);

    void setValueIfAbsent(String key, String value);

    void putInMap(String mapname, String key, String value);

    String getFromMap(String mapname, String key);
}

