package com.linzx.kafka.produce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProduceController {

    @Autowired
    @Qualifier("testTopic")
    private KafkaTemplate<Integer, String> testTopicKafkaTemplate;

    /**
     * 同步发送
     * @param data
     * @return
     */
    @GetMapping("/testTopic/produceMsg")
    public String produceTestTopicMsg(@RequestParam("data") String data) {
        ListenableFuture<SendResult<Integer, String>> future = testTopicKafkaTemplate.sendDefault(data);
        try {
            SendResult<Integer, String> sendResult = future.get();
            return sendResult.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ok";
    }

    /**
     * 指定key，异步发送消息
     * @param data
     * @param key
     * @return
     */
    @GetMapping("/testTopic/produceMsgWithKey")
    public String produceMsgWithKey(@RequestParam("data") String data, @RequestParam("key") Integer key) {
        ListenableFuture<SendResult<Integer, String>> future = testTopicKafkaTemplate.sendDefault(key, data);
        future.addCallback(new ListenableFutureCallback() {

            @Override
            public void onSuccess(Object result) {
                System.err.println("ListenableFutureCallback#onSuccess:" + result);
            }

            @Override
            public void onFailure(Throwable ex) {
                System.err.println("ListenableFutureCallback#onFailure:" + ex.getMessage());
            }
        });
        return "ok";
    }

    /**
     * 一次发送多条
     */
    @GetMapping("/testTopic/produceBatchMsgWithKey")
    public String produceBatchMsgWithKey(@RequestParam("data") String data, @RequestParam("count") Integer count) {
        for (int i = 0; i < count; i++) {
            testTopicKafkaTemplate.sendDefault(i, data + i);
        }
        return "ok";
    }


    /**
     * 创建topic，指定分区个数4
     *  sh kafka-topics.sh --create --zookeeper 139.196.72.210:2181 --replication-factor 1 --partitions 4 --topic partitionTopicTest
     * 发送消息到指定分区上
     */
    @GetMapping("/partitionTopicTest/produceBatchMsgPartition")
    public String produceBatchMsgPartition(@RequestParam("data") String data, @RequestParam("count") Integer count) {
        for (int i = 0; i < count; i++) {
            testTopicKafkaTemplate.send("partitionTopicTest", i, data + "_" + i);
        }
        return "ok";
    }

}
