package com.github.eostermueller.tjp.launcher.agent;


import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;



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
		Configuration cfg = DefaultFactory.getFactory().getConfiguration();
		
		String name="/foo";
		int c0unt = 8675309;
		Path path = Paths.get(name);
		
		cfg.setJavaHome(path);
		cfg.setMavenHome(path);
		cfg.setMavenZipFileNameWithoutExtension(name);
		cfg.setMaxExceptionCountPerEvent(c0unt);
		cfg.setPerfGoatHome(path);
		cfg.setSutHome(path);
		
		ConfigReaderWriter configWriter = DefaultFactory.getFactory().getConfigReaderWriter(cfg,tmpFolder);
		
		configWriter.write();
		
		cfg = configWriter.read();
		
		assertEquals(name,cfg.getJavaHome().toAbsolutePath().toString());
		assertEquals(name,cfg.getMavenHome().toAbsolutePath().toString());
		assertEquals(name,cfg.getPerfGoatHome().toAbsolutePath().toString());
		assertEquals(name,cfg.getSutHome().toAbsolutePath().toString());
		
		assertEquals( name,cfg.getMavenZipFileNameWithoutExtension() );
		assertEquals( name + "-bin.zip",cfg.getMavenZipFileName() );
		assertEquals( c0unt, cfg.getMaxExceptionCountPerEvent());
		
		
	}
	
}
