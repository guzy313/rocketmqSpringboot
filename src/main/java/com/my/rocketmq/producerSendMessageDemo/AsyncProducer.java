package com.my.rocketmq.producerSendMessageDemo;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.util.concurrent.TimeUnit;

/**
 * @author Gzy
 * @version 1.0
 * @Description 发送异步消息-生产者
 * @date create on 2023/4/9
 */
public class AsyncProducer {


    public static void main(String[] args) throws Exception{
        DefaultMQProducer producer = new DefaultMQProducer("TestASyncProducerGroup1");
        producer.setNamesrvAddr("192.168.50.148:9876");
        //发送异步消息失败重试次数
        producer.setRetryTimesWhenSendAsyncFailed(2);
        producer.start();
        for (int i = 0; i < 100; i++) {
            Message message = new Message("HelloMQ","asyncMessageTag",("hello" + i).getBytes());

            producer.send(message, new SendCallback() {
                //消息发送-回调类
                @Override
                public void onSuccess(SendResult sendResult) {
                    //发送成功回调方法
                    System.out.println("异步消息发送成功:" + sendResult);
                }

                @Override
                public void onException(Throwable throwable) {
                    //发送异常回调方法
                    System.out.println("异步消息发送失败:" + throwable);
                }
            });
            TimeUnit.SECONDS.sleep(1);
        }
        producer.shutdown();

    }


}
