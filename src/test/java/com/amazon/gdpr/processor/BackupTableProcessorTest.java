package com.amazon.gdpr.processor;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import com.amazon.gdpr.dao.BackupTableProcessorDaoImpl;
import com.amazon.gdpr.dao.GdprInputDaoImpl;
import com.amazon.gdpr.dao.GdprOutputDaoImpl;
import com.amazon.gdpr.dao.RunMgmtDaoImpl;
import com.amazon.gdpr.model.gdpr.input.ImpactTableDetails;
import com.amazon.gdpr.model.gdpr.output.BackupTableDetails;
import com.amazon.gdpr.model.gdpr.output.RunModuleMgmt;
import com.amazon.gdpr.util.GdprException;
import com.amazon.gdpr.util.GlobalConstants;

import junit.framework.TestCase;
@RunWith(value = BlockJUnit4ClassRunner.class)
public class BackupTableProcessorTest extends TestCase {
	@InjectMocks
	BackupTableProcessor backupTableProcessor;
	@Mock
	private BackupTableProcessorDaoImpl backupTableProcessorDaoImpl;
	@Mock
	GdprInputDaoImpl gdprInputDaoImpl;
	@Mock
	GdprOutputDaoImpl gdprOutputDaoImpl;
	@Mock
	ModuleMgmtProcessor moduleMgmtProcessor;
	@Mock
	RunMgmtDaoImpl runMgmtDaoImpl;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void processBkpupTablePositiveTest() throws GdprException {
		Mockito.when(backupTableProcessorDaoImpl.refreshBackupTables(lstBackupTableDetails())).thenReturn(Boolean.TRUE);
		boolean bkpupTableCheckStatus = true;
		Mockito.when(backupTableProcessorDaoImpl.alterBackupTable(any(String[].class)))
				.thenReturn(bkpupTableCheckStatus);
		Mockito.when(backupTableProcessorDaoImpl.fetchBackupTableDetails()).thenReturn(lstBackupTableDetails());
		Mockito.when(gdprInputDaoImpl.fetchImpactTableDetailsMap()).thenReturn(lstImpactTableDetails());
		long runId = 2L;
		String backupStatus = GlobalConstants.MSG_BKPUP_TABLE_STATUS;
		String moduleStatus=GlobalConstants.STATUS_SUCCESS;
		 RunModuleMgmt runModuleMgmt = new RunModuleMgmt(runId,
				  GlobalConstants.MODULE_INITIALIZATION,
				  GlobalConstants.SUB_MODULE_BACKUP_TABLE_INITIALIZE, moduleStatus,
				  new Date(), new Date(), backupStatus, "");
				 Mockito.doNothing().when(moduleMgmtProcessor).initiateModuleMgmt(runModuleMgmt);
				 Mockito.doNothing().when(runMgmtDaoImpl).updateRunStatus(runId, GlobalConstants.STATUS_FAILURE, backupStatus);
		
		String reponse = backupTableProcessor.processBkpupTable(runId);
		assertEquals(backupStatus, reponse);
	}
	
	@Test(expected=Exception.class)
	public void processBkpupTableExceptionTest() throws Exception {
		Mockito.when(backupTableProcessorDaoImpl.refreshBackupTables(lstBackupTableDetails()))
		.thenReturn(Boolean.TRUE);
		boolean bkpupTableCheckStatus = false;
		Mockito.when(backupTableProcessorDaoImpl.alterBackupTable(any(String[].class)))
		.thenReturn(bkpupTableCheckStatus);
		Mockito.when(backupTableProcessorDaoImpl.fetchBackupTableDetails()).thenReturn(lstBackupTableDetails());
		Mockito.when(gdprInputDaoImpl.fetchImpactTableDetailsMap()).thenReturn(lstImpactTableDetails());
		long runId = 2L;
		String backupStatus = GlobalConstants.MSG_BKPUP_TABLE_STATUS;
		String moduleStatus=GlobalConstants.STATUS_SUCCESS;
		 RunModuleMgmt runModuleMgmt = new RunModuleMgmt(runId,
				  GlobalConstants.MODULE_INITIALIZATION,
				  GlobalConstants.SUB_MODULE_BACKUP_TABLE_INITIALIZE, moduleStatus,
				  new Date(), new Date(), backupStatus, "");
				 Mockito.doThrow(Exception.class).when(moduleMgmtProcessor).initiateModuleMgmt(anyObject());
				 throwException();
				 Mockito.doNothing().when(runMgmtDaoImpl).updateRunStatus(runId, GlobalConstants.STATUS_FAILURE, backupStatus);
		 backupTableProcessor.processBkpupTable(runId);
	 throwException();
	}
	private Object throwException() throws Exception {
		  throw new Exception();
	}
	
	@Test(expected=Exception.class)
	public void processBkpupTablePositiveExceptionTest() throws GdprException {
		Mockito.when(backupTableProcessorDaoImpl.refreshBackupTables(lstBackupTableDetails())).thenReturn(Boolean.TRUE);
		boolean bkpupTableCheckStatus = true;
		Mockito.when(backupTableProcessorDaoImpl.alterBackupTable(any(String[].class)))
				.thenReturn(bkpupTableCheckStatus);
		Mockito.when(backupTableProcessorDaoImpl.fetchBackupTableDetails()).thenThrow(Exception.class);
		Mockito.when(gdprInputDaoImpl.fetchImpactTableDetailsMap()).thenThrow(Exception.class);
		long runId = 2L;
		String backupStatus = GlobalConstants.MSG_BKPUP_TABLE_STATUS;
		String reponse = backupTableProcessor.processBkpupTable(runId);
	}

	@Test
	public void processBkpupTableNegativeTest() throws GdprException {
		Mockito.when(backupTableProcessorDaoImpl.refreshBackupTables(lstBackupTableDetails())).thenReturn(Boolean.TRUE);
		boolean bkpupTableCheckStatus = false;
		Mockito.when(backupTableProcessorDaoImpl.alterBackupTable(any(String[].class)))
				.thenReturn(bkpupTableCheckStatus);
		Mockito.when(backupTableProcessorDaoImpl.fetchBackupTableDetails()).thenReturn(lstBackupTableDetails());
		String backupStatus = GlobalConstants.MSG_BKPUP_TABLE_STATUS;
		Long runId=7L;
		Date moduleStartDateTime = null;
		Date moduleEndDateTime = null;
		moduleStartDateTime = new Date();
		moduleEndDateTime = new Date();
		String moduleStatus=GlobalConstants.STATUS_SUCCESS;
		  RunModuleMgmt runModuleMgmt = new RunModuleMgmt(runId,
		  GlobalConstants.MODULE_INITIALIZATION,
		  GlobalConstants.SUB_MODULE_BACKUP_TABLE_INITIALIZE, moduleStatus,
		  moduleStartDateTime, new Date(), backupStatus, "");
		 Mockito.doNothing().when(moduleMgmtProcessor).initiateModuleMgmt(runModuleMgmt);
		Mockito.when(gdprInputDaoImpl.fetchImpactTableDetailsMap()).thenReturn(lstImpactTableDetails());
		String reponse = backupTableProcessor.processBkpupTable(runId);
		assertEquals(backupStatus, reponse);
	}
	
	@Test(expected=Exception.class)
	public void processBkpupTableNegativeExceptionTest() throws GdprException {
		Mockito.when(backupTableProcessorDaoImpl.refreshBackupTables(lstBackupTableDetails())).thenReturn(Boolean.TRUE);
		boolean bkpupTableCheckStatus = false;
		Mockito.when(backupTableProcessorDaoImpl.alterBackupTable(any(String[].class)))
				.thenReturn(bkpupTableCheckStatus);
		Mockito.when(backupTableProcessorDaoImpl.fetchBackupTableDetails()).thenReturn(lstBackupTableDetails());
		String backupStatus = GlobalConstants.MSG_BKPUP_TABLE_STATUS;
		Long runId=7L;
		Date moduleStartDateTime = null;
		Date moduleEndDateTime = null;
		moduleStartDateTime = new Date();
		moduleEndDateTime = new Date();
		String moduleStatus=GlobalConstants.STATUS_SUCCESS;
		  RunModuleMgmt runModuleMgmt = new RunModuleMgmt(runId,
		  GlobalConstants.MODULE_INITIALIZATION,
		  GlobalConstants.SUB_MODULE_BACKUP_TABLE_INITIALIZE, moduleStatus,
		  moduleStartDateTime, new Date(), backupStatus, "");
		 Mockito.doThrow(Exception.class).when(moduleMgmtProcessor).initiateModuleMgmt(anyObject());
		Mockito.when(gdprInputDaoImpl.fetchImpactTableDetailsMap()).thenReturn(lstImpactTableDetails());
		backupTableProcessor.processBkpupTable(runId);
	}

	@Test(expected = Exception.class)
	public void bkpupTableCheckTest() throws GdprException {
		boolean bkpupTableCheckStatus = false;
		Mockito.when(backupTableProcessorDaoImpl.alterBackupTable(any(String[].class))).thenThrow(Exception.class);
		backupTableProcessor.bkpupTableCheck(lstBackupTableDetails(), lstImpactTableDetails());
	}

	private List<BackupTableDetails> lstBackupTableDetails() {
		List<BackupTableDetails> lstBackupTableDetails = new ArrayList<BackupTableDetails>();
		BackupTableDetails lstBackupTableDetails1 = new BackupTableDetails("bkp_application__c", "id");
		BackupTableDetails lstBackupTableDetails2 = new BackupTableDetails("bkp_assessment__c", "id");
		BackupTableDetails lstBackupTableDetails3 = new BackupTableDetails("bkp_emailmessage", "id");
		BackupTableDetails lstBackupTableDetails4 = new BackupTableDetails("bkp_error_log__c", "id");
		BackupTableDetails lstBackupTableDetails5 = new BackupTableDetails("bkp_integration_transaction__c", "id");
		BackupTableDetails lstBackupTableDetails6 = new BackupTableDetails("bkp_interview__c", "id");
		BackupTableDetails lstBackupTableDetails7 = new BackupTableDetails("bkp_note", "id");
		BackupTableDetails lstBackupTableDetails8 = new BackupTableDetails("bkp_response__c", "id");
		BackupTableDetails lstBackupTableDetails9 = new BackupTableDetails("bkp_response_answer__c", "id");
		BackupTableDetails lstBackupTableDetails10 = new BackupTableDetails("bkp_task", "id");
		BackupTableDetails lstBackupTableDetails11 = new BackupTableDetails("bkp_user", "id");
		BackupTableDetails lstBackupTableDetails12 = new BackupTableDetails("bkp_attachment", "id");
		lstBackupTableDetails.add(lstBackupTableDetails2);
		lstBackupTableDetails.add(lstBackupTableDetails1);
		lstBackupTableDetails.add(lstBackupTableDetails3);
		lstBackupTableDetails.add(lstBackupTableDetails4);
		lstBackupTableDetails.add(lstBackupTableDetails5);
		lstBackupTableDetails.add(lstBackupTableDetails6);
		lstBackupTableDetails.add(lstBackupTableDetails7);
		lstBackupTableDetails.add(lstBackupTableDetails8);
		lstBackupTableDetails.add(lstBackupTableDetails9);
		lstBackupTableDetails.add(lstBackupTableDetails10);
		lstBackupTableDetails.add(lstBackupTableDetails11);
		lstBackupTableDetails.add(lstBackupTableDetails12);
		return lstBackupTableDetails;
	}

	private List<ImpactTableDetails> lstImpactTableDetails() {
		List<ImpactTableDetails> lstImpactTableDetails = new ArrayList<ImpactTableDetails>();
		ImpactTableDetails lstImpactTableDetails1 = new ImpactTableDetails("APPLICATION__C",
				"LEGACY_TALEO_HIRING_AREA_MANAGER__C", "TEXT");
		ImpactTableDetails lstImpactTableDetails2 = new ImpactTableDetails("APPLICATION__C", "CANDIDATE_LAST_NAME__C",
				"TEXT");
		ImpactTableDetails lstImpactTableDetails3 = new ImpactTableDetails("APPLICATION__C", "CANDIDATE_LAST_NAME__C",
				"TEXT");
		ImpactTableDetails lstImpactTableDetails4 = new ImpactTableDetails("INTERVIEW__C", "CANDIDATE_LAST_NAME__C",
				"TEXT");
		ImpactTableDetails lstImpactTableDetails5 = new ImpactTableDetails("TASK", "DESCRIPTION", "TEXT");
		ImpactTableDetails lstImpactTableDetails6 = new ImpactTableDetails("ASSESSMENT__C", "ADPSCREENINGID__C",
				"TEXT");
		ImpactTableDetails lstImpactTableDetails7 = new ImpactTableDetails("RESPONSE__C", "INTERVIEWER_COMMENT__C",
				"TEXT");
		ImpactTableDetails lstImpactTableDetails8 = new ImpactTableDetails("EMAILMESSAGE", "INTERVIEWER_COMMENT__C",
				"TEXT");
		ImpactTableDetails lstImpactTableDetails9 = new ImpactTableDetails("ERROR_LOG__C", "INTERVIEWER_COMMENT__C",
				"TEXT");
		ImpactTableDetails lstImpactTableDetails10 = new ImpactTableDetails("ATTACHMENT", "INTERVIEWER_COMMENT__C",
				"TEXT");
		ImpactTableDetails lstImpactTableDetails11 = new ImpactTableDetails("RESPONSE_ANSWER__C",
				"INTERVIEWER_COMMENT__C", "TEXT");
		ImpactTableDetails lstImpactTableDetails12 = new ImpactTableDetails("INTEGRATION_TRANSACTION__C",
				"INTERVIEWER_COMMENT__C", "TEXT");
		ImpactTableDetails lstImpactTableDetails13 = new ImpactTableDetails("USER", "INTERVIEWER_COMMENT__C", "TEXT");
		ImpactTableDetails lstImpactTableDetails14 = new ImpactTableDetails("NOTE", "INTERVIEWER_COMMENT__C", "TEXT");
		lstImpactTableDetails.add(lstImpactTableDetails1);
		lstImpactTableDetails.add(lstImpactTableDetails2);
		lstImpactTableDetails.add(lstImpactTableDetails3);
		lstImpactTableDetails.add(lstImpactTableDetails4);
		lstImpactTableDetails.add(lstImpactTableDetails5);
		lstImpactTableDetails.add(lstImpactTableDetails6);
		lstImpactTableDetails.add(lstImpactTableDetails7);
		lstImpactTableDetails.add(lstImpactTableDetails8);
		lstImpactTableDetails.add(lstImpactTableDetails9);
		lstImpactTableDetails.add(lstImpactTableDetails10);
		lstImpactTableDetails.add(lstImpactTableDetails11);
		lstImpactTableDetails.add(lstImpactTableDetails12);
		lstImpactTableDetails.add(lstImpactTableDetails13);
		lstImpactTableDetails.add(lstImpactTableDetails14);
		return lstImpactTableDetails;
	}

	@Test
	public void refreshBackupTablesPositiveTest() throws GdprException {
		List<BackupTableDetails> lstBackupTableDetails = new ArrayList<BackupTableDetails>();
		BackupTableDetails lstBackupTableDetails1 = new BackupTableDetails("bkp_assessment__c", "adpscreeningid__c");
		BackupTableDetails lstBackupTableDetails2 = new BackupTableDetails("bkp_interview__c",
				"candidate_last_name__c");
		lstBackupTableDetails.add(lstBackupTableDetails2);
		lstBackupTableDetails.add(lstBackupTableDetails1);
		Mockito.when(backupTableProcessorDaoImpl.refreshBackupTables(lstBackupTableDetails)).thenReturn(Boolean.TRUE);
		Boolean refreshBkpupTableStatus = backupTableProcessor.refreshBackupTables(lstBackupTableDetails);
		assertEquals(Boolean.TRUE, refreshBkpupTableStatus);

	}

	@Test(expected = Exception.class)
	public void refreshBackupTablesNegativeTest() throws GdprException {
		List<BackupTableDetails> lstBackupTableDetails = new ArrayList<BackupTableDetails>();
		BackupTableDetails lstBackupTableDetails1 = new BackupTableDetails("bkp_assessment__c", "adpscreeningid__c");
		BackupTableDetails lstBackupTableDetails2 = new BackupTableDetails("bkp_interview__c",
				"candidate_last_name__c");
		lstBackupTableDetails.add(lstBackupTableDetails2);
		lstBackupTableDetails.add(lstBackupTableDetails1);
		Mockito.when(backupTableProcessorDaoImpl.refreshBackupTables(lstBackupTableDetails)).thenThrow(Exception.class);
		backupTableProcessor.refreshBackupTables(lstBackupTableDetails);
	}

}
