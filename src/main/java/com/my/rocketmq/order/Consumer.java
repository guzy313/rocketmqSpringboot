package com.my.rocketmq.order;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
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
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("OrderGroup");
        consumer.setNamesrvAddr("192.168.50.148:9876");
        consumer.subscribe("OrderStepTestTopic","order");
        //注册消息监听，传入顺序消费接口匿名内部类 MessageListenerOrderly[关键点,传入这个回调函数才能保证一个线程操作进行顺序消费]
        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {
                //遍历取得的消息集合
                for (MessageExt m:list) {
                    System.out.println("线程名称:" + Thread.currentThread().getName() + "消费消息" +new String(m.getBody()));
                }
                //返回消费成功状态
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        //启动消费者监听
        consumer.start();
    }

}
