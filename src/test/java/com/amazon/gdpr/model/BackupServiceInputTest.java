package com.amazon.gdpr.model;

import org.mockito.InjectMocks;

public class BackupServiceInputTest {
    
	@InjectMocks
	BackupServiceInput BackupServiceInput;
	public void BackupServiceInputTest() {
		BackupServiceInput backupServiceInput = new BackupServiceInput();
		backupServiceInput.setSummaryId(7L);
		backupServiceInput.setRunId(7L);
		backupServiceInput.setCategoryId(2);
		backupServiceInput.setRegion("EU");
		backupServiceInput.setImpactTableId(5);
		backupServiceInput.setBackupQuery("Select ..");
		backupServiceInput.setDepersonalizationQuery("set...");
		backupServiceInput.setBackupRowCount(2);
		backupServiceInput.setTaggedRowCount(4);
		backupServiceInput.setDepersonalizedRowCount(3);
		
	}
}
