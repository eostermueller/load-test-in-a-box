package com.github.eostermueller.snail4j.launcher.agent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.eostermueller.snail4j.Snail4jException;
import com.github.eostermueller.snail4j.launcher.Configuration;
import com.github.eostermueller.snail4j.launcher.DefaultConfigReaderWriter;
import com.github.eostermueller.snail4j.launcher.DefaultConfiguration;

class ConfigSerializationTest {

	@Test
	void canSerializeConfiguration() throws Snail4jException {
				
		DefaultConfiguration testConfiguration = new DefaultConfiguration();
		testConfiguration.setH2Port(65000);
		
		DefaultConfigReaderWriter defaultConfigReaderWriter = new DefaultConfigReaderWriter();
		String myJson = defaultConfigReaderWriter.toJson(testConfiguration);
	
		assertTrue( myJson.indexOf("H2Port") < 0); //negative test -- H2Port, with capital H does not exist
		
		assertTrue( myJson.indexOf("\"h2Port\" : 65000,") >= 0);
		
		//System.out.println(myJson);

		//Sure seems likely that we'll have more tests to confirm/tweak formatting of json values.
	}
	@Test 
	void canDeserializeConfiguartion() throws Snail4jException {
		String config = "{  \"sutAppZipFileName\" : \"sutApp.zip\",  \"sutAppPort\" : 8080,  \"loadGenerationThreads\" : 3,  \"loadGenerationRampupTimeInSeconds\" : 3,  \"loadGenerationDurationInSeconds\" : 3600,  \"sutAppHostname\" : \"localhost\",  \"mavenExePath\" : \"C:\\\\Users\\\\eoste\\\\.snail4j\\\\apache-maven-3.6.3\\\\bin\\\\mvn.cmd\",  \"mavenOnline\" : false,  \"snail4jMavenRepo\" : true,  \"wiremockHostname\" : \"localhost\",  \"wiremockPort\" : 8081,  \"h2Hostname\" : \"localhost\",  \"h2Port\" : 65000,  \"wiremockStopCmd\" : \"#{mavenExePath} -Dmaven.repo.local=#{mavenRepositoryHome} -Dsnail4j.wiremock.port=#{wiremockPort} clean compile wiremock:stop\",  \"javaHome\" : \"file:///C:/java/jdk-9.0.4/\",  \"mavenHome\" : \"file:///C:/Users/eoste/.snail4j/apache-maven-3.6.3/\",  \"userHomeDir\" : \"file:///C:/Users/eoste/\",  \"maxExceptionCountPerEvent\" : 100,  \"mavenZipFileNameWithoutExtension\" : \"apache-maven-3.6.3\",  \"mavenRepositoryHome\" : \"file:///C:/Users/eoste/.snail4j/repository/\",  \"processManagerZipFileName\" : \"processManager.zip\",  \"systemUnderTestStdoutLogFileName\" : \"systemUnderTest.log\",  \"loadGeneratorStdoutLogFileName\" : \"loadGenerator.log\",  \"h2DataFileName\" : \"perfSandboxDb.mv.db\",  \"h2DataFileHome\" : \"file:///C:/Users/eoste/.snail4j/data/\",  \"jmeterFilesZipFileName\" : \"jmeterFiles.zip\",  \"loadGeneratorLaunchCmd\" : \"#{mavenExePath} -Dmaven.repo.local=#{mavenRepositoryHome} -f #{jmeterFilesHome}/pom-load.xml -Pno-gui clean verify -Dsnail4j.jmeter.port=#{jmeterNonGuiPort} -Djmeter.test=#{jmeterFilesHome}/load.jmx -DmyHost=#{sutAppHostname} -DmyPort=#{sutAppPort} -DmyDurationInSeconds=#{loadGenerationDurationInSeconds} -DmyRampupInSeconds=#{loadGenerationRampupTimeInSeconds} -DmyThreads=#{loadGenerationThreads}\",  \"processManagerLaunchCmd\" : \"#{mavenExePath} -Dsnail4j.maven.repo.passthru=-Dmaven.repo.local=#{mavenRepositoryHome} -Dmaven.repo.local=#{mavenRepositoryHome} -Dsnail4j.wiremock.port=#{wiremockPort} -Dsnail4j.h2.port=#{h2Port} -Dsnail4j.sut.port=#{sutAppPort} verify\",  \"processManagerHome\" : \"file:///C:/Users/eoste/.snail4j/processManager/\",  \"logDir\" : \"file:///C:/Users/eoste/.snail4j/log/\",  \"sutKillFile\" : \"file:///C:/Users/eoste/.snail4j/deleteMeToStopSnail4jSut.txt\",  \"jmeterNonGuiPort\" : 4455,  \"glowrootZipFileName\" : \"glowroot-0.13.5-dist.zip\",  \"wiremockStopStdoutLogFileName\" : \"wiremockStop.log\",  \"windowsKillerProcess\" : \"#{mavenExePath} antrun:run initialize\",  \"sutAppHome\" : \"file:///C:/Users/eoste/.snail4j/sutApp/\",  \"snail4jHome\" : \"file:///C:/Users/eoste/.snail4j/\",  \"jmeterFilesHome\" : \"file:///C:/Users/eoste/.snail4j/jmeterFiles/\",  \"glowrootHome\" : \"file:///C:/Users/eoste/.snail4j/glowroot/\",  \"wiremockFilesZipFileName\" : \"wiremockFiles.zip\",  \"wiremockFilesHome\" : \"file:///C:/Users/eoste/.snail4j/wiremock/\"}";
		
		DefaultConfigReaderWriter defaultConfigReaderWriter = new DefaultConfigReaderWriter();
		Configuration cfg = defaultConfigReaderWriter.toObject(config,DefaultConfiguration.class);
		
		assertEquals( 65000, cfg.getH2Port());
		assertEquals("C:\\Users\\eoste\\.snail4j\\wiremock", cfg.getWiremockFilesHome().toString() );
	}

}
