package com.github.eostermueller.snail4j.processmodel;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.slf4j.LoggerFactory;

import com.github.eostermueller.snail4j.DefaultFactory;
import com.github.eostermueller.snail4j.Snail4jException;
import com.github.eostermueller.snail4j.launcher.CannotFindSnail4jFactoryClass;
import com.github.eostermueller.snail4j.launcher.CommandLine;
import com.github.eostermueller.snail4j.launcher.ConfigVariableNotFoundException;
import com.github.eostermueller.snail4j.launcher.Configuration;
import com.github.eostermueller.snail4j.launcher.Event;
import com.github.eostermueller.snail4j.launcher.Level;
import com.github.eostermueller.snail4j.launcher.Messages;
import com.github.eostermueller.snail4j.launcher.ProcessKey;
import com.github.eostermueller.snail4j.launcher.SimpleStdoutProcessRunner;
import com.github.eostermueller.snail4j.launcher.SimpleStdoutProcessRunnerJdk8;

import ch.qos.logback.classic.Logger;

public class DefaultLoadGenerator implements LoadGenerator {
	private final Logger LOGGER = (Logger) LoggerFactory.getLogger(this.getClass());
	/**
	 * The following command values came from this and other batch files in the same JMeter distribution folder:
	 * https://github.com/apache/jmeter/blob/598d0428e217a81ee22dde5f4bd59c5271ee3f7f/bin/stoptest.sh
	 */
	private static final String SHUTDOWN = "Shutdown";
	private static final String STOP_TEST_NOW = "StopTestNow";
	private static final String THREAD_DUMP = "ThreadDump";
	private static final String HEAP_DUMP = "HeapDump";
	
	
	
	private Configuration cfg;

	public DefaultLoadGenerator(Configuration val) throws Snail4jException {
		this.cfg = val;
	}
	
	private SimpleStdoutProcessRunner getRunner() throws Snail4jException {
		SimpleStdoutProcessRunner runner = null;

		ProcessKey key = ProcessKey.create(this.getClass().getCanonicalName(), Level.CHILD, "loadGenerator");
		runner = new SimpleStdoutProcessRunnerJdk8(key);
		
		runner.setProcessBuilder( getProcessBuilder() );
		runner.setWorkingDirectory(cfg.getJMeterFilesHome().toFile() );
		
		return runner;
	}
	private Configuration getConfiguration() {
		return this.cfg;
	}
	ProcessBuilder getProcessBuilder() throws ConfigVariableNotFoundException, Snail4jException {

		CommandLine cmdLine = DefaultFactory.getFactory().createNewCommandLine(
				this.getConfiguration().getLoadGeneratorLaunchCmd() 
				);
		
		ProcessBuilder pb = new ProcessBuilder( cmdLine.getProcessedCommandLine() );
				
		pb.directory( this.getConfiguration().getJMeterFilesHome().toFile() );
		File stdoutLogFile = new File( 
				this.getConfiguration().getLogDir().toFile(),
				this.getConfiguration().getLoadGeneratorStdoutLogFileName()  
			);
		pb.redirectOutput(stdoutLogFile);
		
		return pb;
	}

	@Override
	public void start() throws ConfigVariableNotFoundException, IOException, Snail4jException {
		SimpleStdoutProcessRunner runner = getRunner();
		runner.start();
		Messages m = DefaultFactory.getFactory().createMessages();
		String d = m.getLoadGeneratorStartMessage( runner.toHumanReadableString() );
		
		DefaultFactory.getFactory().getEventHistory().getEvents().add( Event.create(d) );
		
	}
	@Override
	public void heapDump() throws Snail4jException {
		this.sendCommand("localhost", (int) this.getConfiguration().getJMeterNonGuiPort(), HEAP_DUMP);
	}
	@Override
	public void threadDump() throws Snail4jException {
		this.sendCommand("localhost", (int) this.getConfiguration().getJMeterNonGuiPort(), THREAD_DUMP);
	}
	@Override
	public void stopTestNow() throws Snail4jException {
		
		this.sendCommand("localhost", (int) this.getConfiguration().getJMeterNonGuiPort(), STOP_TEST_NOW);
	}
	@Override
	public void shutdown() throws Snail4jException {
		this.sendCommand("localhost", (int) this.getConfiguration().getJMeterNonGuiPort(), SHUTDOWN);
	}
	
	/**
	 * @stolenFrom
	 * https://github.com/apache/jmeter/blob/41bad20e9d495240e140da6cbdbf5d9e2c8d3123/src/launcher/src/main/java/org/apache/jmeter/util/ShutdownClient.java
	 */
	@Override
	public void sendCommand(String hostOrIp, int port, String command) throws Snail4jException {
		
        try (DatagramSocket socket = new DatagramSocket()) {
            byte[] buf = command.getBytes("ASCII");
            InetAddress address = InetAddress.getByName(hostOrIp);
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address,port);
	        socket.send(packet);		
			Messages m = DefaultFactory.getFactory().getMessages();
			String d = m.getLoadGeneratorCommandMessage( getRunner().toHumanReadableString(), command, hostOrIp, port); 
			DefaultFactory.getFactory().getEventHistory().getEvents().add( Event.create(d) );
		} catch (Exception e) {
			throw new Snail4jException(e);
		}
	}
}
