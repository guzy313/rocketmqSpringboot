package com.my.rocketmq.consumerReceiveMessageDemo;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.Date;
import java.util.List;

/**
 * @author Gzy
 * @version 1.0
 * @Description
 * @date create on 2023/4/11
 */
public class DelayConsumer {

    public static void main(String[] args) throws Exception{
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("DelayMessageConsumerGroup");
        consumer.setNamesrvAddr("192.168.50.149:9876");
        //订阅Topic和Tag
        consumer.subscribe("DelayMessageTopic","test1");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                for (MessageExt m:list) {
                    System.out.println("消费延迟消息内容:" +new String(m.getBody()) + "延迟时间:" + String.valueOf(System.currentTimeMillis() - m.getStoreTimestamp()) );
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
    }

}
