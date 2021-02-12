package com.amazon.gdpr.batch;

import static org.mockito.Matchers.anyObject;

import java.util.Date;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.amazon.gdpr.dao.RunMgmtDaoImpl;
import com.amazon.gdpr.model.gdpr.output.RunModuleMgmt;
import com.amazon.gdpr.processor.ModuleMgmtProcessor;
import com.amazon.gdpr.processor.RunMgmtProcessor;
import com.amazon.gdpr.util.GdprException;
import com.amazon.gdpr.util.GlobalConstants;

@RunWith(MockitoJUnitRunner.class)
public class GdprReadListenerTest {

	@Mock
	ModuleMgmtProcessor moduleMgmtProcessor;

	@Mock
	RunMgmtDaoImpl runMgmtDaoImpl;

	@InjectMocks
	GdprReadListener gdprReadListener;

	@Mock
	RunMgmtProcessor runMgmtProcessor;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		String impactedClass = "Test";
		Date moduleStartDateTime = new Date();
		long runId = 7L;
		 gdprReadListener = new GdprReadListener(impactedClass, moduleStartDateTime, runId);
	}

	@Test
	@Ignore
	public void beforeReadTest() {
		gdprReadListener.beforeRead();
	}

	@Test
	@Ignore
	public void afterRead() {
		gdprReadListener.afterRead(anyObject());
	}

	/*
	 * @Test public void onReadErrorTest() throws GdprException { // String
	 * impactedClass = "Test"; Date moduleStartDateTime = new Date(); long runId =
	 * 7L; // GdprReadListener gdprReadListenerRef = new
	 * GdprReadListener(impactedClass, moduleStartDateTime, runId);
	 * 
	 * String errorDetails = ""; Exception exception = new Exception(); String
	 * reOrganizeDataStatus =
	 * "Facing issues in reading GDPR_Depersonalization table. "; RunModuleMgmt
	 * runModuleMgmt = new RunModuleMgmt(runId,
	 * GlobalConstants.MODULE_INITIALIZATION,
	 * GlobalConstants.SUB_MODULE_REORGANIZE_JOB_INITIALIZE,
	 * GlobalConstants.STATUS_FAILURE, moduleStartDateTime, new Date(),
	 * reOrganizeDataStatus, errorDetails);
	 * Mockito.doNothing().when(moduleMgmtProcessor).initiateModuleMgmt(
	 * runModuleMgmt);
	 * Mockito.doNothing().when(runMgmtDaoImpl).updateRunComments(runId,
	 * reOrganizeDataStatus); gdprReadListener.onReadError(exception); }
	 */

}
