package com.my.rocketmq.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Gzy
 * @version 1.0
 * @Description 订单步骤类 （顺序：创建-支付-完成）
 * @date create on 2023/4/10
 */
public class OrderStep implements Serializable {
    private Long id;
    private String step;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    @Override
    public String toString() {
        return "OrderStep{" +
                "id=" + id +
                ", step='" + step + '\'' +
                '}';
    }

    /**
     * 创建测试实例集合数据使用
     * @return
     */
    public static List<OrderStep> buildOrders(){
        List<OrderStep> list = new ArrayList<>();
        OrderStep orderStep1 = new OrderStep();
        orderStep1.setId(1L);
        orderStep1.setStep("创建");
        list.add(orderStep1);

        OrderStep orderStep2 = new OrderStep();
        orderStep2.setId(2L);
        orderStep2.setStep("创建");
        list.add(orderStep2);

        OrderStep orderStep3 = new OrderStep();
        orderStep3.setId(1L);
        orderStep3.setStep("支付");
        list.add(orderStep3);

        OrderStep orderStep4 = new OrderStep();
        orderStep4.setId(1L);
        orderStep4.setStep("完成");
        list.add(orderStep4);

        OrderStep orderStep5 = new OrderStep();
        orderStep5.setId(2L);
        orderStep5.setStep("支付");
        list.add(orderStep5);

        OrderStep orderStep6 = new OrderStep();
        orderStep6.setId(2L);
        orderStep6.setStep("完成");
        list.add(orderStep6);

        return list;
    }

}
