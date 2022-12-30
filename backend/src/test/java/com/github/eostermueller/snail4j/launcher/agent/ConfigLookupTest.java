package com.github.eostermueller.snail4j.launcher.agent;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.eostermueller.snail4j.DefaultFactory;
import com.github.eostermueller.snail4j.Snail4jException;
import com.github.eostermueller.snail4j.launcher.ConfigLookup;
import com.github.eostermueller.snail4j.launcher.ConfigVariableNotFoundException;
import com.github.eostermueller.snail4j.util.OS;

public class ConfigLookupTest {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
	TestConfiguration cfg = null;
	String expectedMavenHome = null;
	@TempDir File tmpFolder;

	 @BeforeEach
	public void setup(@TempDir File tmpFolder) throws IOException, Snail4jException  {
		LOGGER.debug("in ConfigLookupTest#setup() b");
    	this.expectedMavenHome = tmpFolder.getAbsolutePath().toString();
		LOGGER.debug("in ConfigLookupTest#setup() c");
    	
    	Path p = Paths.get(this.expectedMavenHome);
		LOGGER.debug("in ConfigLookupTest#setup() d");
    	    	
		cfg = new TestConfiguration();
		LOGGER.debug("in ConfigLookupTest#setup() e");
		cfg.setMavenHome(p);
		LOGGER.debug("in ConfigLookupTest#setup() f");

	}
	@Test
	public void canResolveLoadGenThreadCount() throws ConfigVariableNotFoundException, Snail4jException {
		
		ConfigLookup configLookup = DefaultFactory.getFactory().createConfigLookup();
		configLookup.setConfiguration(cfg);
		long threadCount = cfg.getLoadGenerationThreads();
		String strActualLoadGenThreadCount = String.valueOf(threadCount);
		
		String resolvedText = configLookup.getValue("loadGenerationThreads");
		
		assertEquals(strActualLoadGenThreadCount, resolvedText);
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
	/**
	 * Won't enhance this to test an ACTUAL maven home folder.
	 * Instead, this test just stuffs a temporary folder name into MavenHome and insures correct serialization when we read it from the confile.
	 * @throws ConfigVariableNotFoundException
	 * @throws Snail4jException
	 */
	@Test
	public void canResolvePathVariable() throws ConfigVariableNotFoundException, Snail4jException {
		ConfigLookup configLookup = DefaultFactory.getFactory().createConfigLookup();
		configLookup.setConfiguration(cfg);
		
		String actualMavenHome = configLookup.getValue("mavenHome");
		
		String myExpectedMavenHome = this.expectedMavenHome;
    	if (OS.getOs().getOsFamily()==OS.OsFamily.Windows) {
    		myExpectedMavenHome = myExpectedMavenHome.replace("\\", "/");
    		myExpectedMavenHome = "/" + myExpectedMavenHome;
    	}
		
		assertEquals(
				myExpectedMavenHome, 
				actualMavenHome);
	}

}
