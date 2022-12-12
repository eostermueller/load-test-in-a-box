package com.github.eostermueller.snail4j.health;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;

import com.github.eostermueller.snail4j.Snail4jException;
import com.github.eostermueller.snail4j.processmodel.ProcessIdsSingleton;
import com.github.eostermueller.snail4j.util.OsUtils;


public abstract class AbstractTcpHealthIndicator extends AbstractSpringNetworkHealthIndicator {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	private static final String ACTUATOR_PID_KEY = "pid"; //client will see this in request to actuator/health

	/**
	 *  	@stolenFr0m: http://jdpgrailsdev.github.io/blog/2014/11/11/spring_boot_health_indicators_auto_config.html
	 *  
	 *  	Requires that SpringBoot management.endpoint.health.show-details be set to 'always', per
	 *  https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html#actuator.endpoints.health
	 */
	@Override
    public Health health() {
		Status currentStatus = Status.DOWN;
		Health rc = null;
		try {
			LOGGER.debug("@ Health.health 01.a");
			if (this.getPort()==UNINITIALIZED_PORT)
				currentStatus = Status.DOWN;
			else if (OsUtils.isTcpPortActive(
					this.getInetAddress().getHostAddress(), 
					this.getPort(), 
					getTimeout() )) {
				currentStatus = Status.UP;
			}
			LOGGER.debug("@ Health.health 01.b");
			
			rc = healthDetail(currentStatus);
			LOGGER.debug("@ Health.health 01.c");
			
			if ( rc.getStatus() != Status.UP )
				ProcessIdsSingleton.getInstance().reset(getHealthIndicatorName()); //force getProcessId() to requery process id.
			LOGGER.debug("@ Health.health 01.d");
		} catch (Snail4jException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		LOGGER.debug("@ Health.health 01.e");
		return rc;
	}
	
	private Health healthDetail(Status currentStatus) throws Snail4jException {
		long processId = ProcessIdsSingleton.UNINIT_PID;
		if (this.isTrackingProcessId() ) {
			processId = this.getProcessId();
		}
		Health rc = null;
		
		if (currentStatus==Status.UP ) {
			rc = Health.up().
					withDetail(ACTUATOR_PID_KEY, processId)
					.build();
		} else {
			rc = Health.down().
					withDetail(ACTUATOR_PID_KEY, processId)
					.build();
		}
		return rc;
	}
	public int getTimeout() {
		return TIMEOUT.intValue();
	}
}
