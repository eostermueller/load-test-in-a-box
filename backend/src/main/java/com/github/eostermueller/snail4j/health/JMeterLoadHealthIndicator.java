package com.github.eostermueller.snail4j.health;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import com.github.eostermueller.snail4j.DefaultFactory;
import com.github.eostermueller.snail4j.JdkUtils;
import com.github.eostermueller.snail4j.JdkUtils.ProcessDescriptor;
import com.github.eostermueller.snail4j.OsUtils;
import com.github.eostermueller.snail4j.Snail4jException;
import com.github.eostermueller.snail4j.launcher.CannotFindSnail4jFactoryClass;
import com.github.eostermueller.snail4j.launcher.Configuration;
import com.github.eostermueller.snail4j.launcher.Messages;

/**
 * @author eoste
 *
 */
@Component
public class JMeterLoadHealthIndicator implements HealthIndicator {

	@Override
	public Health health() {
		int countOfJMeterInstances = 0;
		ProcessDescriptor[] processDescriptors = JdkUtils.getJavaProcesses();
		for(ProcessDescriptor p : processDescriptors) {
			if (p.commandLine.indexOf("ApacheJMeter.jar") > 0) {
				if (p.commandLine.indexOf("snail4j.port")> 0) {
					countOfJMeterInstances++;
				}
			}
		}
		
		if (countOfJMeterInstances==1)
				return Health.up().build();
	    	else 
	            return Health.down().build();
	}
}
