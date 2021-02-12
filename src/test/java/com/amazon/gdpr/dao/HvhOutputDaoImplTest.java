package com.amazon.gdpr.dao;

import static org.mockito.Matchers.anyList;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import com.amazon.gdpr.model.GdprDepersonalizationOutput;

public class HvhOutputDaoImplTest {
	@InjectMocks
	HvhOutputDaoImpl hvhOutputDaoImpl;
	@Mock
	JdbcTemplate jdbcTemplate;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(hvhOutputDaoImpl, "jdbcTemplate", jdbcTemplate);

	}
	@Test
	public void batchInsertGdprDepersonalizationOutputTest() {
		hvhOutputDaoImpl.batchInsertGdprDepersonalizationOutput(lstGdprDepersonalizationOutput());
	}
	
private List<GdprDepersonalizationOutput> lstGdprDepersonalizationOutput() {
	List<GdprDepersonalizationOutput> lstGdprDepersonalizationOutput= new ArrayList<GdprDepersonalizationOutput>();
	GdprDepersonalizationOutput lst1= new GdprDepersonalizationOutput(7L,"Candidate_01",1,"AUT",
			"Test","Test");
	lstGdprDepersonalizationOutput.add(lst1);
	
	GdprDepersonalizationOutput lst2= new GdprDepersonalizationOutput(7L,"Candidate_01",1,"AUT",
			"Test","Test");
	lst2.setCandidate("Test");
	lst2.setCategory(1);
	lst2.setCountryCode("Test");
	lst2.setHerokuStatus("Test");
	lst2.setRunId(7L);
    lst2.setCountryCode("AUT");
    lst2.setHvhStatus("Test");
    
    lst2.getCandidate();
    lst2.getCategory();
    lst2.getCountryCode();
    lst2.getHerokuStatus();
    lst2.getHvhStatus();
    lst2.getRunId();
	return lstGdprDepersonalizationOutput;
}
	
}
