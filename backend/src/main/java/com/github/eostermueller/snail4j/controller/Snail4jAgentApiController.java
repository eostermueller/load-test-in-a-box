package com.github.eostermueller.snail4j.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.eostermueller.snail4j.DefaultFactory;
import com.github.eostermueller.snail4j.Snail4jException;
import com.github.eostermueller.snail4j.launcher.BootstrapConfig;
import com.github.eostermueller.snail4j.launcher.CannotFindSnail4jFactoryClass;
import com.github.eostermueller.snail4j.launcher.ConfigReaderWriter;
import com.github.eostermueller.snail4j.launcher.ConfigVariableNotFoundException;
import com.github.eostermueller.snail4j.launcher.Configuration;
import com.github.eostermueller.snail4j.processmodel.ProcessModelSingleton;


@RequestMapping("/snail4j")
@RestController
@EnableAutoConfiguration
public class Snail4jAgentApiController {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());	
	@Autowired
	private ResourceLoader resourceLoader;
	
	/**
	 * Launches a number of processes that comprise the "System Under Test".
	 * Does not wait for launch to complete.
	 * @return
	 * @throws Snail4jException
	 * @throws ConfigVariableNotFoundException
	 * @throws IOException
	 */
	@GetMapping("/startSut")
	public AgentApiResponse startSystemUnderTest() throws Snail4jException, ConfigVariableNotFoundException, IOException {
		AgentApiResponse apiResponse = new AgentApiResponse( System.nanoTime() );
		LOGGER.info("About to start snail4j system under test!");
		ProcessModelSingleton.getInstance().getSystemUnderTest().start();
		apiResponse.setStatus(AgentStatus.SUCCESS);
		LOGGER.info(" started!!");
		return apiResponse;
	}
	@GetMapping("/stopSut")
	public AgentApiResponse stopSystemUnderTest() throws Snail4jException, ConfigVariableNotFoundException, IOException {
		AgentApiResponse apiResponse = new AgentApiResponse( System.nanoTime() );
		LOGGER.info("About to stop snail4j system under test!");
		ProcessModelSingleton.getInstance().getSystemUnderTest().stop();
		LOGGER.info(" stopped!!");
		apiResponse.setStatus(AgentStatus.SUCCESS);
		return apiResponse;
	}

	/**
	 * Apply the given json to the singleton Configuration and to the snail4j.json configuration file on the disk where the snail4j agent is running.
	 * @param json
	 * @return
	 * @throws Snail4jException
	 */
	@RequestMapping(
		    value = "/config", 		    		    
		    method = RequestMethod.PUT)	
	public ApiResponse2 updateConfig(
			@RequestBody String json
			) throws Snail4jException {
		ApiResponse2 apiResponse = new ApiResponse2( System.nanoTime() );
		
		ConfigReaderWriter defaultConfigReaderWriter = DefaultFactory.getFactory().getConfigReaderWriter();
		Configuration cfg = defaultConfigReaderWriter.toObject( json, DefaultFactory.getFactory().getConfiguration().getClass() );
		apiResponse.setStatus(Status2.SUCCESS);
		
		defaultConfigReaderWriter.write(new BootstrapConfig().getFullPathToConfigFile().toFile(), cfg);
		DefaultFactory.getFactory().setConfiguration(cfg);
		LOGGER.info("call to snail4j/config");

		return apiResponse;
		
	}
	
	@GetMapping("/config")
	public String getConfig() throws Snail4jException {
		
		ConfigReaderWriter defaultConfigReaderWriter = DefaultFactory.getFactory().getConfigReaderWriter();
		String myJson = defaultConfigReaderWriter.toJson( DefaultFactory.getFactory().getConfiguration() );
		
		return myJson;
	}
	@GetMapping("/getEventHistory")
	public String getEventHistory() throws CannotFindSnail4jFactoryClass {
		return DefaultFactory.getFactory().getEventHistory().debug();
	}
	@GetMapping("/getExceptionHistory")
	public String getExceptionHistory() throws CannotFindSnail4jFactoryClass {
		return Snail4jException.getExceptionHistory().toString();
	}
	@GetMapping("/startLg")
	public AgentApiResponse startLoadGenerator() throws Snail4jException, ConfigVariableNotFoundException, IOException {
		AgentApiResponse apiResponse = new AgentApiResponse( System.nanoTime() );

		LOGGER.info("About to start the wind tunnel!");
		ProcessModelSingleton.getInstance().getLoadGenerator().start();
		LOGGER.info(" started!!");
		apiResponse.setStatus(AgentStatus.SUCCESS);
		return apiResponse;
	}
	@GetMapping("/stopLg")
	public AgentApiResponse stopLoadGenerator() throws Snail4jException, ConfigVariableNotFoundException, IOException {
		AgentApiResponse apiResponse = new AgentApiResponse( System.nanoTime() );

		LOGGER.info("About to stop the load generator!");
		ProcessModelSingleton.getInstance().getLoadGenerator().shutdown();
		LOGGER.info(" stopped!!");
		
		apiResponse.setStatus(AgentStatus.SUCCESS);
		//apiResponse.setMessage(message);
		return apiResponse;
	}
	
}
