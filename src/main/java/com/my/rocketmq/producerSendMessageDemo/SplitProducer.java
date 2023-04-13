package com.my.rocketmq.producerSendMessageDemo;


import com.my.rocketmq.util.MessageListSplitter;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gzy
 * @version 1.0
 * @Description 分割消息发送（超过4M的消息进行分割发送）
 * @date create on 2023/4/11
 */
public class SplitProducer {

    public static void main(String[] args) throws Exception{
        DefaultMQProducer producer = new DefaultMQProducer("SplitMessageProducerGroup");
        producer.setNamesrvAddr("192.168.50.148:9876");
        //设置最大消息大小，默认4M (这里指的是单次发送消息的大小是4M,单个message的消息体body大小不能超过16K)
        producer.setMaxMessageSize(1024 * 1024 * 4);
        producer.start();
        List<Message> messageList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            byte[] messageBody = (("hi" + i).getBytes());
            if(messageBody.length > 1024 * 1){
                System.out.println("iiii");
            }
            Message message = new Message("SpiltMessageTopic","test1",messageBody);
            messageList.add(message);
        }


        MessageListSplitter listSplitter = new MessageListSplitter(messageList);
        while (listSplitter.hasNext()) {
            //此处按4M已经分割好
            List<Message> next = listSplitter.next();
            //批量发送分割后的消息集合
            SendResult sendResult = producer.send(next);
            //打印分割消息发送结果
            System.out.println("分割消息发送结果:" + sendResult);
        }
        producer.shutdown();
        
    }

}
