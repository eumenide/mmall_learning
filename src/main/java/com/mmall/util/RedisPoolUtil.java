package com.mmall.util;

import com.mmall.common.RedisPool;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

/**
 * @author: eumes
 * @date: 2019/12/4
 **/

@Slf4j
public class RedisPoolUtil {

    public static String set(String key, String value) {
        Jedis jedis = null;
        String result = null;

        try {
            jedis = RedisPool.getJedis();
            result = jedis.set(key, value);
        } catch (Exception e) {
            log.error("set key:{} value:{} error:", key, value, e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        log.info("set key:{} value:{} success", key, value);

        RedisPool.returnResource(jedis);
        return result;
    }

    public static String get(String key) {
        Jedis jedis = null;
        String result = null;

        try {
            jedis = RedisPool.getJedis();
            result = jedis.get(key);
        } catch (Exception e) {
            log.error("get key:{} error:", key, e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        log.info("get key:{} success", key);

        RedisPool.returnResource(jedis);
        return result;
    }

    /**
     *  设置key的有效期，单位是秒
     * @param key
     * @param time
     * @return
     */
    public static Long expire(String key, int time) {
        Jedis jedis = null;
        Long result = null;

        try {
            jedis = RedisPool.getJedis();
            result = jedis.expire(key, time);
        } catch (Exception e) {
            log.error("expire key:{} time:{} error:", key, time, e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        log.info("expire key:{} time:{} success", key, time);

        RedisPool.returnResource(jedis);
        return result;
    }

    public static String setEx(String key, String value, int time) {
        Jedis jedis = null;
        String result = null;

        try {
            jedis = RedisPool.getJedis();
            result = jedis.setex(key, time, value);
        } catch (Exception e) {
            log.error("setex key:{} value:{} time:{} error:", key, value, time, e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        log.info("setex key:{} value:{} time:{} success", key, value, time);

        RedisPool.returnResource(jedis);
        return result;
    }

    public static Long del(String key) {
        Jedis jedis = null;
        Long result = null;

        try {
            jedis = RedisPool.getJedis();
            result = jedis.del(key);
        } catch (Exception e) {
            log.error("del key:{} error:", key, e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        log.info("del key:{} success", key);

        RedisPool.returnResource(jedis);
        return result;
    }


    public static void main(String[] args) {
        System.out.println("test start.......");

        RedisPoolUtil.set("keyTest", "value");
        RedisPoolUtil.get("keyTest");
        RedisPoolUtil.setEx("keyEx", "valueEx", 60 * 2);
        RedisPoolUtil.expire("keyTest", 60 * 5);
        RedisPoolUtil.del("keyTest");

        System.out.println("test end.");
    }
}
