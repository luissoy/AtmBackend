package com.luissoy.atmbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class AtmBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(AtmBackendApplication.class, args);
	}

}
