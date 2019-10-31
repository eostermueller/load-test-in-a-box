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

import com.github.eostermueller.snail4j.DefaultFactory;
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
		
		String name="/foo";
		int c0unt = 8675309;
		Path path = Paths.get(name);
		
		cfg.setJavaHome(path);
		cfg.setMavenHome(path);
		cfg.setMavenZipFileNameWithoutExtension(name);
		cfg.setMaxExceptionCountPerEvent(c0unt);
		cfg.setSnail4jHome(path);
		cfg.setSutAppHome(path);
		
//		ConfigReaderWriter configWriter = DefaultFactory.getFactory().getConfigReaderWriter(cfg,tmpFolder);
		
		if (tmpFolder!=null) {
			ConfigReaderWriter configWriter = new DefaultConfigReaderWriter(cfg,tmpFolder);
			
			configWriter.write();
			
			cfg = configWriter.read();
			
			assertEquals(name,cfg.getJavaHome().toAbsolutePath().toString());
			assertEquals(name,cfg.getMavenHome().toAbsolutePath().toString());
			assertEquals(name,cfg.getSnail4jHome().toAbsolutePath().toString());
			assertEquals(name,cfg.getSutAppHome().toAbsolutePath().toString());
			
			assertEquals( name,cfg.getMavenZipFileNameWithoutExtension() );
			assertEquals( name + "-bin.zip",cfg.getMavenZipFileName() );
			assertEquals( c0unt, cfg.getMaxExceptionCountPerEvent());
		}
		
		
	}
	
}
