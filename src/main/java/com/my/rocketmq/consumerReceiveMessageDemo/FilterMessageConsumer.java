package com.my.rocketmq.consumerReceiveMessageDemo;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MessageSelector;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @author Gzy
 * @version 1.0
 * @Description 过滤消息 消费者
 * @date create on 2023/4/13
 */
public class FilterMessageConsumer {

    public static void main(String[] args) throws Exception{
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("FilterMessageConsumerGroup");
        consumer.setNamesrvAddr("192.168.50.149:9876");
//        consumer.subscribe("FilterMessageTopic"," tag1 || tag2");//tag过滤 * 代表查询所有tag
        consumer.subscribe("FilterMessageTopic",MessageSelector.bySql("name = 1"));//sql92语法标准
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                for (MessageExt m:list ) {
                    System.out.println("消费结果:" + new String(m.getBody()));
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
    }

}
