package com.my.rocketmq.producerSendMessageDemo;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author Gzy
 * @version 1.0
 * @Description 延迟消息生产者
 * @date create on 2023/4/11
 */
public class DelayProducer {

    public static void main(String[] args) throws Exception{
        DefaultMQProducer producer = new DefaultMQProducer("DelayMessageProducerGroup");
        producer.setNamesrvAddr("192.168.50.149:9876");
        producer.start();
        for (int i = 0; i < 100; i++) {
            Message message = new Message("DelayMessageTopic","test1",("delayMessage" + i).getBytes() );
            message.setDelayTimeLevel(5);//设置消息 延迟30秒消费者可拉取
            SendResult sendResult = producer.send(message);
            System.out.println("延迟消息发送结果:" + sendResult + "延迟消息发送时间:" + new Date().toString());
            TimeUnit.SECONDS.sleep(1);
        }
        producer.shutdown();

    }

}
