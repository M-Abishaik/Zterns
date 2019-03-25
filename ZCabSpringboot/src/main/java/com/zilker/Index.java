package com.zilker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({ "com.zilker.bean", "com.zilker.controller", "com.zilker.delegate", "com.zilker.dao", "com.zilker.util", "com.zilker.customexception"})
public class Index{
	
	public static void main(String[] args) {
		SpringApplication.run(Index.class, args);
	}
	
}