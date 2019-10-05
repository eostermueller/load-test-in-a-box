package com.github.eostermueller.havoc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.github.eostermueller.havoc.launcher.Configuration;
import com.github.eostermueller.havoc.launcher.DefaultFactory;

/**
 * Placeholder class to handle progress meter for browser UI, because this could take a few minutes.
 * This event is executed as late as conceivably possible to indicate that 
 * the application is ready to service requests.
 * 
 * @author erikostermueller
 *
 */
@Component
public class SpringBootStartupInstaller implements ApplicationListener<ApplicationReadyEvent> {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		
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
