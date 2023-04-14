package com.my.rocketmq.producerSendMessageDemo;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gzy
 * @version 1.0
 * @Description 自定义消息过滤 生产者
 * @date create on 2023/4/13
 */
public class FilterMessageProducer {

    public static void main(String[] args) throws Exception{
        DefaultMQProducer producer = new DefaultMQProducer("FilterMessageProducerGroup");
        producer.setNamesrvAddr("192.168.50.149:9876");
        producer.start();
        List<Message> messageList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Message message = new Message("FilterMessageTopic","test1",(i + "测试自定义过滤" + Math.random()).getBytes());
            //此处给消息添加额外属性用来 让consumer 进行sql92过滤 消费
            message.putUserProperty("name",String.valueOf(i));
            messageList.add(message);
        }

        SendResult sendResult = producer.send(messageList);
        System.out.println("发送过滤消息结果:" + sendResult);

        producer.shutdown();

    }

}
