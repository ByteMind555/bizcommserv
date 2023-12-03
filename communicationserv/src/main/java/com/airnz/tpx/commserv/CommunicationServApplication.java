package com.airnz.tpx.commserv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 *
 *
 */
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class CommunicationServApplication {
	public static void main(String[] args) {
		SpringApplication.run(CommunicationServApplication.class, args);
	}

}
