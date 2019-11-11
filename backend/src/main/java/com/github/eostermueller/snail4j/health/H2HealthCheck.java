package com.github.eostermueller.snail4j.health;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.github.eostermueller.snail4j.DefaultFactory;
import com.github.eostermueller.snail4j.Snail4jException;
import com.github.eostermueller.snail4j.launcher.CannotFindTjpFactoryClass;
import com.github.eostermueller.snail4j.launcher.Configuration;
import com.github.eostermueller.snail4j.launcher.Messages;

public class H2HealthCheck extends AbstractSpringTcpHealthIndicator {

		public H2HealthCheck() {
			try {
				Configuration cfg = DefaultFactory.getFactory().getConfiguration();
				String hostname = cfg.getH2Hostname();
				InetAddress addr = InetAddress.getByName(hostname);
				
				this.setInetAddress(addr);
				this.setPort( cfg.getH2Port() );
			} catch (CannotFindTjpFactoryClass | UnknownHostException e) {
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
