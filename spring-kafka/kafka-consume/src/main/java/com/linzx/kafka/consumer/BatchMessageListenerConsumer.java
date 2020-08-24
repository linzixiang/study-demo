package com.linzx.kafka.consumer;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.BatchAcknowledgingMessageListener;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.support.Acknowledgment;

import java.util.List;

public class BatchMessageListenerConsumer {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 批量消费
     * @return
     */
    @Bean
    public KafkaMessageListenerContainer kafkaMessageBatchListenerContainer(ConsumerFactory consumerFactory) {
        ContainerProperties properties = new ContainerProperties("testTopic");
        properties.setGroupId("testTopic-group");
        properties.setMessageListener(new BatchMessageListener());
        return new KafkaMessageListenerContainer(consumerFactory, properties);
    }

    public class BatchMessageListener implements BatchAcknowledgingMessageListener<Integer, String> {

        @Override
        public void onMessage(List<ConsumerRecord<Integer, String>> data, Acknowledgment acknowledgment) {
            log.info("BatchMessageListener#onMessage拉取消息数量：" + data.size());
            for(ConsumerRecord consumerRecord : data) {
                log.info(consumerRecord.toString());
            }
        }
    }

}
