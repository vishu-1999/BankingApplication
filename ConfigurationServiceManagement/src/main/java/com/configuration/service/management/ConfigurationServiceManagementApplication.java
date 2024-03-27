package com.configuration.service.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigurationServiceManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigurationServiceManagementApplication.class, args);
	}

}
