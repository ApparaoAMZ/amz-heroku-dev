package com.amazon.gdpr.dao;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.util.ReflectionTestUtils;


import com.amazon.gdpr.dao.GdprOutputDaoImpl.ModuleDataRowMapper;

import com.amazon.gdpr.dao.RunMgmtDaoImpl.RunMgmtRowMapper;
import com.amazon.gdpr.model.gdpr.input.AnonymizationDetail;

import com.amazon.gdpr.model.gdpr.output.RunErrorMgmt;
import com.amazon.gdpr.model.gdpr.output.RunModuleMgmt;
import com.amazon.gdpr.model.gdpr.output.RunSummaryMgmt;
import com.amazon.gdpr.model.gdpr.output.SummaryData;
import com.amazon.gdpr.util.SqlQueriesConstant;

public class GdprOutputDaoImplTest {
	@Mock
	JdbcTemplate jdbcTemplate;

	@InjectMocks
	GdprOutputDaoImpl gdprOutputDaoImpl;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(gdprOutputDaoImpl, "jdbcTemplate", jdbcTemplate);

	}

	@Test
	public void batchInsertRunAnonymizeMappingTest() {
		long runId = 7L;
		String countryCode = "AUT";
		String region = "EUR-EU";
		gdprOutputDaoImpl.batchInsertRunAnonymizeMapping(runId, countryCode, region);
	}

	/*
	 * @Test public void insertRunSummaryMgmtTest() {
	 * gdprOutputDaoImpl.insertRunSummaryMgmt(lstRunSummaryMgmt());
	 * 
	 * }
	 * 
	 * @Test public void batchInsertRunSummaryMgmtTest() { int batchSize = 100;
	 * gdprOutputDaoImpl.batchInsertRunSummaryMgmt(lstRunSummaryMgmt(), batchSize);
	 * }
	 */

	private List<RunSummaryMgmt> lstRunSummaryMgmt() {
		List<RunSummaryMgmt> lstRunSummaryMgmt = new ArrayList<RunSummaryMgmt>();
		RunSummaryMgmt runSummaryMgmt1 = new RunSummaryMgmt(1L, 1, "EUR-EU", "AUT", 1, "APPLICATION__C",
				"select * from APPLICATION__C..", " Alter Table APPLICATION__C..");
		RunSummaryMgmt runSummaryMgmt2 = new RunSummaryMgmt(1L, 1, "EUR-EU", "AUT", 1, "INTERVIEW__C",
				"select * from INTERVIEW__C..", " Alter Table INTERVIEW__C..");
		//Set data ussing setters
		RunSummaryMgmt runSummaryMgmt3 = new RunSummaryMgmt();
		runSummaryMgmt3.setBackupQuery("select * from INTERVIEW__C..");;
		runSummaryMgmt3.setBackupRowCount(12);
		runSummaryMgmt3.setCategoryId(1);
		runSummaryMgmt3.setCountryCode("AUT");
		runSummaryMgmt3.setDepersonalizationQuery("Alter Table INTERVIEW__C..");
		runSummaryMgmt3.setDepersonalizedRowCount(12);
		runSummaryMgmt3.setImpactTableId(2);
		runSummaryMgmt3.setImpactTableName("APPLICATION__C");
		runSummaryMgmt3.setRegion("EUR-EU");
		runSummaryMgmt3.setRunId(2);
		runSummaryMgmt3.setSummaryId(4);
		runSummaryMgmt3.setTaggedQueryLoad("select * from INTERVIEW__C....");
		runSummaryMgmt3.setTaggedRowCount(12);
		runSummaryMgmt3.getBackupQuery();
		runSummaryMgmt3.getBackupRowCount();
		runSummaryMgmt3.getCategoryId();
		runSummaryMgmt3.getCountryCode();
		runSummaryMgmt3.getDepersonalizationQuery();
		runSummaryMgmt3.getDepersonalizedRowCount();
		runSummaryMgmt3.getImpactTableId();
		runSummaryMgmt3.getImpactTableName();
		runSummaryMgmt3.getRegion();
		runSummaryMgmt3.getRunId();
		runSummaryMgmt3.getSummaryId();
		runSummaryMgmt3.getTaggedQueryLoad();
		runSummaryMgmt3.getTaggedRowCount();
		//runSummaryMgmt3.toString();
		lstRunSummaryMgmt.add(runSummaryMgmt2);
		lstRunSummaryMgmt.add(runSummaryMgmt1);
		return lstRunSummaryMgmt;
	}

	@Test
	public void fetchLastModuleDataTest() {

		Mockito.when(jdbcTemplate.query(any(String.class), (Object[]) anyVararg(), any(ModuleDataRowMapper.class)))
				.thenReturn(lstRunModuleMgmt());
		Long rundId = 7L;
		gdprOutputDaoImpl.fetchLastModuleData(rundId);
	}

	@Test
	public void loadDataProcessTest() {
		Long runId = 7L;
		gdprOutputDaoImpl.loadDataProcess(0);
	}

	private List<RunModuleMgmt> lstRunModuleMgmt() {
		Date moduleStartDateTime = null;
		Date moduleEndDateTime = null;
		moduleStartDateTime = new Date();
		moduleEndDateTime = new Date();
		List<RunModuleMgmt> lstrunModuleMgmt = new ArrayList<RunModuleMgmt>();
		RunModuleMgmt runModuleMgmt2 = new RunModuleMgmt();
		runModuleMgmt2.setComments("Run Anonymization Initiated Count : 0");
		runModuleMgmt2.setCount(4);
		runModuleMgmt2.setModuleEndDateTime(moduleEndDateTime);
		runModuleMgmt2.setModuleStartDateTime(moduleStartDateTime);
		runModuleMgmt2.setSubModuleName("Anonymize Initialize Sub Module");
		runModuleMgmt2.setModuleName("GlobalConstants.MODULE_INITIALIZATION");
		runModuleMgmt2.setRunId(2L);
		runModuleMgmt2.setModuleStatus("GlobalConstants.STATUS_SUCCESS");
		runModuleMgmt2.setModuleId(1L);
		//set data to cover model class
		RunModuleMgmt runModuleMgmt3 = new RunModuleMgmt();
		runModuleMgmt3.setComments("Run Anonymization Initiated Count : 0");
		runModuleMgmt3.setCount(4);
		runModuleMgmt3.setModuleEndDateTime(moduleEndDateTime);
		runModuleMgmt3.setModuleStartDateTime(moduleStartDateTime);
		runModuleMgmt3.setSubModuleName("Anonymize Initialize Sub Module");
		runModuleMgmt3.setModuleName("GlobalConstants.MODULE_INITIALIZATION");
		runModuleMgmt3.setRunId(2L);
		runModuleMgmt3.setModuleStatus("GlobalConstants.STATUS_SUCCESS");
		runModuleMgmt3.setModuleId(1L);
		runModuleMgmt3.setErrorDetails("Test Exception");
		runModuleMgmt3.getModuleId();
		runModuleMgmt3.getCount();
		runModuleMgmt3.setComments("Running");
		RunModuleMgmt runModuleMgmt4 = new RunModuleMgmt("SUCCESS");
		lstrunModuleMgmt.add(runModuleMgmt2);
		return lstrunModuleMgmt;
	}

	private RunErrorMgmt runErrorMgmt() {
	
	RunErrorMgmt runErrorMgmt = new RunErrorMgmt(1L, 2L, "Test", "test", "Test", "test");
	RunErrorMgmt runErrorMgmt1 = new RunErrorMgmt(1L, 2L, "Test", "XX", "Test", "Test error details");
	runErrorMgmt1.setErrorId(1);
	runErrorMgmt1.setErrorClass("ModuleMgmt");
	runErrorMgmt1.setRunId(7);
	runErrorMgmt1.setErrorMethod("Loaddata");
	runErrorMgmt1.setErrorSummary("test");
	runErrorMgmt1.setErrorDetail("Test error details");
	runErrorMgmt1.getErrorClass();
	runErrorMgmt1.getErrorDetail();
	runErrorMgmt1.getErrorId();
	runErrorMgmt1.getErrorMethod();
	runErrorMgmt1.getErrorSummary();
	runErrorMgmt1.getRunId();
	
	return runErrorMgmt;
	}
	@Test
	public void loadErrorDetailsTest() {
		gdprOutputDaoImpl.loadErrorDetails(runErrorMgmt());
	}

	/*
	 * @Test public void fetchSummaryDetailsTest() { Long runId = 2L; //
	 * List<SummaryData> lstSummaryData = //
	 * jdbcTemplate.query(SqlQueriesConstant.SUMMARY_DATA_FETCH, new //
	 * Object[]{runId}, new SummaryDataRowMapper());
	 * Mockito.when(jdbcTemplate.query(any(String.class), (Object[]) anyVararg(),
	 * any(SummaryDataRowMapper.class))) .thenReturn(lstSummaryData());
	 * List<SummaryData> lstSummaryData =
	 * gdprOutputDaoImpl.fetchSummaryDetails(runId);
	 * assertEquals(lstSummaryData.size(), lstSummaryData.size()); }
	 */

	/*
	 * @Test public void insertDataLodTest() { Date dataLoadDateTime = null;
	 * dataLoadDateTime = new Date(); DataLoad dataLoad = new
	 * DataLoad("APPLIVATION_C", "AUT", dataLoadDateTime);
	 * 
	 * gdprOutputDaoImpl.insertDataLod(dataLoad); }
	 */
	@Test
	public void fetchLastDataLoadTest() {
		//List<DataLoad> lstDataLoad = new ArrayList<DataLoad>();
		long now = System.currentTimeMillis();
		Timestamp sqlTimestamp = new Timestamp(now);
		/*
		 * DataLoad dataLoad1 = new DataLoad("APPLICATION_C", "AUT", sqlTimestamp);
		 * DataLoad dataLoad2 = new DataLoad("INTERVIEW_C", "AUT", sqlTimestamp);
		 * lstDataLoad.add(dataLoad2); lstDataLoad.add(dataLoad1);
		 */
		
		String timeStampStr=sqlTimestamp.toString();
		Mockito.when(jdbcTemplate.queryForObject(any(String.class),eq(String.class))).thenReturn(timeStampStr);
		gdprOutputDaoImpl.fetchLastDataLoad();
	}

	@Test
	public void updateDataLoadTest() {
		/*
		 * long now = System.currentTimeMillis(); Timestamp sqlTimestamp = new
		 * Timestamp(now); DataLoad dataLoad = new DataLoad("APPLIVATION_C", "AUT",
		 * sqlTimestamp);
		 */
		Long runId=7L;
		gdprOutputDaoImpl.updateDataLoad(runId);
	}

	@Test
	 public void fetchCurrentTimestampTest() {
		long now = System.currentTimeMillis();
		Timestamp sqlTimestamp = new Timestamp(now);
		String timeStampStr=sqlTimestamp.toString();
		Mockito.when(jdbcTemplate.queryForObject(any(String.class),eq(String.class))).thenReturn(timeStampStr);
		gdprOutputDaoImpl.fetchCurrentTimestamp();
	 }   

	private List<SummaryData> lstSummaryData() {
		List<SummaryData> lstSummaryData = new ArrayList<SummaryData>();
		SummaryData summaryData1 = new SummaryData(2, "EUR-EU", "AUT", "APPLICATION__C", "SF_ARCHIVE",
				"FELONY_CONVICTION_QUESTION_2__C", "TEXT", "PRIVACY DELETED", 2);
		SummaryData summaryData2 = new SummaryData(2, "EUR-EU", "BEL", "APPLICATION__C", "SF_ARCHIVE", "CANDIDATE_C",
				"TEXT", "PRIVACY DELETED", 2);
		lstSummaryData.add(summaryData2);
		lstSummaryData.add(summaryData1);
		return lstSummaryData;
	}

}
