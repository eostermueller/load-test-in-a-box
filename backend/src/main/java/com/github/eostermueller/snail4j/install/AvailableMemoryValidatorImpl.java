package com.github.eostermueller.snail4j.install;

import com.github.eostermueller.snail4j.DefaultFactory;
import com.github.eostermueller.snail4j.Snail4jException;
import com.github.eostermueller.snail4j.launcher.BootstrapConfig;
import com.github.eostermueller.snail4j.systemproperty.AvailableMemoryValidation;

/**
 * Important doc:
 * 
 * https://developers.redhat.com/articles/2022/04/19/java-17-whats-new-openjdks-container-awareness#why_container_awareness_is_important
 * <pre>
 * cgroups v2 support
Since Java 15, OpenJDK detects the cgroup version in use and detects limits according to cgroup version-specific settings. For Java 15 and onwards, OpenJDK supports cgroups v1 as well as cgroups v2 or the unified hierarchy (see JDK-8230305 for more on this).

If you run Java 11 or Java 8 on a system that has only cgroups v2 , no container detection will be in place and the host values will be used instead. As explained earlier, this might yield unexpected application behavior in containerized deployments.

One quick way to show which cgroup version is in use on a system is the -XshowSettings:system option of the java launcher. (This option is Linux-specific.) Here's an example:

$ java -XshowSettings:system -version
Operating System Metrics:
    Provider: cgroupv2
    Effective CPU Count: 2
    CPU Period: 100000us
    CPU Quota: 200000us
    CPU Shares: 1024us
    List of Processors: N/A
    List of Effective Processors, 4 total:
    0 1 2 3
    List of Memory Nodes: N/A
    List of Available Memory Nodes, 1 total:
    0
    Memory Limit: 1.00G
    Memory Soft Limit: 800.00M
    Memory & Swap Limit: 1.00G
 * </pre
 * @author eoste
 *
 */
public class AvailableMemoryValidatorImpl implements AvailableMemoryValidator {

	public AvailableMemoryValidatorImpl() throws Snail4jException {
		
		this.setAvailableMemoryValidationActive( 	new BootstrapConfig().getSystemPropertyMgr().getBoolean(new AvailableMemoryValidation()));
		
		com.sun.management.OperatingSystemMXBean bean =
	            (com.sun.management.OperatingSystemMXBean)
	                    java.lang.management.ManagementFactory.getOperatingSystemMXBean();
	    
		this.setActualMemoryAvailableInBytes( bean.getFreePhysicalMemorySize() );
	    
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
