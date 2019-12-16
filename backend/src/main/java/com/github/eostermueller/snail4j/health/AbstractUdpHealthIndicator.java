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
	/**
	 * commands for displaying UDP listening ports.
	 */
	String MS_WIN = "netstat -ano -p UDP";
	String MAC = "sudo lsof -iUDP -P -n";
	String LINUX = "netstat -au";
	@Override
    public Health health() {
		boolean up = false;
		try {
			if ( getConfiguration().isOsWin()) {
				OsResult osResult = OsUtils.executeWindowsCmd(MS_WIN);
				String portCriteria = ":" + String.valueOf(this.getPort() ).trim();
				if ( osResult.toString().indexOf(portCriteria) > -1) {
					up = true;
				}
			}
		} catch (Snail4jException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (up)
			return Health.up().build();
		else 
			return Health.down().build();
	}
}
