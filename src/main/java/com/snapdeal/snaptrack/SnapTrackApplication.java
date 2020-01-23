package com.snapdeal.snaptrack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.snapdeal.web.controller, com.snapdeal.web.services")
public class SnapTrackApplication {

	public static void main(String[] args) {
		SpringApplication.run(SnapTrackApplication.class, args);
	}

}
