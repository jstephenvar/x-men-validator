package com.meli.challenge.xmenvalidator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class XMenValidatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(XMenValidatorApplication.class, args);
	}

}
