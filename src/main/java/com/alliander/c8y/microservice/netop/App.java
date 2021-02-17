package com.alliander.c8y.microservice.netop;

import org.springframework.boot.SpringApplication;

import com.cumulocity.microservice.autoconfigure.MicroserviceApplication;

@MicroserviceApplication
public class App {
	
	public static void main(String[] argv) {
		SpringApplication.run(App.class, argv);
	}

}
