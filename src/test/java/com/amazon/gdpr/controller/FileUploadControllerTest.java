package com.amazon.gdpr.controller;

import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.amazon.gdpr.processor.AnonymizationFileProcessor;
import com.amazon.gdpr.processor.GdprDataProcessor;
import com.amazon.gdpr.util.GdprException;
import com.amazon.gdpr.view.AnonymizationInputView;

@RunWith(MockitoJUnitRunner.class)
public class FileUploadControllerTest {

	int runId = 0;

	@InjectMocks
	private FileUploadController fileUploadController;
	
	@Mock
    AnonymizationFileProcessor anonymizationFileProcessor;
	
	@Mock 
	GdprDataProcessor gdprDataProcessor;
	
	private MockMvc mockMvc;	
	private MockMvc mock;

	List<AnonymizationInputView> lstAnonymizationInputView = null;
	MockMultipartFile file = null;
	@Before
	public void setup() throws IOException {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(fileUploadController).build();
		
		this.mock = MockMvcBuilders.standaloneSetup(fileUploadController)
                .setViewResolvers(viewResolver()).build();	
		lstAnonymizationInputView = new ArrayList<AnonymizationInputView>();
		file = new MockMultipartFile("file", "HerokuAnonymization-V0.1.xlsx", "application/vnd.ms-excel", 
	    		new ClassPathResource("HerokuAnonymization-V0.1.xlsx").getInputStream());
	}
	
	@Test
	public @ResponseBody void gdprInputFormTestScenario() throws Exception {
		mock.perform(MockMvcRequestBuilders.get("/fileUpload"));
	}
	
	@Test
	public void uploadMultipartFileTestScenario1() throws Exception {
		when(anonymizationFileProcessor.parseAnonymizationFile(file)).thenReturn(lstAnonymizationInputView);
		
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/processFile").file(file).contentType(MediaType.MULTIPART_FORM_DATA_VALUE));
		
	}
	
	@Test
	public void uploadMultipartFileTestScenario2() throws Exception {
		when(anonymizationFileProcessor.parseAnonymizationFile(file)).thenReturn(lstAnonymizationInputView);
		mock.perform(MockMvcRequestBuilders.fileUpload("/processFile").file(file).contentType(MediaType.MULTIPART_FORM_DATA_VALUE));
		//mockMvc.perform(MockMvcRequestBuilders.fileUpload("/processFile").file(file).contentType(MediaType.MULTIPART_FORM_DATA_VALUE));
	}
	
	@Test
	public void uploadMultipartFileTestScenario3() throws Exception {
		when(anonymizationFileProcessor.parseAnonymizationFile(file)).thenReturn(lstAnonymizationInputView);
		fileUploadController.uploadMultipartFile(file, new RedirectAttributesModelMap());
	}
	
	@Test
	public void uploadMultipartFileTestScenario4() throws Exception {
		lstAnonymizationInputView.add(new AnonymizationInputView());
		when(anonymizationFileProcessor.parseAnonymizationFile(file)).thenReturn(lstAnonymizationInputView);
		fileUploadController.uploadMultipartFile(file, new RedirectAttributesModelMap());
	}
	
	@Test
	public void uploadMultipartFileTestScenario5() throws Exception {
		MockMultipartFile file = new MockMultipartFile("file", "file.txt", "text/plain", 
				"".getBytes(StandardCharsets.UTF_8));
		lstAnonymizationInputView.add(new AnonymizationInputView());
		when(anonymizationFileProcessor.parseAnonymizationFile(file)).thenReturn(lstAnonymizationInputView);
		fileUploadController.uploadMultipartFile(file, new RedirectAttributesModelMap());
	}

	@Test
	public void uploadMultipartFileTestScenario6() throws Exception {
		lstAnonymizationInputView.add(new AnonymizationInputView());
		when(anonymizationFileProcessor.parseAnonymizationFile(file)).thenThrow(GdprException.class);
		fileUploadController.uploadMultipartFile(file, new RedirectAttributesModelMap());
	}
	
	public static ViewResolver viewResolver() {
	    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
	    viewResolver.setPrefix("classpath:templates/");
	    viewResolver.setSuffix(".html");
	    return viewResolver;
	}	
}