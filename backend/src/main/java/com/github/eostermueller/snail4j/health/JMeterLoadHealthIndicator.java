package com.github.eostermueller.snail4j.health;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

import com.github.eostermueller.snail4j.DefaultFactory;
import com.github.eostermueller.snail4j.OsUtils;
import com.github.eostermueller.snail4j.Snail4jException;
import com.github.eostermueller.snail4j.launcher.Configuration;
import com.github.eostermueller.snail4j.launcher.Messages;

/**
 * If some process is listening on UDP port 4445, then JMeter load from the command line (as is used in snail4j) is in progress.
 * See this for more details:
 * https://github.com/apache/jmeter/blob/master/bin/shutdown.sh
 * @author eoste
 *
 */
@Component
public class JMeterLoadHealthIndicator extends AbstractUdpHealthIndicator {
	public JMeterLoadHealthIndicator() {
		try {
			Configuration cfg = getConfiguration();
			int jmeterNonGuiPort = (int) cfg.getJMeterNonGuiPort();
			InetAddress addr = InetAddress.getByName("localhost");
			
			this.setInetAddress(addr);
			this.setPort( jmeterNonGuiPort );
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
