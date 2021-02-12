package com.amazon.gdpr.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import com.amazon.gdpr.processor.AnonymizationFileProcessor;
import com.amazon.gdpr.processor.GdprDataProcessor;
import com.amazon.gdpr.view.AnonymizationInputView;

public class FileUploadControllerTest {

	@Mock
	AnonymizationFileProcessor anonymizationFileProcessor;

	@Mock
	GdprDataProcessor gdprDataProcessor;

	@InjectMocks
	FileUploadController fileUploadController;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

		mockMvc = MockMvcBuilders.standaloneSetup(fileUploadController).build();
	}

	@Test
	 @Ignore
	public void loadAnonymizationFormTest() throws Exception {
		mockMvc.perform(get("/fileUploadT")).andExpect(status().isOk())
		.andExpect(view().name("fileUpload"))
				.andExpect(model().attribute("message", "Please upload Anonymization File only. "));
	}

	@Test
	@Ignore
	public void uploadMultipartFileTest() throws Exception {
		MultipartFile file1 = new MockMultipartFile("HerokuAnonymization-V0.1", "HerokuAnonymization-V0.1.xlsx",
				"application/vnd.ms-excel", new ClassPathResource("HerokuAnonymization-V0.1.xlsx").getInputStream());
		Mockito.when(anonymizationFileProcessor.parseAnonymizationFile(file1))
				.thenReturn(anonymizationProcessorInsertData());
		int insertImpactFieldCount = 2;
		int insertAnonymizationDetailCount = 2;
		Mockito.when(gdprDataProcessor.loadInputFieldDetails(anonymizationProcessorInsertData()))
				.thenReturn(insertImpactFieldCount);
		Mockito.when(anonymizationFileProcessor.loadAnonymizationDetails(anonymizationProcessorInsertData()))
				.thenReturn(insertImpactFieldCount);
		MockMultipartFile uploadfile = new MockMultipartFile("HerokuAnonymization-V0.1",
				"HerokuAnonymization-V0.1.xlsx", "application/vnd.ms-excel",
				new ClassPathResource("HerokuAnonymization-V0.1.xlsx").getInputStream());

		 //mockMvc.perform(post("/processFile"))
		mockMvc.perform(fileUpload("uploadfile")
		//mockMvc.perform(request("POST","/processFile")	
				.file(uploadfile))
		       .andExpect(view().name("fileUpload"))
				// .andExpect(forwardedUrl("/resources/templates/fileUpload.html"))
				/*
				 * .andExpect(model().attribute(
				 * "message","Rows parsed from Anonymization file : "));
				 */
				.andExpect(status().isOk());
	}

	private AnonymizationInputView anonymizationInputView() {
		AnonymizationInputView anonymizationInputView = new AnonymizationInputView("APPLICATION__C",
				"Email Notification Body", "EMAIL_NOTIFICATION_BODY__C", "Textarea (32768)",
				"PRE-HIRE: CANDIDATE-PROVIDED DATA OF UNSUCCESSFUL CANDIDATES (WITHOUT CONSENT)",
				"Null/Empty/Privacy Deleted", "Privacy Deleted", "EUR-EU", "AUT");
		return anonymizationInputView;
	}

	private List<AnonymizationInputView> anonymizationProcessorInsertData() {

		List<AnonymizationInputView> lstAnonymizationInputView = new ArrayList<AnonymizationInputView>();
		AnonymizationInputView anonymizationInputView1 = new AnonymizationInputView("APPLICATION__C",
				"Email Notification Body", "EMAIL_NOTIFICATION_BODY__C", "Textarea (32768)",
				"PRE-HIRE: CANDIDATE-PROVIDED DATA OF UNSUCCESSFUL CANDIDATES (WITHOUT CONSENT)",
				"Null/Empty/Privacy Deleted", "Privacy Deleted", "EUR-EU", "AUT");

		AnonymizationInputView anonymizationInputView2 = new AnonymizationInputView("INTERVIEW__C", "Candidate Email",
				"CANDIDATE_EMAIL__C", "Formula (Text)",
				"PRE-HIRE: CANDIDATE-PROVIDED DATA OF UNSUCCESSFUL CANDIDATES (WITHOUT CONSENT)",
				"Null/Empty/Privacy Deleted", "Privacy Deleted", "EUR-EU", "AUT");
		AnonymizationInputView anonymizationInputView3 = new AnonymizationInputView("INTERVIEW__C", "Candidate Email",
				"CANDIDATE_EMAIL__C", "Formula (Text)",
				"PRE-HIRE: CANDIDATE-PROVIDED DATA OF UNSUCCESSFUL CANDIDATES (WITHOUT CONSENT)",
				"Null/Empty/Privacy Deleted", "Privacy Deleted", "EUR-EU", "AUT");
		lstAnonymizationInputView.add(anonymizationInputView2);
		lstAnonymizationInputView.add(anonymizationInputView1);
		lstAnonymizationInputView.add(anonymizationInputView3);
		return lstAnonymizationInputView;
	}
}
