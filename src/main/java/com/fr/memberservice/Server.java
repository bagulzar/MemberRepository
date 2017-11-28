package com.fr.memberservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.fr.memberservice")
public class Server {

	public static void main(String[] args) {

		
		/*
		 * 
		 */
		SpringApplication.run(Server.class, args);
	}

}