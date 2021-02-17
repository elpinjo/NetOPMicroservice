package com.alliander.c8y.microservice.netop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cumulocity.sdk.client.Platform;

@RestController
public class HelloWorldService {
	
	@GetMapping("greet")
	public String greeting(@RequestParam String aName) {
		
		return "Hello " + aName + " to the microservice world!";
	}
	
	@Autowired
	private Platform platform;

}
