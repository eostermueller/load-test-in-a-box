package com.github.eostermueller.havoc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.github.eostermueller.havoc.launcher.CannotFindTjpFactoryClass;
import com.github.eostermueller.havoc.launcher.ConfigVariableNotFoundException;
import com.github.eostermueller.havoc.launcher.Configuration;
import com.github.eostermueller.havoc.launcher.DefaultFactory;
import com.github.eostermueller.havoc.launcher.Suite;
import com.github.eostermueller.havoc.processmodel.ProcessModelBuilder;
import com.github.eostermueller.havoc.processmodel.ProcessModelSingleton;

/**
 * Placeholder class to handle progress meter for browser UI, because this could take a few minutes.
 * This event is executed as late as conceivably possible to indicate that 
 * the application is ready to service requests.
 * 
 * @author erikostermueller
 *
 */
@Component
public class SpringBootWindTunnel implements ApplicationListener<ApplicationReadyEvent> {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	private static Object lock = new Object();

	private static ProcessModelSingleton PROCESS_MODEL_SINGLETON = null;
	
	public static ProcessModelSingleton getProcessModelSingleton() throws ConfigVariableNotFoundException, PerfGoatException {
		return PROCESS_MODEL_SINGLETON;
	}
	public static void setProcessModelSingleton(ProcessModelSingleton val){
		PROCESS_MODEL_SINGLETON = val;
	}
	
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		
		try {
			install();
			initProcessModel();
		} catch (PerfGoatException e) {
			try {
				ProcessModelSingleton.getInstance().setCauseOfSystemFailure(e);
			} catch (PerfGoatException e1) {
			}
		}
		
		
	}

	private void initProcessModel() throws ConfigVariableNotFoundException, PerfGoatException {
		ProcessModelSingleton.getInstance();
		
	}
	private void install() {
		String path = new PathUtil().getBaseClassspath();
		if (path.contains(PathUtil.JAR_SUFFIX)) { //only install if launched using "java -jar".  Elsewise, installs happen with every "backend" build, because Spring Boot is launched during integration testing. 
			dispInstallBanner();
			Configuration cfg;
			try {
				cfg = DefaultFactory.getFactory().getConfiguration();
				PerfGoatInstaller perfGoatInstaller = DefaultFactory.getFactory().createNewInstaller(cfg);
				perfGoatInstaller.install();
			} catch (PerfGoatException e) {
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
