package com.github.eostermueller.snail4j.install;

import com.github.eostermueller.snail4j.Snail4jException;

public interface DiskSpaceValidator {

	void setMinDiskSpaceAvailableRequirementInBytes(long m);
	long getMinDiskSpaceAvailableRequirementInBytes();
	void setActualDiskSpaceAvailableInBytes(long m);
	long getActualDiskSpaceAvailableInBytes();
	
	boolean isAvailableDiskSpaceValidationActive();
	void setAvailableDiskSpaceValidationActive(boolean a);
	
	void validate() throws Snail4jException;
}
