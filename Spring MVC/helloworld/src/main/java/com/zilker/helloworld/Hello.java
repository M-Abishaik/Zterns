package com.zilker.helloworld;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

import com.zilker.helloworld.services.ConstructorDependency;
import com.zilker.helloworld.services.HelloWorldService;

public class Hello {
	
	public static void main(String[] args) {
 
		// loading the definitions from the given XML file
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
 
		HelloWorldService service = (HelloWorldService) context
				.getBean("helloWorldService");
		String message = service.sayHello();
		System.out.println(message);
 
		//set a new name
		service.setName("Spring");
		message = service.sayHello();
		System.out.println(message);
		
		((ClassPathXmlApplicationContext) context).close();
		
		XmlBeanFactory factory = new XmlBeanFactory (new ClassPathResource("applicationContext.xml")); 
	    HelloWorldService obj = (HelloWorldService) factory.getBean("helloWorldService");    
	    System.out.println(obj.sayHello());
		
		
		 factory = new XmlBeanFactory (new ClassPathResource("applicationContext.xml")); 
	     ConstructorDependency constructorDependency = (ConstructorDependency) factory.getBean("helloWorldServiceOne");    
	      message = constructorDependency.sayHello();
 	     System.out.println(message);

		
	}

}
