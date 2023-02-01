package com.github.eostermueller.snail4j.install;

import java.io.File;
import java.nio.file.Path;

import com.github.eostermueller.snail4j.DefaultFactory;
import com.github.eostermueller.snail4j.Snail4jException;
import com.github.eostermueller.snail4j.launcher.CannotFindSnail4jFactoryClass;
import com.github.eostermueller.snail4j.systemproperty.AvailableDiskSpaceValidation;
import com.github.eostermueller.snail4j.systemproperty.AvailableMemoryValidation;
import com.github.eostermueller.snail4j.util.OS;
import com.github.eostermueller.snail4j.util.OS.OsFamily;
import com.github.eostermueller.snail4j.util.OsUtils.OsResult;

public class DiskSpaceValidatorImpl implements DiskSpaceValidator {

	
	
	public DiskSpaceValidatorImpl() throws Snail4jException {

		/**
		 * If we're already installed, our massive files are already in place, 
		 * so it's too late to do anything about it (ex: abort an install that's already been done) , so skip the check.
		 */
		Path installRoot = DefaultFactory.getFactory().getConfiguration().getSnail4jHome();
		if (installRoot.toFile().exists()) {
			this.setAvailableDiskSpaceValidationActive(false);
		}
		
		// ...but of course if they set the -D parameter for validation to true, we'll validate for them.
		this.setAvailableDiskSpaceValidationActive( 	
				DefaultFactory.getFactory().getSystemPropertyMgr().getBoolean(new AvailableDiskSpaceValidation()));

		this.setMinDiskSpaceAvailableRequirementInBytes( 
				DefaultFactory.getFactory().getConfiguration().getMinDiskSpaceAvailableRequirementInBytes()  );

		this.setActualDiskSpaceAvailableInBytes(installRoot.getRoot().toFile().getFreeSpace());
	}
	

	private long minDiskAvailableSizeRequirementInBytes = 0;
	private long actualDiskAvailableSizeInBytes = 0;
	private boolean availableDiskSpaceValidationActive;
	

	@Override
	public void setMinDiskSpaceAvailableRequirementInBytes(long m) {
			this.minDiskAvailableSizeRequirementInBytes = m;
	}

	@Override
	public long getMinDiskSpaceAvailableRequirementInBytes() {
		return this.minDiskAvailableSizeRequirementInBytes;
	}

	@Override
	public void setActualDiskSpaceAvailableInBytes(long m) {
		this.actualDiskAvailableSizeInBytes = m;
	}

	@Override
	public long getActualDiskSpaceAvailableInBytes() {
		return this.actualDiskAvailableSizeInBytes;
	}

	@Override
	public void validate() throws Snail4jException {

		if (this.isAvailableDiskSpaceValidationActive()) {
			if (
					this.getActualDiskSpaceAvailableInBytes() < this.getMinDiskSpaceAvailableRequirementInBytes()
			) {
				String m = DefaultFactory.getFactory().getMessages().insufficientDiskSpace(
						this.getActualDiskSpaceAvailableInBytes(),
						this.getMinDiskSpaceAvailableRequirementInBytes()
						);
				throw new Snail4jException(m);
			}
		}
		
	}

	@Override
	public boolean isAvailableDiskSpaceValidationActive() {
		return availableDiskSpaceValidationActive;
	}

	@Override
	public void setAvailableDiskSpaceValidationActive(boolean a) {
		this.availableDiskSpaceValidationActive = a;
		
	}

}
