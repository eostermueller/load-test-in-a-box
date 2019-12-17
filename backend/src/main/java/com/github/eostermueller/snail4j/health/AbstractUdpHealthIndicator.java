package com.github.eostermueller.snail4j.health;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketImpl;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Health.Builder;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import com.github.eostermueller.snail4j.DefaultFactory;
import com.github.eostermueller.snail4j.OsUtils;
import com.github.eostermueller.snail4j.OsUtils.OsResult;
import com.github.eostermueller.snail4j.Snail4jException;
import com.github.eostermueller.snail4j.launcher.Configuration;
import com.github.eostermueller.snail4j.launcher.Messages;

public abstract class AbstractUdpHealthIndicator extends AbstractSpringNetworkHealthIndicator {

	@Override
	public Health health() {
        LOGGER.debug("Testing UDP [" + this.getInetAddress().getHostAddress() + ":" + this.getPort() + "]");
		
		boolean up = OsUtils.isUdpPortActive(getPort());
        LOGGER.debug( up ? "UP" : "DOWN");

		if (up)
			return Health.up().build();
		else
			return Health.down().build();
	}
}
