package com.linzx.rocket.produce;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 事务消息
 */
public class TransactionProducer {

    public static void main(String[] args) throws Exception {
        TransactionMQProducer producer = new TransactionMQProducer("tx_producer");
        producer.setNamesrvAddr("192.168.1.215:9876");
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        producer.setExecutorService(executorService);
        producer.setTransactionListener(new TransactionListenerLocal());
        producer.start();
        for (int i = 0; i < 20; i++) {
            String orderId = UUID.randomUUID().toString();
            String msgBody = "{'operation': 'doOrder', 'orderId': " + orderId + "}";
            Message message = new Message("TransactionTopic", "TAGA", orderId, msgBody.getBytes(RemotingHelper.DEFAULT_CHARSET));
            producer.sendMessageInTransaction(message, orderId + "&" + i);
            Thread.sleep(1000);
        }
    }

    /**
     * 本地事务监听
     */
    public static class TransactionListenerLocal implements TransactionListener {

        private Map<String, Integer> dbResult = new ConcurrentHashMap<>();

        /**
         * 执行本地事务
         */
        @Override
        public LocalTransactionState executeLocalTransaction(Message msg, Object obj) {
            System.out.println("开始执行本地事务：" + obj.toString());
            String orderId = obj.toString();
            // 模拟数据库保存成功/失败
            int result = Math.abs(Objects.hash(orderId)) % 2;
            dbResult.put(orderId, result);
            if (Math.random() < 0.5) {
                System.err.println("提交事务失败：" + orderId);
                // 事务提交失败
                return LocalTransactionState.UNKNOW;
            }
            if (result == 0) {
                // 本地事务成功
                System.out.println("事务成功：" + orderId);
                return LocalTransactionState.COMMIT_MESSAGE;
            } else if (result == 1) {
                // 本地事务回滚
                System.out.println("事务回滚：" + orderId);
                return LocalTransactionState.ROLLBACK_MESSAGE;
            }
            return LocalTransactionState.UNKNOW;
        }

        /**
         * 事务状态检查回调（提供给broker使用）
         */
        @Override
        public LocalTransactionState checkLocalTransaction(MessageExt msg) {
            String orderId = msg.getKeys();
            System.out.println("执行事务回调检查：" + orderId);
            Integer result = dbResult.get(orderId);
            if (result == 0) {
                // 本地事务成功
                System.out.println("事务成功：" + orderId);
                return LocalTransactionState.COMMIT_MESSAGE;
            } else {
                // 本地事务回滚
                System.out.println("事务回滚：" + orderId);
                return LocalTransactionState.ROLLBACK_MESSAGE;
            }
        }
    }

}
