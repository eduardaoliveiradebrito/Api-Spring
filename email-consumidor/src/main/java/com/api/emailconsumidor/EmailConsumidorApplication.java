package com.api.emailconsumidor;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@EnableRabbit
@SpringBootApplication
public class EmailConsumidorApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(EmailConsumidorApplication.class, args);
	}

}
