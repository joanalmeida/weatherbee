package com.redbee.weatherbee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WeatherbeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherbeeApplication.class, args);
	}
}
