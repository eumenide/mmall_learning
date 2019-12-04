package com.mmall.common;

import com.mmall.util.PropertiesUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author: eumes
 * @date: 2019/12/4
 **/
public class RedisPool {
    private static JedisPool pool;  //jedis连接池
    private static Integer maxTotal = PropertiesUtil.getIntProperty("redis.max.total", 20);    //最大连接数
    private static Integer maxIdle = PropertiesUtil.getIntProperty("redis.max.idle", 10);     //在JedisPool中最大的idle状态（空闲）的jedis实例的个数
    private static Integer minIdel = PropertiesUtil.getIntProperty("redis.min.idle", 2);     //在JedisPool中最大的idle状态（空闲）的jedis实例的个数
    //在borrow一个jedis实例的时候，是否需要验证操作
    //如果赋值为true，则得到的jedis实例肯定是可以用的
    private static Boolean testOnBorrow = PropertiesUtil.getBoolProperty("redis.test.borrow", true);
    //在return一个jedis实例的时候，是否需要验证操作
    //如果赋值为true，则放回JedisPool的jedis实例肯定是可以用的
    private static Boolean testOnReturn = PropertiesUtil.getBoolProperty("redis.test.return", true);

    private static String redisIp = PropertiesUtil.getProperty("redis.ip");
    private static Integer redisPort = PropertiesUtil.getIntProperty("redis.port");
    private static Integer redisTimeout = PropertiesUtil.getIntProperty("redis.timeout", 1000 * 2);

    static {
        initPool();
    }

    private static void initPool() {
        JedisPoolConfig config = new JedisPoolConfig();

        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdel);

        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);

        // 连接耗尽时，是否阻塞。false会抛出异常，true则阻塞直到超时。默认为true
        config.setBlockWhenExhausted(true);

        pool = new JedisPool(config, redisIp, redisPort, redisTimeout);
    }

    public static Jedis getJedis() {
        return pool.getResource();
    }

    public static void returnResource(Jedis jedis) {
        // 源码中已经判断是否为null，此处可以不判断
        pool.returnResource(jedis);
    }

    public static void returnBrokenResource(Jedis jedis) {
        pool.returnBrokenResource(jedis);
    }


    public static void main(String[] args) {
        Jedis jedis = pool.getResource();
        System.out.println(jedis.get("testKey"));
        jedis.set("testKey", "testValue");
        System.out.println(jedis.get("testKey"));
        returnResource(jedis);

        // 临时调用，销毁连接池中所有连接
        pool.destroy();
        System.out.println("end");
    }

}
