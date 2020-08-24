package com.linzx.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;

/**
 * MessageListener消费消息
 */
public class SingleMessageListenerConsume {

    /**
     * 单条消费
     * @return
     */
    @Bean
    public KafkaMessageListenerContainer kafkaMessageSingleListenerContainer(ConsumerFactory consumerFactory) {
        ContainerProperties properties = new ContainerProperties("testTopic");
        properties.setMessageListener(new SingleMessageListener());
        return new KafkaMessageListenerContainer(consumerFactory, properties);
    }

    public class SingleMessageListener implements MessageListener<Integer, String> {

        private Logger log = LoggerFactory.getLogger(this.getClass());

        @Override
        public void onMessage(ConsumerRecord<Integer, String> data) {
            log.info("SingleMessageListener#onMessage拉取消息");
            log.info("------------" + data.toString() + "--------------");
        }
    }

}
