package com.zilker.helloworld.services;

import org.springframework.stereotype.Service;

@Service("helloWorldService")
public class HelloWorldService {
	
	private String name;
	 
	public void setName(String name) {
		this.name = name;
	}
 
	public String sayHello() {
		return "Hello! " + name;
	}
	
	public void myInit() {
		System.out.println("Instantiated");
	}
	
	public void myDestruct() {
		System.out.println("Destructed");
	}

}
