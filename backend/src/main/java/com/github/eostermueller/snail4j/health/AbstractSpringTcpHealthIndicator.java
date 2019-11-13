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


public abstract class AbstractSpringTcpHealthIndicator implements HealthIndicator {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
	private final static Long TIMEOUT = TimeUnit.SECONDS.toMillis(10);	
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

	/**
	 *  	@st0lenFr0m: http://jdpgrailsdev.github.io/blog/2014/11/11/spring_boot_health_indicators_auto_config.html
	 */
	@Override
    public Health health() {
	     Socket socket = null;

	        try {

	        	socket = new Socket();
                LOGGER.debug("Testing [" + this.getInetAddress().getHostAddress() + ":" + this.getPort() + "]");
	        	
	            socket.connect(new InetSocketAddress(this.getInetAddress().getHostAddress(), this.getPort()), TIMEOUT.intValue());
	            LOGGER.debug("UP");
	            return Health.up().build();
	        } catch (final Exception e) {
	            LOGGER.debug("DOWN");
	            return Health.down(e).build();
	        } finally {
	            if (socket != null) {
	                try {
	                    socket.close();
	                } catch (final IOException e) {
	                    LOGGER.debug("Unable to close consumer socket.", e);
	                }
	            }
	        }
	   }
}
