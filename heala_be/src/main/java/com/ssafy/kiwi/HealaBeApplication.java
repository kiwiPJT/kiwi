package com.ssafy.kiwi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class HealaBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(HealaBeApplication.class, args);
	}

}