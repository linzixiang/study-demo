package com.linzx.redis;

import org.junit.After;
import org.junit.Before;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class Base {

    /**
     * redis操作客户端
     */
    Jedis jedis;

    /**
     * 连接池
     */
    JedisPool jedisPool;

    /**
     * 开启连接
     */
    @Before
    public void openConnection() {
        //1 获得连接池配置对象，设置配置项
        JedisPoolConfig config = new JedisPoolConfig();
        // 1.1 最大连接数
        config.setMaxTotal(30);
        //1.2 最大空闲连接数
        config.setMaxIdle(10);
        //获得连接池
        jedisPool = new JedisPool(config,"localhost",6379);
        jedis= jedisPool.getResource();
    }



    /**
     * 关闭连接
     */
    @After
    public void closeConnection() {
        if (jedis != null) {
            jedis.close();
        }
    }

}
