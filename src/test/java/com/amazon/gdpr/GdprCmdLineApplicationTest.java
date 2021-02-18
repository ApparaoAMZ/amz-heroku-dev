package com.amazon.gdpr;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
@RunWith(MockitoJUnitRunner.class)
public class GdprCmdLineApplicationTest extends GdprCmdLineApplication{

	@InjectMocks
	GdprCmdLineApplication gdprCmdLineApplication;
	
	@Test
	public void runTest() {
		gdprCmdLineApplication.run();
	}
	
	/*@Test 
	public void mainTest() { 
		//GdprCmdLineApplication.main(new String[] {"a"}); 
	}*/	
}