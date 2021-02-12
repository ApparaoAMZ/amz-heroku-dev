package com.amazon.gdpr.processor;

import static org.mockito.Matchers.anyInt;
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

import com.amazon.gdpr.dao.RunMgmtDaoImpl;
import com.amazon.gdpr.dao.RunSummaryDaoImpl;
import com.amazon.gdpr.model.gdpr.output.RunErrorMgmt;
import com.amazon.gdpr.model.gdpr.output.RunModuleMgmt;
import com.amazon.gdpr.model.gdpr.output.RunSummaryMgmt;
import com.amazon.gdpr.processor.TagDataProcessor.TaggedJobThread;
import com.amazon.gdpr.util.GdprException;
import com.amazon.gdpr.util.GlobalConstants;

public class TagDataProcessorTest {
	@Mock
	JobLauncher jobLauncher;

	@Mock
	Job processTaggingJob;

	@Mock
	ModuleMgmtProcessor moduleMgmtProcessor;

	@InjectMocks
	TagDataProcessor tagDataProcessor;

	@Mock
	RunMgmtDaoImpl runMgmtDaoImpl;
	@Mock
	RunSummaryDaoImpl runSummaryDaoImpl;
	@Mock
	AnonymizeProcessor depersonalizationProcessor;
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void taggingInitializeSuccessTest() throws GdprException {
		long runId=7L;
		TaggedJobThread jobThread;
		Date moduleStartDateTime = new Date();
		Date moduleEndDateTime = new Date();
	
		String moduleStatus= GlobalConstants.STATUS_SUCCESS;
		String taggingDataStatus = GlobalConstants.ERR_TAGGED_JOB_RUN;
		String prevJobModuleStatus="SUCCESS";
		RunModuleMgmt runModuleMgmt = new RunModuleMgmt(runId, GlobalConstants.MODULE_DEPERSONALIZATION, 
				GlobalConstants.SUB_MODULE_TAG_JOB_INITIALIZE, moduleStatus, moduleStartDateTime, 
				moduleEndDateTime, taggingDataStatus,"");
		Mockito.when(runSummaryDaoImpl.fetchRunSummaryDetail(anyInt(),anyString())).thenReturn(lstRunSummaryMgmt());
		Mockito.doNothing().when(moduleMgmtProcessor).initiateModuleMgmt(runModuleMgmt);
		Mockito.when(moduleMgmtProcessor.prevJobModuleStatus(runId)).thenReturn(prevJobModuleStatus);
		Mockito.doNothing().when(depersonalizationProcessor).depersonalizationInitialize(runId);
		Mockito.doNothing().when(runMgmtDaoImpl).updateRunComments(runId,taggingDataStatus);
		tagDataProcessor.taggingInitialize(runId);
		jobThread = tagDataProcessor.new TaggedJobThread(runId);
		jobThread.run();
	}
	
	
	@Test
	public void taggingInitializeFailureTest() throws GdprException {
		long runId=7L;
		TaggedJobThread jobThread;
		Date moduleStartDateTime = new Date();
		Date moduleEndDateTime = new Date();
	
		String moduleStatus= GlobalConstants.STATUS_SUCCESS;
		String taggingDataStatus = GlobalConstants.ERR_TAGGED_JOB_RUN;
		String prevJobModuleStatus="FAILURE";
		RunModuleMgmt runModuleMgmt = new RunModuleMgmt(runId, GlobalConstants.MODULE_DEPERSONALIZATION, 
				GlobalConstants.SUB_MODULE_TAG_JOB_INITIALIZE, moduleStatus, moduleStartDateTime, 
				moduleEndDateTime, taggingDataStatus,"");
		Mockito.when(runSummaryDaoImpl.fetchRunSummaryDetail(anyInt(),anyString())).thenReturn(lstRunSummaryMgmt());
		Mockito.doNothing().when(moduleMgmtProcessor).initiateModuleMgmt(runModuleMgmt);
		Mockito.when(moduleMgmtProcessor.prevJobModuleStatus(runId)).thenReturn(prevJobModuleStatus);
		Mockito.doNothing().when(depersonalizationProcessor).depersonalizationInitialize(runId);
		Mockito.doNothing().when(runMgmtDaoImpl).updateRunStatus(runId,GlobalConstants.STATUS_FAILURE,taggingDataStatus);
		tagDataProcessor.taggingInitialize(runId);
		jobThread = tagDataProcessor.new TaggedJobThread(runId);
		jobThread.run();
	}
	
	@Test(expected=Exception.class)
	public void taggingInitializeExceptionTest() throws GdprException {
		long runId=7L;
		TaggedJobThread jobThread;
		Date moduleStartDateTime = new Date();
		Date moduleEndDateTime = new Date();
	
		String moduleStatus= GlobalConstants.STATUS_SUCCESS;
		String taggingDataStatus = GlobalConstants.ERR_TAGGED_JOB_RUN;
		String prevJobModuleStatus="FAILURE";
		RunModuleMgmt runModuleMgmt = new RunModuleMgmt(runId, GlobalConstants.MODULE_DEPERSONALIZATION, 
				GlobalConstants.SUB_MODULE_TAG_JOB_INITIALIZE, moduleStatus, moduleStartDateTime, 
				moduleEndDateTime, taggingDataStatus,"[Ljava.lang.StackTraceElement;@614ca7df");
		Mockito.when(runSummaryDaoImpl.fetchRunSummaryDetail(anyInt(),anyString())).thenReturn(lstRunSummaryMgmt());
		Mockito.doThrow(Exception.class).when(moduleMgmtProcessor).initiateModuleMgmt(runModuleMgmt);
		Mockito.when(moduleMgmtProcessor.prevJobModuleStatus(runId)).thenThrow(Exception.class);
		Mockito.doThrow(Exception.class).when(depersonalizationProcessor).depersonalizationInitialize(runId);
		Mockito.doThrow(Exception.class).when(runMgmtDaoImpl).updateRunStatus(runId,GlobalConstants.STATUS_FAILURE,taggingDataStatus);
		tagDataProcessor.taggingInitialize(runId);
		jobThread = tagDataProcessor.new TaggedJobThread(runId);
		jobThread.run();
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
		lstRunSummaryMgmt.add(runSummaryMgmt2);
		lstRunSummaryMgmt.add(runSummaryMgmt1);
		lstRunSummaryMgmt.add(runSummaryMgmt3);
		lstRunSummaryMgmt.add(runSummaryMgmt4);
		lstRunSummaryMgmt.add(runSummaryMgmt5);
		return lstRunSummaryMgmt;
	}
}
