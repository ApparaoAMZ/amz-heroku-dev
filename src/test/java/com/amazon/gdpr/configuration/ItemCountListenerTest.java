package com.amazon.gdpr.configuration;

import static org.mockito.Matchers.anyInt;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
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
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.scope.context.StepContext;

import com.amazon.gdpr.dao.RunSummaryDaoImpl;
import com.amazon.gdpr.util.GlobalConstants;

@RunWith(MockitoJUnitRunner.class)
public class ItemCountListenerTest {

	@Mock
	RunSummaryDaoImpl runSummaryDaoImpl;

	@InjectMocks
	ItemCountListener ItemCountListener;
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void beforeChunkTest() {
		Long runId = 7L;
		Long summaryId = 2L;
		JobParameter jobParameter = new JobParameter(runId);
		JobParameter jobParameter1 = new JobParameter(summaryId);
		Map<String, JobParameter> obj = new HashMap<String, JobParameter>();
		obj.put(GlobalConstants.JOB_INPUT_RUN_ID, jobParameter);
		obj.put(GlobalConstants.JOB_INPUT_JOB_ID, jobParameter);
		obj.put(GlobalConstants.JOB_INPUT_RUN_SUMMARY_ID, jobParameter1);
		JobParameters jobParameters = new JobParameters(obj);
		JobExecution jobExecution = new JobExecution(7L, jobParameters);
		String stepName = "data";
		StepExecution stepExecution = new StepExecution(stepName, jobExecution);
		StepContext stepContext = new StepContext(stepExecution);
		ChunkContext chunkContext = new ChunkContext(stepContext);
		ItemCountListener.beforeChunk(chunkContext);
	}

	// runSummaryDaoImpl.depersonalizationCountUpdate(depersonalizationCount,
	// runSummaryId);
	@Test
	public void afterChunk() {
		Long runId = 7L;
		Long summaryId = 2L;
		JobParameter jobParameter = new JobParameter(runId);
		JobParameter jobParameter1 = new JobParameter(summaryId);
		Map<String, JobParameter> obj = new HashMap<String, JobParameter>();
		obj.put(GlobalConstants.JOB_INPUT_RUN_ID, jobParameter);
		obj.put(GlobalConstants.JOB_INPUT_JOB_ID, jobParameter);
		obj.put(GlobalConstants.JOB_INPUT_RUN_SUMMARY_ID, jobParameter1);
		JobParameters jobParameters = new JobParameters(obj);
		JobExecution jobExecution = new JobExecution(7L, jobParameters);
		String stepName = "data";
		StepExecution stepExecution = new StepExecution(stepName, jobExecution);
		StepContext stepContext = new StepContext(stepExecution);
		ChunkContext chunkContext = new ChunkContext(stepContext);
		int count=0;
		Mockito.when(runSummaryDaoImpl.depersonalizationCountUpdate(anyInt(), anyInt())).thenReturn(count);
		ItemCountListener.afterChunk(chunkContext);
	}
}
