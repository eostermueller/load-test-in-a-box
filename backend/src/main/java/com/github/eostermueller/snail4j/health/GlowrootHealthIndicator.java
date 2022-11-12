package com.github.eostermueller.snail4j.health;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.github.eostermueller.snail4j.DefaultFactory;
import com.github.eostermueller.snail4j.Snail4jException;
import com.github.eostermueller.snail4j.launcher.Configuration;
import com.github.eostermueller.snail4j.launcher.Messages;
import com.github.eostermueller.snail4j.util.OsUtils;

@Component
public class GlowrootHealthIndicator extends AbstractTcpHealthIndicator {
	
	private static volatile String  glowrootHost = null;
	private Object initLock = new Object();
	

	/**
	 * added thread safety to this ctor because not sure how/when spring boot loads this for health check.
	 */
	public GlowrootHealthIndicator() {
		try {
			Configuration cfg = getConfiguration();
			
		     if (glowrootHost == null) {
		        synchronized (initLock) {
	                if (glowrootHost == null) {
	                    glowrootHost = decideWhichHostnameToUse(cfg.getSutAppHostname(), cfg.getGlowrootPort());
		            }
		        }
		    }
		     
			InetAddress addr = InetAddress.getByName(glowrootHost);
			this.setInetAddress(addr);
			this.setPort(cfg.getGlowrootPort() );
			
		} catch (UnknownHostException | Snail4jException e) {
			Messages m;
			try {
				m = DefaultFactory.getFactory().getMessages();
				throw new Snail4jException( m.unableToFindGlowrootHostAndPort() );
			} catch (Snail4jException e1) {
				e1.printStackTrace();
			}
		}
		
	}
	public int getTimeout() {
		return 	(int) TimeUnit.SECONDS.toMillis(20); //for some reason glowroot doesn't respond very quickly, need to wait longer.

	}

	/**
	 * As a security measure, glowroot is (by default) only available at localhost,
	 * ....and this is how the workbench installs it.
	 * 
	 * But if you point a browser at a workbench installation on a different host,
	 * the end user will need to follow the glowroot instructions below.
	 * 
	 * "By default, the Glowroot UI binds to 127.0.0.1 and is only accessible from localhost. 
	 * If you wish to access the Glowroot UI from remote machines, 
	 * change the bind address to 0.0.0.0 under Glowroot UI > Configuration > Web. 
	 * Alternatively, the bind address can be changed by creating (or editing) admin.json (in the same directory as glowroot.jar), e.g."
	 * 
	 * taken from https://github.com/glowroot/glowroot/wiki/Agent-Installation-%28with-Embedded-Collector%29
	 * @param cfg 
	 */
	private String decideWhichHostnameToUse(String hostname, int tcpPort) {
		
		String rcHostname = hostname;
		
		if (!OsUtils.isTcpPortActive(
				rcHostname, 
				tcpPort, 
				//since this is just run at startup, ok to wait a little longer
				10000))  {
			rcHostname = "localhost"; 
			boolean justTest = OsUtils.isTcpPortActive(
					rcHostname, 
					tcpPort, 
					//since this is just run at startup, ok to wait a little longer
					20000); 
		}

		
		return rcHostname;
	}

}
