package com.example.spark;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

@SpringBootApplication
public class SparkApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(SparkApplication.class, args);
		String[] beanNames = applicationContext.getBeanDefinitionNames();
		Arrays.sort(beanNames);
		for (String beanName : beanNames)
			System.out.println(beanName);
	}
}
