package com.github.eostermueller.snail4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.github.eostermueller.snail4j.launcher.CannotFindTjpFactoryClass;
import com.github.eostermueller.snail4j.launcher.ConfigVariableNotFoundException;
import com.github.eostermueller.snail4j.launcher.Configuration;
import com.github.eostermueller.snail4j.launcher.Event;
import com.github.eostermueller.snail4j.launcher.Suite;
import com.github.eostermueller.snail4j.processmodel.ProcessModelBuilder;
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
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	private static Object lock = new Object();

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
			install();
			DefaultFactory.getFactory().getEventHistory().getEvents().add(
					Event.create("snail4j install complete.") );
			initProcessModel();
			DefaultFactory.getFactory().getEventHistory().getEvents().add(
					Event.create("snail4j startup complete.") );
		} catch (Snail4jException e) {
			try {
				ProcessModelSingleton.getInstance().setCauseOfSystemFailure(e);
				DefaultFactory.getFactory().getEventHistory().addException("Exception during snail4j startup.", e);
			} catch (Snail4jException e1) {
			}
		}
	}

	private void initProcessModel() throws ConfigVariableNotFoundException, Snail4jException {
		ProcessModelSingleton.getInstance();
	}
	private void install() {
		String path = new PathUtil().getBaseClassspath();
		if (path.contains(PathUtil.JAR_SUFFIX)) { //only install if launched using "java -jar".  Elsewise, installs happen with every "backend" build, because Spring Boot is launched during integration testing. 
			dispInstallBanner();
			try {
				Snail4jInstaller snail4jInstaller = DefaultFactory.getFactory().createNewInstaller();
				PathUtil pathUtil = new PathUtil();
		    	pathUtil.createSnail4jHomeIfNotExist();
				snail4jInstaller.initSnail4jCfgFile();
				
				Configuration cfg = DefaultFactory.getFactory().getConfiguration();
				LOGGER.info("online: " + cfg.isMavenOnline() );
				LOGGER.info("snail4j maven repo: " + cfg.isSnail4jMavenRepo() );
				snail4jInstaller.install();
				
		    	LOGGER.info("Install finished.  Ready to load test!");

			} catch (Snail4jException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			this.LOGGER.info("Install has been skipped!   It only runs when WindTunnel is launched with 'java -jar'. Startup: [" + path + "]" );
		}
	}

	private void dispInstallBanner() {
		this.LOGGER.info("##########################" );
		this.LOGGER.info("##########################" );
		this.LOGGER.info("####                 #####" );
		this.LOGGER.info("####  I N S T A L L  #####" );
		this.LOGGER.info("####                 #####" );
		this.LOGGER.info("##########################" );
		this.LOGGER.info("##########################" );
	}

}
