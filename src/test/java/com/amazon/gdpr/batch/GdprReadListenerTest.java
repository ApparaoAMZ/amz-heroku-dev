package com.amazon.gdpr.batch;

import static org.hamcrest.CoreMatchers.any;
import static org.mockito.Matchers.any;
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

	
	String impactedClass = "Test";
	Date moduleStartDateTime = new Date();
	long runId = 7L;
	@InjectMocks
	GdprReadListener gdprReadListener = new GdprReadListener(impactedClass, moduleStartDateTime, runId);

	@Mock
	RunMgmtProcessor runMgmtProcessor;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	
	}

	@Test
	//@Ignore
	public void beforeReadTest() {
		gdprReadListener.beforeRead();
	}
	
	

	@Test
	@Ignore
	public void afterRead() {
		gdprReadListener.afterRead(any());
	}

	@Test
	//@Ignore
	public void onReadErrorTest() throws GdprException { 
		String impactedClass = "Test";
		Date moduleStartDateTime = new Date();
		long runId = 7L;

		String errorDetails = "[Ljava.lang.StackTraceElement;@614ca7df";
		Exception exception = new Exception();
		String reOrganizeDataStatus = "Facing issues in reading GDPR_Depersonalization table. ";
		RunModuleMgmt runModuleMgmt = new RunModuleMgmt(runId, GlobalConstants.MODULE_INITIALIZATION,
				GlobalConstants.SUB_MODULE_REORGANIZE_JOB_INITIALIZE, GlobalConstants.STATUS_FAILURE,
				moduleStartDateTime, moduleStartDateTime, reOrganizeDataStatus, errorDetails);
		Mockito.doNothing().when(moduleMgmtProcessor).initiateModuleMgmt(runModuleMgmt);
		Mockito.doNothing().when(runMgmtDaoImpl).updateRunComments(runId, reOrganizeDataStatus);
		gdprReadListener.onReadError(exception);
	}

}
