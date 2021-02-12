package com.amazon.gdpr.processor;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;

import com.amazon.gdpr.dao.BackupServiceDaoImpl;
import com.amazon.gdpr.dao.RunMgmtDaoImpl;
import com.amazon.gdpr.dao.RunSummaryDaoImpl;
import com.amazon.gdpr.model.gdpr.output.RunModuleMgmt;
import com.amazon.gdpr.model.gdpr.output.RunSummaryMgmt;
import com.amazon.gdpr.processor.AnonymizeProcessor.DepersonalizationJobThread;

import com.amazon.gdpr.util.GdprException;
import com.amazon.gdpr.util.GlobalConstants;

public class AnonymizeProcessorTest {
	@InjectMocks
	AnonymizeProcessor anonymizeProcessor;

	@Mock
	JobLauncher jobLauncher;

	@Mock
	Job processAnonymizeJob;

	@Mock
	ModuleMgmtProcessor moduleMgmtProcessor;

	@Mock
	RunSummaryDaoImpl runSummaryDaoImpl;

	@Mock
	RunMgmtDaoImpl runMgmtDaoImpl;

	@Mock
	BackupServiceDaoImpl backupServiceDaoImpl;

	@Mock
	DataLoadProcessor dataLoadProcessor;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void depersonalizationInitializeSuccessTest() throws GdprException {
		long runId = 7L;
		DepersonalizationJobThread jobThread;
		Mockito.when(runSummaryDaoImpl.fetchRunSummaryDetail(anyInt(),anyString())).thenReturn(lstRunSummaryMgmt());
		String prevModuleStatus = GlobalConstants.STATUS_SUCCESS;
		Mockito.when(moduleMgmtProcessor.prevJobModuleStatus(runId)).thenReturn(prevModuleStatus);
		int gdprDepCount = 2;
		Mockito.when(backupServiceDaoImpl.gdprDepStatusUpdate(runId, prevModuleStatus)).thenReturn(gdprDepCount);
		Mockito.doNothing().when(dataLoadProcessor).updateDataLoad(runId);
		String moduleStatus = GlobalConstants.STATUS_SUCCESS;
		String errorDetails = "";
		Date moduleStartDateTime = new Date();
		String depersonalizationDataStatus = "The Timestamp details are updated in DATA_LOAD table. ";
		RunModuleMgmt runModuleMgmt = new RunModuleMgmt(runId, GlobalConstants.MODULE_DEPERSONALIZATION,
				GlobalConstants.SUB_MODULE_ANONYMIZE_JOB_INITIALIZE, moduleStatus, moduleStartDateTime, new Date(),
				depersonalizationDataStatus, errorDetails);
		// dataLoadProcessor.updateDataLoad(runId);
		Mockito.doNothing().when(moduleMgmtProcessor).initiateModuleMgmt(runModuleMgmt);
		Mockito.doNothing().when(runMgmtDaoImpl).updateRunStatus(runId, GlobalConstants.STATUS_SUCCESS,
				depersonalizationDataStatus);
		anonymizeProcessor.depersonalizationInitialize(runId);
		jobThread = anonymizeProcessor.new DepersonalizationJobThread(runId);
		jobThread.run();
	}

	@Test
	public void depersonalizationInitializeFailureTest() throws GdprException {
		long runId = 7L;
		DepersonalizationJobThread jobThread;
		Mockito.when(runSummaryDaoImpl.fetchRunSummaryDetail(anyInt(),anyString())).thenReturn(lstRunSummaryMgmt());
		String prevModuleStatus = GlobalConstants.STATUS_FAILURE;
		Mockito.when(moduleMgmtProcessor.prevJobModuleStatus(runId)).thenReturn(prevModuleStatus);
		int gdprDepCount = 2;
		Mockito.when(backupServiceDaoImpl.gdprDepStatusUpdate(runId, prevModuleStatus)).thenReturn(gdprDepCount);
		// Mockito.doNothing().when(dataLoadProcessor).updateDataLoad(runId);
		String moduleStatus = GlobalConstants.STATUS_SUCCESS;
		String errorDetails = "";
		Date moduleStartDateTime = new Date();
		String depersonalizationDataStatus = "The Timestamp details are updated in DATA_LOAD table. ";
		RunModuleMgmt runModuleMgmt = new RunModuleMgmt(runId, GlobalConstants.MODULE_DEPERSONALIZATION,
				GlobalConstants.SUB_MODULE_ANONYMIZE_JOB_INITIALIZE, moduleStatus, moduleStartDateTime, new Date(),
				depersonalizationDataStatus, errorDetails);
		Mockito.doNothing().when(moduleMgmtProcessor).initiateModuleMgmt(runModuleMgmt);
		Mockito.doNothing().when(runMgmtDaoImpl).updateRunStatus(runId, GlobalConstants.STATUS_FAILURE,
				depersonalizationDataStatus);
		anonymizeProcessor.depersonalizationInitialize(runId);
		jobThread = anonymizeProcessor.new DepersonalizationJobThread(runId);
		jobThread.run();
	}
	

	@Test(expected = Exception.class)
	public void depersonalizationInitializeExceptionTest() throws GdprException {
		long runId = 7L;
		DepersonalizationJobThread jobThread;
		Mockito.when(runSummaryDaoImpl.fetchRunSummaryDetail(anyInt(),anyString())).thenReturn(lstRunSummaryMgmt());
		String prevModuleStatus = GlobalConstants.STATUS_FAILURE;
		Mockito.when(moduleMgmtProcessor.prevJobModuleStatus(runId)).thenReturn(prevModuleStatus);
		int gdprDepCount = 2;
		Mockito.when(backupServiceDaoImpl.gdprDepStatusUpdate(runId, prevModuleStatus)).thenReturn(gdprDepCount);
		Mockito.doNothing().when(dataLoadProcessor).updateDataLoad(runId);
		String moduleStatus = GlobalConstants.STATUS_SUCCESS;
		String errorDetails = "[Ljava.lang.StackTraceElement;@614ca7df";
		Date moduleStartDateTime = new Date();
		String depersonalizationDataStatus = "The Timestamp details are updated in DATA_LOAD table. ";
		RunModuleMgmt runModuleMgmt = new RunModuleMgmt(runId, GlobalConstants.MODULE_DEPERSONALIZATION,
				GlobalConstants.SUB_MODULE_ANONYMIZE_JOB_INITIALIZE, moduleStatus, moduleStartDateTime, new Date(),
				depersonalizationDataStatus, errorDetails);
		Mockito.doThrow(Exception.class).when(moduleMgmtProcessor).initiateModuleMgmt(anyObject());
		Mockito.doThrow(Exception.class).when(runMgmtDaoImpl).updateRunStatus(runId, GlobalConstants.STATUS_SUCCESS,
				depersonalizationDataStatus);
		anonymizeProcessor.depersonalizationInitialize(runId);
		jobThread = anonymizeProcessor.new DepersonalizationJobThread(runId);
		jobThread.run();
	}

	/*
	 * @Test(expected = Exception.class) public void
	 * depersonalizationInitializeExceptionTest() throws GdprException { long runId
	 * = 7L; DepersonalizationJobThread jobThread;
	 * Mockito.when(runSummaryDaoImpl.fetchRunSummaryDetail(runId)).thenReturn(
	 * lstRunSummaryMgmt()); String prevModuleStatus =
	 * GlobalConstants.STATUS_FAILURE;
	 * Mockito.when(moduleMgmtProcessor.prevJobModuleStatus(runId)).thenThrow(
	 * Exception.class); int gdprDepCount = 2;
	 * Mockito.when(backupServiceDaoImpl.gdprDepStatusUpdate(anyLong(),
	 * anyString())).thenThrow(Exception.class);
	 * Mockito.doNothing().when(dataLoadProcessor).updateDataLoad(runId); String
	 * moduleStatus = GlobalConstants.STATUS_SUCCESS; String errorDetails = ""; Date
	 * moduleStartDateTime = new Date(); String depersonalizationDataStatus =
	 * "The Timestamp details are updated in DATA_LOAD table. "; RunModuleMgmt
	 * runModuleMgmt = new RunModuleMgmt(runId,
	 * GlobalConstants.MODULE_DEPERSONALIZATION,
	 * GlobalConstants.SUB_MODULE_ANONYMIZE_JOB_INITIALIZE, moduleStatus,
	 * moduleStartDateTime, new Date(), depersonalizationDataStatus, errorDetails);
	 * Mockito.doNothing().when(moduleMgmtProcessor).initiateModuleMgmt(anyObject())
	 * ; Mockito.doNothing().when(runMgmtDaoImpl).updateRunStatus(runId,
	 * GlobalConstants.STATUS_SUCCESS, depersonalizationDataStatus);
	 * anonymizeProcessor.depersonalizationInitialize(runId); jobThread =
	 * anonymizeProcessor.new DepersonalizationJobThread(runId); jobThread.run(); }
	 */

	private List<RunSummaryMgmt> lstRunSummaryMgmt() {
		List<RunSummaryMgmt> lstRunSummaryMgmt = new ArrayList<RunSummaryMgmt>();
		RunSummaryMgmt runSummaryMgmt1 = new RunSummaryMgmt(7L, 1, "EUR-EU", "AUT", 1, "APPLICATION__C",
				"select * from APPLICATION__C..", " Alter Table APPLICATION__C.."); //
		runSummaryMgmt1.setTaggedQueryLoad("Update table APPLICATION__C set ..where ");
		RunSummaryMgmt runSummaryMgmt2 = new RunSummaryMgmt(7L, 1, "EUR-EU", "AUT", 1, "INTERVIEW__C",
				"select * from INTERVIEW__C ..", " Alter Table INTERVIEW__C ..");
		lstRunSummaryMgmt.add(runSummaryMgmt2);
		lstRunSummaryMgmt.add(runSummaryMgmt1);
		return lstRunSummaryMgmt;
	}
}
