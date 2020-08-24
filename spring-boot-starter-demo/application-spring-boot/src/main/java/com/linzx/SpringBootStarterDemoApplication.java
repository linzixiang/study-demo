package com.linzx;

import com.linzx.redission.PersonService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.PropertySource;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@SpringBootApplication
public class SpringBootStarterDemoApplication {

	/**
	 * 启动方式一
	 * @param args
	 */
	public static void main1(String[] args) {
		SpringApplication.run(SpringBootStarterDemoApplication.class, args);
	}

	/**
	 * 启动方式二
	 * @param args
	 */
	public static void main2(String[] args) {
		SpringApplication springApplication = new SpringApplication();
		springApplication.setMainApplicationClass(SpringBootStarterDemoApplication.class);
		springApplication.run(args);
	}

	/**
	 * 启动方式三
	 * @param args
	 */
	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder()
				.sources(SpringBootStarterDemoApplication.class)
				.run(args);
		PersonService personService = (PersonService) applicationContext.getBean("personService");
		personService.sayHello();
	}

}
