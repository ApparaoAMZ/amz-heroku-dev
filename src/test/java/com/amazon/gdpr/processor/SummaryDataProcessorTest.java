
package com.amazon.gdpr.processor;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import com.amazon.gdpr.dao.GdprOutputDaoImpl;
import com.amazon.gdpr.dao.RunSummaryDaoImpl;
import com.amazon.gdpr.model.gdpr.output.RunModuleMgmt;
import com.amazon.gdpr.model.gdpr.output.RunSummaryMgmt;
import com.amazon.gdpr.model.gdpr.output.SummaryData;
import com.amazon.gdpr.util.GdprException;

@RunWith(MockitoJUnitRunner.class)
public class SummaryDataProcessorTest {

	@Mock
	GdprOutputDaoImpl gdprOutputDaoImpl;
	@Mock
	ModuleMgmtProcessor moduleMgmtProcessor;
	@InjectMocks
	SummaryDataProcessor SummaryDataProcessor;

	@Mock
	TagQueryProcessor tagQueryProcessor;

	@Mock
	RunSummaryDaoImpl runSummaryDaoImpl;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void processSummaryDataTest() throws GdprException {
		Date moduleStartDateTime = null;
		Date moduleEndDateTime = null;
		moduleStartDateTime = new Date();
		moduleEndDateTime = new Date();
		RunModuleMgmt runModuleMgmt = new RunModuleMgmt(2L, "Initialization Module", "Anonymize Initialize Sub Module",
				"SUCCESS", moduleStartDateTime, moduleStartDateTime, "Run Anonymization Initiated Count : 0","");
		Long runId = 7L;
		Mockito.when(runSummaryDaoImpl.fetchSummaryDetails(runId)).thenReturn(lstSummaryData());
		Mockito.doNothing().when(moduleMgmtProcessor).initiateModuleMgmt(runModuleMgmt);
		Mockito.when(tagQueryProcessor.updateSummaryQuery(runId, lstRunSummaryMgmt())).thenReturn(lstRunSummaryMgmt());
		String summaryDataProcessStatus = "Summary details : 0";
		String summaryDataProcessStatusRes = SummaryDataProcessor.processSummaryData(runId);
		 assertEquals(summaryDataProcessStatus,summaryDataProcessStatusRes);
	}


	@Test(expected = Exception.class)
	public void processSummaryDataExceptionTest4() throws GdprException {
		Date moduleStartDateTime = null;
		Date moduleEndDateTime = null;
		moduleStartDateTime = new Date();
		moduleEndDateTime = new Date();
		RunModuleMgmt runModuleMgmt = new RunModuleMgmt(2L, "Initialization Module", "Anonymize Initialize Sub Module",
				"FAILURE", moduleStartDateTime, moduleStartDateTime, "Run Anonymization Initiated Count : 0","");
		Long runId = 7L;
		Mockito.when(runSummaryDaoImpl.fetchSummaryDetails(anyInt())).thenThrow(Exception.class);
		Mockito.doNothing().when(moduleMgmtProcessor).initiateModuleMgmt(runModuleMgmt);
		Mockito.when(tagQueryProcessor.updateSummaryQuery(anyInt(), anyList())).thenThrow(Exception.class);
		SummaryDataProcessor.processSummaryData(runId);
	}
	@Test(expected = Exception.class)
	public void processSummaryDataExceptionTest3() throws GdprException {
		Date moduleStartDateTime = null;
		Date moduleEndDateTime = null;
		moduleStartDateTime = new Date();
		moduleEndDateTime = new Date();
		RunModuleMgmt runModuleMgmt = new RunModuleMgmt(2L, "Initialization Module", "Anonymize Initialize Sub Module",
				"SUCCESS", moduleStartDateTime, moduleStartDateTime, "Run Anonymization Initiated Count : 0","");
		Long runId = 7L;
		Mockito.when(runSummaryDaoImpl.fetchSummaryDetails(anyInt())).thenThrow(Exception.class);
		Mockito.doThrow(Exception.class).when(moduleMgmtProcessor).initiateModuleMgmt(anyObject());
		Mockito.when(tagQueryProcessor.updateSummaryQuery(anyInt(), anyList())).thenThrow(Exception.class);
		SummaryDataProcessor.processSummaryData(runId);
	}
	@Test(expected = Exception.class)
	public void processSummaryDataExceptionTest2() throws GdprException {
		Date moduleStartDateTime = null;
		Date moduleEndDateTime = null;
		moduleStartDateTime = new Date();
		moduleEndDateTime = new Date();
		RunModuleMgmt runModuleMgmt = new RunModuleMgmt(2L, "Initialization Module", "Anonymize Initialize Sub Module",
				"SUCCESS", moduleStartDateTime, moduleStartDateTime, "Run Anonymization Initiated Count : 0","");
		Long runId = 7L;
		Mockito.when(runSummaryDaoImpl.fetchSummaryDetails(runId)).thenThrow(Exception.class);
		Mockito.doThrow(Exception.class).when(moduleMgmtProcessor).initiateModuleMgmt(anyObject());
		Mockito.when(tagQueryProcessor.updateSummaryQuery(anyInt(), anyList())).thenThrow(Exception.class);
		SummaryDataProcessor.processSummaryData(runId);
	}

	@Test(expected = Exception.class)
	public void processSummaryDataExceptionTest() throws GdprException {
		Date moduleStartDateTime = null;
		Date moduleEndDateTime = null;
		moduleStartDateTime = new Date();
		moduleEndDateTime = new Date();
		RunModuleMgmt runModuleMgmt = new RunModuleMgmt(2L, "Initialization Module", "Anonymize Initialize Sub Module",
				"SUCCESS", moduleStartDateTime, moduleStartDateTime, "Run Anonymization Initiated Count : 0","");
		Mockito.doThrow(Exception.class).when(moduleMgmtProcessor).initiateModuleMgmt(anyObject());
		Long runId = 7L;
		 SummaryDataProcessor.processSummaryData(runId);
	}

	@Test
	public void extractSummaryDetailsTest() throws GdprException {
		Long runId = 2L;
		Mockito.when(runSummaryDaoImpl.fetchSummaryDetails(runId)).thenReturn(lstSummaryData());
		List<SummaryData> lstSummaryDataResult = SummaryDataProcessor.extractSummaryDetails(runId);
		assertEquals(lstSummaryData().size(), lstSummaryDataResult.size());

	}

	@Test(expected = Exception.class)
	public void extractSummaryDetailsNegativeTest() throws GdprException {
		Long runId = 7L;
		Mockito.when(runSummaryDaoImpl.fetchSummaryDetails(runId)).thenThrow(Exception.class);
		SummaryDataProcessor.extractSummaryDetails(runId);

	}

	@Test
	public void loadRunSummaryMgmtPositiveTest() throws GdprException {
		int batchSize = 1000;
		Mockito.doNothing().when(runSummaryDaoImpl).batchInsertRunSummaryMgmt(lstRunSummaryMgmt(), batchSize);
		long runId = 7L;
		SummaryDataProcessor.loadRunSummaryMgmt(runId, lstRunSummaryMgmt());
	}

	@Test(expected = Exception.class)
	public void loadRunSummaryMgmtExceptionTest() throws GdprException {
		List<RunSummaryMgmt> lstRunSummaryMgmt = new ArrayList<RunSummaryMgmt>();
		RunSummaryMgmt runSummaryMgmt1 = new RunSummaryMgmt(1L, 1, "EUR-EU", "AUT", 1, "APPLICATION__C",
				"select * from APPLICATION__C", " Alter Table APPLICATION__C"); //
		runSummaryMgmt1.setTaggedQueryLoad("Update table APPLICATION__C set ..where ");
		RunSummaryMgmt runSummaryMgmt2 = new RunSummaryMgmt(1L, 1, "EUR-EU", "AUT", 1, "INTERVIEW__C",
				"select * from INTERVIEW__C", " Alter Table INTERVIEW__C");
		lstRunSummaryMgmt.add(runSummaryMgmt2);
		lstRunSummaryMgmt.add(runSummaryMgmt1);

		int batchSize = 10;
		Mockito.doThrow(Exception.class).when(runSummaryDaoImpl).batchInsertRunSummaryMgmt(anyList(), anyInt());
		long runId = 1L;
		SummaryDataProcessor.loadRunSummaryMgmt(runId, lstRunSummaryMgmt);
	}

	@Test
	public void transformSummaryDetailsTest() throws GdprException {
		long runId = 7L;
		List<RunSummaryMgmt> lstRunSummaryMgmtRes = SummaryDataProcessor.transformSummaryDetails(runId,
				lstSummaryData());
		assertEquals(lstRunSummaryMgmt().size(), lstRunSummaryMgmtRes.size());
	}



	

	private List<RunSummaryMgmt> lstRunSummaryMgmt() {
		List<RunSummaryMgmt> lstRunSummaryMgmt = new ArrayList<RunSummaryMgmt>();
		RunSummaryMgmt runSummaryMgmt1 = new RunSummaryMgmt(7L, 2, "EUR-EU", "AUT", 2, "APPLICATION__C",
				"select * from APPLICATION__C..", " Alter Table APPLICATION__C..");
		RunSummaryMgmt runSummaryMgmt2 = new RunSummaryMgmt(7L, 2, "EUR-EU", "BEL", 3, "INTERVIEW__C",
				"select * from INTERVIEW__C..", " Alter Table INTERVIEW__C..");
		RunSummaryMgmt runSummaryMgmt3 = new RunSummaryMgmt(7L, 2, "EUR-EU", "BEL", 4, "TASK", "select * from TASK..",
				" Alter Table TASK..");
		RunSummaryMgmt runSummaryMgmt4 = new RunSummaryMgmt(7L, 2, "EUR-EU", "BEL", 8, "ATTACHMENT", "select * from ATTACHMENT..",
				" Alter Table ATTACHMENT..");
		RunSummaryMgmt runSummaryMgmt5 = new RunSummaryMgmt(7L, 2, "EUR-EU", "BEL", 9, "RESPONSE__C",
				"select * from RESPONSE__C..", " Alter Table RESPONSE__C..");
		//Set data ussing setters
		RunSummaryMgmt runSummaryMgmt6 = new RunSummaryMgmt();
		runSummaryMgmt6.setBackupQuery("select * from INTERVIEW__C..");;
		runSummaryMgmt6.setBackupRowCount(12);
		runSummaryMgmt6.setCategoryId(1);
		runSummaryMgmt6.setCountryCode("AUT");
		runSummaryMgmt6.setDepersonalizationQuery("Alter Table INTERVIEW__C..");
		runSummaryMgmt6.setDepersonalizedRowCount(12);
		runSummaryMgmt6.setImpactTableId(2);
		runSummaryMgmt6.setImpactTableName("APPLICATION__C");
		runSummaryMgmt6.setRegion("EUR-EU");
		runSummaryMgmt6.setRunId(2);
		runSummaryMgmt6.setSummaryId(4);
		runSummaryMgmt6.setTaggedQueryLoad("select * from INTERVIEW__C....");
		runSummaryMgmt6.setTaggedRowCount(12);
		runSummaryMgmt6.getBackupQuery();
		runSummaryMgmt6.getBackupRowCount();
		runSummaryMgmt6.getCategoryId();
		runSummaryMgmt6.getCountryCode();
		runSummaryMgmt6.getDepersonalizationQuery();
		runSummaryMgmt6.getDepersonalizedRowCount();
		runSummaryMgmt6.getImpactTableId();
		runSummaryMgmt6.getImpactTableName();
		runSummaryMgmt6.getRegion();
		runSummaryMgmt6.getRunId();
		runSummaryMgmt6.getSummaryId();
		runSummaryMgmt6.getTaggedQueryLoad();
		runSummaryMgmt6.getTaggedRowCount();
		runSummaryMgmt6.toString();
		RunSummaryMgmt runSummaryMgmt7 = new RunSummaryMgmt(7L, 2, "EUR-EU", "AUT", 2, "APPLICATION__C",
				"select * from APPLICATION__C..", " Alter Table APPLICATION__C..");
		RunSummaryMgmt runSummaryMgmt8 = new RunSummaryMgmt(7L, 2, 1,"EUR-EU", "AUT", 2, "APPLICATION__C",
				"select * from APPLICATION__C..", " Alter Table APPLICATION__C..",11L,11L,11L);
		RunSummaryMgmt runSummaryMgmt9 = new RunSummaryMgmt( 1l,  2l,  1,  "AUT",  2,
		        "APPLICATION_C",  "Test", "Test" ,  "Test");
		lstRunSummaryMgmt.add(runSummaryMgmt2);
		lstRunSummaryMgmt.add(runSummaryMgmt1);
		lstRunSummaryMgmt.add(runSummaryMgmt3);
		lstRunSummaryMgmt.add(runSummaryMgmt4);
		lstRunSummaryMgmt.add(runSummaryMgmt5);
		return lstRunSummaryMgmt;
	}

	private List<SummaryData> lstSummaryData() {
		List<SummaryData> lstSummaryData = new ArrayList<SummaryData>();
		SummaryData summaryData1 = new SummaryData(2, "EUR-EU", "AUT", "APPLICATION__C", "SF_ARCHIVE",
				"FELONY_CONVICTION_QUESTION_2__C", "TEXT", "PRIVACY DELETED", 2);
		SummaryData summaryData2 = new SummaryData(2, "EUR-EU", "BEL", "INTERVIEW__C", "SF_ARCHIVE", "CANDIDATE_C",
				"TEXT", "NULL", 3);
		SummaryData summaryData3 = new SummaryData(2, "EUR-EU", "BEL", "TASK", "SF_ARCHIVE", "CANDIDATE_C", "TEXT",
				"EMPTY", 4);
		SummaryData summaryData4 = new SummaryData(2, "EUR-EU", "BEL", "ATTACHMENT", "SF_ARCHIVE", "CANDIDATE_C",
				"TEXT", "ALL ZEROS", 8);
		SummaryData summaryData5 = new SummaryData(2, "EUR-EU", "BEL", "RESPONSE__C", "SF_ARCHIVE", "CANDIDATE_C",
				"TEXT", "PRIVACY DELETED", 9);
		//set data using setters
		SummaryData summaryData6 = new SummaryData(2, "EUR-EU", "BEL", "RESPONSE__C", "SF_ARCHIVE", "CANDIDATE_C",
				"TEXT", "PRIVACY DELETED", 9);
		summaryData6.setCategoryId(1);
		summaryData6.setCountryCode("AUT");
		summaryData6.setImpactFieldName("ADPSCREENINGID__C");
		summaryData6.setImpactFieldType("TEXT");
		summaryData6.setImpactSchema("SF_ARCHIVE");
		summaryData6.setImpactTableId(1);
		summaryData6.setImpactTableName("RESPONSE__C");
		summaryData6.setRegion("EUR-EU");
		summaryData6.setTransformationType("PRIVACY DELETED");
//		3	EUR-EU	AUT	ASSESSMENT__C	SF_ARCHIVE	ADPSCREENINGID__C	TEXT	PRIVACY DELETED	6
//		3	EUR-EU	AUT	RESPONSE__C	SF_ARCHIVE	INTERVIEWER_COMMENT__C	TEXT	PRIVACY DELETED	9		
		lstSummaryData.add(summaryData2);
		lstSummaryData.add(summaryData1);
		lstSummaryData.add(summaryData3);
		lstSummaryData.add(summaryData4);
		lstSummaryData.add(summaryData5);
		return lstSummaryData;
	}
}
