package cn.com.wanshi.common.redis;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * @desc: cn.com.bluemoon.redis.repository.RedisProperties
 * @author: niejian9001@163.com
 * @date: 2021/11/5 15:42
 */
@Configuration
@ConfigurationProperties(prefix="spring.redis")
public class RefreshableRedisProperties {
    private int database;

    private String host;

    private int port;

    private String password;

    private int maxIdle;

    private int minIdle;

    private int maxActive;

    private int maxWait;

    private Duration timeout;

    /**
     * Whether to enable SSL support.
     */
    private boolean ssl;

    private RedisProperties.Sentinel sentinel;
    private RedisProperties.Cluster cluster;
    private RedisProperties.Jedis jedis;


    public RedisProperties.Jedis getJedis() {
        return jedis;
    }

    public void setJedis(RedisProperties.Jedis jedis) {
        this.jedis = jedis;
    }

    public int getDatabase() {
        return database;
    }

    public void setDatabase(int database) {
        this.database = database;
    }
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public int getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(int maxWait) {
        this.maxWait = maxWait;
    }

    public Duration getTimeout() {
        return timeout;
    }

    public void setTimeout(Duration timeout) {
        this.timeout = timeout;
    }

    public boolean isSsl() {
        return ssl;
    }

    public void setSsl(boolean ssl) {
        this.ssl = ssl;
    }

    public RedisProperties.Sentinel getSentinel() {
        return sentinel;
    }

    public void setSentinel(RedisProperties.Sentinel sentinel) {
        this.sentinel = sentinel;
    }

    public RedisProperties.Cluster getCluster() {
        return cluster;
    }

    public void setCluster(RedisProperties.Cluster cluster) {
        this.cluster = cluster;
    }
}