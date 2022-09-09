package com.example.spark.domain;

import javax.persistence.*;
import java.time.Instant;
public class Order {

    private Long id;

    private Instant orderDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Instant orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public String toString() {
        return "Order {" +
                "id:" + "'" + id + "'"  +
                ", orderDate:" + "'" + orderDate + "'" +
                '}';
    }
}
