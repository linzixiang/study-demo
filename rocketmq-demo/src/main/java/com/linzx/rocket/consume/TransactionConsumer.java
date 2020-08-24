package com.linzx.rocket.consume;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.nio.charset.Charset;
import java.util.List;

public class TransactionConsumer {

    public static void main(String[] args) throws Exception {
        // Instantiate with specified consumer group name.
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("transactionTopicConsumer");
        // 从哪里开始消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        // 指定NameServer
        consumer.setNamesrvAddr("192.168.1.215:9876");

        // 订阅topic响应的tag
        consumer.subscribe("TransactionTopic", "*");
        // Register callback to execute on arrival of messages fetched from brokers.
        consumer.registerMessageListener(new MessageListenerConcurrently() {

            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                            ConsumeConcurrentlyContext context) {
                for (MessageExt messageExt : msgs) {
                    System.out.printf("keys: %s,body:%s\n", messageExt.getKeys(), new String(messageExt.getBody(), Charset.forName("utf-8")));
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        // 启动
        consumer.start();

        System.out.printf("Consumer Started.%n");
    }

}
