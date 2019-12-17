package com.github.eostermueller.snail4j.launcher.agent;


import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.github.eostermueller.snail4j.OS;
import com.github.eostermueller.snail4j.launcher.ConfigReaderWriter;
import com.github.eostermueller.snail4j.launcher.Configuration;
import com.github.eostermueller.snail4j.launcher.DefaultConfigReaderWriter;
import com.github.eostermueller.snail4j.launcher.DefaultConfiguration;



/**
 * @author erikostermueller
 *
 */
public class ConfigWriteAndReadTest {
	boolean ynStateChanged = false;
	
	 @Rule
	    public TemporaryFolder testFolder = new TemporaryFolder();
	 File tmpFolder = null;
	 @Before
	 public void setup() throws IOException {
		 this.tmpFolder = testFolder.newFolder();
	 }
	@Test
	public void canWriteAndReadPropertyFile() throws Exception {
		//Configuration cfg = DefaultFactory.getFactory().getConfiguration();
		Configuration cfg = new DefaultConfiguration();
		
		String somePath = null;
		String os = OS.getOs().getPlatformName();
		OS myos = OS.getOs();
		switch( OS.getOs().getOsFamily() ) {
			case Windows:
				somePath = "C:\\foo";
				break;
			default:
				somePath = "/foo";
		}
		int c0unt = 8675309;
		Path path = Paths.get(somePath);
		
		int loadGenDuration = 2539;
		cfg.setLoadGenerationDurationInSeconds(loadGenDuration);
		
		cfg.setJavaHome(path);
		cfg.setMavenHome(path);
		cfg.setMavenZipFileNameWithoutExtension(somePath);
		cfg.setMaxExceptionCountPerEvent(c0unt);
		cfg.setSnail4jHome(path);
		cfg.setSutAppHome(path);
		
//		ConfigReaderWriter configWriter = DefaultFactory.getFactory().getConfigReaderWriter(cfg,tmpFolder);
		
		if (tmpFolder!=null) {
			ConfigReaderWriter configWriter = new DefaultConfigReaderWriter();
			
			File cfgFile = new File(tmpFolder, "snail4j.json");
			configWriter.write(cfgFile,cfg);
			
			cfg = configWriter.read(cfgFile,DefaultConfiguration.class);
			
			assertEquals(somePath,cfg.getJavaHome().toAbsolutePath().toString());
			assertEquals(somePath,cfg.getMavenHome().toAbsolutePath().toString());
			assertEquals(somePath,cfg.getSnail4jHome().toAbsolutePath().toString());
			assertEquals(somePath,cfg.getSutAppHome().toAbsolutePath().toString());
			
			assertEquals( somePath,cfg.getMavenZipFileNameWithoutExtension() );
			assertEquals( somePath + "-bin.zip",cfg.getMavenZipFileName() );
			assertEquals( c0unt, cfg.getMaxExceptionCountPerEvent() );
			assertEquals( loadGenDuration, cfg.getLoadGenerationDurationInSeconds() );
		}
		
		
	}
	@Test
	public void canWriteAndReadBooleansPropertyFile() throws Exception {
		//Configuration cfg = DefaultFactory.getFactory().getConfiguration();
		Configuration cfg = new DefaultConfiguration();
		
		String name="/foo";
		int c0unt = 8675309;
		Path path = Paths.get(name);
		
		cfg.setSnail4jMavenRepo(false);
		
//		ConfigReaderWriter configWriter = DefaultFactory.getFactory().getConfigReaderWriter(cfg,tmpFolder);
		
		if (tmpFolder!=null) {
			ConfigReaderWriter configWriter = new DefaultConfigReaderWriter();
			
			File cfgFile = new File(tmpFolder,"snail4j.json");
			configWriter.write(cfgFile,cfg);
			
			cfg = configWriter.read(cfgFile, DefaultConfiguration.class);
			
			assertEquals( false, cfg.isSnail4jMavenRepo() );
		}
		
		
	}
	
}
