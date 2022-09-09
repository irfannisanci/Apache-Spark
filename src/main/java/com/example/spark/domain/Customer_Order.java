package com.example.spark.domain;

import javax.persistence.*;

public class Customer_Order {

    private Long customer_id;
    private Long order_id;
    private String shop_type;

    public Long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Long customer_id) {
        this.customer_id = customer_id;
    }

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public String getShop_type() {
        return shop_type;
    }

    public void setShop_type(String shop_type) {
        this.shop_type = shop_type;
    }

    @Override
    public String toString() {
        return "Customer_Order {" +
                "customer_id:" + "'" + customer_id + "'" +
                ", order_id:" + "'" + order_id + "'" +
                ", shop_type:" + "'" + shop_type + "'" +
                '}';
    }
}
