package com.my.rocketmq.producerSendMessageDemo;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gzy
 * @version 1.0
 * @Description 批量发送消息 生产者
 * @date create on 2023/4/11
 */
public class BatchProducer {

    public static void main(String[] args) throws Exception{
        DefaultMQProducer producer = new DefaultMQProducer("BatchMessageProducerGroup");
        producer.setNamesrvAddr("192.168.50.148:9876");
        producer.start();
        //创建消息集合,并且加入消息
        List<Message> messageList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            messageList.add(new Message("BatchMessageTopic","test1",("batch" + i).getBytes()));
        }
        //将消息集合批量发送
        SendResult sendResult = producer.send(messageList);
        System.out.println("批量发送消息结果:" + sendResult);
        producer.shutdown();


    }

}
