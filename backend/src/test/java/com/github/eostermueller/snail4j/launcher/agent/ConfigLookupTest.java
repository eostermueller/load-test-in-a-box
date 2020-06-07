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

import com.github.eostermueller.snail4j.DefaultFactory;
import com.github.eostermueller.snail4j.OS;
import com.github.eostermueller.snail4j.Snail4jException;
import com.github.eostermueller.snail4j.launcher.ConfigLookup;
import com.github.eostermueller.snail4j.launcher.ConfigVariableNotFoundException;

public class ConfigLookupTest {
	TestConfiguration cfg = null;
	String expectedJavaHome = null;
	 @Rule
	    public TemporaryFolder testFolder = new TemporaryFolder();
	 File tmpFolder = null;
	
	@Before
	public void setup() throws IOException, Snail4jException  {
    	this.tmpFolder = testFolder.newFolder();
    	this.expectedJavaHome = this.tmpFolder.getAbsolutePath().toString();
    	
    	Path p = Paths.get(this.expectedJavaHome);
    	    	
		cfg = new TestConfiguration();
		cfg.setJavaHome(p);

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
