package com.github.eostermueller.snail4j;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import com.github.eostermueller.snail4j.launcher.CannotFindSnail4jFactoryClass;
import com.github.eostermueller.snail4j.launcher.ConfigVariableNotFoundException;
import com.github.eostermueller.snail4j.launcher.Configuration;
import com.github.eostermueller.snail4j.launcher.Event;
import com.github.eostermueller.snail4j.launcher.Messages;
import com.github.eostermueller.snail4j.processmodel.ProcessModelSingleton;

/**
 * Placeholder class to handle progress meter for browser UI, because this could take a few minutes.
 * This event is executed as late as conceivably possible to indicate that 
 * the application is ready to service requests.
 * 
 * @author erikostermueller
 *
 */
@Component
public class SpringBootSnail4J implements ApplicationListener<ApplicationReadyEvent> {
	@Autowired
	private ConfigurableApplicationContext ctx;

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	private static ProcessModelSingleton PROCESS_MODEL_SINGLETON = null;
	
	public static ProcessModelSingleton getProcessModelSingleton() throws ConfigVariableNotFoundException, Snail4jException {
		return PROCESS_MODEL_SINGLETON;
	}
	public static void setProcessModelSingleton(ProcessModelSingleton val){
		PROCESS_MODEL_SINGLETON = val;
	}
	
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {

		try {
			Configuration cfg = DefaultFactory.getFactory().getConfiguration();									
			
			install(cfg);
			initProcessModel();
			initPorts(cfg);
			
		} catch (Snail4jException | MalformedURLException e) {
			try {
				ProcessModelSingleton.getInstance().setCauseOfSystemFailure(e);
				DefaultFactory.getFactory().getEventHistory().addException("Exception during snail4j startup.", e);
				
			} catch (Snail4jException e1) {
			}
		}
	}

	private void initPorts(Configuration cfg) throws Snail4jException {
	
		List<String> errors = new ArrayList<String>();
		int availableUdpPort = OsUtils.getAvailableUdpPort( (int)cfg.getStartJMeterNonGuiPort(), (int)cfg.getMaxJMeterNonGuiPort() );
		if (availableUdpPort >= 0) {
			cfg.setJMeterNonGuiPort(availableUdpPort);
			LOGGER.debug(String.format("Cfg instance [%d] JMeterNonGuiPort availableUdpPort [%d]", cfg.hashCode(), availableUdpPort) );
		} else {
			errors.add( DefaultFactory.getFactory().getMessages().getNoUdpPortsAvailableBetween((int)cfg.getStartJMeterNonGuiPort(), (int)cfg.getMaxJMeterNonGuiPort()));
		}
		LOGGER.info( String.format("JMeter non-gui port is [%d].  Start [%d] and max port [%d].", cfg.getJMeterNonGuiPort(), cfg.getStartJMeterNonGuiPort(), cfg.getMaxJMeterNonGuiPort() ) );
		
	}
	private void initProcessModel() throws ConfigVariableNotFoundException, Snail4jException {
		ProcessModelSingleton.getInstance();
	}
	private void install(Configuration cfg) throws CannotFindSnail4jFactoryClass, MalformedURLException {
		String path = new PathUtil().getBaseClassspath();
		Messages m = DefaultFactory.getFactory().getMessages();
		if (path.contains(PathUtil.JAR_SUFFIX)
			|| path.contains(PathUtil.ZIP_SUFFIX)
				) { //only install if launched using "java -jar".  Elsewise, installs happen with every "backend" build, because Spring Boot is launched during integration testing. 
			try {
				
				dispStartInstallBanner(m.startInstallMessage() );
				DefaultFactory.getFactory().getEventHistory().getEvents().add(
						Event.create(m.startInstallMessage()) );
				
				Snail4jInstaller snail4jInstaller = DefaultFactory.getFactory().createNewInstaller();
				LOGGER.info(InstallAdvice.LOG_PREFIX+"Maven online mode: " + cfg.isMavenOnline() );
				LOGGER.info(InstallAdvice.LOG_PREFIX+"snail4j maven repo location: " + cfg.isSnail4jMavenRepo() );
				
				int countOfFailedPreChecks = snail4jInstaller.preinstallCheck(cfg);
				LOGGER.info(InstallAdvice.LOG_PREFIX+"Number if install issues: " + countOfFailedPreChecks );

				if (countOfFailedPreChecks==0)
					snail4jInstaller.install();
				else
					throw new Snail4jException(m.startupAborted(countOfFailedPreChecks) );
				
		    	LOGGER.info(InstallAdvice.LOG_PREFIX+"Install finished.  Ready to load test!");
		    	dispEndInstallBanner( m.successfulInstallation() );
				DefaultFactory.getFactory().getEventHistory().getEvents().add(
						Event.create(m.successfulInstallation()) );
			} catch (Snail4jException e) {
		    	dispEndInstallBanner( m.failedInstallation() );
				DefaultFactory.getFactory().getEventHistory().getEvents().add(
						Event.create(m.failedInstallation()) );
				LOGGER.error( e.getMessage() );
				//e.printStackTrace();
				if (ctx !=null) 
					ctx.close();//Shut down spring boot
				else 
					LOGGER.error("Unable to find SpringBoot context, unable to abort startup.");
			} 
			
		} else {
			this.LOGGER.error("Install has been skipped!   It only runs when WindTunnel is launched with 'java -jar'. Startup: [" + path + "]" );
		}
	}

	private void dispStartInstallBanner(String msg) {
		this.LOGGER.info("########################################" );
		this.LOGGER.info("########################################" );
		this.LOGGER.info("####                               #####" );
		this.LOGGER.info("#### " + msg);
		this.LOGGER.info("####                               #####" );
		this.LOGGER.info("####                               #####" );
	}
	private void dispEndInstallBanner(String msg) {
		this.LOGGER.info("####                               #####" );
		this.LOGGER.info("####                               #####" );
		this.LOGGER.info("#### " + msg);
		this.LOGGER.info("####                               #####" );
		this.LOGGER.info("########################################" );
		this.LOGGER.info("########################################" );
	}

}
