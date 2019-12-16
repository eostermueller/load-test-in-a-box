package com.github.eostermueller.snail4j.health;


import java.net.InetAddress;

import java.net.UnknownHostException;

import org.springframework.stereotype.Component;

import com.github.eostermueller.snail4j.DefaultFactory;
import com.github.eostermueller.snail4j.Snail4jException;
import com.github.eostermueller.snail4j.launcher.CannotFindSnail4jFactoryClass;
import com.github.eostermueller.snail4j.launcher.Configuration;
import com.github.eostermueller.snail4j.launcher.Messages;
@Component
public class SutAppHealthIndicator extends AbstractTcpHealthIndicator {

	public SutAppHealthIndicator() {
		try {
			Configuration cfg = DefaultFactory.getFactory().getConfiguration();
			String hostname = cfg.getSutAppHostname();
			InetAddress addr = InetAddress.getByName(hostname);
			
			this.setInetAddress(addr);
			this.setPort( cfg.getSutAppPort() );
		} catch (UnknownHostException | Snail4jException e) {
			Messages m;
			try {
				m = DefaultFactory.getFactory().getMessages();
				throw new Snail4jException( m.unableToFindSutHostAndPort() );
			} catch (Snail4jException e1) {
				e1.printStackTrace();
			}
		}
	}

}
