package com.amazon.gdpr.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(MockitoJUnitRunner.class)
public class SalesforceDetailControllerTest {

	@InjectMocks
	private SalesforceDetailController salesforceDetailController;

	private MockMvc mockMvc;	
	private MockMvc mock;

	@Before
	public void setup() throws IOException {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(salesforceDetailController).build();
		
		this.mock = MockMvcBuilders.standaloneSetup(salesforceDetailController)
                .setViewResolvers(FileUploadControllerTest.viewResolver()).build();	
	}

	@Test	
	public void salesforceDetailsTest() throws Exception {
		mock.perform(MockMvcRequestBuilders.get("/salesforceDetails")).andExpect(status().isOk())
		.andExpect(view().name("salesforceDetails"));
		
	}

}
