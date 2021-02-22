package com.amazon.gdpr.processor;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import com.amazon.gdpr.dao.GdprInputFetchDaoImpl;
import com.amazon.gdpr.dao.GdprOutputDaoImpl;
import com.amazon.gdpr.dao.RunMgmtDaoImpl;
import com.amazon.gdpr.model.gdpr.input.Country;
import com.amazon.gdpr.model.gdpr.output.RunMgmt;
import com.amazon.gdpr.model.gdpr.output.RunModuleMgmt;
import com.amazon.gdpr.util.GdprException;
import com.amazon.gdpr.util.GlobalConstants;
import com.amazon.gdpr.view.GdprInput;

public class RunMgmtProcessorTest {
	@InjectMocks
	RunMgmtProcessor runMgmtProcessor;
	@Mock
	RunMgmtDaoImpl runMgmtDaoImpl;

	@Mock
	GdprOutputDaoImpl gdprOutputDaoImpl;

	@Mock
	ModuleMgmtProcessor moduleMgmtProcessor;
	@Mock
	GdprInputFetchDaoImpl gdprInputFetchDaoImpl;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void initializeRunPositiveTest() throws GdprException {
		Date moduleStartDateTime = null;
		Date moduleEndDateTime = null;
		moduleStartDateTime = new Date();
		moduleEndDateTime = new Date();
		RunModuleMgmt runModuleMgmt = new RunModuleMgmt(2L, "Initialization Module", "Anonymize Initialize Sub Module",
				"SUCCESS", moduleStartDateTime, moduleEndDateTime, "Run Anonymization Initiated Count : 0","");
		RunMgmt runMgmt = new RunMgmt();
		runMgmt.setRunId(2L);
		runMgmt.setRunName("Test");
		runMgmt.setRunStatus("SUCCESS");
		String runName = "Test";
		Long runId = 2L;
		Mockito.when(runMgmtDaoImpl.fetchLastRunDetail()).thenReturn(runMgmt);
		Mockito.doNothing().when(moduleMgmtProcessor).initiateModuleMgmt(runModuleMgmt);
		Mockito.doNothing().when(runMgmtDaoImpl).initiateNewRun(runName);
		Long response=runMgmtProcessor.initializeRun(runName);
		assertEquals(runId, response);
	}
	@Test
	public void initializeRunPositiveTest1() throws GdprException {
		Date moduleStartDateTime = null;
		Date moduleEndDateTime = null;
		moduleStartDateTime = new Date();
		moduleEndDateTime = new Date();
		RunModuleMgmt runModuleMgmt = new RunModuleMgmt(2L, "Initialization Module", "Tagging Archival Job Sub Module",
				"RE-RUN", moduleStartDateTime, moduleEndDateTime, "Run Anonymization Initiated Count : 0","");
		RunMgmt runMgmt = new RunMgmt();
		runMgmt.setRunId(2L);
		runMgmt.setRunName("Test");
		runMgmt.setRunStatus("SUCCESS");
		String runName = "Test";
		Long runId = 2L;
		Mockito.when(runMgmtDaoImpl.fetchLastRunDetail()).thenReturn(runMgmt);
		Mockito.doNothing().when(moduleMgmtProcessor).initiateModuleMgmt(runModuleMgmt);
		Mockito.doNothing().when(runMgmtDaoImpl).initiateNewRun(runName);
		Long response=runMgmtProcessor.initializeRun(runName);
		assertEquals(runId, response);
		//System.out.println("RunId:"+response);
	}
	
	@Test(expected = Exception.class)
	public void initializeRunExceptionTest() throws GdprException {
		Date moduleStartDateTime = null;
		Date moduleEndDateTime = null;
		moduleStartDateTime = new Date();
		moduleEndDateTime = new Date();
		RunModuleMgmt runModuleMgmt = new RunModuleMgmt(2L, "Initialization Module", "Anonymize Initialize Sub Module",
				"SUCCESS", moduleStartDateTime, moduleEndDateTime, "Run Anonymization Initiated Count : 0","");
		RunMgmt runMgmt = new RunMgmt();
		runMgmt.setRunId(2L);
		runMgmt.setRunName("Test");
		runMgmt.setRunStatus("SUCCESS");
		String runName = "Test";
		Long runId = 2L;
		Mockito.when(runMgmtDaoImpl.fetchLastRunDetail()).thenThrow(Exception.class);
		Mockito.doNothing().when(moduleMgmtProcessor).initiateModuleMgmt(runModuleMgmt);
		Mockito.doNothing().when(runMgmtDaoImpl).initiateNewRun(runName);
		runMgmtProcessor.initializeRun(runName);
	}
	
	
	@Test(expected=Exception.class)
	public void initializeRunExceptionTest2() throws GdprException {
		Date moduleStartDateTime = null;
		Date moduleEndDateTime = null;
		moduleStartDateTime = new Date();
		moduleEndDateTime = new Date();
		RunModuleMgmt runModuleMgmt = new RunModuleMgmt(2L, "Initialization Module", "Anonymize Initialize Sub Module",
				"SUCCESS", moduleStartDateTime, moduleEndDateTime, "Run Anonymization Initiated Count : 0","");
		RunMgmt runMgmt = new RunMgmt();
		runMgmt.setRunId(2L);
		runMgmt.setRunName("Test");
		runMgmt.setRunStatus("SUCCESS");
		String runName = "Test";
		Long runId = 2L;
		Mockito.when(runMgmtDaoImpl.fetchLastRunDetail()).thenReturn(runMgmt);
		Mockito.doThrow(Exception.class).when(moduleMgmtProcessor).initiateModuleMgmt(anyObject());
		Mockito.doNothing().when(runMgmtDaoImpl).initiateNewRun(runName);
		runMgmtProcessor.initializeRun(runName);
	}
	@Test
	public void initializeRunRerunTest() throws GdprException {
		Date moduleStartDateTime = null;
		Date moduleEndDateTime = null;
		moduleStartDateTime = new Date();
		moduleEndDateTime = new Date();
		RunModuleMgmt runModuleMgmt = new RunModuleMgmt(2L, "Initialization Module", "Anonymize Initialize Sub Module",
				"FAILURE", moduleStartDateTime, moduleEndDateTime, "Run Anonymization Initiated Count : 0","");
		RunMgmt runMgmt = new RunMgmt();
		runMgmt.setRunId(2L);
		runMgmt.setRunName("Test");
		runMgmt.setRunStatus("RE-RUN");
		String runName = "Test";
		Long runId = 2L;
		Mockito.when(runMgmtDaoImpl.fetchLastRunDetail()).thenReturn(runMgmt);
		Mockito.doNothing().when(moduleMgmtProcessor).initiateModuleMgmt(runModuleMgmt);
		Mockito.doNothing().when(runMgmtDaoImpl).initiateNewRun(runName);
		Long response=runMgmtProcessor.initializeRun(runName);
		assertEquals(runId, response);
	}
	
	@Test
	public void initializeFailureTest() throws GdprException {
		Date moduleStartDateTime = null;
		Date moduleEndDateTime = null;
		moduleStartDateTime = new Date();
		moduleEndDateTime = new Date();
		RunModuleMgmt runModuleMgmt = new RunModuleMgmt(2L, "Initialization Module", "Anonymize Initialize Sub Module",
				"FAILURE", moduleStartDateTime, moduleEndDateTime, "Run Anonymization Initiated Count : 0","");
		RunMgmt runMgmt = new RunMgmt();
		runMgmt.setRunId(2L);
		runMgmt.setRunName("Test");
		runMgmt.setRunStatus("FAILURE");
		String runName = "Test";
		Long runId = 0L;
		Mockito.when(runMgmtDaoImpl.fetchLastRunDetail()).thenReturn(runMgmt);
		Mockito.doNothing().when(moduleMgmtProcessor).initiateModuleMgmt(runModuleMgmt);
		Mockito.doNothing().when(runMgmtDaoImpl).initiateNewRun(runName);
		Long response=runMgmtProcessor.initializeRun(runName);
		assertEquals(runId, response);
	}
	@Test
	public void reinitiatePendingActivitySuccessTest() throws GdprException {
		Long runId=2L;
		Mockito.when(runMgmtDaoImpl.fetchLastModuleMgmtDetail(runId)).thenReturn(lstrunModuleMgmtreinitiatePendingActivitySuccess());
		String response=runMgmtProcessor.reinitiatePendingActivity(runId);
		String expectRes="The archival of the data was successful. No re-run is required.";
		System.out.println("res:"+expectRes);
		//assertEquals(expectRes, response);
	}
	
	@Test
	public void reinitiatePendingActivityFailureTest() throws GdprException {
		Long runId=2L;
		Mockito.when(runMgmtDaoImpl.fetchLastModuleMgmtDetail(runId)).thenReturn(lstrunModuleMgmtreinitiatePendingActivityFailure());
		String response=runMgmtProcessor.reinitiatePendingActivity(runId);
		String expectRes="The reorganizing of input data has failed. Please refresh the DB and rerun from start.";
		System.out.println("res:"+response);
	//	assertEquals(expectRes, response);
	}
	
	@Test
	public void reinitiatePendingActivitySuccessTest2() throws GdprException {
		Long runId=2L;
		List<RunModuleMgmt> lstrunModuleMgmt = new ArrayList<RunModuleMgmt>();
		RunModuleMgmt runModuleMgmt1 = new RunModuleMgmt();
		runModuleMgmt1.setComments("Run Anonymization Initiated Count : 0");
		runModuleMgmt1.setCount(4);
		runModuleMgmt1.setModuleEndDateTime(new Date());
		runModuleMgmt1.setModuleStartDateTime(new Date());
		runModuleMgmt1.setSubModuleName(GlobalConstants.SUB_MODULE_REORGANIZE_JOB_INITIALIZE);
		runModuleMgmt1.setModuleName("Initialization Module");
		runModuleMgmt1.setRunId(2L);
		runModuleMgmt1.setModuleStatus("SUCCESS");
		runModuleMgmt1.setModuleId(1L);
		lstrunModuleMgmt.add(runModuleMgmt1);
		Mockito.when(runMgmtDaoImpl.fetchLastModuleMgmtDetail(runId)).thenReturn(lstrunModuleMgmt);
		String response=runMgmtProcessor.reinitiatePendingActivity(runId);
		String expectRes="The reorganizing of input data was successful.";
		System.out.println("res:"+response);
		//assertEquals(expectRes, response);
	}
	
	@Test
	public void reinitiatePendingActivitySuccessTest3() throws GdprException {
		Long runId=2L;
		List<RunModuleMgmt> lstrunModuleMgmt = new ArrayList<RunModuleMgmt>();
		RunModuleMgmt runModuleMgmt1 = new RunModuleMgmt();
		runModuleMgmt1.setComments("Run Anonymization Initiated Count : 0");
		runModuleMgmt1.setCount(4);
		runModuleMgmt1.setModuleEndDateTime(new Date());
		runModuleMgmt1.setModuleStartDateTime(new Date());
		runModuleMgmt1.setSubModuleName(GlobalConstants.SUB_MODULE_BACKUPSERVICE_JOB_INITIALIZE);
		runModuleMgmt1.setModuleName("Initialization Module");
		runModuleMgmt1.setRunId(2L);
		runModuleMgmt1.setModuleStatus("SUCCESS");
		runModuleMgmt1.setModuleId(1L);
		lstrunModuleMgmt.add(runModuleMgmt1);
		Mockito.when(runMgmtDaoImpl.fetchLastModuleMgmtDetail(runId)).thenReturn(lstrunModuleMgmt);
		String response=runMgmtProcessor.reinitiatePendingActivity(runId);
		String expectRes="The backup of the run was successful.";
		System.out.println("res:"+response);
	//	assertEquals(expectRes, response);
	}
	
	@Test
	public void reinitiatePendingActivityFailureTest3() throws GdprException {
		Long runId=2L;
		List<RunModuleMgmt> lstrunModuleMgmt = new ArrayList<RunModuleMgmt>();
		RunModuleMgmt runModuleMgmt1 = new RunModuleMgmt();
		runModuleMgmt1.setComments("Run Anonymization Initiated Count : 0");
		runModuleMgmt1.setCount(4);
		runModuleMgmt1.setModuleEndDateTime(new Date());
		runModuleMgmt1.setModuleStartDateTime(new Date());
		runModuleMgmt1.setSubModuleName(GlobalConstants.SUB_MODULE_BACKUPSERVICE_JOB_INITIALIZE);
		runModuleMgmt1.setModuleName("Initialization Module");
		runModuleMgmt1.setRunId(2L);
		runModuleMgmt1.setModuleStatus("FAILURE");
		runModuleMgmt1.setModuleId(1L);
		lstrunModuleMgmt.add(runModuleMgmt1);
		Mockito.when(runMgmtDaoImpl.fetchLastModuleMgmtDetail(runId)).thenReturn(lstrunModuleMgmt);
		String response=runMgmtProcessor.reinitiatePendingActivity(runId);
		String expectRes="The backup of the run has failed. Please refresh the DataLoad, GDPR_Depersonalization table and rerun from start.";
		System.out.println("res:"+response);
		//assertEquals(expectRes, response);
	}
	//GlobalConstants.SUB_MODULE_TAG_JOB_INITIALIZE
	
	
	@Test(expected=Exception.class)
	public void reinitiatePendingActivityExceptionTest() throws GdprException {
		Long runId=2L;
		List<RunModuleMgmt> lstrunModuleMgmt = new ArrayList<RunModuleMgmt>();
		RunModuleMgmt runModuleMgmt1 = new RunModuleMgmt();
		runModuleMgmt1.setComments("Run Anonymization Initiated Count : 0");
		runModuleMgmt1.setCount(4);
		runModuleMgmt1.setModuleEndDateTime(new Date());
		runModuleMgmt1.setModuleStartDateTime(new Date());
		runModuleMgmt1.setSubModuleName(GlobalConstants.SUB_MODULE_TAG_JOB_INITIALIZE);
		runModuleMgmt1.setModuleName("Initialization Module");
		runModuleMgmt1.setRunId(2L);
		runModuleMgmt1.setModuleStatus("SUCCESS");
		runModuleMgmt1.setModuleId(1L);
		lstrunModuleMgmt.add(runModuleMgmt1);
		Mockito.when(runMgmtDaoImpl.fetchLastModuleMgmtDetail(runId)).thenReturn(lstrunModuleMgmt);
		String response=runMgmtProcessor.reinitiatePendingActivity(runId);
		String expectRes="The backup of the run has failed. Please refresh the DataLoad, GDPR_Depersonalization table and rerun from start.";
		System.out.println("res:"+response);
		//assertEquals(expectRes, response);
	}
	
	private List<RunModuleMgmt> lstrunModuleMgmtreinitiatePendingActivityFailure() {
		Date moduleStartDateTime = null;
		Date moduleEndDateTime = null;
		moduleStartDateTime = new Date();
		moduleEndDateTime = new Date();
		List<RunModuleMgmt> lstrunModuleMgmt = new ArrayList<RunModuleMgmt>();
		RunModuleMgmt runModuleMgmt1 = new RunModuleMgmt();
		runModuleMgmt1.setComments("Run Anonymization Initiated Count : 0");
		runModuleMgmt1.setCount(4);
		runModuleMgmt1.setModuleEndDateTime(moduleEndDateTime);
		runModuleMgmt1.setModuleStartDateTime(moduleStartDateTime);
		runModuleMgmt1.setSubModuleName(GlobalConstants.SUB_MODULE_REORGANIZE_JOB_INITIALIZE);
		runModuleMgmt1.setModuleName("Initialization Module");
		runModuleMgmt1.setRunId(2L);
		runModuleMgmt1.setModuleStatus("FAILURE");
		runModuleMgmt1.setModuleId(1L);

		/*
		 * RunModuleMgmt runModuleMgmt2 = new RunModuleMgmt();
		 * runModuleMgmt2.setComments("Run Anonymization Initiated Count : 0");
		 * runModuleMgmt2.setCount(4);
		 * runModuleMgmt2.setModuleEndDateTime(moduleEndDateTime);
		 * runModuleMgmt2.setModuleStartDateTime(moduleStartDateTime);
		 * runModuleMgmt2.setSubModuleName("Tagging Archival Job Sub Module");
		 * runModuleMgmt2.setModuleName("GlobalConstants.MODULE_INITIALIZATION");
		 * runModuleMgmt2.setRunId(2L); runModuleMgmt2.setModuleStatus("FAILURE");
		 * runModuleMgmt2.setModuleId(1L); RunModuleMgmt runModuleMgmt3 = new
		 * RunModuleMgmt();
		 * runModuleMgmt3.setComments("Run Anonymization Initiated Count : 0");
		 * runModuleMgmt3.setCount(4);
		 * runModuleMgmt3.setModuleEndDateTime(moduleEndDateTime);
		 * runModuleMgmt3.setModuleStartDateTime(moduleStartDateTime);
		 * runModuleMgmt3.setSubModuleName("Anonymize Archival Job Sub Module");
		 * runModuleMgmt3.setModuleName("GlobalConstants.MODULE_INITIALIZATION");
		 * runModuleMgmt3.setRunId(2L); runModuleMgmt3.setModuleStatus("FAILURE");
		 * runModuleMgmt3.setModuleId(1L); lstrunModuleMgmt.add(runModuleMgmt3);
		 * lstrunModuleMgmt.add(runModuleMgmt2);
		 */

		lstrunModuleMgmt.add(runModuleMgmt1);

		return lstrunModuleMgmt;
	}
	private List<RunModuleMgmt> lstrunModuleMgmtreinitiatePendingActivitySuccess() {
		Date moduleStartDateTime = null;
		Date moduleEndDateTime = null;
		moduleStartDateTime = new Date();
		moduleEndDateTime = new Date();
		List<RunModuleMgmt> lstrunModuleMgmt = new ArrayList<RunModuleMgmt>();
		RunModuleMgmt runModuleMgmt1 = new RunModuleMgmt();
		runModuleMgmt1.setComments("Run Anonymization Initiated Count : 0");
		runModuleMgmt1.setCount(4);
		runModuleMgmt1.setModuleEndDateTime(moduleEndDateTime);
		runModuleMgmt1.setModuleStartDateTime(moduleStartDateTime);
		runModuleMgmt1.setSubModuleName("Backup Service Job Sub Module");
		runModuleMgmt1.setModuleName("Initialization Module");
		runModuleMgmt1.setRunId(2L);
		runModuleMgmt1.setModuleStatus("SUCCESS");
		runModuleMgmt1.setModuleId(1L);

		RunModuleMgmt runModuleMgmt2 = new RunModuleMgmt();
		runModuleMgmt2.setComments("Run Anonymization Initiated Count : 0");
		runModuleMgmt2.setCount(4);
		runModuleMgmt2.setModuleEndDateTime(moduleEndDateTime);
		runModuleMgmt2.setModuleStartDateTime(moduleStartDateTime);
		runModuleMgmt2.setSubModuleName("Tagging Archival Job Sub Module");
		runModuleMgmt2.setModuleName("GlobalConstants.MODULE_INITIALIZATION");
		runModuleMgmt2.setRunId(2L);
		runModuleMgmt2.setModuleStatus("SUCCESS");
		runModuleMgmt2.setModuleId(1L);
		RunModuleMgmt runModuleMgmt3 = new RunModuleMgmt();
		runModuleMgmt3.setComments("Run Anonymization Initiated Count : 0");
		runModuleMgmt3.setCount(4);
		runModuleMgmt3.setModuleEndDateTime(moduleEndDateTime);
		runModuleMgmt3.setModuleStartDateTime(moduleStartDateTime);
		runModuleMgmt3.setSubModuleName("Anonymize Archival Job Sub Module");
		runModuleMgmt3.setModuleName("GlobalConstants.MODULE_INITIALIZATION");
		runModuleMgmt3.setRunId(2L);
		runModuleMgmt3.setModuleStatus("SUCCESS");
		runModuleMgmt3.setModuleId(1L);
		lstrunModuleMgmt.add(runModuleMgmt3);
		lstrunModuleMgmt.add(runModuleMgmt2);

		lstrunModuleMgmt.add(runModuleMgmt1);

		return lstrunModuleMgmt;
	}
	@Test(expected=Exception.class)
	public void initiateNewRunTest() throws GdprException {
		String runName="Test";
		Mockito.doThrow(Exception.class).when(runMgmtDaoImpl).initiateNewRun(anyString());
		Mockito.when(runMgmtDaoImpl.fetchLastRunDetail().getRunId()).thenThrow(Exception.class);
		runMgmtProcessor.initiateNewRun(runName);
	}
//GIT
	/*
	 * @Test(expected=Exception.class) public void initializeRunExceptionTest()
	 * throws GdprException { Date moduleStartDateTime = null; Date
	 * moduleEndDateTime = null;
	 * 
	 * moduleStartDateTime = new Date(); moduleEndDateTime = new Date();
	 * RunModuleMgmt runModuleMgmt = new RunModuleMgmt(2L, "Initialization Module",
	 * "Anonymize Initialize Sub Module", "SUCCESS", moduleStartDateTime,
	 * moduleStartDateTime, "Run Anonymization Initiated Count : 0",""); RunMgmt
	 * runMgmt = new RunMgmt(); runMgmt.setRunId(2L); runMgmt.setRunName("Test");
	 * runMgmt.setRunStatus("FAILURE"); String runName = "Test"; Long runId = 2L;
	 * Mockito.when(runMgmtDaoImpl.fetchLastRunDetail()).thenReturn(runMgmt);
	 * Mockito.doThrow(Exception.class).when(moduleMgmtProcessor).initiateModuleMgmt
	 * (anyObject());
	 * Mockito.doThrow(Exception.class).when(runMgmtDaoImpl).initiateNewRun(runName)
	 * ; runMgmtProcessor.initializeRun(runName); }
	 */
	
	
	/*
	 * @Test public void initializeRunException2Test() throws GdprException { Date
	 * moduleStartDateTime = null; Date moduleEndDateTime = null; Date
	 * runStartDateTime = null; Date runEndDateTime = null; moduleStartDateTime =
	 * new Date(); moduleEndDateTime = new Date(); RunModuleMgmt runModuleMgmt = new
	 * RunModuleMgmt(2L, "Initialization Module", "Anonymize Initialize Sub Module",
	 * "SUCCESS", moduleStartDateTime, moduleStartDateTime,
	 * "Run Anonymization Initiated Count : 0"); RunErrorMgmt runErrorMgmt = new
	 * RunErrorMgmt(1L, 2L, "Test", "XX", "Test", "XX"); RunMgmt runMgmt = new
	 * RunMgmt(); runMgmt.setRunId(2L); runMgmt.setRunName("Test");
	 * runMgmt.setRunStatus("FAILURE"); String runName = "Test"; Long runId = 2L;
	 * Mockito.when(runMgmtDaoImpl.fetchLastRunDetail()).thenReturn(runMgmt);
	 * Mockito.doThrow(Exception.class).when(moduleMgmtProcessor).initiateModuleMgmt
	 * (runModuleMgmt);
	 * Mockito.doNothing().when(gdprOutputDaoImpl).loadErrorDetails(runErrorMgmt);
	 * Mockito.doThrow(Exception.class).when(runMgmtDaoImpl).initiateNewRun(runName)
	 * ;
	 * 
	 * runMgmtProcessor.initializeRun(runName); }
	 */
//GIT
	/*
	 * @Test(expected = Exception.class) public void oldRunVerificationTest() throws
	 * GdprException { RunMgmt runMgmt = new RunMgmt(); runMgmt.setRunId(2L);
	 * runMgmt.setRunName("Test"); runMgmt.setRunStatus("FAILURE"); // RunMgmt
	 * runMgmt = runMgmtDaoImpl.fetchLastRunDetail();
	 * Mockito.when(runMgmtDaoImpl.fetchLastRunDetail()).thenThrow(Exception.class);
	 * runMgmtProcessor.oldRunVerification();
	 * 
	 * }
	 */

	@Test
	public void loadCountryDetailTest() throws GdprException {

		GdprInput gdprInput = new GdprInput();
		GdprInput gdprInput1 =gdprInputData();
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

		//gdprInput.setErrorStatus(" ");
		gdprInput.setLstRegion(lstRegion);
		gdprInput.setLstCountry(lstCountry);
		//latest
	//	gdprInput.setLstSelectedRegion(lstSelectedRegion);
	//	gdprInput.setLstSelectedCountryCode(new ArrayList<String>());
		gdprInput.setMapRegionCountry(mapRegionCountry);
		Mockito.when(gdprInputFetchDaoImpl.fetchAllCountries()).thenReturn(mockCountryDetails());
		GdprInput gdprInputRes = runMgmtProcessor.loadCountryDetail();
		assertEquals(gdprInput.getLstRegion(), gdprInputRes.getLstRegion());
		assertEquals(gdprInput.getMapRegionCountry(), gdprInputRes.getMapRegionCountry());

	}
	
	
	private GdprInput gdprInputData() {
		GdprInput gdprInput1 = new GdprInput();
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

		gdprInput1.setErrorStatus(" ");
		gdprInput1.setLstRegion(lstRegion);
		gdprInput1.setLstCountry(lstCountry);
		String[] lstSelectedRegion1= {"EUR-EU"};
		String[] lstSelectedCountry= {"AUT","BEL"};
		gdprInput1.setArySelectedRegion(lstSelectedRegion1);
		gdprInput1.setArySelectedCountryCode(lstSelectedCountry);
		gdprInput1.setMapRegionCountry(mapRegionCountry);
		gdprInput1.getRunStatus(); 
		gdprInput1.getErrorStatus();  
		gdprInput1.getCountries(); 
		gdprInput1.getArySelectedRegion();  
		gdprInput1.getArySelectedCountryCode();  
		gdprInput1.getLstSelectedCountry();
		return gdprInput1;
		
	}

	@Test(expected = Exception.class)
	public void loadCountryDetailExceptionTest() throws GdprException {
	    Mockito.when(gdprInputFetchDaoImpl.fetchAllCountries()).thenThrow(Exception.class);
		runMgmtProcessor.loadCountryDetail();

	}

	private List<Country> mockCountryDetails() {
		List<Country> country = new ArrayList<Country>();

		Country country2 = new Country("EUR-EU", "AUT");
		Country country3 = new Country("EUR-EU", "BEL");
		/*
		 * Country country4 = new Country("EUR-EU","CHE"); Country country5 = new
		 * Country("EUR-EU","CZE"); Country country6 = new Country("EUR-EU","DEU");
		 * Country country7 = new Country("EUR-EU","DNK"); Country country8 = new
		 * Country("EUR-EU","ESP"); Country country9 = new Country("EUR-EU","FIN");
		 * Country country10 = new Country("EUR-EU","FRA");
		 * 
		 * Country country11 = new Country("EUR-EU","GBR"); Country country12 = new
		 * Country("EUR-EU","HRV"); Country country13 = new Country("EUR-EU","ITA");
		 * Country country14 = new Country("EUR-EU","LUX"); Country country15 = new
		 * Country("EUR-EU","NLD"); Country country16 = new Country("EUR-EU","NOR");
		 * Country country17 = new Country("EUR-EU","POL"); Country country18 = new
		 * Country("EUR-EU","PRT"); Country country19 = new Country("EUR-EU","ROI");
		 * Country country20 = new Country("EUR-EU","ROM"); Country country21 = new
		 * Country("EUR-EU","SVK"); Country country22 = new Country("EUR-EU","SWE");
		 * country.add(country22); country.add(country21); country.add(country20);
		 * country.add(country19); country.add(country18); country.add(country17);
		 * country.add(country16); country.add(country15); country.add(country14);
		 * country.add(country13); country.add(country12); country.add(country11);
		 * 
		 * country.add(country10); country.add(country9); country.add(country8);
		 * country.add(country7); country.add(country6); country.add(country5);
		 * country.add(country4);
		 */
		//set data using Setters
		Country countrySet = new Country("EUR-EU", "AUT");
		countrySet.setCountryCode("AUT");
		countrySet.setRegion("EUR-EU");
		countrySet.setStatus("Active");
		countrySet.getCountryCode();
		countrySet.getRegion();
		countrySet.getStatus();
		country.add(country3);
		country.add(country2);

		return country;
	}

}
