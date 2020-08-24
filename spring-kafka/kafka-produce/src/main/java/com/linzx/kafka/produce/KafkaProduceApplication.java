package com.linzx.kafka.produce;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.ProducerListener;

import java.util.Map;

@SpringBootApplication
public class KafkaProduceApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaProduceApplication.class, args);
    }

    @Bean
    @Qualifier("testTopic")
    public KafkaTemplate<Integer, String>  testTopicKafkaTemplate(ProducerFactory producerFactory) {
        KafkaTemplate<Integer, String> kafkaTemplate = new KafkaTemplate(producerFactory);
        kafkaTemplate.setDefaultTopic("testTopic");
        kafkaTemplate.setProducerListener(new ProducerListener() {
            @Override
            public void onSuccess(ProducerRecord producerRecord, RecordMetadata recordMetadata) {
                System.out.println("ProducerListener#onSuccess:" + producerRecord.topic());
            }

            @Override
            public void onError(ProducerRecord producerRecord, Exception exception) {
                System.out.println("ProducerListener#orError:" + producerRecord.topic());
            }
        });
        return kafkaTemplate;
    }

    @Autowired
    private KafkaProperties kafkaProperties;

    @Bean
    public ProducerFactory<Integer, String> producerFactory() {
        KafkaProperties.Producer producer = kafkaProperties.getProducer();
        Map<String, Object> produceProp = producer.buildProperties();
        /** 触发批量发送的间隔时间 **/
        produceProp.put(ProducerConfig.LINGER_MS_CONFIG, 1000);
        /** 指定分区策略，实现Partitioner接口 **/
        produceProp.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, MyPartitioner.class.getName());
        /** 生产端发送消息重试次数 **/
        produceProp.put(ProducerConfig.RETRIES_CONFIG, 3);
        /** 生产端发送消息重试间隔 **/
        produceProp.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, "500");
        return new DefaultKafkaProducerFactory<>(produceProp);
    }

}
