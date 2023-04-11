package com.my.rocketmq.producerSendMessageDemo;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

/**
 * @author Gzy
 * @version 1.0
 * @Description 单向发送消息-生产者 无返回
 * @date create on 2023/4/8
 */
public class OneWayProducer {

    public static void main(String[] args) throws Exception{
        DefaultMQProducer producer = new DefaultMQProducer("TestOneWayProducerGroup");
        producer.setNamesrvAddr("192.168.50.148:9876");
        producer.start();
        for (int i = 0; i < 100; i++) {
            Message message = new Message("HelloMQ","oneWayMessageTag",("hello" + i).getBytes());
            producer.sendOneway(message);
            System.out.println("单向发送消息成功" + i);
        }
        producer.shutdown();

    }

}
