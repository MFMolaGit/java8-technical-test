package com.ciklum.configuration;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication(scanBasePackages = {"com.ciklum"})
public class WebApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(ImplementationConfig.class).run(args);
	}

}