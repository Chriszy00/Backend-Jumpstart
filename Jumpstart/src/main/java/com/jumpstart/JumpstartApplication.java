package com.jumpstart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.jumpstart")
public class JumpstartApplication {

	public static void main(String[] args) {
		SpringApplication.run(JumpstartApplication.class, args);
	}

}
