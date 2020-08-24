package com.linzx.redis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class ListOperateDemo extends Base{

    /**
     * 阻塞等待取出元素
     */
    @Test
    public void listPushBlockPop() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        new ListPush(jedisPool.getResource(), countDownLatch).start();
        new ListPop(jedisPool.getResource(), countDownLatch).start();
        countDownLatch.await();
    }

    @Test
    public void listPushPop() {
        jedis.lpush("language","java", "python");
        jedis.rpush("language", "c");
        System.out.println("队列长度：" + jedis.llen("language"));
        jedis.lset("language", 1, "c++");
        jedis.lrem("language", 0, "java");
        List<String> popEle = new ArrayList<String>();
        while (true) {
            String language = jedis.lpop("language");
            if (language == null) {
                break;
            }
            popEle.add(language);
        }
        System.out.println("取出元素：" + popEle);
    }


    /**
     * 入队
     */
    public class ListPush extends Thread {

        private Jedis jedis;

        private CountDownLatch countDownLatch;

        private int count = 10;

        private String[] arr = new String[]{ "java", "python", "c", "c++" };

        public ListPush(Jedis jedis, CountDownLatch countDownLatch) {
            this.jedis = jedis;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            super.run();
            int i = 0;
            while (i < this.count) {
                int index = new Random().nextInt(4);
                System.out.println("放入language：" + arr[index]);
                jedis.lpush("language", arr[index]);
                try {
                    Thread.sleep(index * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i++;
            }
            this.jedis.close();
            countDownLatch.countDown();
        }
    }

    /**
     * 出队
     */
    public class ListPop extends Thread {

        private Jedis jedis;

        private CountDownLatch countDownLatch;

        public ListPop(Jedis jedis, CountDownLatch countDownLatch) {
            this.jedis = jedis;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            super.run();
            while (true) {
                long startTime = new Date().getTime();
                List<String> language = jedis.blpop(10, "language");// 设为 0 表示阻塞时间可以无限期延长
                if (language == null) {
                    // 超时了
                    break;
                }
                double waitTime = (new Date().getTime() - startTime) / 1000;
                System.out.println("等待" + waitTime + "秒，取出language:" + language);
            }
            this.jedis.close();
            countDownLatch.countDown();
        }
    }

}
