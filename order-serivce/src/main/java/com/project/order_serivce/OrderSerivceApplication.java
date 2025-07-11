package com.project.order_serivce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class OrderSerivceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderSerivceApplication.class, args);
	}

}
