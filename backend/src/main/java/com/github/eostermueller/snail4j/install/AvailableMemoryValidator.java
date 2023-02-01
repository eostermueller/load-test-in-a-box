package com.github.eostermueller.snail4j.install;

import com.github.eostermueller.snail4j.Snail4jException;

public interface AvailableMemoryValidator {

	void setMinMemoryAvailableRequirementInBytes(long m);
	long getMinMemoryAvailableRequirementInBytes();
	void setActualMemoryAvailableInBytes(long m);
	long getActualMemoryAvailabilInBytes();
	
	
	boolean isAvailableMemoryValidationActive();
	void setAvailableMemoryValidationActive(boolean a);
	
	void validate() throws Snail4jException;
}
