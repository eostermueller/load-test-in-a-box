package com.github.eostermueller.snail4j.install;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import com.github.eostermueller.snail4j.Application;
import com.github.eostermueller.snail4j.DefaultFactory;
import com.github.eostermueller.snail4j.Snail4jException;
import com.github.eostermueller.snail4j.launcher.CannotFindSnail4jFactoryClass;
import com.github.eostermueller.snail4j.launcher.ConfigVariableNotFoundException;
import com.github.eostermueller.snail4j.launcher.Configuration;
import com.github.eostermueller.snail4j.launcher.Event;
import com.github.eostermueller.snail4j.launcher.Messages;
import com.github.eostermueller.snail4j.processmodel.ProcessModelSingleton;
import com.github.eostermueller.snail4j.util.OsUtils;
import com.github.eostermueller.snail4j.util.PathUtil;

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
	private String BLANK_SPACE = "\n\n\n";
	@Autowired
	private ConfigurableApplicationContext ctx;

	@Autowired
	private ServerProperties serverProperties;
	
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
				String message = DefaultFactory.getFactory().getMessages().abortingInstall(e);
				DefaultFactory.getFactory().getEventHistory().addException(message, e);
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
		
		cfg.setWorkbenchAgentPort( this.serverProperties.getPort() );
		
		LOGGER.info( String.format("Workbench Agent listening on tcp port [%d].", cfg.getWorkbenchAgentPort() ) );
		
		
		
		Configuration cfg2 = DefaultFactory.getFactory().getConfiguration();
		LOGGER.info( String.format("TAKE 2: Workbench Agent listening on tcp port [%d].", cfg2.getWorkbenchAgentPort() ) );
		
		
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
				LOGGER.debug(Snail4jInstaller.LOG_PREFIX+"Maven online mode: " + cfg.isMavenOnline() );
				LOGGER.debug(Snail4jInstaller.LOG_PREFIX+"snail4j maven repo location: " + cfg.isSnail4jMavenRepo() );
				
				int countOfFailedPreChecks = snail4jInstaller.preInstallValidation(cfg);

				if (countOfFailedPreChecks==0 || Application.commandLineArguments.isForceLaunch() )
					snail4jInstaller.install();
				else
					throw new Snail4jException(m.startupAborted(countOfFailedPreChecks) );
				
		    	LOGGER.info(Snail4jInstaller.LOG_PREFIX+"Install finished.  Ready to load test!");
		    	dispEndInstallBanner( m.successfulInstallation() );
		    	dispSuccessLargeLetters();
				DefaultFactory.getFactory().getEventHistory().getEvents().add(
						Event.create(m.successfulInstallation()) );
			} catch (Snail4jException e) {
		    	dispEndInstallBanner( m.failedInstallation() );
		    	dispFailLargeLetters();
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
			this.LOGGER.error("Install has been skipped!   It only runs when load-test-in-a-box is launched with 'java -jar'. Startup: [" + path + "]" );
		}
	}
	private void dispSuccessLargeLetters() {
		System.out.println("     _______. __    __    ______   ______  _______     _______.     _______.");
		System.out.println("    /       ||  |  |  |  /      | /      ||   ____|   /       |    /       |");
		System.out.println("   |   (----`|  |  |  | |  ,----'|  ,----'|  |__     |   (----`   |   (----`");
		System.out.println("    \\   \\    |  |  |  | |  |     |  |     |   __|     \\   \\        \\   \\    ");
		System.out.println(".----)   |   |  `--'  | |  `----.|  `----.|  |____.----)   |   .----)   |   ");
		System.out.println("|_______/     \\______/   \\______| \\______||_______|_______/    |_______/    ");
	}
	private void dispFailLargeLetters() {
		System.out.println(" _______    ___       __   __      ");
		System.out.println("|   ____|  /   \\     |  | |  |     ");
		System.out.println("|  |__    /  ^  \\    |  | |  |     ");
		System.out.println("|   __|  /  /_\\  \\   |  | |  |     ");
		System.out.println("|  |    /  _____  \\  |  | |  `----.");
		System.out.println("|__|   /__/     \\__\\ |__| |_______|");		
	}
	private void dispStartInstallBanner(String msg) {
		
		System.out.println(BLANK_SPACE);
		System.out.println("########################################" );
		System.out.println("########################################" );
		System.out.println(Snail4jInstaller.LOG_PREFIX );
		System.out.println(Snail4jInstaller.LOG_PREFIX + msg);
		System.out.println(Snail4jInstaller.LOG_PREFIX );
		System.out.println(Snail4jInstaller.LOG_PREFIX );
	}
	private void dispEndInstallBanner(String msg) {
		System.out.println(Snail4jInstaller.LOG_PREFIX );
		System.out.println(Snail4jInstaller.LOG_PREFIX );
		System.out.println(Snail4jInstaller.LOG_PREFIX + msg);
		System.out.println(Snail4jInstaller.LOG_PREFIX );
		System.out.println(Snail4jInstaller.LOG_PREFIX );
		System.out.println("########################################" );
		System.out.println("########################################" );
		System.out.println(BLANK_SPACE);
	}

}
