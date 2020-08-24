package com.linzx.redis;

import org.junit.Test;

public class KeyOperateDemo extends Base {

    @Test
    public void ttl() throws InterruptedException {
        jedis.set("key2", "value2");
        jedis.expire("key2", 10); // 设置有效时间
        int i = 0;
        while (true) {
            Thread.sleep(1000);
            i = i +1;
            Long ttl = jedis.ttl("key2");
            System.out.println("ttl" + i + ">>>>>>" +  ttl);
            if (ttl < 0) {
                break;
            }
        }
    }

    @Test
    public void object() throws Exception {
        jedis.set("key1", "1");
        String encoding = jedis.objectEncoding("key1"); // 查看内部使用的编码类型
        Thread.sleep(1000);
        Long idletime = jedis.objectIdletime("key1");
        Long refcount = jedis.objectRefcount("key1");
        System.out.println("encoding:" + encoding + ",idletime:" + idletime + ",refcount:" + refcount);
        jedis.del("key1");
    }

}
