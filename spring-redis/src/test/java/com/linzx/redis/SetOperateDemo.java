package com.linzx.redis;

import org.junit.Test;

import java.util.Set;

public class SetOperateDemo extends Base{

    @Test
    public void setOp() {
        jedis.sadd("setKey1", "val1", "val2", "val3");
        jedis.sadd("setKey2", "val1", "val2", "val4", "val5");
        Long setKey1Size = jedis.scard("setKey1");// 返回集合中元素的数量

        Set<String> setDiff1 = jedis.sdiff("setKey1", "setKey2"); // 返回集合的差集， 集合setKey1 - 集合setKey2
        jedis.sdiffstore("setDiffDestKey", "setKey1", "setKey2"); // 返回集合的差集，放到setDestKey中
        Set<String> setDiff2 = jedis.smembers("setDiffDestKey");

        Set<String> setInter1 = jedis.sinter("setKey1", "setKey2"); // 返回集合的交集
        jedis.sinterstore("setInterDestKey", "setKey1", "setKey2"); // 返回集合的交集，放到setInterDestKey中
        Set<String> setInter2 = jedis.smembers("setInterDestKey");

        jedis.smove("setKey1", "setKey2", "val3"); // 将集合setKey2的val3移动到setKey1集合
        Set<String> key2Set = jedis.smembers("setKey2");

        String setKey1Ele = jedis.spop("setKey1"); // 移除并返回集合中的一个随机元素
        Set<String> key1Set = jedis.smembers("setKey1");

        String setKey1RandEle = jedis.srandmember("setKey1"); // 随机返回setKey1集合中的一个元素（不删除）
        key1Set = jedis.smembers("setKey1");

        jedis.srem("setKey2","val5"); // 移除集合setKey2的val5元素

        Set<String> sunionSet = jedis.sunion("setKey1", "setKey2");// 求并集
        jedis.sunionstore("setUnionDestKey", "setKey1", "setKey2");// 求并集，放到setUnionDestKey中
        sunionSet = jedis.smembers("setUnionDestKey");

        jedis.del("setKey1", "setKey2", "setDiffDestKey", "setInterDestKey", "setUnionDestKey");
    }

}
