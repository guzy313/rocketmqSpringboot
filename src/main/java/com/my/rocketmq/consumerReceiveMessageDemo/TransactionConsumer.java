package com.my.rocketmq.consumerReceiveMessageDemo;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @author Gzy
 * @version 1.0
 * @Description 事务消息消费者
 * @date create on 2023/4/17
 */
public class TransactionConsumer {

    public static void main(String[] args) throws Exception{
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("TransactionMessageConsumerGroup");
        consumer.setNamesrvAddr("192.168.50.149:9876");
        consumer.subscribe("TransactionMessageTopic","*");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                for (MessageExt m:list) {
                    System.out.println("事务消息消费结果:" + new String(m.getBody()));
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        System.out.println("事务消息消费者启动");
        consumer.start();
    }

}
