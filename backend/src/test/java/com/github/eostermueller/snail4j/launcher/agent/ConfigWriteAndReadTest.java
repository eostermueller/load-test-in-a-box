package com.github.eostermueller.snail4j.launcher.agent;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.github.eostermueller.snail4j.launcher.ConfigReaderWriter;
import com.github.eostermueller.snail4j.launcher.Configuration;
import com.github.eostermueller.snail4j.launcher.DefaultConfigReaderWriter;
import com.github.eostermueller.snail4j.launcher.DefaultConfiguration;
import com.github.eostermueller.snail4j.util.OS;



/**
 * @author erikostermueller
 *
 */
public class ConfigWriteAndReadTest {
	boolean ynStateChanged = false;
	
	@Test
	public void canWriteAndReadPropertyFile(@TempDir Path tmpFolder) throws Exception {
		Configuration cfg = new DefaultConfiguration();
		
		String somePath = null;
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
		
		cfg.setMavenHome(path);
		cfg.setMavenZipFileNameWithoutExtension(somePath);
		cfg.setMaxExceptionCountPerEvent(c0unt);
		cfg.setSnail4jHome(path);
		cfg.setSutAppHome(path);
		
//		ConfigReaderWriter configWriter = DefaultFactory.getFactory().getConfigReaderWriter(cfg,tmpFolder);
		
		if (tmpFolder!=null) {
			ConfigReaderWriter configWriter = new DefaultConfigReaderWriter();
			
			File cfgFile = new File(tmpFolder.toFile(), "snail4j.json");
			configWriter.write(cfgFile,cfg);
			
			cfg = configWriter.read(cfgFile,DefaultConfiguration.class);
			
			assertEquals(somePath,cfg.getMavenHome().toAbsolutePath().toString());
			assertEquals(somePath,cfg.getSnail4jHome().toAbsolutePath().toString());
			assertEquals(somePath,cfg.getSutAppHome().toAbsolutePath().toString());
			
			assertEquals( somePath,cfg.getMavenZipFileNameWithoutExtension() );
			assertEquals( somePath + "-bin.zip",cfg.getMavenZipFileName() );
			assertEquals( c0unt, cfg.getMaxExceptionCountPerEvent() );
			assertEquals( loadGenDuration, cfg.getLoadGenerationDurationInSeconds() );
		} else {
			String msg = "JUnit 5 @TempDir annotation did not pass this test a non-null Path.";
			throw new Exception(msg);
		}
		
		
	}
	@Test
	public void canWriteAndReadBooleansPropertyFile(@TempDir Path tmpFolder) throws Exception {
		//Configuration cfg = DefaultFactory.getFactory().getConfiguration();
		Configuration cfg = new DefaultConfiguration();
		
		
		cfg.setSnail4jMavenRepo(false);
		
//		ConfigReaderWriter configWriter = DefaultFactory.getFactory().getConfigReaderWriter(cfg,tmpFolder);
		
		if (tmpFolder!=null) {
			ConfigReaderWriter configWriter = new DefaultConfigReaderWriter();
			
			File cfgFile = new File(tmpFolder.toFile(),"snail4j.json");
			configWriter.write(cfgFile,cfg);
			
			cfg = configWriter.read(cfgFile, DefaultConfiguration.class);
			
			assertEquals( false, cfg.isSnail4jMavenRepo() );
		}
		
		
	}
	
}
