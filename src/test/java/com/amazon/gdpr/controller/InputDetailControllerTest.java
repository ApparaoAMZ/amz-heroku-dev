package com.amazon.gdpr.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class InputDetailControllerTest {

	@InjectMocks
	private InputDetailController inputDetailController;

	private MockMvc mockMvc;	
	private MockMvc mock;

	@Before
	public void setup() throws IOException {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(inputDetailController).build();
		
		this.mock = MockMvcBuilders.standaloneSetup(inputDetailController)
                .setViewResolvers(FileUploadControllerTest.viewResolver()).build();	
	}

	@Test	
	public void inputDetailsTest() throws Exception {
		mock.perform(MockMvcRequestBuilders.get("/inputDetails")).andExpect(status().isOk())
		.andExpect(view().name("inputDetails"));
		
	}

}
