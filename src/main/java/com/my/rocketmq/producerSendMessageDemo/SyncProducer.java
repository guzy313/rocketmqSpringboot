package com.my.rocketmq.producerSendMessageDemo;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;

import java.util.concurrent.TimeUnit;

/**
 * @author Gzy
 * @version 1.0
 * @Description 发送同步消息
 * @date create on 2023/4/7
 */
public class SyncProducer {

    public static void main(String[] args) throws Exception{
        //创建生产者对象，并在构造器中传入生产者所属的生产者群组
        DefaultMQProducer producer = new DefaultMQProducer("TestSyncProducerGroup1");
        //指向中间件NameServer的IP + PORT
        producer.setNamesrvAddr("192.168.50.148:9876");
        //发送同步消息失败重试次数
        producer.setRetryTimesWhenSendFailed(2);
        //启动生产者
        producer.start();
        for (int i = 0; i < 100; i++) {
            Message msg = new Message("HelloMQ","syncMessageTag",("hello"+i).getBytes());
            SendResult send = producer.send(msg);
            SendStatus sendStatus = send.getSendStatus();
            System.out.println(sendStatus);
            //延迟1秒发送
            TimeUnit.SECONDS.sleep(1);
        }
        //关闭生产者
        producer.shutdown();


    }


}
