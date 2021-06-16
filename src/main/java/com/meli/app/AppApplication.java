package com.meli.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Configuration and application start, spring cache is used: to store repetitive data and feignClient to consume rest services
 * @author James
 * @version: 2021/06/15
 */
@SpringBootApplication
@EnableFeignClients
@EnableCaching
public class AppApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}

}
