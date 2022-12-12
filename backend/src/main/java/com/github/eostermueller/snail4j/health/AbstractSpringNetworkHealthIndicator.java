package com.github.eostermueller.snail4j.health;

import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

import com.github.eostermueller.snail4j.DefaultFactory;
import com.github.eostermueller.snail4j.Snail4jException;
import com.github.eostermueller.snail4j.launcher.CannotFindSnail4jFactoryClass;
import com.github.eostermueller.snail4j.launcher.Configuration;
import com.github.eostermueller.snail4j.processmodel.ProcessIdsSingleton;
import com.github.eostermueller.snail4j.util.JdkUtils;
import com.github.eostermueller.snail4j.util.StringUtils;

/**
 * TODO:  add classname, to be used to get pid from jcmd.
 * add classname to the load-test-in-a-box.json.
 * add pid to health response with "withDetails('pid',myPid)"
 * as detailed here:  https://www.baeldung.com/spring-boot-health-indicators
 * @author eoste
 *
 */
abstract public class AbstractSpringNetworkHealthIndicator implements HealthIndicator {
	protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	protected static final int UNINITIALIZED_PORT = -1;

	protected final static Long TIMEOUT = TimeUnit.SECONDS.toMillis(5);

	/**
	 * Spring derives the health indicator 'name' from the name of the class implementing HealthIndicator.
	 * Per https://docs.spring.io/spring-boot/docs/2.1.7.RELEASE/reference/html/production-ready-endpoints.html:
	 * <PRE>
	 * The identifier for a given HealthIndicator is the name of the bean 
	 *  without the HealthIndicator suffix, if it exists. In the preceding example [MyHealthIndicator], 
	 *  the health information is available in an entry named my."
	 *  <PRE>
	 * @return
	 */
	public String getHealthIndicatorName() {
		String simpleClassName = this.getClass().getSimpleName();
		return StringUtils.getSpringHealthIndicatorName(simpleClassName);
	}
	
	public InetAddress getInetAddress() {
		return inetAddress;
	}

	public void setInetAddress(InetAddress inetAddress) {
		this.inetAddress = inetAddress;
	}

	private boolean isTrackingProcessId = false;
	
	public boolean isTrackingProcessId() {
		boolean rc = true;
		if (this.getJcmdClassName() == null || this.getJcmdClassName().length() ==0) {
			rc = false;
		}
		return rc;
	}

	private InetAddress inetAddress;
	public long getProcessId() throws Snail4jException {
		ProcessIdsSingleton processIds = ProcessIdsSingleton.getInstance(); 
		
		long cachedPid = processIds.get( this.getHealthIndicatorName() );
		if (cachedPid == ProcessIdsSingleton.UNINIT_PID) {
			cachedPid = JdkUtils.getPidForProcessNameContains(getJcmdClassName());
			LOGGER.debug("looking for pid where jcmd command line contains [" +  getJcmdClassName() + "] + pid [" + cachedPid + "]");
			if (cachedPid != ProcessIdsSingleton.UNINIT_PID) {
				processIds.put(this.getHealthIndicatorName(), cachedPid);
			} else {  //so any previously cached PID will thus be invalid, so remove it from cache.
				ProcessIdsSingleton.getInstance().reset(getHealthIndicatorName());
			}
			//else {
			//	Snail4jException e = new Snail4jException("Unabled to find PID for command line containing [" + getJcmdClassName() + "]" );
			//	throw e;
			//}
		}
		return cachedPid;
	}

	private int port = UNINITIALIZED_PORT;
	/**
	 * For the process listening on port 'getPort()', the classname listed in JDK's jcmd output
	 */
	private String jcmdClassName = null;

	public String getJcmdClassName() {
		return jcmdClassName;
	}

	public void setJcmdClassName(String jcmdClassName) {
		this.jcmdClassName = jcmdClassName;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	abstract public Health health();
	protected Configuration getConfiguration() throws CannotFindSnail4jFactoryClass, Snail4jException {
		return DefaultFactory.getFactory().getConfiguration();
	}

}
