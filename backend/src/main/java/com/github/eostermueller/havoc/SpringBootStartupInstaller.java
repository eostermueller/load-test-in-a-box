package com.github.eostermueller.havoc;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.github.eostermueller.tjp.launcher.Configuration;
import com.github.eostermueller.tjp.launcher.DefaultFactory;

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

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		Configuration cfg;
		try {
			cfg = DefaultFactory.getFactory().getConfiguration();
			PerfGoatInstaller perfGoatInstaller = new PerfGoatInstaller(cfg);
			perfGoatInstaller.install();
		} catch (PerfGoatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
