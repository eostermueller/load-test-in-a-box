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

abstract public class AbstractSpringNetworkHealthIndicator implements HealthIndicator {
	protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	protected final static Long TIMEOUT = TimeUnit.SECONDS.toMillis(10);

	public InetAddress getInetAddress() {
		return inetAddress;
	}

	public void setInetAddress(InetAddress inetAddress) {
		this.inetAddress = inetAddress;
	}

	private InetAddress inetAddress;
	private int port;

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
