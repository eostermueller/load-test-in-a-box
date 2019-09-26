package com.github.eostermueller.tjp.launcher.agent;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.github.eostermueller.havoc.launcher.CannotFindTjpFactoryClass;
import com.github.eostermueller.havoc.launcher.ConfigLookup;
import com.github.eostermueller.havoc.launcher.ConfigVariableNotFoundException;
import com.github.eostermueller.havoc.launcher.DefaultFactory;

public class ConfigLookupTest {
	TestConfiguration cfg = null;
	String expectedJavaHome = null;
	 @Rule
	    public TemporaryFolder testFolder = new TemporaryFolder();
	 File tmpFolder = null;
	
	@Before
	public void setup() throws IOException {
    	this.tmpFolder = testFolder.newFolder();
    	this.expectedJavaHome = this.tmpFolder.getAbsolutePath().toString();
    	Path p = Paths.get(this.expectedJavaHome);
		cfg = new TestConfiguration();
		cfg.setJavaHome(p);

	}
	@Test
	public void canResolveIntVariable() throws CannotFindTjpFactoryClass, ConfigVariableNotFoundException {
		
		ConfigLookup configLookup = DefaultFactory.getFactory().createConfigLookup();
		configLookup.setConfiguration(cfg);
		int actualMax = cfg.getMaxExceptionCountPerEvent();
		String strActualMax = String.valueOf(actualMax);
		
		
		String resolvedText = configLookup.getValue("maxExceptionCountPerEvent");
		
		assertEquals(strActualMax, resolvedText);
	}
	@Test
	public void canResolveStringVariable() throws CannotFindTjpFactoryClass, ConfigVariableNotFoundException {
		
		ConfigLookup configLookup = DefaultFactory.getFactory().createConfigLookup();
		configLookup.setConfiguration(cfg);
		
		String expectedZipFileName = cfg.getJMeterFilesZipFileName();
		
		String actualZipFileName = configLookup.getValue("jmeterFilesZipFileName");
		assertEquals(expectedZipFileName, actualZipFileName);
	}
	@Test
	public void canResolvePathVariable() throws CannotFindTjpFactoryClass, ConfigVariableNotFoundException {
		ConfigLookup configLookup = DefaultFactory.getFactory().createConfigLookup();
		configLookup.setConfiguration(cfg);
		
		String actualJavaHome = configLookup.getValue("javaHome");
		
		assertEquals(
				this.expectedJavaHome + "/", 
				actualJavaHome);
	}

}
