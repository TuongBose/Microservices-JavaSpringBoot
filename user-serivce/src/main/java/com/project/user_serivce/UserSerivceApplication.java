package com.project.user_serivce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class UserSerivceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserSerivceApplication.class, args);
	}

}
