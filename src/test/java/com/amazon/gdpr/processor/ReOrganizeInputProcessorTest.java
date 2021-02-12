package com.amazon.gdpr.processor;

import static org.junit.Assert.assertEquals;

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

import com.amazon.gdpr.dao.GdprInputDaoImpl;
import com.amazon.gdpr.dao.GdprOutputDaoImpl;
import com.amazon.gdpr.dao.RunMgmtDaoImpl;
import com.amazon.gdpr.model.gdpr.output.RunModuleMgmt;
import com.amazon.gdpr.processor.ReOrganizeInputProcessor.JobThread;
import com.amazon.gdpr.service.BackupService;
import com.amazon.gdpr.util.GdprException;
import com.amazon.gdpr.util.GlobalConstants;

public class ReOrganizeInputProcessorTest {
	@InjectMocks
	ReOrganizeInputProcessor reOrganizeInputProcessor;
	@Mock
	JobLauncher jobLauncher;
	
	@Mock
    Job processreorganizeInputJob;
	
	@Mock
	RunMgmtProcessor runMgmtProcessor;

	@Mock
	ModuleMgmtProcessor moduleMgmtProcessor;
			
	@Mock
	GdprInputDaoImpl gdprInputDaoImpl;
	
	@Mock
	GdprOutputDaoImpl gdprOutputDaoImpl;
	
	@Mock
	RunMgmtDaoImpl runMgmtDaoImpl;
	
	@Mock
	BackupService backupService;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void reOrganizeDataSuccessTest() throws GdprException {
		JobThread jobThread ;
		List<String> selectedCountries = new ArrayList<String>();
		selectedCountries.add("AUT");
		selectedCountries.add("BEL");
		long runId=3L;
		Date moduleStartDateTime = null;
		Date moduleEndDateTime = null;
		moduleStartDateTime = new Date();
		moduleEndDateTime = new Date();
		String errorDetails="";
		String moduleStatus="SUCCESS";
		String prevJobModuleStatus="SUCCESS";
		String reOrganizeDataStatus=GlobalConstants.MSG_REORGANIZEINPUT_JOB;
		String resActual=GlobalConstants.MSG_REORGANIZEINPUT_JOB;
		RunModuleMgmt runModuleMgmt = new RunModuleMgmt(runId, GlobalConstants.MODULE_INITIALIZATION, 
				GlobalConstants.SUB_MODULE_REORGANIZE_JOB_INITIALIZE, moduleStatus,moduleStartDateTime, 
				new Date(), reOrganizeDataStatus, errorDetails);
		Mockito.doNothing().when(moduleMgmtProcessor).initiateModuleMgmt(runModuleMgmt);
		Mockito.when(moduleMgmtProcessor.prevJobModuleStatus(runId)).thenReturn(prevJobModuleStatus);
		Mockito.doNothing().when(runMgmtDaoImpl).updateRunComments(runId, reOrganizeDataStatus);
		String backupServiceResponse=GlobalConstants.MSG_BACKUPSERVICE_JOB;
		Mockito.when(backupService.backupServiceInitiate(runId)).thenReturn(backupServiceResponse);
		String response=reOrganizeInputProcessor.reOrganizeData(runId, selectedCountries);
		jobThread= reOrganizeInputProcessor.new JobThread(runId, selectedCountries);
		jobThread.run();
		assertEquals(resActual, response);
	}
	
	@Test
	public void reOrganizeDataFAILURETest() throws GdprException {
		JobThread jobThread ;
		List<String> selectedCountries = new ArrayList<String>();
		selectedCountries.add("AUT");
		selectedCountries.add("BEL");
		long runId=3L;
		Date moduleStartDateTime = null;
		Date moduleEndDateTime = null;
		moduleStartDateTime = new Date();
		moduleEndDateTime = new Date();
		String errorDetails="";
		String moduleStatus="FAILURE";
		String prevJobModuleStatus="FAILURE";
		String reOrganizeDataStatus=GlobalConstants.MSG_REORGANIZEINPUT_JOB;
		String resActual=GlobalConstants.MSG_REORGANIZEINPUT_JOB;
		RunModuleMgmt runModuleMgmt = new RunModuleMgmt(runId, GlobalConstants.MODULE_INITIALIZATION, 
				GlobalConstants.SUB_MODULE_REORGANIZE_JOB_INITIALIZE, moduleStatus,moduleStartDateTime, 
				new Date(), reOrganizeDataStatus, errorDetails);
		Mockito.doNothing().when(moduleMgmtProcessor).initiateModuleMgmt(runModuleMgmt);
		Mockito.when(moduleMgmtProcessor.prevJobModuleStatus(runId)).thenReturn(prevJobModuleStatus);
		Mockito.doNothing().when(runMgmtDaoImpl).updateRunStatus(runId,GlobalConstants.STATUS_FAILURE, reOrganizeDataStatus);
		String backupServiceResponse=GlobalConstants.MSG_BACKUPSERVICE_JOB;
		//Mockito.when(backupService.backupServiceInitiate(runId)).thenReturn(backupServiceResponse);
		String response=reOrganizeInputProcessor.reOrganizeData(runId, selectedCountries);
		jobThread= reOrganizeInputProcessor.new JobThread(runId, selectedCountries);
		jobThread.run();
		assertEquals(resActual, response);
	}
	@Test(expected=Exception.class)
	public void reOrganizeDataExceptionTest() throws GdprException {
		JobThread jobThread ;
		List<String> selectedCountries = new ArrayList<String>();
		selectedCountries.add("AUT");
		selectedCountries.add("BEL");
		long runId=3L;
		Date moduleStartDateTime = null;
		Date moduleEndDateTime = null;
		moduleStartDateTime = new Date();
		moduleEndDateTime = new Date();
		String errorDetails="[Ljava.lang.StackTraceElement;@614ca7df";
		String moduleStatus="FAILURE";
		String prevJobModuleStatus="FAILURE";
		String reOrganizeDataStatus=GlobalConstants.MSG_REORGANIZEINPUT_JOB;
		String resActual=GlobalConstants.MSG_REORGANIZEINPUT_JOB;
		RunModuleMgmt runModuleMgmt = new RunModuleMgmt(runId, GlobalConstants.MODULE_INITIALIZATION, 
				GlobalConstants.SUB_MODULE_REORGANIZE_JOB_INITIALIZE, moduleStatus,moduleStartDateTime, 
				new Date(), reOrganizeDataStatus, errorDetails);
		Mockito.doNothing().when(moduleMgmtProcessor).initiateModuleMgmt(runModuleMgmt);
		Mockito.when(moduleMgmtProcessor.prevJobModuleStatus(runId)).thenThrow(Exception.class);
		Mockito.doThrow(Exception.class).when(runMgmtDaoImpl).updateRunStatus(runId,GlobalConstants.STATUS_FAILURE, reOrganizeDataStatus);
		String response=reOrganizeInputProcessor.reOrganizeData(runId, selectedCountries);
		jobThread= reOrganizeInputProcessor.new JobThread(runId, selectedCountries);
		jobThread.run();
	}
}
