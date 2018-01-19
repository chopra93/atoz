package com.cfs.core.vault;

/**
 * @author chopra
 * 12/11/17
 */
public class VaultConstants {

    public static final String VAULT_APPLICATION_PATH = "secret/atoz/";
    //"secret/uad/";

    public static final String VAULT_MYSQL_DB_USER = "db.user";
    public static final String VAULT_MYSQL_DB_PASS = "db.pass";
    public static final String VAULT_MYSQL_DB_URL = "db.url";
    public static final String VAULT_REDIS_HOST = "redis.host";
    public static final String VAULT_REDIS_SERVER_PASS = "redis.pass";
    public static final String VAULT_REDIS_MAX_WAIT = "redis.pool.maxWaitMillis";
    public static final String VAULT_REDIS_MAX_TOTAL = "redis.pool.maxTotal";
    public static final String VAULT_REDIS_MAX_IDLE = "redis.pool.maxIdle";
    public static final String VAULT_REDIS_TEST_ON_BORROW = "redis.pool.testOnBorrow";

    public static final String VAULT_ACCESS_KEY_ID = "aws.access.key.id";
    public static final String VAULT_SECRET_KEY = "aws.secret.key";

    public static final String VAULT_RABBIT_HOST_URL = "rabbit.host.url";
    public static final String VAULT_RABBIT_EXCHANGE = "rabbit.exchange";
    public static final String VAULT_RABBIT_KEY = "rabbit.key";
    public static final String VAULT_RABBIT_QUEUE = "rabbit.queue";

}
