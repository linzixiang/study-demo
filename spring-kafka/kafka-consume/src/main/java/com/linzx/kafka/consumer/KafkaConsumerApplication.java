package com.linzx.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.Map;
import java.util.concurrent.CountDownLatch;

@SpringBootApplication
@Import(BatchAnnoListenerConsumer.class)
public class KafkaConsumerApplication {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(KafkaConsumerApplication.class, args);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        countDownLatch.await();
    }

    @Autowired
    private KafkaProperties kafkaProperties;

    @Bean
    public ConsumerFactory<Integer, String> consumerFactory() {
        KafkaProperties.Consumer consumer = kafkaProperties.getConsumer();
        Map<String, Object> props= consumer.buildProperties();
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000");
        /**
         * 多个consumer订阅同一个topic
         *  groupId不同：同时对该topic消息消费
         *  groupId相同：对该topic消息消费负载均衡
         */
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000"); // 自动提交的时间间隔
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 5); // 一次拉取消息数量
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false); // false表示关闭消息自动提交
        return new DefaultKafkaConsumerFactory(props);
    }

    /**
     * 单条消息消费
     * @param consumerFactory
     * @return
     */
    @Bean("kafkaListenerSingleContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<Integer, String> kafkaListenerSingleContainerFactory(ConsumerFactory consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<Integer, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    /**
     * 批量消息消费
     * @param consumerFactory
     * @return
     */
    @Bean("kafkaListenerBatchContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<Integer, String> kafkaListenerBatchContainerFactory(ConsumerFactory consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<Integer, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        //设置并发量，小于或等于Topic的分区数
        factory.setConcurrency(5);
        //设置为批量监听
        factory.setBatchListener(true);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        factory.getContainerProperties().setPollTimeout(3000);
        return factory;
    }

}
