package com.amazon.gdpr.controller;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.amazon.gdpr.dao.RunMgmtDaoImpl;
import com.amazon.gdpr.service.BackupService;
import com.amazon.gdpr.service.InitService;
import com.amazon.gdpr.view.GdprInput;

@RunWith(MockitoJUnitRunner.class)
public class GdprProcessControllerTest {

	int runId = 0;

	@InjectMocks
	private GdprProcessController gdprProcessController;

	@Mock
	BackupService backpupService;

	@Mock
	InitService initService;

	private MockMvc mockMvc;

	@Mock
	BackupService backupService;

	@Mock
	RunMgmtDaoImpl runMgmtDaoImpl;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

		mockMvc = MockMvcBuilders.standaloneSetup(gdprProcessController).build();
	}

	@Test
	@Ignore
	public void gdprInputFormTest() throws Exception {
		GdprInput gdprInput = GdprInputData();
		when(initService.loadGdprForm()).thenReturn(gdprInput);
		// GdprInput gdprInputData = initService.loadGdprForm();
		gdprInput.setLstCountry(gdprInput.getLstCountry());
		mockMvc.perform(get("/gdprInputT")).andExpect(status().isOk()).andExpect(view().name("gdprInput"))
				.andExpect(model().attribute("gdprInput", instanceOf(GdprInput.class)));

	}

	@Test
	//@Ignore
	public void herokuDepersonalizationTest() throws Exception {
		GdprInput gdprInput = GdprInputData();
		String runName = "TestRun";
		String status = "Success";
		when(initService.loadGdprForm()).thenReturn(gdprInput);
		when(initService.initService(runName, gdprInput.getLstCountry())).thenReturn(status);
		gdprInput.setRunName(runName);
		gdprInput.setRunStatus(status);
		gdprInput.setLstSelectedCountry(gdprInput.getLstCountry());

		mockMvc.perform(post("/gdprSubmit"))
		.andExpect(status().isOk())
		.andExpect(view().name("gdprInputStatus"))
		.andExpect(model().attribute("gdprInput", instanceOf(GdprInput.class)));
		// .flashAttr("gdprInput", new GdprInput()));

	}

	private GdprInput GdprInputData() {
		GdprInput gdprInput = new GdprInput();
		List<String> lstSelectedRegion = new ArrayList<String>();
		lstSelectedRegion.add("BEL");
		lstSelectedRegion.add("AUT");

		List<String> lstRegion = new ArrayList<String>();
		lstRegion.add("EUR-EU");
		List<String> lstRegionsCountry = new ArrayList<String>();
		lstRegionsCountry.add("EUR-EU");
		Map<String, List<String>> mapRegionCountry = new HashMap<String, List<String>>();
		mapRegionCountry.put("EUR-EU", lstSelectedRegion);
		List<String> lstCountry = new ArrayList<String>();
		lstCountry.add("AUT");
		lstCountry.add("BEL");
		gdprInput.setLstRegion(lstRegion);
		gdprInput.setLstCountry(lstCountry);
		gdprInput.setMapRegionCountry(mapRegionCountry);

		return gdprInput;
	}

}
