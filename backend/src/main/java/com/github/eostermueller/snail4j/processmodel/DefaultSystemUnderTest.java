package com.github.eostermueller.snail4j.processmodel;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
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
import com.github.eostermueller.snail4j.util.OS;
import com.github.eostermueller.snail4j.util.OsUtils;
import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

import ch.qos.logback.classic.Logger;

public class DefaultSystemUnderTest implements SystemUnderTest {
	private final Logger LOGGER = (Logger) LoggerFactory.getLogger(this.getClass());
	//private String THREAD_NAME = "sut_load-test-in-a-box";
	private Configuration cfg;
	private SimpleStdoutProcessRunner runner = null;
	private static String OS = System.getProperty("os.name").toLowerCase();
	private SimpleStdoutProcessRunner runnerStop = null;

	public DefaultSystemUnderTest(Configuration val) throws Snail4jException {
		this.cfg = val;

		ProcessKey key = ProcessKey.create(this.getClass().getCanonicalName(), Level.CHILD, "sut");
		runner = new SimpleStdoutProcessRunnerJdk8(key);

		runner.setProcessBuilder( getProcessBuilder() );
		runner.setWorkingDirectory(val.getProcessManagerHome().toFile());

		//TODO: refactor to be more elegant than create another Process (runner), maybe not leaving it here, but call directly to DefaultProcessModelBuilder:
		runnerStop = new SimpleStdoutProcessRunnerJdk8(key);
		runnerStop.setProcessBuilder( getProcessBuilderStop() );
		runnerStop.setWorkingDirectory(val.getProcessManagerHome().toFile());

	}


	private Configuration getConfiguration() {
		return this.cfg;
	}


	//TODO: refactor this method to be more elegant.
	ProcessBuilder getProcessBuilderStop() throws ConfigVariableNotFoundException, Snail4jException {

		CommandLine cmdLine = DefaultFactory.getFactory().createNewCommandLine(
				this.getConfiguration().getWindowsKillerProcess()  //new, comand maven
				);

		ProcessBuilder pb = new ProcessBuilder( cmdLine.getProcessedCommandLine() ); // maven parsing

		pb.directory( this.getConfiguration().getProcessManagerHome().toFile());
		File stdoutLogFile = new File(
				this.getConfiguration().getLogDir().toFile(),
//				this.getConfiguration().getSystemUnderTestStdoutLogFileName()
				"sutKiller.log"
			);
		pb.redirectOutput(stdoutLogFile);

		return pb;
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
		
		String wiremockShutdownUrl = getWiremockShutdownUrl();
		this.shutdownAppViaHttpPost(
				wiremockShutdownUrl, 
				"wiremock", 
				getMessages().getDefaultShutdownMessage(wiremockShutdownUrl, "wiremock"));

		String sutShutdownUrl = getSutShutdownUrl();
		this.shutdownAppViaHttpPost(
				sutShutdownUrl, 
				"sut", 
				getMessages().getDefaultShutdownMessage(sutShutdownUrl, "sut"));
		
		File killFile = this.getConfiguration().getSutKillFile().toFile();
		ynKillFileExistsBefore = killFile.exists();
		killFile.delete();
		ynKillFileExistsAfter = killFile.exists();
		Messages m = DefaultFactory.getFactory().createMessages();

		String d = m.getSutStopMessage( runner.toHumanReadableString(), killFile.getAbsolutePath(), ynKillFileExistsBefore, ynKillFileExistsAfter);
		DefaultFactory.getFactory().getEventHistory().getEvents().add( Event.create(d) );



		//TODO: if it's windows then maven-antrun-plugin must be executed
		if(isWindows()) {

			LOGGER.debug("It's Windows!");

			runnerStop.start();


		} else if (isMac()) {
			LOGGER.debug("This is Mac!");

			runnerStop.start();

		}

	}
	private Messages getMessages() throws CannotFindSnail4jFactoryClass {
		Messages m = DefaultFactory.getFactory().createMessages();
		return m;
	}
	/**
	 * As documented here:  http://wiremock.org/docs/running-standalone/
	 * @return
	 */
	private String getWiremockShutdownUrl() {
		
		String url = 
				"http://" 
						+ this.getConfiguration().getSutAppHostname() 
						+ ":" + this.getConfiguration().getWiremockPort() 
						+ "/__admin/shutdown";
		LOGGER.debug("wiremock shutdown URL: [" + url + "]");
		return url;
	}
	/**
	 * As documented here:  http://wiremock.org/docs/running-standalone/
	 * @return
	 */
	private String getSutShutdownUrl() {
		
		String url = 
				"http://" 
						+ this.getConfiguration().getSutAppHostname() 
						+ ":" + this.getConfiguration().getSutAppPort() 
						+ "/actuator/shutdown";
		LOGGER.debug("sutApp shutdown URL: [" + url + "]");
		return url;
	}
	
	/**
	 * @throws Snail4jException 
	 * @stolenFrom: https://www.mkyong.com/java/how-to-send-http-request-getpost-in-java/
	 */
	private void shutdownAppViaHttpPost(String url, String appName, String defaultMessage) throws Snail4jException {
		
		DefaultFactory.getFactory().getEventHistory().getEvents().add( Event.create(defaultMessage) );

		HttpPost post = new HttpPost( url );

		String httpResponse = this.getMessages().getDefaultShutdownMessage(url, appName);
	       
	        try (CloseableHttpClient httpClient = HttpClients.createDefault();
	             CloseableHttpResponse response = httpClient.execute(post)) {

	            httpResponse = EntityUtils.toString(response.getEntity() );
	        } catch (ParseException | IOException e) {
				throw new Snail4jException(e, httpResponse);
			}		
	}


	public static boolean isWindows() {

		return (OS.indexOf("win") >= 0);

	}

	public static boolean isMac() {

		return (OS.indexOf("mac") >= 0);

	}
}
