package com.github.eostermueller.snail4j.processmodel;


import java.io.File;
import java.io.IOException;

import org.slf4j.LoggerFactory;

import com.github.eostermueller.snail4j.DefaultFactory;
import com.github.eostermueller.snail4j.Snail4jException;
import com.github.eostermueller.snail4j.launcher.CannotFindSnail4jFactoryClass;
import com.github.eostermueller.snail4j.launcher.CommandLine;
import com.github.eostermueller.snail4j.launcher.ConfigVariableNotFoundException;
import com.github.eostermueller.snail4j.launcher.Configuration;
import com.github.eostermueller.snail4j.launcher.Event;
import com.github.eostermueller.snail4j.launcher.GroupNameThreadFactory;
import com.github.eostermueller.snail4j.launcher.Level;
import com.github.eostermueller.snail4j.launcher.Messages;
import com.github.eostermueller.snail4j.launcher.ProcessKey;
import com.github.eostermueller.snail4j.launcher.SimpleStdoutProcessRunner;
import com.github.eostermueller.snail4j.launcher.SimpleStdoutProcessRunnerJdk8;
import com.github.eostermueller.snail4j.launcher.StdoutProcessRunner;
import com.github.eostermueller.snail4j.launcher.StdoutProcessRunnerJdk8;

import ch.qos.logback.classic.Logger;

public class DefaultSystemUnderTest implements SystemUnderTest {
	private final Logger LOGGER = (Logger) LoggerFactory.getLogger(this.getClass());
	private String THREAD_NAME = "snail4j-sut";
	private Configuration cfg;
	private SimpleStdoutProcessRunner runner = null;

	public DefaultSystemUnderTest(Configuration val) throws Snail4jException {
		this.cfg = val;
		
		ProcessKey key = ProcessKey.create(this.getClass().getCanonicalName(), Level.CHILD, "sut");
		runner = new SimpleStdoutProcessRunnerJdk8(key);
		
		runner.setProcessBuilder( getProcessBuilder() );
		runner.setWorkingDirectory(val.getProcessManagerHome().toFile());
		
	}
	private Configuration getConfiguration() {
		return this.cfg;
	}
	ProcessBuilder getProcessBuilder() throws ConfigVariableNotFoundException, Snail4jException {

		CommandLine cmdLine = DefaultFactory.getFactory().createNewCommandLine(
				this.getConfiguration().getProcessManagerLaunchCmd()
				);
		
		ProcessBuilder pb = new ProcessBuilder( cmdLine.getProcessedCommandLine() );
				
		pb.directory( this.getConfiguration().getProcessManagerHome().toFile());
		File stdoutLogFile = new File( 
				this.getConfiguration().getLogDir().toFile(),
				this.getConfiguration().getSystemUnderTestStdoutLogFileName() 
			);
		pb.redirectOutput(stdoutLogFile);
		
		return pb;
	}

	@Override
	public void start() throws ConfigVariableNotFoundException, IOException, Snail4jException {
		
		runner.start();
		Messages m = DefaultFactory.getFactory().createMessages();
		String d = m.getSutStartMessage( runner.toHumanReadableString() );
		
		DefaultFactory.getFactory().getEventHistory().getEvents().add( Event.create(d) );
		
	}
	@Override
	public void stop() throws Snail4jException {
		boolean ynKillFileExistsBefore = false;
		boolean ynKillFileExistsAfter = true;
		
		File killFile = this.getConfiguration().getSutKillFile().toFile();
		ynKillFileExistsBefore = killFile.exists();
		killFile.delete();
		ynKillFileExistsAfter = killFile.exists();
		
		Messages m = DefaultFactory.getFactory().createMessages();
		
		String d = m.getSutStopMessage( runner.toHumanReadableString(), killFile.getAbsolutePath(), ynKillFileExistsBefore, ynKillFileExistsAfter);
		DefaultFactory.getFactory().getEventHistory().getEvents().add( Event.create(d) );
	}
}

//Runnable r = new Runnable() {
//
//	@Override
//	public void run() {
//	    Process process;
//		try {
//			
//			process = DefaultSystemUnderTest.this.getProcessBuilder().start();
//			if (status !=0) {
//				throw new Exception("The following source code failed to compile [" + this.getSourceFileText() + "]");
//			} else {
//				//System.out.println(".class was created after compile?[" + getAbsoluteClassFileName().exists()  + "] javac output: [" + getAbsoluteClassFileName() + "]");
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		
//	}
//};
//Thread t = new GroupNameThreadFactory(THREAD_NAME).newThread(r);
//t.start();
