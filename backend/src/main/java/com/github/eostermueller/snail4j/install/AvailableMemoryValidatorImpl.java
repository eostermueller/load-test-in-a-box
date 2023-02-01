package com.github.eostermueller.snail4j.install;

import com.github.eostermueller.snail4j.DefaultFactory;
import com.github.eostermueller.snail4j.Snail4jException;
import com.github.eostermueller.snail4j.systemproperty.AvailableMemoryValidation;

public class AvailableMemoryValidatorImpl implements AvailableMemoryValidator {

	public AvailableMemoryValidatorImpl() throws Snail4jException {
		
		this.setAvailableMemoryValidationActive( 	DefaultFactory.getFactory().getSystemPropertyMgr().getBoolean(new AvailableMemoryValidation()));
		
		this.setActualMemoryAvailableInBytes( Runtime.getRuntime().freeMemory() );
		
	}
	private long minMemoryAvailableRequirementInBytes = 0;
	private long actualMemoryAvailableInBytes = 0;
	
	
	private boolean availableMemoryValidationActive;
	private boolean availableDiskSpaceValidationActive;
	
	@Override
	public void setMinMemoryAvailableRequirementInBytes(long m) {
		this.minMemoryAvailableRequirementInBytes = m;
	}

	@Override
	public long getMinMemoryAvailableRequirementInBytes() {
		return this.minMemoryAvailableRequirementInBytes;
	}

	@Override
	public void setActualMemoryAvailableInBytes(long m) {
		this.actualMemoryAvailableInBytes = m;
	}

	@Override
	public long getActualMemoryAvailabilInBytes() {
		return this.actualMemoryAvailableInBytes;
	}





	@Override
	public void validate() throws Snail4jException {
		
		if (this.isAvailableMemoryValidationActive()) {
			if (
					this.getActualMemoryAvailabilInBytes() < this.getMinMemoryAvailableRequirementInBytes()
			) {
				String m = DefaultFactory.getFactory().getMessages().insufficientMemory(this.getActualMemoryAvailabilInBytes(),this.getMinMemoryAvailableRequirementInBytes());
				throw new Snail4jException(m);
			}
		}

	}

	@Override
	public boolean isAvailableMemoryValidationActive() {
		return this.availableMemoryValidationActive;
	}

	@Override
	public void setAvailableMemoryValidationActive(boolean a) {
		this.availableMemoryValidationActive = a;
	}


}
