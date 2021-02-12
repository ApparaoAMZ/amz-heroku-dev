package com.amazon.gdpr.configuration;

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
import org.springframework.batch.item.database.JdbcCursorItemReader;

import com.amazon.gdpr.configuration.TaggingBatchConfig.TagInputWriter;
import com.amazon.gdpr.configuration.TaggingBatchConfig.TaggingProcessor;
import com.amazon.gdpr.dao.BackupServiceDaoImpl;
import com.amazon.gdpr.dao.GdprInputDaoImpl;
import com.amazon.gdpr.dao.RunMgmtDaoImpl;
import com.amazon.gdpr.dao.RunSummaryDaoImpl;
import com.amazon.gdpr.model.archive.ArchiveTable;
import com.amazon.gdpr.model.gdpr.output.RunSummaryMgmt;
import com.amazon.gdpr.processor.ModuleMgmtProcessor;
import com.amazon.gdpr.util.GlobalConstants;
@RunWith(MockitoJUnitRunner.class)
public class TaggingBatchConfigTest {
	@Mock
	public JobBuilderFactory jobBuilderFactory;

	@Mock
	public StepBuilderFactory stepBuilderFactory;
	
	@Mock
	public DataSource dataSource;
	
	@Mock
	RunSummaryDaoImpl runSummaryDaoImpl;	

	@Mock
	GdprInputDaoImpl gdprInputDaoImpl;

	@Mock
	ModuleMgmtProcessor moduleMgmtProcessor;
	
	@Mock
	BackupServiceDaoImpl backupServiceDaoImpl;
	
	@Mock
	RunMgmtDaoImpl runMgmtDaoImpl;
	@InjectMocks
	TaggingBatchConfig taggingBatchConfig;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	@Test
	public void archiveTableReaderTest() {
		 long runId=1L;
		 long runSummaryId=2L;
		 Date moduleStartDateTime = new  Date();
		 ArchiveTable  archiveTableData=archiveTableData();
		 Mockito.when(runSummaryDaoImpl.fetchRunSummaryDetail(runId,runSummaryId)).thenReturn(runSummaryMgmt());
		 JdbcCursorItemReader<ArchiveTable> res=taggingBatchConfig.archiveTableReader(runId, runSummaryId, moduleStartDateTime);
	}
	
	private RunSummaryMgmt runSummaryMgmt() {
		
		RunSummaryMgmt runSummaryMgmt= new RunSummaryMgmt();
		runSummaryMgmt.setRunId(1);
		runSummaryMgmt.setSummaryId(2);
		runSummaryMgmt.setCategoryId(2);
		runSummaryMgmt.setCountryCode("DEU");
		runSummaryMgmt.setImpactTableId(2);
		runSummaryMgmt.setImpactTableName("APPLICATION__C");
		runSummaryMgmt.setBackupQuery("SELECT APP_CANDIDATE_NAME__C, BGC_AUTHORIZATION1__C, BGC_AUTHORIZATION2__C, BGC_DISCLOSURE_FRCA_ESIGNATURE__C, CANDIDATE_FIRST_NAME__C, CANDIDATE_LAST_NAME__C, EMAIL_NOTIFICATION_BODY__C, EMAIL_NOTIFICATION_PROMPT_1_TEXT__C, EMAIL_NOTIFICATION_PROMPT_2_TEXT__C, EMAIL_NOTIFICATION_PROMPT_3_TEXT__C, EMAIL_NOTIFICATION_PROMPT_4_TEXT__C, EMAIL_NOTIFICATION_PROMPT_MEMO__C, EMAIL_NOTIFICATION_RICH_TEXT_2__C, EMAIL_NOTIFICATION_SUBJECT__C, ESIGNATUREPART2__C, ESIGNATURE_READ_GENERAL__C, LEGACY_TALEO_APP_INTERVIEW_INFO_DE__C, LEGACY_TALEO_CORRESPONDENCE_INFORMATION__C, LEGACY_TALEO_E_SIGNATURE_IP__C, LEGACY_TALEO_HIRING_AREA_MANAGER__C, LEGACY_TALEO_RECRUITER__C, LEGACY_TALEO_SCHEDULING_INFORMATION__C, PHONE_SCREEN_RESULTS__C, PREFERRED_FIRST_NAME_PS__C, PREFERRED_LAST_NAME_PS__C FROM APPLICATION__C");
		runSummaryMgmt.setDepersonalizationQuery("UPDATE SF_ARCHIVE.APPLICATION__C SET APP_CANDIDATE_NAME__C = (CASE WHEN (APP_CANDIDATE_NAME__C IS NULL OR TRIM(APP_CANDIDATE_NAME__C) = '') THEN APP_CANDIDATE_NAME__C ELSE 'Privacy Deleted' END), BGC_AUTHORIZATION1__C = (CASE WHEN (BGC_AUTHORIZATION1__C IS NULL OR TRIM(BGC_AUTHORIZATION1__C) = '') THEN BGC_AUTHORIZATION1__C ELSE 'Privacy Deleted' END), BGC_AUTHORIZATION2__C = (CASE WHEN (BGC_AUTHORIZATION2__C IS NULL OR TRIM(BGC_AUTHORIZATION2__C) = '') THEN BGC_AUTHORIZATION2__C ELSE 'Privacy Deleted' END), BGC_DISCLOSURE_FRCA_ESIGNATURE__C = (CASE WHEN (BGC_DISCLOSURE_FRCA_ESIGNATURE__C IS NULL OR TRIM(BGC_DISCLOSURE_FRCA_ESIGNATURE__C) = '') THEN BGC_DISCLOSURE_FRCA_ESIGNATURE__C ELSE 'Privacy Deleted' END), CANDIDATE_FIRST_NAME__C = (CASE WHEN (CANDIDATE_FIRST_NAME__C IS NULL OR TRIM(CANDIDATE_FIRST_NAME__C) = '') THEN CANDIDATE_FIRST_NAME__C ELSE 'Privacy Deleted' END), CANDIDATE_LAST_NAME__C = (CASE WHEN (CANDIDATE_LAST_NAME__C IS NULL OR TRIM(CANDIDATE_LAST_NAME__C) = '') THEN CANDIDATE_LAST_NAME__C ELSE 'Privacy Deleted' END), EMAIL_NOTIFICATION_BODY__C = (CASE WHEN (EMAIL_NOTIFICATION_BODY__C IS NULL OR TRIM(EMAIL_NOTIFICATION_BODY__C) = '') THEN EMAIL_NOTIFICATION_BODY__C ELSE 'Privacy Deleted' END), EMAIL_NOTIFICATION_PROMPT_1_TEXT__C = (CASE WHEN (EMAIL_NOTIFICATION_PROMPT_1_TEXT__C IS NULL OR TRIM(EMAIL_NOTIFICATION_PROMPT_1_TEXT__C) = '') THEN EMAIL_NOTIFICATION_PROMPT_1_TEXT__C ELSE 'Privacy Deleted' END), EMAIL_NOTIFICATION_PROMPT_2_TEXT__C = (CASE WHEN (EMAIL_NOTIFICATION_PROMPT_2_TEXT__C IS NULL OR TRIM(EMAIL_NOTIFICATION_PROMPT_2_TEXT__C) = '') THEN EMAIL_NOTIFICATION_PROMPT_2_TEXT__C ELSE 'Privacy Deleted' END), EMAIL_NOTIFICATION_PROMPT_3_TEXT__C = (CASE WHEN (EMAIL_NOTIFICATION_PROMPT_3_TEXT__C IS NULL OR TRIM(EMAIL_NOTIFICATION_PROMPT_3_TEXT__C) = '') THEN EMAIL_NOTIFICATION_PROMPT_3_TEXT__C ELSE 'Privacy Deleted' END), EMAIL_NOTIFICATION_PROMPT_4_TEXT__C = (CASE WHEN (EMAIL_NOTIFICATION_PROMPT_4_TEXT__C IS NULL OR TRIM(EMAIL_NOTIFICATION_PROMPT_4_TEXT__C) = '') THEN EMAIL_NOTIFICATION_PROMPT_4_TEXT__C ELSE 'Privacy Deleted' END), EMAIL_NOTIFICATION_PROMPT_MEMO__C = (CASE WHEN (EMAIL_NOTIFICATION_PROMPT_MEMO__C IS NULL OR TRIM(EMAIL_NOTIFICATION_PROMPT_MEMO__C) = '') THEN EMAIL_NOTIFICATION_PROMPT_MEMO__C ELSE 'Privacy Deleted' END), EMAIL_NOTIFICATION_RICH_TEXT_2__C = (CASE WHEN (EMAIL_NOTIFICATION_RICH_TEXT_2__C IS NULL OR TRIM(EMAIL_NOTIFICATION_RICH_TEXT_2__C) = '') THEN EMAIL_NOTIFICATION_RICH_TEXT_2__C ELSE 'Privacy Deleted' END), EMAIL_NOTIFICATION_SUBJECT__C = (CASE WHEN (EMAIL_NOTIFICATION_SUBJECT__C IS NULL OR TRIM(EMAIL_NOTIFICATION_SUBJECT__C) = '') THEN EMAIL_NOTIFICATION_SUBJECT__C ELSE 'Privacy Deleted' END), ESIGNATUREPART2__C = (CASE WHEN (ESIGNATUREPART2__C IS NULL OR TRIM(ESIGNATUREPART2__C) = '') THEN ESIGNATUREPART2__C ELSE 'Privacy Deleted' END), ESIGNATURE_READ_GENERAL__C = (CASE WHEN (ESIGNATURE_READ_GENERAL__C IS NULL OR TRIM(ESIGNATURE_READ_GENERAL__C) = '') THEN ESIGNATURE_READ_GENERAL__C ELSE 'Privacy Deleted' END), LEGACY_TALEO_APP_INTERVIEW_INFO_DE__C = (CASE WHEN (LEGACY_TALEO_APP_INTERVIEW_INFO_DE__C IS NULL OR TRIM(LEGACY_TALEO_APP_INTERVIEW_INFO_DE__C) = '') THEN LEGACY_TALEO_APP_INTERVIEW_INFO_DE__C ELSE 'Privacy Deleted' END), LEGACY_TALEO_CORRESPONDENCE_INFORMATION__C = (CASE WHEN (LEGACY_TALEO_CORRESPONDENCE_INFORMATION__C IS NULL OR TRIM(LEGACY_TALEO_CORRESPONDENCE_INFORMATION__C) = '') THEN LEGACY_TALEO_CORRESPONDENCE_INFORMATION__C ELSE 'Privacy Deleted' END), LEGACY_TALEO_E_SIGNATURE_IP__C = (CASE WHEN (LEGACY_TALEO_E_SIGNATURE_IP__C IS NULL OR TRIM(LEGACY_TALEO_E_SIGNATURE_IP__C) = '') THEN LEGACY_TALEO_E_SIGNATURE_IP__C ELSE 'Privacy Deleted' END), LEGACY_TALEO_HIRING_AREA_MANAGER__C = (CASE WHEN (LEGACY_TALEO_HIRING_AREA_MANAGER__C IS NULL OR TRIM(LEGACY_TALEO_HIRING_AREA_MANAGER__C) = '') THEN LEGACY_TALEO_HIRING_AREA_MANAGER__C ELSE 'Privacy Deleted' END), LEGACY_TALEO_RECRUITER__C = (CASE WHEN (LEGACY_TALEO_RECRUITER__C IS NULL OR TRIM(LEGACY_TALEO_RECRUITER__C) = '') THEN LEGACY_TALEO_RECRUITER__C ELSE 'Privacy Deleted' END), LEGACY_TALEO_SCHEDULING_INFORMATION__C = (CASE WHEN (LEGACY_TALEO_SCHEDULING_INFORMATION__C IS NULL OR TRIM(LEGACY_TALEO_SCHEDULING_INFORMATION__C) = '') THEN LEGACY_TALEO_SCHEDULING_INFORMATION__C ELSE 'Privacy Deleted' END), PHONE_SCREEN_RESULTS__C = (CASE WHEN (PHONE_SCREEN_RESULTS__C IS NULL OR TRIM(PHONE_SCREEN_RESULTS__C) = '') THEN PHONE_SCREEN_RESULTS__C ELSE 'Privacy Deleted' END), PREFERRED_FIRST_NAME_PS__C = (CASE WHEN (PREFERRED_FIRST_NAME_PS__C IS NULL OR TRIM(PREFERRED_FIRST_NAME_PS__C) = '') THEN PREFERRED_FIRST_NAME_PS__C ELSE 'Privacy Deleted' END), PREFERRED_LAST_NAME_PS__C = (CASE WHEN (PREFERRED_LAST_NAME_PS__C IS NULL OR TRIM(PREFERRED_LAST_NAME_PS__C) = '') THEN PREFERRED_LAST_NAME_PS__C ELSE 'Privacy Deleted' END) WHERE ID = ?");
		runSummaryMgmt.setTaggedQueryLoad("SELECT DISTINCT APPLICATION__C.ID ID, 'APPLICATION__C' IMPACT_TABLE_NAME , GDPR_DEPERSONALIZATION.CATEGORY_ID CATEGORY_ID, GDPR_DEPERSONALIZATION.COUNTRY_CODE COUNTRY_CODE FROM SF_ARCHIVE.APPLICATION__C APPLICATION__C, GDPR.GDPR_DEPERSONALIZATION GDPR_DEPERSONALIZATION WHERE APPLICATION__C.CANDIDATE__C = GDPR_DEPERSONALIZATION.CANDIDATE__C AND GDPR_DEPERSONALIZATION.CATEGORY_ID = 2 AND GDPR_DEPERSONALIZATION.COUNTRY_CODE = 'DEU' AND RUN_ID = 1");
		return runSummaryMgmt;
	}
	
	@Test
	public void taggingListenerTest() {
		String jobRelatedName="Test";
		taggingBatchConfig.taggingListener(jobRelatedName);
	}
	
	@Test
	public void beforeStepTest(){
		TaggingProcessor objStep;
		objStep = taggingBatchConfig.new TaggingProcessor();
		Long runId=7L;
		Date moduleDateTime= new Date();
		JobParameter jobParameter = new JobParameter(runId);
		// JobParameter jobParameter1 = new JobParameter(countryCode);
		JobParameter jobParameter2 = new JobParameter(moduleDateTime);
		Map<String, JobParameter> obj = new HashMap<String, JobParameter>();
		obj.put(GlobalConstants.JOB_INPUT_RUN_ID, jobParameter);
		obj.put(GlobalConstants.JOB_INPUT_JOB_ID, jobParameter);
		obj.put(GlobalConstants.JOB_INPUT_RUN_SUMMARY_ID, jobParameter);
		obj.put(GlobalConstants.JOB_INPUT_START_DATE, jobParameter2);
		JobParameters jobParameters = new JobParameters(obj);
		JobExecution jobExecution = new JobExecution(7L, jobParameters);
		String stepName = "data";
		String date = "1900-01-01 00:00:00";
		StepExecution stepExecution = new StepExecution(stepName, jobExecution);
		objStep.beforeStep(stepExecution);
	}
	
	
	@Test
	public void writeTest() {
		TagInputWriter objWrite;
		 Date moduleStartDateTime =new Date();
		 objWrite = taggingBatchConfig.new TagInputWriter(moduleStartDateTime);
		 objWrite.write(lstArchiveTableData());
	}
	
	@Test
	public void process() {
		TaggingProcessor objStep;
		objStep = taggingBatchConfig.new TaggingProcessor();
		objStep.process(archiveTableData());
	}
	
	@Test
	@Ignore
	public void taggingStepTest() {
		taggingBatchConfig.taggingStep();
	}
	
	@Test
	@Ignore
	public void processTaggingJob() {
		taggingBatchConfig.processTaggingJob();
	}
	
	private List<ArchiveTable> lstArchiveTableData() {
		List<ArchiveTable> lstArchiveTableData= new ArrayList<ArchiveTable>();
		ArchiveTable archiveTable = new ArchiveTable( 7L, "APPLICATION_C",  1,  "AUT",  1,  "SUCCESS");
		ArchiveTable archiveTable1 = new ArchiveTable( 7L, "INTERVIEW_C",  1,  "AUT",  1,  "SUCCESS");
		lstArchiveTableData.add(archiveTable1);
		lstArchiveTableData.add(archiveTable);
		return lstArchiveTableData;
		
	}
	private ArchiveTable archiveTableData() {
		ArchiveTable archiveTable = new ArchiveTable( 7L, "APPLICATION_C",  1,  "AUT",  1,  "SUCCESS");
		//set data using setters
		ArchiveTable archiveTable1 = new ArchiveTable( 7L, 2,  1,  "AUT",  1,  "SUCCESS");
		ArchiveTable archiveTable2 = new ArchiveTable( 7L, 2,  1,  "AUT",  1,  "SUCCESS");
		archiveTable2.setCategoryId(1);
		archiveTable2.setCountryCode("AUT");
		archiveTable2.setId(1);
		archiveTable2.setRunId(7);
		archiveTable2.setStatus("SUCCESS");
		archiveTable2.setTableId(2);
		archiveTable2.setTableName("AAPLICATION_C");
		archiveTable2.getCategoryId();
		archiveTable2.getCountryCode();
		archiveTable2.getId();
		archiveTable2.getRunId();
		archiveTable2.getStatus();
		archiveTable2.getTableId();
		archiveTable2.getTableName();
		return archiveTable;
		
	}
}
