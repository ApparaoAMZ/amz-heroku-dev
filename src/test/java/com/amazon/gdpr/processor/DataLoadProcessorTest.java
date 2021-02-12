package com.amazon.gdpr.processor;

import static org.mockito.Matchers.anyInt;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.amazon.gdpr.dao.GdprOutputDaoImpl;

import com.amazon.gdpr.util.GdprException;

public class DataLoadProcessorTest {
	@Mock
	GdprOutputDaoImpl gdprOutputDaoImpl;
	@InjectMocks
	DataLoadProcessor dataLoadProcessor;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void updateDataLoadTest() throws GdprException {
		/*
		 * long now = System.currentTimeMillis(); Timestamp sqlTimestamp = new
		 * Timestamp(now); DataLoad dataLoad = new DataLoad("APPLIVATION_C", "AUT",
		 * sqlTimestamp);
		 */
		Long runId = 2L;
		int mockRes = 0;
		Mockito.when(gdprOutputDaoImpl.updateDataLoad(runId)).thenReturn(mockRes);
		dataLoadProcessor.updateDataLoad(runId);
	}

	@Test(expected = Exception.class)
	public void updateDataLoadExceptionTest() throws GdprException {
		Long runId = 2L;
		Mockito.when(gdprOutputDaoImpl.updateDataLoad(anyInt())).thenThrow(Exception.class);
		dataLoadProcessor.updateDataLoad(runId);
	}

	/*
	 * private List<DataLoad> lstDataLoad() { List<DataLoad> lstDataLoad= new
	 * ArrayList<DataLoad>(); long now = System.currentTimeMillis(); Timestamp
	 * sqlTimestamp = new Timestamp(now); DataLoad dataLoad1 = new
	 * DataLoad("APPLIVATION_C", "AUT", sqlTimestamp); DataLoad dataLoad2 = new
	 * DataLoad("APPLIVATION_C", "AUT", sqlTimestamp);
	 * dataLoad2.setCountryCode("AUT"); dataLoad2.setDataLoadDateTime(sqlTimestamp);
	 * dataLoad2.setTableName("APPLIVATION_C");
	 * dataLoad2.setStrDataLoadDate(dataLoad1.toString());
	 * lstDataLoad.add(dataLoad1); return lstDataLoad;
	 * 
	 * }
	 */

}
