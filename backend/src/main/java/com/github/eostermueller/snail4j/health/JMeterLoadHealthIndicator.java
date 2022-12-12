package com.github.eostermueller.snail4j.health;


import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import com.github.eostermueller.snail4j.DefaultFactory;
import com.github.eostermueller.snail4j.Snail4jException;
import com.github.eostermueller.snail4j.launcher.Configuration;
import com.github.eostermueller.snail4j.util.JdkUtils;
import com.github.eostermueller.snail4j.util.JdkUtils.ProcessDescriptor;
import com.github.eostermueller.snail4j.util.OsUtils.OsResult;

/**
 * @author eoste
 *
 */
@Component
public class JMeterLoadHealthIndicator implements HealthIndicator {

	@Override
	public Health health() {
		
		int countOfJMeterInstances = 0;
		Configuration cfg;
		
		
		try {
			cfg = DefaultFactory.getFactory().getConfiguration();
			ProcessDescriptor[] processes = JdkUtils.getJavaProcesses();
			
			for(ProcessDescriptor desc : processes) {
				
				if (     desc.commandLine.indexOf("ApacheJMeter.jar") > 0
						 &&  desc.commandLine.indexOf("snail4j.port") > 0      
						 ) {
								countOfJMeterInstances++;
					}
			}
			
		} catch (Snail4jException e) {
			e.printStackTrace();
		}
		
		if (countOfJMeterInstances==1)
				return Health.up().build();
	    	else 
	            return Health.down().build();
	}
}
