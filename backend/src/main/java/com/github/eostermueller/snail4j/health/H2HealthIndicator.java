package com.github.eostermueller.snail4j.health;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.stereotype.Component;

import com.github.eostermueller.snail4j.DefaultFactory;
import com.github.eostermueller.snail4j.Snail4jException;
import com.github.eostermueller.snail4j.launcher.Configuration;
import com.github.eostermueller.snail4j.launcher.Messages;

@Component
public class H2HealthIndicator extends AbstractTcpHealthIndicator {

		public H2HealthIndicator() {
			try {
				Configuration cfg = getConfiguration();
				String hostname = cfg.getH2Hostname();
				InetAddress addr = InetAddress.getByName(hostname);
				
				this.setInetAddress(addr);
				this.setPort( cfg.getH2Port() );
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
