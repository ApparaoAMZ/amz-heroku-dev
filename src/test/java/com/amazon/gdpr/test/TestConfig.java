package com.amazon.gdpr.test;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.amazon.gdpr.configuration.DatabaseConfig;

@Configuration
@Import(DatabaseConfig.class)
@EnableAutoConfiguration
public class TestConfig {

	static {
		System.setProperty("spring.config.location", "classpath:application-test.properties");
	}
	
}