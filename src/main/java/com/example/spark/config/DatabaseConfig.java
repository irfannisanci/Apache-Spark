package com.example.spark.config;

import org.apache.spark.sql.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig {

    private final SparkSession sparkSession;

    public DatabaseConfig(SparkSession sparkSession) {
        this.sparkSession = sparkSession;
    }

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String user;
    @Value("${spring.datasource.password}")
    private String password;

    public Dataset<Row> dbConnection( String dbtable) {

        Dataset<Row> datadb = sparkSession.read()
                .option("url", url)
                .option("dbtable", dbtable)
                .option("user", user)
                .option("password", password)
                .option("serverTimeZone", "EST")
                .format("jdbc")
                .load();
        return datadb;
    }
}
