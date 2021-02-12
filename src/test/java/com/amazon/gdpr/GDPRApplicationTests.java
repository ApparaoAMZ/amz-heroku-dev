package com.amazon.gdpr;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.builder.SpringApplicationBuilder;
@RunWith(MockitoJUnitRunner.class)
public class GDPRApplicationTests {
	@InjectMocks
	GdprApplication GDPRApplication;
	@Mock
	SpringApplicationBuilder application;
	@Test
	public void SpringApplicationBuilderTest() {
		
		GDPRApplication.configure(application);
	}
	
}
