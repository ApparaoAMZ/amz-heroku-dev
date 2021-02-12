
package com.amazon.gdpr.configuration;

import static org.mockito.Matchers.anyMap;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;

import com.amazon.gdpr.configuration.GdprBackupServiceBatchConfig.GdprBackupServiceProcessor;
import com.amazon.gdpr.configuration.ReOrganizeInputBatchConfig.ReorganizeDataProcessor;
import com.amazon.gdpr.dao.GdprInputDaoImpl;
import com.amazon.gdpr.dao.HvhOutputDaoImpl;
import com.amazon.gdpr.dao.RunMgmtDaoImpl;
import com.amazon.gdpr.model.GdprDepersonalizationInput;
import com.amazon.gdpr.model.GdprDepersonalizationOutput;
import com.amazon.gdpr.model.gdpr.output.RunModuleMgmt;
import com.amazon.gdpr.processor.ModuleMgmtProcessor;
import com.amazon.gdpr.util.GdprException;
import com.amazon.gdpr.util.GlobalConstants;

@RunWith(MockitoJUnitRunner.class)

//@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class ReOrganizeInputBatchConfigTest {

	@InjectMocks
	ReOrganizeInputBatchConfig reOrganizeInputBatchConfig;

	@Mock
	public JobBuilderFactory jobBuilderFactory;

	@Mock
	public StepBuilderFactory stepBuilderFactory;

	@Mock
	public DataSource dataSource;

	@Mock
	public HvhOutputDaoImpl hvhOutputDaoImpl;

	@Mock
	GdprInputDaoImpl gdprInputDaoImpl;

	@Mock
	ModuleMgmtProcessor moduleMgmtProcessor;

	@Mock
	RunMgmtDaoImpl runMgmtDaoImpl;

	/*
	 * @After public void cleanUp() { jobRepositoryTestUtils.removeJobExecutions();
	 * }
	 */

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void beforeStepTest() throws GdprException {
		ReorganizeDataProcessor reorganizeDataProcessor;
		reorganizeDataProcessor = reOrganizeInputBatchConfig.new ReorganizeDataProcessor();
		Long runId = 7L;
		Date moduleDateTime = new Date();
		String countryCode = "AUT";
		JobParameter jobParameter = new JobParameter(runId);
		JobParameter jobParameter2 = new JobParameter(countryCode);
		JobParameter jobParameter1 = new JobParameter(moduleDateTime);
		Map<String, JobParameter> obj = new HashMap<String, JobParameter>();
		obj.put(GlobalConstants.JOB_INPUT_RUN_ID, jobParameter);
		obj.put(GlobalConstants.JOB_INPUT_JOB_ID, jobParameter);
		obj.put(GlobalConstants.JOB_INPUT_START_DATE, jobParameter1);
		obj.put(GlobalConstants.JOB_INPUT_COUNTRY_CODE, jobParameter2);
		JobParameters jobParameters = new JobParameters(obj);
		JobExecution jobExecution = new JobExecution(7L, jobParameters);
		String stepName = "data";
		String date = "1900-01-01 00:00:00";
		StepExecution stepExecution = new StepExecution(stepName, jobExecution);
		Mockito.when(gdprInputDaoImpl.fetchCategoryDetails()).thenReturn(mapCategoryData());
		Mockito.when(gdprInputDaoImpl.getMapFieldCategory()).thenReturn(mapFieldCategoryData());
		reorganizeDataProcessor.beforeStep(stepExecution);
	}

	@Test(expected = Exception.class)
	public void beforeStepExceptionTest() throws GdprException {
		ReorganizeDataProcessor reorganizeDataProcessor;
		reorganizeDataProcessor = reOrganizeInputBatchConfig.new ReorganizeDataProcessor();
		Long runId = 7L;
		Date moduleDateTime = new Date();
		String countryCode = "AUT";
		JobParameter jobParameter = new JobParameter(runId);
		JobParameter jobParameter2 = new JobParameter(countryCode);
		JobParameter jobParameter1 = new JobParameter(moduleDateTime);
		Map<String, JobParameter> obj = new HashMap<String, JobParameter>();
		obj.put(GlobalConstants.JOB_INPUT_RUN_ID, jobParameter);
		obj.put(GlobalConstants.JOB_INPUT_JOB_ID, jobParameter);
		obj.put(GlobalConstants.JOB_INPUT_START_DATE, jobParameter1);
		obj.put(GlobalConstants.JOB_INPUT_COUNTRY_CODE, jobParameter2);
		JobParameters jobParameters = new JobParameters(obj);
		JobExecution jobExecution = new JobExecution(7L, jobParameters);
		String stepName = "data";
		String date = "1900-01-01 00:00:00";
		StepExecution stepExecution = new StepExecution(stepName, jobExecution);
		Mockito.when(gdprInputDaoImpl.fetchCategoryDetails()).thenThrow(Exception.class);
		Mockito.when(gdprInputDaoImpl.getMapFieldCategory()).thenThrow(Exception.class);
		RunModuleMgmt runModuleMgmt = new RunModuleMgmt(runId, GlobalConstants.MODULE_INITIALIZATION,
				GlobalConstants.SUB_MODULE_REORGANIZE_DATA, GlobalConstants.STATUS_FAILURE, new Date(), new Date(),
				"testData", "[Ljava.lang.StackTraceElement;@614ca7df");
		Mockito.doNothing().when(moduleMgmtProcessor).initiateModuleMgmt(runModuleMgmt);
		reorganizeDataProcessor.beforeStep(stepExecution);
	}



	@Test
	public void processTest()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Mockito.when(gdprInputDaoImpl.fetchCategoryDetails()).thenReturn(mapCategoryData());
		Mockito.when(gdprInputDaoImpl.getMapFieldCategory()).thenReturn(mapFieldCategoryData());
		Mockito.doNothing().when(hvhOutputDaoImpl)
				.batchInsertGdprDepersonalizationOutput(lstGdprDepersonalizationOutput());
		ReorganizeDataProcessor reorganizeDataProcessor;
		reorganizeDataProcessor = reOrganizeInputBatchConfig.new ReorganizeDataProcessor();
		reorganizeDataProcessor.process(gdprDepersonalizationInput());

	}
	//public String candidate;
	//public String category;
	//public String countryCode;
	//public String bgcStatus;
	//public String amazonAssessmentStatus;
	//public String candidateProvidedStatus;
	//public String masterDataStatus;
	//public String withConsentStatus;
	
	private GdprDepersonalizationInput gdprDepersonalizationInput() {
		GdprDepersonalizationInput gdprDepersonalizationInput = new GdprDepersonalizationInput();
		gdprDepersonalizationInput.setAmazonAssessmentStatus("Cleared");
		gdprDepersonalizationInput.setBgcStatus("Cleared");
		gdprDepersonalizationInput.setCandidate("Cleared");
		gdprDepersonalizationInput.setCategory("2");
		gdprDepersonalizationInput.setCountryCode("AUT");
		gdprDepersonalizationInput.setMasterDataStatus("Cleared");
		gdprDepersonalizationInput.setWithConsentStatus("Cleared");
		gdprDepersonalizationInput.setCandidateProvidedStatus("Cleared");
		gdprDepersonalizationInput.getAmazonAssessmentStatus();
		gdprDepersonalizationInput.getBgcStatus();
		gdprDepersonalizationInput.getCandidate();
		gdprDepersonalizationInput.getCandidateProvidedStatus();
		gdprDepersonalizationInput.getCategory();
		gdprDepersonalizationInput.getCountryCode();
		gdprDepersonalizationInput.getMasterDataStatus();
		gdprDepersonalizationInput.getWithConsentStatus();
		GdprDepersonalizationInput gdprDepersonalizationInput1 = new GdprDepersonalizationInput("CANDIDATE1", "1",
				"AUT", "Cleared", "Cleared", "Cleared", "Cleared", "Cleared");
		/*
		 * dprDepersonalizationInput(String candidate, String category, String
		 * countryCode, String bgcStatus, String amazonAssessmentStatus, String
		 * candidateProvidedStatus, String masterDataStatus, String withConsentStatus).
		 */
		return gdprDepersonalizationInput1;

	}

	private  Map<String,String> mapFieldCategoryData() {
		Map<String,String> mapFieldCategory= new HashMap<String,String>();
		mapFieldCategory.put("bgcStatus", "2");
		mapFieldCategory.put("amazonAssessmentStatus", "3");
		mapFieldCategory.put("candidateProvidedStatus", "4");
		mapFieldCategory.put("masterDataStatus", "5");
		mapFieldCategory.put("withConsentStatus", "6");
		return mapFieldCategory;
		
 	}
	private List<GdprDepersonalizationOutput> lstGdprDepersonalizationOutput() {
		List<GdprDepersonalizationOutput> lstGdprDepersonalizationOutput = new ArrayList<GdprDepersonalizationOutput>();
		GdprDepersonalizationOutput lst1 = new GdprDepersonalizationOutput(7L, "candidate2", 2, "AUT", "Test",
				"Test");
		GdprDepersonalizationOutput lst2 = new GdprDepersonalizationOutput(7L, "candidate3", 2, "AUT", "Test",
				"Test");
		lst2.setCandidate("Test");
		lst2.setCategory(1);
		lst2.setCountryCode("Test");
		lst2.setHerokuStatus("Test");
		lst2.setRunId(7L);
		lst2.setCountryCode("AUT");
		lst2.setHvhStatus("Test");

		lstGdprDepersonalizationOutput.add(lst1);
		lstGdprDepersonalizationOutput.add(lst2);
		return lstGdprDepersonalizationOutput;
	}

	private Map<String, String> mapCategoryData() {
		Map<String, String> mapCategory = new HashMap<String, String>();
		mapCategory.put("ACCESS CONTROL", "1");
		mapCategory.put("BACKGROUND CHECK", "2");
		mapCategory.put("PRE-HIRE: AMAZON ASSESSMENT OF UNSUCCESSFUL CANDIDATES (WITHOUT CONSENT)", "3");
		mapCategory.put("PRE-HIRE: CANDIDATE-PROVIDED DATA OF UNSUCCESSFUL CANDIDATES (WITHOUT CONSENT)", "4");
		mapCategory.put("PRE-HIRE: MASTER DATA OF UNSUCCESSFUL CANDIDATES (WITHOUT CONSENT)", "5");
		return mapCategory;
	}

	@Test
	@Ignore
	public void processreorganizeInputJobTest() {
		reOrganizeInputBatchConfig.processreorganizeInputJob();
	}

	@Test
	@Ignore
	public void reorganizeInputStepsTest() {

		reOrganizeInputBatchConfig.reorganizeInputStep();

	}

	@Test
	public void reorganizeInputlistenerTest() {
		String processreorganizeInputJob = "processreorganizeInputJob";
		reOrganizeInputBatchConfig.reorganizeInputlistener(processreorganizeInputJob);

	}

	@Test
	public void gdprDepersonalizationDBreaderTest() {
		Date moduleDate = new Date();
		String countryCode = "AUT";
		reOrganizeInputBatchConfig.gdprDepersonalizationDBreader(7L, countryCode, moduleDate);

	}

}
