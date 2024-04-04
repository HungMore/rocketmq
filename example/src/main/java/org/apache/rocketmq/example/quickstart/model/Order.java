package org.apache.rocketmq.example.quickstart.model;

import lombok.Data;

/**
 * @author mo
 * @Description 订单实体类
 * @createTime 2024年04月03日 17:48
 */
@Data
public class Order {

    /**
     * 数据库自增id
     */
    private Long id;

    /**
     * 订单编号，唯一
     */
    private String orderNo;

    /**
     * 订单总价
     */
    private Double totalPrice;

    /**
     * 购买者姓名
     */
    private String buyer;

    /**
     * 销售姓名
     */
    private String seller;

    /**
     * 订单状态，0未支付，1已支付，2未发货，3已发货，4已签收
     */
    private Integer status;

}
