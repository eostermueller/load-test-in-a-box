package com.github.eostermueller.snail4j.launcher.agent;



import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.github.eostermueller.snail4j.Application;
import com.github.eostermueller.snail4j.config.DefaultGenericConfigFileReaderWriter;
import com.github.eostermueller.snail4j.config.GenericConfigFileReaderWriter;



/**
 * @author erikostermueller
 *
 */
public class GenericConfigWriteAndReadTest {
	boolean ynStateChanged = false;
	
	private static final String text = "{\r\n"
			+ "  \"web\": {\r\n"
			+ "    \"bindAddress\": \"0.0.0.0\"\r\n"
			+ "  }\r\n"
			+ "}\r\n";
	private static final String text_127_0_0_1 = "{\r\n"
			+ "  \"web\": {\r\n"
			+ "    \"bindAddress\": \"0.0.0.0\"\r\n"
			+ "  }\r\n"
			+ "}\r\n";
	
	@Test
	public void canWriteAndReadGenericConfigFile(@TempDir Path tmpFolder) throws Exception {
		
		if (tmpFolder!=null && tmpFolder.toFile().exists() ) {
			GenericConfigFileReaderWriter configFileUtil = new DefaultGenericConfigFileReaderWriter();
	
			File cfgFile = new File(tmpFolder.toFile(), "admin.json");

			configFileUtil.write(
					text,
					cfgFile.toPath());
			
			String actualRead = configFileUtil.read(cfgFile.toPath());
			
			Assertions.assertEquals(
					text, actualRead,
					"The text we wrote to and read from the file did not batch, but should have"
					);
			
		} else {
			String msg = "JUnit 5 @TempDir annotation did not pass this test a non-null Path.";
			throw new Exception(msg);
		}
	}
}
