package com.linzx.redis;

import org.junit.Test;
import redis.clients.jedis.BitOP;

import java.util.List;

/**
 * 字符串类型操作
 */
public class StringOperateDemo extends Base {

    /**
     * 设置字符串
     */
    @Test
    public void setSimpleKeyValue() {
        // 单个值操作
        String result = jedis.set("key1", "value1");// 设置值
        System.out.println("result:" + result);
        String val = jedis.get("key1");
        System.out.println("value:" + val);
        System.out.println("ttl>>>>>>" +  jedis.ttl("key"));
        jedis.del("key1"); // 删除

        // 批量操作
        jedis.mset("key1", "value1", "key2", "value2");
        List<String> valList = jedis.mget("key1", "key2");
        System.out.println("valList:" + valList);
        jedis.del("key1", "key2");

        // 设置并返回旧值
        jedis.set("key1", "value1");
        String oldValue = jedis.getSet("key1", "value2");
        System.out.println("newValue -> oldValue:" + jedis.get("key1") + "->" + oldValue);
        jedis.del("key1");

        // 设置key，带超时时间
        jedis.setex("key1", 10, "value1");
        jedis.del("key1");

        // 设值成功返回1，失败返回0
        Long nxRes = jedis.setnx("key1", "value1");
        System.out.println("nxRes:" + nxRes);
        nxRes = jedis.setnx("key1", "value2");
        System.out.println("nxRes:" + nxRes);
        jedis.del("key1");
    }

    /*************** 位运算操作 ******************/
    @Test
    public void bitOp() {
        String key1 = "keyBit1", key2 = "keyBit2", destKey = "destKey";
        jedis.setbit(key1, 1, true);
        jedis.setbit(key1, 3, true);
        jedis.setbit(key1, 5, "0");
        jedis.setbit(key1, 7, "1");
        System.out.println("value1:" + jedis.get(key1)); // 字符：Q ->  ASCII码：81 -> 二进制：1010001
        System.out.println("bitcount:" + jedis.bitcount(key1));
        System.out.println("bitcount1_3:" + jedis.bitcount(key1, 1,4)); // TODO 测试有问题

        jedis.setbit(key2, 1, true);
        jedis.setbit(key2, 3, true);
        jedis.setbit(key2, 6, true);
        System.out.println("value2:" + jedis.get(key2)); // 字符：R ->  ASCII码：82 -> 二进制：101001

        jedis.bitop(BitOP.AND, destKey, key1, key2); // destKey = key1 & key2 =>  1010001 & 1010010 = 1010000
        System.out.println("destKey:" + jedis.get(destKey)); // 字符：P -> ASCII码：80 -> 二进制：1010000

        System.out.println("destKey_bit_offset_1:" + jedis.getbit(destKey, 1)); // true
        System.out.println("destKey_bit_offset_2:" + jedis.getbit(destKey, 2)); // false
        System.out.println("destKey_bit_offset_3:" + jedis.getbit(destKey, 3)); // true
        System.out.println("destKey_bit_offset_4:" + jedis.getbit(destKey, 4)); // false
        
        jedis.del(key1);
        jedis.del(key2);
        jedis.del(destKey);
    }

}
