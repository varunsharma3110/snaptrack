package com.snapdeal.snaptrack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableAutoConfiguration
@EnableJpaRepositories("com.snapdeal.repository")
@EntityScan("com.snapdeal.entity")
@ComponentScan(basePackages = "com.snapdeal.web.controller, com.snapdeal.web.services")
public class SnapTrackApplication {

	public static void main(String[] args) {
		SpringApplication.run(SnapTrackApplication.class, args);
	}






}
