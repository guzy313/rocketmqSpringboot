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
 * @Description
 * @date create on 2023/4/10
 */
public class Consumer {

    public static void main(String[] args) throws Exception{
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("TestPushConsumerGroup");
        //指定NameServer
        consumer.setNamesrvAddr("192.168.50.148:9876");
        //设置订阅的消息Topic和Tag
        consumer.subscribe("HelloMQ","oneWayMessageTag");
        //设置回调函数
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            //接收消息内容的方法
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                for (MessageExt m:list) {
                    System.out.println(new String(m.getBody()));
                }

                //返回消费成功
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        //启动消费者
        consumer.start();

    }


}
