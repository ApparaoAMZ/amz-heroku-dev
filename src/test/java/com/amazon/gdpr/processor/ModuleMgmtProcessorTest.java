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

import com.amazon.gdpr.dao.GdprOutputDaoImpl;
import com.amazon.gdpr.dao.RunMgmtDaoImpl;
import com.amazon.gdpr.model.gdpr.output.RunErrorMgmt;
import com.amazon.gdpr.model.gdpr.output.RunModuleMgmt;
import com.amazon.gdpr.util.GdprException;
import com.amazon.gdpr.util.GlobalConstants;

public class ModuleMgmtProcessorTest {
	int runId = 0;
	@InjectMocks
	ModuleMgmtProcessor moduleMgmtProcessor;
	@Mock
	RunMgmtDaoImpl runMgmtDaoImpl;

	@Mock
	GdprOutputDaoImpl gdprOutputDaoImpl;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void initiateModuleMgmtPositiveTest() throws GdprException {
		Date moduleStartDateTime = null;
		Date moduleEndDateTime = null;
		moduleStartDateTime = new Date();
		moduleEndDateTime = new Date();
		RunModuleMgmt runModuleMgmt = new RunModuleMgmt(2L, "Initialization Module", "Anonymize Initialize Sub Module",
				"SUCCESS", moduleStartDateTime, moduleStartDateTime, "Run Anonymization Initiated Count : 0", "");
		Mockito.doNothing().when(runMgmtDaoImpl).insertModuleUpdates(runModuleMgmt);
		moduleMgmtProcessor.initiateModuleMgmt(runModuleMgmt);

	}

	@Test(expected = Exception.class)
	public void initiateModuleMgmtExceptionTest() throws GdprException {

		Date moduleStartDateTime = null;
		Date moduleEndDateTime = null;
		moduleStartDateTime = new Date();
		moduleEndDateTime = new Date(); //
		RunModuleMgmt runModuleMgmt = new RunModuleMgmt(2L, "Initialization Module", "Anonymize Initialize Sub Module",
				"FAILURE", moduleStartDateTime, moduleStartDateTime, "Run Anonymization Initiated Count : 0", "");
		Mockito.doThrow(Exception.class).when(runMgmtDaoImpl).insertModuleUpdates(runModuleMgmt);
		moduleMgmtProcessor.initiateModuleMgmt(runModuleMgmt);
	}

	/*
	 * @Test(expected=Exception.class) public void prevJobModuleStatusFalseTest()
	 * throws GdprException { //int count =
	 * runMgmtDaoImpl.prevModuleRunStatus(runId, moduleName); long runId=3L; String
	 * moduleName="Initialization Module"; int count = 1; String prevModuleStatus =
	 * GlobalConstants.STATUS_SUCCESS;
	 * Mockito.when(runMgmtDaoImpl.prevModuleRunStatus(runId,
	 * moduleName)).thenReturn(lstrunModuleMgmt()); String
	 * res=moduleMgmtProcessor.prevJobModuleStatus(runId, moduleName);
	 * assertEquals(prevModuleStatus, res); }
	 */

	@Test
	public void prevJobModuleStatusIntializationSuccessTest() throws GdprException {
		long runId = 2L;
		String moduleName = GlobalConstants.MODULE_INITIALIZATION;
		String prevModuleStatus = "SUCCESS";
		Mockito.when(runMgmtDaoImpl.prevModuleRunStatus(runId)).thenReturn(lstrunModuleMgmtIntializationSuccess());
		String response = moduleMgmtProcessor.prevJobModuleStatus(runId);
		assertEquals(prevModuleStatus, response);
	}

	@Test
	public void prevJobModuleStatusIntializationFailureTest() throws GdprException {
		long runId = 2L;
		String moduleName = GlobalConstants.MODULE_INITIALIZATION;
		String prevModuleStatus = "FAILURE";
		Mockito.when(runMgmtDaoImpl.prevModuleRunStatus(runId)).thenReturn(lstrunModuleMgmIntializationFailure());
		String res = moduleMgmtProcessor.prevJobModuleStatus(runId);
		assertEquals(prevModuleStatus, res);
	}

	private List<RunModuleMgmt> lstrunModuleMgmtIntializationSuccess() {
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
		runModuleMgmt1.setSubModuleName("Anonymize Initialize Sub Module");
		runModuleMgmt1.setModuleName("GlobalConstants.MODULE_INITIALIZATION");
		runModuleMgmt1.setRunId(2L);
		runModuleMgmt1.setModuleStatus("SUCCESS");
		runModuleMgmt1.setModuleId(1L);

		RunModuleMgmt runModuleMgmt2 = new RunModuleMgmt();
		runModuleMgmt2.setComments("Run Anonymization Initiated Count : 0");
		runModuleMgmt2.setCount(4);
		runModuleMgmt2.setModuleEndDateTime(moduleEndDateTime);
		runModuleMgmt2.setModuleStartDateTime(moduleStartDateTime);
		runModuleMgmt2.setSubModuleName("Anonymize Initialize Sub Module");
		runModuleMgmt2.setModuleName("GlobalConstants.MODULE_INITIALIZATION");
		runModuleMgmt2.setRunId(2L);
		runModuleMgmt2.setModuleStatus("SUCCESS");
		runModuleMgmt2.setModuleId(1L);
		lstrunModuleMgmt.add(runModuleMgmt2);

		lstrunModuleMgmt.add(runModuleMgmt1);

		return lstrunModuleMgmt;
	}

	private List<RunModuleMgmt> lstrunModuleMgmIntializationFailure() {
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
		runModuleMgmt2.setModuleStatus("FAILURE");
		runModuleMgmt2.setModuleId(1L);
		lstrunModuleMgmt.add(runModuleMgmt2);
		return lstrunModuleMgmt;
	}


	@Test(expected = Exception.class)
	public void prevJobModuleStatusExceptionTest() throws GdprException {
		long runId = 3L;
		String moduleName = GlobalConstants.MODULE_DEPERSONALIZATION;
		int count = 7;
		Mockito.when(runMgmtDaoImpl.prevModuleRunStatus(runId)).thenThrow(Exception.class);
		moduleMgmtProcessor.prevJobModuleStatus(runId);
	}
}
