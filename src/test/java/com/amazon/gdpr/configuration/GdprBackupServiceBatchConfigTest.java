package com.amazon.gdpr.configuration;

import static org.mockito.Matchers.anyInt;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import com.amazon.gdpr.configuration.GdprBackupServiceBatchConfig.BackupServiceOutputWriter;
import com.amazon.gdpr.configuration.GdprBackupServiceBatchConfig.GdprBackupServiceProcessor;
import com.amazon.gdpr.dao.BackupServiceDaoImpl;
import com.amazon.gdpr.dao.GdprInputDaoImpl;
import com.amazon.gdpr.dao.GdprInputFetchDaoImpl;
import com.amazon.gdpr.dao.GdprOutputDaoImpl;
import com.amazon.gdpr.model.BackupServiceInput;
import com.amazon.gdpr.model.BackupServiceOutput;
import com.amazon.gdpr.model.gdpr.input.ImpactTable;
import com.amazon.gdpr.model.gdpr.output.RunModuleMgmt;
import com.amazon.gdpr.processor.ModuleMgmtProcessor;
import com.amazon.gdpr.util.GdprException;
import com.amazon.gdpr.util.GlobalConstants;

@RunWith(MockitoJUnitRunner.class)
public class GdprBackupServiceBatchConfigTest {
	@Mock
	public JobBuilderFactory jobBuilderFactory;

	@Mock
	public StepBuilderFactory stepBuilderFactory;

	@Mock
	public DataSource dataSource;

	@Mock
	GdprOutputDaoImpl gdprOutputDaoImpl;

	@Mock
	GdprInputDaoImpl gdprInputDaoImpl;
	@Mock
	GdprInputFetchDaoImpl gdprInputFetchDaoImpl;

	@Mock
	public BackupServiceDaoImpl backupServiceDaoImpl;
	@Mock
	ModuleMgmtProcessor moduleMgmtProcessor;

	@Mock
	@Qualifier("gdprJdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	public long runId;
	public Date moduleStartDateTime;
	@InjectMocks
	GdprBackupServiceBatchConfig gdprBackupServiceBatchConfig;

	@Test
	public void backupListenerTest() {
		String jobRelatedName = "Backup Service Listener";
		gdprBackupServiceBatchConfig.backupListener(jobRelatedName);

	}

	@Mock
	BackupServiceDaoImpl bkpServiceDaoImpl;

	@Test
	public void backupServiceReaderTest() {
		Long runId = 7L;
		// Mockito.doNothing().when(bkpServiceDaoImpl).updateSummaryTable(lstBackupServiceOutput());
		JdbcCursorItemReader<BackupServiceInput> res = gdprBackupServiceBatchConfig.backupServiceReader(runId);
	}

	@Test
	public void beforeStepTest() throws GdprException {
		GdprBackupServiceProcessor objStep;
		objStep = gdprBackupServiceBatchConfig.new GdprBackupServiceProcessor();
		Long runId = 7L;
		Date moduleDateTime = new Date();
		String countryCode = "AUT";
		JobParameter jobParameter = new JobParameter(runId);
		// JobParameter jobParameter1 = new JobParameter(countryCode);
		JobParameter jobParameter1 = new JobParameter(moduleDateTime);
		Map<String, JobParameter> obj = new HashMap<String, JobParameter>();
		obj.put(GlobalConstants.JOB_REORGANIZE_INPUT_RUNID, jobParameter);
		obj.put(GlobalConstants.JOB_INPUT_JOB_ID, jobParameter);
		obj.put(GlobalConstants.JOB_INPUT_START_DATE, jobParameter1);
		JobParameters jobParameters = new JobParameters(obj);
		JobExecution jobExecution = new JobExecution(7L, jobParameters);
		String stepName = "data";
		String date = "1900-01-01 00:00:00";
		StepExecution stepExecution = new StepExecution(stepName, jobExecution);
		Mockito.when(gdprInputDaoImpl.fetchCategoryDetails()).thenReturn(mapCategoryData());
		Mockito.when(gdprInputFetchDaoImpl.fetchImpactTable()).thenReturn(lstImpactTable());
		Mockito.when(gdprOutputDaoImpl.fetchLastDataLoad()).thenReturn(date);
		// Mockito.when(gdprInputDaoImpl.fetchCategoryDetails()).thenReturn(mapCategoryData());
		 String tableName = "APPLICATION__C";
			BackupServiceInput BackupServiceInput1 = new BackupServiceInput(1, 7L, 2, "EUR-EU", "AUT", 2,
					"SELECT FELONY_CONVICTION_QUESTION_2__C FROM APPLICATION__C",
					"UPDATE SF_ARCHIVE.APPLICATION__C SET FELONY_CONVICTION_QUESTION_2__C = (CASE WHEN (FELONY_CONVICTION_QUESTION_2__C IS NULL OR TRIM(FELONY_CONVICTION_QUESTION_2__C) = '') THEN FELONY_CONVICTION_QUESTION_2__C ELSE 'Privacy Deleted' END) WHERE ID = ?");
			String varCls = "FELONY_CONVICTION_QUESTION_2__C";
			//long runId = 7L;
			try {
		objStep.beforeStep(stepExecution);
		objStep.process(backupServiceInputProcessData());
		
		  gdprBackupServiceBatchConfig.fetchCompleteBackupDataQuery(tableName,
		  mapImpactTable(), backupServiceInput(), varCls, runId);
		 
			}catch(Exception e) {
				e.printStackTrace();
			}
	}

	@Test(expected = Exception.class)
	public void beforeStepExceptionTest() throws GdprException {
		GdprBackupServiceProcessor objStep;
		objStep = gdprBackupServiceBatchConfig.new GdprBackupServiceProcessor();
		Long runId = 7L;
		Date moduleDateTime = new Date();
		String countryCode = "AUT";
		JobParameter jobParameter = new JobParameter(runId);
		// JobParameter jobParameter1 = new JobParameter(countryCode);
		JobParameter jobParameter1 = new JobParameter(moduleDateTime);
		Map<String, JobParameter> obj = new HashMap<String, JobParameter>();
		obj.put(GlobalConstants.JOB_REORGANIZE_INPUT_RUNID, jobParameter);
		obj.put(GlobalConstants.JOB_INPUT_JOB_ID, jobParameter);
		obj.put(GlobalConstants.JOB_INPUT_START_DATE, jobParameter1);
		JobParameters jobParameters = new JobParameters(obj);
		JobExecution jobExecution = new JobExecution(7L, jobParameters);
		String stepName = "data";
		String date = "1900-01-01 00:00:00";
		StepExecution stepExecution = new StepExecution(stepName, jobExecution);
		Mockito.when(gdprInputDaoImpl.fetchCategoryDetails()).thenThrow(Exception.class);
		Mockito.when(gdprInputFetchDaoImpl.fetchImpactTable()).thenThrow(Exception.class);
		RunModuleMgmt runModuleMgmt = new RunModuleMgmt(runId, GlobalConstants.MODULE_BACKUPSERVICE,
				GlobalConstants.SUB_MODULE_BACKUPSERVICE_JOB_INITIALIZE, "FAILURE", moduleStartDateTime,
				moduleStartDateTime, "TestStatus", "[Ljava.lang.StackTraceElement;@614ca7df");
		Mockito.doNothing().when(moduleMgmtProcessor).initiateModuleMgmt(runModuleMgmt);

		objStep.beforeStep(stepExecution);
	}

	@Test
	@Ignore
	public void processTest() {
		GdprBackupServiceProcessor objStep;
		objStep = gdprBackupServiceBatchConfig.new GdprBackupServiceProcessor();
		Mockito.when(gdprInputFetchDaoImpl.fetchImpactTable()).thenReturn(lstImpactTable());
		Mockito.when(gdprInputDaoImpl.fetchCategoryDetails()).thenReturn(mapCategoryData());
		objStep.process(backupServiceInput());
	}

	@Test
	public void writeTest() {
		BackupServiceOutputWriter objWrite = gdprBackupServiceBatchConfig.new BackupServiceOutputWriter(
				bkpServiceDaoImpl);
		// bkpServiceDaoImpl.updateSummaryTable(lstBackupServiceOutput);
		Mockito.doNothing().when(bkpServiceDaoImpl).updateSummaryTable(lstBackupServiceOutput());
		objWrite.write(lstBackupServiceOutput());
	}

	

	/*
	 * @Test
	 * 
	 * @Ignore public void gdprBackupServiceStepTest() {
	 * 
	 * Step step = (Step) new TaskletStep(); stepBuilderFactory = new
	 * StepBuilderFactory(jobRepository, platformTransactionManager);
	 * GdprBackupServiceProcessor objStep; objStep =
	 * gdprBackupServiceBatchConfig.new GdprBackupServiceProcessor();
	 * BackupServiceOutputWriter writerObj; writerObj =
	 * gdprBackupServiceBatchConfig.new
	 * BackupServiceOutputWriter(backupServiceDaoImpl); StepBuilder stepBuilder =
	 * new StepBuilder("gdprBackupServiceStep");
	 * JdbcCursorItemReader<BackupServiceInput>
	 * test=gdprBackupServiceBatchConfig.backupServiceReader(7);
	 * System.out.println("Test::"+ test); StepBuilder stepBuilder1=
	 * stepBuilderFactory.get("gdprBackupServiceStep");
	 * System.out.println("stepBuilder1:"+stepBuilder1);
	 * SimpleStepBuilder<BackupServiceInput, BackupServiceOutput>
	 * chunckObj=stepBuilder1.chunk(anyInt()); SimpleStepBuilder<BackupServiceInput,
	 * BackupServiceOutput> readerObj=chunckObj.reader(test);
	 * Mockito.when(readerObj.processor(objStep).writer(writerObj).build()).
	 * thenReturn((TaskletStep) step);
	 * gdprBackupServiceBatchConfig.gdprBackupServiceStep();
	 * 
	 * }
	 */

	@Test
	public void fetchCompleteBackupDataQueryTest() {
		String tableName = "APPLICATION__C";
		BackupServiceInput BackupServiceInput1 = new BackupServiceInput(1, 7L, 2, "EUR-EU", "AUT", 2,
				"SELECT FELONY_CONVICTION_QUESTION_2__C FROM APPLICATION__C",
				"UPDATE SF_ARCHIVE.APPLICATION__C SET FELONY_CONVICTION_QUESTION_2__C = (CASE WHEN (FELONY_CONVICTION_QUESTION_2__C IS NULL OR TRIM(FELONY_CONVICTION_QUESTION_2__C) = '') THEN FELONY_CONVICTION_QUESTION_2__C ELSE 'Privacy Deleted' END) WHERE ID = ?");
		String varCls = "FELONY_CONVICTION_QUESTION_2__C";
		long runId = 7L;
		gdprBackupServiceBatchConfig.fetchCompleteBackupDataQuery(tableName, mapImpactTable(), backupServiceInput(),
				varCls, runId);
	}

	private Map<String, ImpactTable> mapImpactTable() {
		Map<String, ImpactTable> mapImpactTable = new HashMap<String, ImpactTable>();

		ImpactTable impactTable1 = new ImpactTable(2, "SF_ARCHIVE", "APPLICATION__C", "ID", "CHILD", "GDPR",
				"GDPR_DEPERSONALIZATION", "CANDIDATE__C", "CANDIDATE__C");
		ImpactTable impactTable2 = new ImpactTable(3, "SF_ARCHIVE", "INTERVIEW__C", "ID", "CHILD", "GDPR",
				"GDPR_DEPERSONALIZATION", "CANDIDATE__C", "CANDIDATE__C");
		ImpactTable impactTable3 = new ImpactTable(9, "SF_ARCHIVE", "RESPONSE__C", "ID", "CHILD", "SF_ARCHIVE",
				"APPLICATION__C", "APPLICATION__C", "SFID");
		ImpactTable impactTable4 = new ImpactTable(1, "GDPR", "GDPR_DEPERSONALIZATION", "CATEGORY_ID,COUNTRY_CODE",
				"PARENT", "NA", "NA", "NA", "NA");

		mapImpactTable.put("APPLICATION__C", impactTable1);
		mapImpactTable.put("INTERVIEW__C", impactTable2);
		mapImpactTable.put("GDPR_DEPERSONALIZATION", impactTable4);
		mapImpactTable.put("RESPONSE__C", impactTable3);
		return mapImpactTable;

	}

	/*
	 * public BackupServiceInput(long summaryId,long runId, int categoryId, String
	 * region, String countryCode, int impactTableId, String backupQuery, String
	 * depersonalizationQuery) {
	 */
	
	private BackupServiceInput backupServiceInputProcessData() {

		BackupServiceInput BackupServiceInput1 = new BackupServiceInput(1, 7L, 2, "EUR-EU", "AUT", 2,
				"SELECT FELONY_CONVICTION_QUESTION_2__C FROM APPLICATION__C",
				"UPDATE SF_ARCHIVE.APPLICATION__C SET FELONY_CONVICTION_QUESTION_2__C = (CASE WHEN (FELONY_CONVICTION_QUESTION_2__C IS NULL OR TRIM(FELONY_CONVICTION_QUESTION_2__C) = '') THEN FELONY_CONVICTION_QUESTION_2__C ELSE 'Privacy Deleted' END) WHERE ID = ?");
	
		

		return BackupServiceInput1;

	}
	private BackupServiceInput backupServiceInput() {

		BackupServiceInput BackupServiceInput1 = new BackupServiceInput(1, 7L, 2, "EUR-EU", "AUT", 2,
				"SELECT FELONY_CONVICTION_QUESTION_2__C FROM APPLICATION__C",
				"UPDATE SF_ARCHIVE.APPLICATION__C SET FELONY_CONVICTION_QUESTION_2__C = (CASE WHEN (FELONY_CONVICTION_QUESTION_2__C IS NULL OR TRIM(FELONY_CONVICTION_QUESTION_2__C) = '') THEN FELONY_CONVICTION_QUESTION_2__C ELSE 'Privacy Deleted' END) WHERE ID = ?");
		// Set data using setters
		BackupServiceInput backupServiceInput = new BackupServiceInput();
		backupServiceInput.setBackupQuery("SELECT FELONY_CONVICTION_QUESTION_2__C FROM APPLICATION__C");
		backupServiceInput.setBackupRowCount(6);
		backupServiceInput.setCategoryId(2);
		backupServiceInput.setCountryCode("AUT");
		backupServiceInput.setDepersonalizationQuery(
				"UPDATE SF_ARCHIVE.APPLICATION__C SET FELONY_CONVICTION_QUESTION_2__C = (CASE WHEN (FELONY_CONVICTION_QUESTION_2__C IS NULL OR TRIM(FELONY_CONVICTION_QUESTION_2__C) = '') THEN FELONY_CONVICTION_QUESTION_2__C ELSE 'Privacy Deleted' END) WHERE ID = ?");
		backupServiceInput.setDepersonalizedRowCount(6);
		backupServiceInput.setImpactTableId(2);
		backupServiceInput.setRegion("EUR-EU");
		backupServiceInput.setRunId(runId);
		backupServiceInput.setSummaryId(1);
		backupServiceInput.setTaggedRowCount(6);
		backupServiceInput.getBackupQuery();
		backupServiceInput.getBackupRowCount();
		backupServiceInput.getCategoryId();
		backupServiceInput.getCountryCode();
		backupServiceInput.getDepersonalizationQuery();
		backupServiceInput.getDepersonalizedRowCount();
		backupServiceInput.getImpactTableId();
		backupServiceInput.getRegion();
		backupServiceInput.getRunId();
		backupServiceInput.getSummaryId();
		backupServiceInput.getTaggedRowCount();
		backupServiceInput.toString();
		BackupServiceInput BackupServiceInput2 = new BackupServiceInput(1, 7L, 2, "EUR-EU", "AUT", 2,
				"SELECT FELONY_CONVICTION_QUESTION_2__C FROM APPLICATION__C",
				"UPDATE SF_ARCHIVE.APPLICATION__C SET FELONY_CONVICTION_QUESTION_2__C = (CASE WHEN (FELONY_CONVICTION_QUESTION_2__C IS NULL OR TRIM(FELONY_CONVICTION_QUESTION_2__C) = '') THEN FELONY_CONVICTION_QUESTION_2__C ELSE 'Privacy Deleted' END) WHERE ID = ?",
				11, 12, 11);

		return BackupServiceInput1;

	}

	private List<ImpactTable> lstImpactTable() {
		List<ImpactTable> lstImpactTable = new ArrayList<ImpactTable>();
		ImpactTable impactTable1 = new ImpactTable(2, "TAG", "APPLICATION__C", "ID", "CHILD", "GDPR",
				"GDPR_DEPERSONALIZATION", "CANDIDATE__C", "CANDIDATE__C");
		ImpactTable impactTable2 = new ImpactTable(3, "TAG", "INTERVIEW__C", "ID", "CHILD", "GDPR",
				"GDPR_DEPERSONALIZATION", "CANDIDATE__C", "CANDIDATE__C");
		lstImpactTable.add(impactTable1);
		lstImpactTable.add(impactTable2);
		return lstImpactTable;
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

	private List<BackupServiceOutput> lstBackupServiceOutput() {
		/*
		 * long summaryId; long runId; long backupRowCount;
		 */
		List<BackupServiceOutput> lstBackupServiceOutput = new ArrayList<BackupServiceOutput>();
		BackupServiceOutput backupServiceOutput1 = new BackupServiceOutput(2, 7, 11);
		BackupServiceOutput backupServiceOutput2 = new BackupServiceOutput();
		backupServiceOutput2.setBackupRowCount(11);
		backupServiceOutput2.setRunId(2);
		backupServiceOutput2.setSummaryId(7);
		lstBackupServiceOutput.add(backupServiceOutput1);
		return lstBackupServiceOutput;
	}
}
