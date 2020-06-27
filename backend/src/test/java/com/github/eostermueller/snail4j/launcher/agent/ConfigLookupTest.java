package com.github.eostermueller.snail4j.launcher.agent;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.eostermueller.snail4j.DefaultFactory;
import com.github.eostermueller.snail4j.OS;
import com.github.eostermueller.snail4j.Snail4jException;
import com.github.eostermueller.snail4j.launcher.ConfigLookup;
import com.github.eostermueller.snail4j.launcher.ConfigVariableNotFoundException;

public class ConfigLookupTest {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
	TestConfiguration cfg = null;
	String expectedJavaHome = null;
	 @Rule
	    public TemporaryFolder testFolder = new TemporaryFolder();
	 File tmpFolder = null;
	
	@Before
	public void setup() throws IOException, Snail4jException  {
		LOGGER.debug("in ConfigLookupTest#setup() a");
    	this.tmpFolder = testFolder.newFolder();
		LOGGER.debug("in ConfigLookupTest#setup() b");
    	this.expectedJavaHome = this.tmpFolder.getAbsolutePath().toString();
		LOGGER.debug("in ConfigLookupTest#setup() c");
    	
    	Path p = Paths.get(this.expectedJavaHome);
		LOGGER.debug("in ConfigLookupTest#setup() d");
    	    	
		cfg = new TestConfiguration();
		LOGGER.debug("in ConfigLookupTest#setup() e");
		cfg.setJavaHome(p);
		LOGGER.debug("in ConfigLookupTest#setup() f");

	}
	@Test
	public void canResolveIntVariable() throws ConfigVariableNotFoundException, Snail4jException {
		
		ConfigLookup configLookup = DefaultFactory.getFactory().createConfigLookup();
		configLookup.setConfiguration(cfg);
		int actualMax = cfg.getMaxExceptionCountPerEvent();
		String strActualMax = String.valueOf(actualMax);
		
		String resolvedText = configLookup.getValue("maxExceptionCountPerEvent");
		
		assertEquals(strActualMax, resolvedText);
	}
	@Test
	public void canResolveStringVariable() throws ConfigVariableNotFoundException, Snail4jException {
		
		ConfigLookup configLookup = DefaultFactory.getFactory().createConfigLookup();
		configLookup.setConfiguration(cfg);
		
		String expectedZipFileName = cfg.getJMeterFilesZipFileName();
		
		String actualZipFileName = configLookup.getValue("jmeterFilesZipFileName");
		assertEquals(expectedZipFileName, actualZipFileName);
	}
	@Test
	public void canResolvePathVariable() throws ConfigVariableNotFoundException, Snail4jException {
		ConfigLookup configLookup = DefaultFactory.getFactory().createConfigLookup();
		configLookup.setConfiguration(cfg);
		
		String actualJavaHome = configLookup.getValue("javaHome");
		
		String myExpectedJavaHome = this.expectedJavaHome;
    	if (OS.getOs().getOsFamily()==OS.OsFamily.Windows) {
    		myExpectedJavaHome = myExpectedJavaHome.replace("\\", "/");
    		myExpectedJavaHome = "/" + myExpectedJavaHome;
    	}
		
		assertEquals(
				myExpectedJavaHome, 
				actualJavaHome);
	}

}
