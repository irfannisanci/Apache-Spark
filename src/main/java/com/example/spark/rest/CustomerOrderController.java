package com.example.spark.rest;

import com.example.spark.config.DatabaseConfig;
import com.example.spark.domain.Customer;
import com.example.spark.domain.Customer_Order;
import com.example.spark.domain.Order;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/v1")
@Controller
public class CustomerOrderController {
    private final DatabaseConfig databaseConfig;

    public CustomerOrderController(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    @GetMapping("/customerOrder")
    public ResponseEntity<String> getCustomerOrder(@RequestParam("sourceId") String sourceId) {

        //Encoders
        Encoder<Customer_Order> customerOrderEncoder = Encoders.bean(Customer_Order.class);
        Encoder<Order> orderEncoder = Encoders.bean(Order.class);
        Encoder<Customer> customerEncoder = Encoders.bean(Customer.class);

        //Edge Table
        Dataset<Row> customerOrderDataset = databaseConfig.dbConnection("customer_order");
        customerOrderDataset.createOrReplaceTempView("customer_order");
        Dataset<Row> getCustomerOrder = customerOrderDataset.sparkSession().sql("select * from customer_order where customer_id == '"+sourceId+"'");

        //Source Table
        Long customerId = getCustomerOrder.as(customerOrderEncoder).collectAsList().stream().findFirst().get().getCustomer_id();
        Dataset<Row> customDataset = databaseConfig.dbConnection("customer_dataset")
                .withColumnRenamed("shop_type", "shopType")
                .withColumnRenamed("demographic_region", "demographicRegion")
                .withColumnRenamed("age_group", "ageGroup")
                .withColumnRenamed("days_since_last_order", "daysSinceLastOrder");
        customDataset.createOrReplaceTempView("customer_dataset");
        Dataset<Row> getCustomerById = customerOrderDataset.sparkSession().sql("select * from customer_dataset where id == '" + customerId +"'");
        Customer customer = getCustomerById.as(customerEncoder).first();

        List<String> stringArrayList = new ArrayList<>();
        getCustomerOrder.as(customerOrderEncoder).collectAsList().forEach(customer_order -> {

            //Target Table
            Dataset<Row> orderDataset = databaseConfig.dbConnection("order_dataset");
            orderDataset.createOrReplaceTempView("order_dataset");
            Dataset<Row> getOrder = orderDataset.sparkSession().sql("select * from order_dataset where id == '"+customer_order.getOrder_id()+"' ")
                    .withColumnRenamed("order_date", "orderDate");
            Order order = getOrder.as(orderEncoder).first();

            //OpenCypherQuery Format
            String string = "(:"+ customer.toString() + ")-[:" + customer_order.getShop_type() + "]->(:" + order.toString() + ")";
            stringArrayList.add(string);
        });

        String listString = String.join(", ", stringArrayList);
        return ResponseEntity.ok(listString);
    }
}
