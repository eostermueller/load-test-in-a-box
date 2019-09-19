package com.github.eostermueller.tjp.launcher.agent.littleMock;

import static org.junit.Assert.*;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.eostermueller.tjp.launcher.Configuration;
import com.github.eostermueller.tjp.launcher.Suite;
import com.github.eostermueller.tjp.launcher.agent.TestConfiguration;
import com.github.eostermueller.tjp.launcher.agent.TestTheTestConfiguration;
import com.github.eostermueller.tjp.launcher.agent.runner.MavenSpringBootRunner;


public class LittleMockTest {

	@Before
	public void canAddLoadTestStructure() {
		Path tjpHome = Paths.get(TestTheTestConfiguration.unix_ABS_PATH_TO_TJP);
		Path javaHome = Paths.get(TestTheTestConfiguration.unix_JAVA_HOME);				
		TestConfiguration testConfiguration = new TestConfiguration(tjpHome, javaHome);
		
//		Configuration c = new TestConfiguration(tjpHome, javaHome);
//		Suite littleMockProcessSuite = 
//				new LittleMockProcessSuite(testConfiguration.getLittleMockHome()
//						);
//		littleMockProcessSuite.addRunnerInOrder( new MavenSpringBootRunner("clean package") );
		
//		littleMockLoadTest.addProcess( new SpringBootLaunch() );
//		littleMockLoadTest.addProcess( new SpringBootConfigure() );
//		littleMockLoadTest.addProcess( new LoadGenerator() );

	}

}
