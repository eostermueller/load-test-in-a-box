package com.github.eostermueller.snail4j.health;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.stereotype.Component;

import com.github.eostermueller.snail4j.DefaultFactory;
import com.github.eostermueller.snail4j.Snail4jException;
import com.github.eostermueller.snail4j.launcher.Configuration;
import com.github.eostermueller.snail4j.launcher.Messages;

@Component
public class WorkbenchAgentHealthIndicator extends AbstractTcpHealthIndicator {
	public WorkbenchAgentHealthIndicator() {
		try {
			
			Configuration cfg = getConfiguration();
			String hostname = cfg.getSutAppHostname();
			InetAddress addr = InetAddress.getByName(hostname);
			
			this.setInetAddress(addr);
			this.setPort( cfg.getWorkbenchAgentPort() );
			
			//can't set the port here because we won't know the port until spring notifies us :-(, see methods below
			
		} catch (UnknownHostException | Snail4jException e) {
			Messages m;
			try {
				m = DefaultFactory.getFactory().getMessages();
				throw new Snail4jException( m.unableToFindH2HostAndPort() );
			} catch (Snail4jException e1) {
				e1.printStackTrace();
			}
		}
		
	}

}
