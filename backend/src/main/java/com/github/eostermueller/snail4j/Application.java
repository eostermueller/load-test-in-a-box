/**
 * 
 */
package com.github.eostermueller.snail4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
/**
 * @author eostermueller
 *
 */
@SpringBootApplication
public class Application extends SpringBootServletInitializer {
	public static String APP_NAME = "load-test-in-a-box";
	public static String INSTALL_ROOT = "." + APP_NAME;
	public static String CONFIG_FILE_NAME = APP_NAME + ".json";
	public static String PID_FILE_NAME = APP_NAME + ".pid";
	
	public static CommandLineArgs commandLineArguments = null;
//public class Application implements ApplicationRunner {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	
	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(Application.class);
		Application.commandLineArguments = CommandLineArgs.create(args);
		application.addListeners(new ApplicationPidFileWriter(PID_FILE_NAME));
		application.run(args);
	}

//	 @Override
//	    public void run(ApplicationArguments args) throws Exception {
//	        LOGGER.info("Application started with command-line arguments: {}", Arrays.toString(args.getSourceArgs()));
//	        LOGGER.info("NonOptionArgs: {}", args.getNonOptionArgs());
//	        LOGGER.info("OptionNames: {}", args.getOptionNames());
//
//	        for (String name : args.getOptionNames()){
//	            LOGGER.info("arg-" + name + "=" + args.getOptionValues(name));
//	        }
//
//	        boolean containsOption = args.containsOption("person.name");
//	        LOGGER.info("Contains person.name: " + containsOption);
//	    }	

}
