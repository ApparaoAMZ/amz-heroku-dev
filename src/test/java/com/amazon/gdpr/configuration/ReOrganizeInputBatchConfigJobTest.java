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
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import com.amazon.gdpr.dao.BackupServiceDaoImpl;
import com.amazon.gdpr.dao.GdprInputDaoImpl;
import com.amazon.gdpr.dao.GdprInputFetchDaoImpl;
import com.amazon.gdpr.dao.GdprOutputDaoImpl;
import com.amazon.gdpr.dao.HvhOutputDaoImpl;
import com.amazon.gdpr.dao.RunMgmtDaoImpl;
import com.amazon.gdpr.processor.ModuleMgmtProcessor;
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = ReOrganizeInputBatchConfig.class)
public class ReOrganizeInputBatchConfigJobTest {
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


	@Mock
	@Qualifier("gdprJdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@InjectMocks
	ReOrganizeInputBatchConfig reOrganizeInputBatchConfig;

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
	public void processTaggingJob() {
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
		reOrganizeInputBatchConfig.processreorganizeInputJob();
	}
}
