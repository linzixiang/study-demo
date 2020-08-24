package com.linzx.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;

/**
 * 注解方式@KafkaListener 消费消息
 *  id：消费者的id，当GroupId没有被配置的时候，默认id为GroupId
 *  containerFactory：区分单条数据消费，还是多条数据消费
 *  topics：需要监听的Topic，可监听多个
 *  topicPartitions：
 */
public class SingleAnnoListenerConsumer {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 单条消费
     * @param record
     */
    @KafkaListener(id = "consumerSingleListener1", topics = "testTopic", containerFactory = "kafkaListenerSingleContainerFactory")
    public void consumerSingleListener1(ConsumerRecord<Integer, String> record) {
        log.info("consumerSingleListener1拉去消息数量" );
        log.info(record.toString());
    }

    /**
     * 单条消费，指定分区1和3
     */
    @KafkaListener(id = "consumerSingleListener2", containerFactory = "kafkaListenerSingleContainerFactory",
        topicPartitions = {
            @TopicPartition(topic = "partitionTopicTest", partitions = {"0", "2"})
        })
    public void consumerSingleListener2(ConsumerRecord<Integer, String> record) {
        log.info("consumerSingleListener2拉去消息数量:" + record.toString());
    }

    /**
     * 单条消费，指定分区2和4
     */
    @KafkaListener(id = "consumerSingleListener3", containerFactory = "kafkaListenerSingleContainerFactory",
            topicPartitions = {
                    @TopicPartition(topic = "partitionTopicTest", partitions = {"1", "3"})
            })
    public void consumerSingleListener3(ConsumerRecord<Integer, String> record) {
        log.info("consumerSingleListener3拉去消息数量" + record.toString());
    }



}
