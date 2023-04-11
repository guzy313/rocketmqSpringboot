package com.my.rocketmq.order;

import com.my.rocketmq.pojo.OrderStep;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.List;

/**
 * @author Gzy
 * @version 1.0
 * @Description
 * @date create on 2023/4/10
 */
public class Producer {

    public static void main(String[] args) throws Exception{
        DefaultMQProducer producer = new DefaultMQProducer("OrderGroup");
        producer.setNamesrvAddr("192.168.50.148:9876");
        producer.start();
        List<OrderStep> list = OrderStep.buildOrders();
        int i = 1;
        for (OrderStep o:list) {
            i ++;
            //指定Topic，Tag
            Message message = new Message("OrderStepTestTopic","order","key" + i,o.toString().getBytes());
            SendResult sendResult = producer.send(message, new MessageQueueSelector() {
                /**
                 *
                 * @param list 队列集合
                 * @param message 消息
                 * @param arg 参数
                 * @return
                 */
                @Override
                public MessageQueue select(List<MessageQueue> list, Message message, Object arg) {
                    //强转订单ID为long
                    long orderId = (long) arg;
                    //给订单ID用队列总数取模,使当前订单ID指定一个固定的queue
                    int queueNum = (int) orderId % list.size();
                    //从队列集合中获取指定下标的队列
                    return list.get(queueNum);
                }
            }, o.getId());
            //打印返回
            System.out.println(sendResult);
        }
        producer.shutdown();

    }

}
