package com.amazon.gdpr.configuration;

import static org.mockito.Mockito.mock;

import javax.batch.api.chunk.listener.ChunkListener;
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
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.AbstractTaskletStepBuilder;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.builder.StepBuilderHelper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import com.amazon.gdpr.dao.BackupServiceDaoImpl;
import com.amazon.gdpr.dao.GdprInputDaoImpl;
import com.amazon.gdpr.dao.RunMgmtDaoImpl;
import com.amazon.gdpr.dao.RunSummaryDaoImpl;
import com.amazon.gdpr.processor.ModuleMgmtProcessor;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = AnonymizeBatchConfig.class)
public class AnonymizeBatchConfigJobTest {
	@Mock
	public DataSource dataSource;

	@Mock
	RunSummaryDaoImpl runSummaryDaoImpl;

	@Mock
	GdprInputDaoImpl gdprInputDaoImpl;

	@Mock
	ModuleMgmtProcessor moduleMgmtProcessor;

	@Mock
	public BackupServiceDaoImpl backupServiceDaoImpl;

	@Mock
	RunMgmtDaoImpl runMgmtDaoImpl;

	@Mock
	@Qualifier("gdprJdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@InjectMocks
	AnonymizeBatchConfig anonymizeBatchConfig;

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
	
	@Mock 
	AbstractTaskletStepBuilder abstractTaskletStepBuilder= new SimpleStepBuilder(simpleStepBuilder);
	
	@Mock
	ItemCountListener itemCountListener;
	@Mock
	ChunkListener chunkListener;
	//abstractTaskletStepBuilder=
	
	@Test
	@Ignore
	public void processAnonymizeJobTest() {
		/*
		 * job = jobBuilderFactory.get(CURRENT_METHOD) .incrementer(new
		 * RunIdIncrementer()).listener(anonymizeListener(GlobalConstants.JOB_ANONYMIZE)
		 * ) .flow(anonymizeStep()) .end() .build();
		 */
		
		/*
		 * step = stepBuilderFactory.get(CURRENT_METHOD) .<AnonymizeTable,
		 * AnonymizeTable> chunk(SqlQueriesConstant.BATCH_ROW_COUNT)
		 * .reader(anonymizeTableReader(0,0, new Date())) .processor(new
		 * AnonymizeProcessor()) .writer(new AnonymizeInputWriter<Object>(new Date(),
		 * 0)) .listener(listener()) .build();
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
	    ChunkContext chunkContext1 = Mockito.mock(ChunkContext.class);
	    ChunkContext chunkContext = mock(ChunkContext.class);
	  //  Mockito.when(simpleStepBuilder.listener(chunkContext)).thenReturn(simpleStepBuilder);
	    //.listener(chunkContext)).thenReturn((SimpleStepBuilder) abstractTaskletStepBuilder);
	    //public SimpleStepBuilder(StepBuilderHelper<?> parent) {
		Mockito.when(jobBuilder.listener(Mockito.anyObject())).thenReturn(jobBuilder);
		Mockito.when(jobFlowBuilder.end()).thenReturn(flowBuilder);
		Mockito.when(flowBuilder.build()).thenReturn(job);
		anonymizeBatchConfig.processAnonymizeJob();
	}	
}
