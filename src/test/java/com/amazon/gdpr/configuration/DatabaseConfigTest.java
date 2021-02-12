package com.amazon.gdpr.configuration;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DatabaseConfigTest {
	@InjectMocks
	DatabaseConfig DatabaseConfig;
	@Mock 
	DataSource dataSource;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	@Test
	public void dataSourceTest() {
		DatabaseConfig.dataSource();
	}
	
	@Test
	public void jdbcTemplateTest() {
		DatabaseConfig.jdbcTemplate(dataSource);
	}
}
