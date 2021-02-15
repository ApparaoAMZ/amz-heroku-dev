
package com.amazon.gdpr.configuration;

import javax.sql.DataSource;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowJobBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.builder.JobFlowBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import com.amazon.gdpr.configuration.GdprBackupServiceBatchConfig.BackupServiceOutputWriter;
import com.amazon.gdpr.configuration.GdprBackupServiceBatchConfig.GdprBackupServiceProcessor;
import com.amazon.gdpr.dao.BackupServiceDaoImpl;
import com.amazon.gdpr.dao.GdprInputDaoImpl;
import com.amazon.gdpr.dao.GdprInputFetchDaoImpl;
import com.amazon.gdpr.dao.GdprOutputDaoImpl;
import com.amazon.gdpr.model.BackupServiceInput;
import com.amazon.gdpr.model.BackupServiceOutput;
import com.amazon.gdpr.processor.ModuleMgmtProcessor;
import com.amazon.gdpr.util.GlobalConstants;
import com.amazon.gdpr.util.SqlQueriesConstant;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = GdprBackupServiceBatchConfig.class)
public class GdprBackupServiceBatchConfigJobTest {

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

	@InjectMocks
	GdprBackupServiceBatchConfig gdprBackupServiceBatchConfig;

	@Mock
	public JobBuilderFactory jobBuilderFactory;

	@Mock
	public StepBuilderFactory stepBuilderFactory;

	@Mock
	private JobBuilder jobBuilder;

	@Mock
	private JobFlowBuilder jobFlowBuilder;

	@Mock
	private StepBuilder stepBuilder;

	@Mock
	private SimpleStepBuilder<Object, Object> simpleStepBuilder;

	@Mock
	private FlowJobBuilder flowBuilder;

	@Mock
	private Job job;

	@Test
	public void processGdprBackupServiceJobTest() { 
		/*
		 * job = jobBuilderFactory.get("processGdprBackupServiceJob").incrementer(new
		 * RunIdIncrementer())
		 * .listener(backupListener(GlobalConstants.JOB_BACKUP_SERVICE_LISTENER)).flow(
		 * gdprBackupServiceStep()) .end().build();
		 */

		/*
		 * step = stepBuilderFactory.get("gdprBackupServiceStep") .<BackupServiceInput,
		 * BackupServiceOutput>chunk(SqlQueriesConstant.BATCH_ROW_COUNT)
		 * .reader(backupServiceReader(0)).processor(new GdprBackupServiceProcessor())
		 * .writer(new BackupServiceOutputWriter(backupServiceDaoImpl)).build();
		 */
		Mockito.when(jobBuilderFactory.get(Mockito.anyString())).thenReturn(jobBuilder);
		Mockito.when(jobBuilder.incrementer(Mockito.anyObject())).thenReturn(jobBuilder);
		Mockito.when(jobBuilder.listener(Mockito.anyObject())).thenReturn(jobBuilder);
		Mockito.when(jobBuilder.flow(Mockito.anyObject())).thenReturn(jobFlowBuilder);
		Mockito.when(stepBuilderFactory.get(Mockito.anyString())).thenReturn(stepBuilder);
		Mockito.when(stepBuilder.chunk(Mockito.anyInt())).thenReturn(simpleStepBuilder);
		Mockito.when(simpleStepBuilder.reader(Mockito.anyObject())).thenReturn(simpleStepBuilder);
		Mockito.when(simpleStepBuilder.processor(Mockito.anyObject())).thenReturn(simpleStepBuilder);
		Mockito.when(simpleStepBuilder.writer(Mockito.anyObject())).thenReturn(simpleStepBuilder);
		Mockito.when(jobFlowBuilder.end()).thenReturn(flowBuilder);
		Mockito.when(flowBuilder.build()).thenReturn(job);
		gdprBackupServiceBatchConfig.processGdprBackupServiceJob();

	}

}
