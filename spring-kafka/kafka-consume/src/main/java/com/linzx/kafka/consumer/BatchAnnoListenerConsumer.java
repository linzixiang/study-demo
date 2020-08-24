package com.linzx.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 批量消费
 */
public class BatchAnnoListenerConsumer {

    private Logger log = LoggerFactory.getLogger(this.getClass());

//    @KafkaListener(id = "consumerBatchListener", topics = {"testTopic"},containerFactory = "kafkaListenerBatchContainerFactory")
//    public void consumerBatchListener(List<String> dataList) {
//        log.info("consumerBatchListener拉去消息数量：" + dataList.size());
//        for(String data : dataList) {
//            log.info(data);
//        }
//    }

    @KafkaListener(id = "consumerBatchListener1", topics = {"testTopic"},containerFactory = "kafkaListenerBatchContainerFactory")
    public void consumerBatchListener1(List<ConsumerRecord<Integer, String>> dataList, Acknowledgment ack) {
        List<String> datas = new ArrayList<>();
        for(ConsumerRecord data : dataList) {
            Optional<ConsumerRecord> consumerRecord = Optional.ofNullable(data);
            if (consumerRecord.isPresent()) {
                datas.add(consumerRecord.get().value().toString());
            }
        }
        if (dataList.size() > 1) {
            // 提交消息
            ack.acknowledge();
            log.info("consumerBatchListener1消费消息：" + dataList.size());
        }
    }

    /** 同一个groupId下，消息实现负载均衡(模拟启动两个服务) **/
    @KafkaListener(id = "consumerBatchListener2", topics = {"partitionTopicTest"}, groupId = "groupTopic-partitionTopicTest", containerFactory = "kafkaListenerBatchContainerFactory")
    public void consumerBatchListener2(List<ConsumerRecord<Integer, String>> consumerRecordList, Acknowledgment ack) {
        List<String> datas = new ArrayList<>();
        for (ConsumerRecord consumerRecord : consumerRecordList) {
            datas.add(consumerRecord.value().toString());
        }
        log.info("consumerBatchListener2消费消息：" + datas);
        ack.acknowledge();
    }

}
