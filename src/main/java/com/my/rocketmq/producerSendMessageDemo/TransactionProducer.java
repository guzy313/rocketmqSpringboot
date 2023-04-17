package com.my.rocketmq.producerSendMessageDemo;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * @author Gzy
 * @version 1.0
 * @Description 事务消息生产者
 * @date create on 2023/4/14
 */
public class TransactionProducer {

    public static void main(String[] args) throws Exception{
        //创建事务监听器
        TransactionListener listener = new TransactionListener() {
            //执行事务
            @Override
            public LocalTransactionState executeLocalTransaction(Message message, Object o) {
                //通过tag区别处理事务消息
                if("test1".equals(message.getTags())){
                    //回滚
                    System.out.println("事务回滚:" + new String(message.getBody()));
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                }else if("test2".equals(message.getTags())){
                    //中间状态
                    System.out.println("事务中间状态:" + new String(message.getBody()));
                    return LocalTransactionState.UNKNOW;
                }else{
                    //提交
                    System.out.println("事务提交:" + new String(message.getBody()));
                    return LocalTransactionState.COMMIT_MESSAGE;
                }
            }
            //回查事务状态（事务消息处于中间状态[不提交也不回滚时候]broker对Producer进行回查）
            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
                System.out.println("回查事务,最终提交.消息体:" + new String(messageExt.getBody()));
                return LocalTransactionState.COMMIT_MESSAGE;
            }
        };
        //创建(事务消息)生产者对象
        TransactionMQProducer producer = new TransactionMQProducer("TransactionMessageProducerGroup");
        producer.setNamesrvAddr("192.168.50.149:9876");
        //给消息生产者添加事务监听器
        producer.setTransactionListener(listener);
        producer.start();
        for (int i = 0; i < 3; i++) {
            Message message = new Message("TransactionMessageTopic","test" + i,("事务消息测试" + i).getBytes());
            //发送事务消息[参数1:消息对象,参数2:指定其中一条消息进行事务控制,传null则控制所有的消息]
            producer.sendMessageInTransaction(message,null);
        }
        //此处是为了测试unknown状态的消息,因为broker回查需要等待一定时间,所以生产者不能马上关闭，否则回查方法会无法执行
        System.out.println("1分钟之后生产者关闭");
        TimeUnit.SECONDS.sleep(60);
        producer.shutdown();

    }

}
