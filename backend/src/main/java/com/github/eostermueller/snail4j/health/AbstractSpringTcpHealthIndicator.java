package com.github.eostermueller.snail4j.health;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.SocketImpl;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Health.Builder;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractSpringTcpHealthIndicator extends AbstractHealthIndicator {
	
	/**
	 * backl0g
	 * https://stackoverflow.com/a/33193011/2377579
	 * @param val
	 */
	private static int BACKLOG = 50; //seems reasonable, per above
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

	@Override
    protected void doHealthCheck(Health.Builder bldr) throws Exception {

    	try {
    	    ServerSocket serverSocket = new ServerSocket(this.getPort(),BACKLOG,this.getInetAddress());
            bldr.up();
    	} catch (IOException e) {
            bldr.down();
    	}    	
    }


}
