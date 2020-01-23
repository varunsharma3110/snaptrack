package com.snapdeal.shiptrack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.snapdeal.web.controller")
public class ShipTrackApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShipTrackApplication.class, args);
	}

}
