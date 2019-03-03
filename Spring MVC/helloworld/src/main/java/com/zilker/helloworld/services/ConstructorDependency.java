package com.zilker.helloworld.services;

import org.springframework.stereotype.Service;

@Service("helloWorldServiceOne")
public class ConstructorDependency {
	
	private String name;
	private int age;
	
	public ConstructorDependency(int age,String name) {
		this.age=age;
		this.name=name;
	}
	public String sayHello() {
		return  name + " is "+ age+ " yrs old" ;
	}
	public void initIt() {
		  System.out.println("Init method after properties are set : " + name);
	}
	
    public void cleanUp() {
		  System.out.println("Spring Container is destroyed!");
	}

}
