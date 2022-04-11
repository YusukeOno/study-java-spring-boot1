package com.example.demo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class SpringIocAopApplication implements ApplicationRunner {

	private final ApplicationContext applicationContext;

	public static void main(String[] args) {
		SpringApplication.run(SpringIocAopApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		var allBeanNames = applicationContext.getBeanDefinitionNames();
		for (var beanName : allBeanNames) {
			log.info("Bean名:{}", beanName);
		}

	}
}
