package com.github.eostermueller.snail4j.health;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketImpl;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Health.Builder;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import com.github.eostermueller.snail4j.OsUtils;


public abstract class AbstractTcpHealthIndicator extends AbstractSpringNetworkHealthIndicator {

	/**
	 *  	@stolenFr0m: http://jdpgrailsdev.github.io/blog/2014/11/11/spring_boot_health_indicators_auto_config.html
	 */
	@Override
    public Health health() {
		
		if (this.getPort()==UNINITIALIZED_PORT)
			return Health.down().build();
		else if (OsUtils.isTcpPortActive(
				this.getInetAddress().getHostAddress(), 
				this.getPort(), 
				TIMEOUT.intValue()))
            return Health.up().build();
		else 
            return Health.down().build();
	}
}
