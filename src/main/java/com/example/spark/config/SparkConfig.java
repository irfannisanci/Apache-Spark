package com.example.spark.config;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.postgresql.Driver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;

@Configuration
@PropertySource("classpath:application.yml")
public class SparkConfig {

    private final Environment env;
    @Value("${spring.application.name}")
    private String appName;

    @Value("${spark.master}")
    private String sparkMaster;

    @Value("${spark.driver.host}")
    private String sparkHost;

    @Value("${spark.driver.port}")
    private String sparkPort;

    @Value("${spark.driver.bind-address}")
    private String sparkBindAddress;

    public SparkConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public SparkConf sparkConf() {

        URL driverPath = Driver.class.getProtectionDomain().getCodeSource().getLocation();

        ClassPathResource resource = new ClassPathResource(driverPath.toString());

        InputStream inputStream = null;
        File postgreDriver = new File("");
        try {
            postgreDriver = File.createTempFile("postgre-driver", ".jar");
            inputStream = resource.getInputStream();

            FileUtils.copyInputStreamToFile(inputStream, postgreDriver);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(inputStream);
        }

        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
        SparkConf sparkConf;

        if (activeProfiles.contains("dev")) {
            sparkConf = new SparkConf()
                    .setAppName(appName)
                    .set("spark.jars", postgreDriver.getPath())
                    .set("spark.driver.host", sparkHost)
                    .set("spark.driver.port", sparkPort)
                    .set("spark.driver.bindAddress", sparkBindAddress)
                    .set("spark.driver.bindPort", sparkPort)
                    .set("spark.blockManager.port", "30806")
                    .setMaster(sparkMaster);
        } else {
            sparkConf = new SparkConf()
                    .setAppName(appName)
                    .set("spark.jars", postgreDriver.getPath())
                    .setMaster(sparkMaster);
        }

        return sparkConf;
       /* SparkConf sparkConf = new SparkConf()
                .setAppName(appName)
                .setMaster("local[*]");

        return sparkConf;*/
    }

    @Bean
    public JavaSparkContext javaSparkContext() {
        return new JavaSparkContext(sparkConf());
    }

    @Bean
    public SparkSession sparkSession() {
        return SparkSession
                .builder()
                .sparkContext(javaSparkContext().sc())
                .appName("Integrating Spring-boot with Apache Spark")
                .getOrCreate();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
