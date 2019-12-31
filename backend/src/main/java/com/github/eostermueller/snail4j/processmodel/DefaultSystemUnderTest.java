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
import com.github.eostermueller.snail4j.OS;
import com.github.eostermueller.snail4j.OsUtils;
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
import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

import ch.qos.logback.classic.Logger;

public class DefaultSystemUnderTest implements SystemUnderTest {
	private final Logger LOGGER = (Logger) LoggerFactory.getLogger(this.getClass());
	private String THREAD_NAME = "snail4j-sut";
	private Configuration cfg;
	private SimpleStdoutProcessRunner runner = null;
	private SimpleStdoutProcessRunner wiremockStopper = null;

	public DefaultSystemUnderTest(Configuration val) throws Snail4jException {
		this.cfg = val;
		
		ProcessKey key = ProcessKey.create(this.getClass().getCanonicalName(), Level.CHILD, "sut");
		runner = new SimpleStdoutProcessRunnerJdk8(key);
		
		runner.setProcessBuilder( getProcessBuilder() );
		runner.setWorkingDirectory(val.getProcessManagerHome().toFile());
		
		
		ProcessKey wmsKey = ProcessKey.create(this.getClass().getCanonicalName(), Level.CHILD, "wiremockStopper");
		wiremockStopper = new SimpleStdoutProcessRunnerJdk8(wmsKey);
		
//		wiremockStopper.setProcessBuilder( getWiremockStopperProcessBuilder() );
//		wiremockStopper.setWorkingDirectory(val.getProcessManagerHome().toFile());
		
	}
	private List<Long> getWiremockPids() throws Snail4jException {
		List<Long> rc = new ArrayList<Long>();
		try {
			for(VirtualMachineDescriptor jvm :  VirtualMachine.list() ){
				VirtualMachine vm = VirtualMachine.attach(jvm.id() );
				String wiremockPort = vm.getSystemProperties().getProperty("snail4j.wiremock.port");
			    LOGGER.debug("jvm id (aka pid): [" + jvm.id() + "] displayName: [" + jvm.displayName() + "] Value of -Dsnail4j.wiremock.port: " + wiremockPort);
				if (wiremockPort!=null && !"".equals(wiremockPort) ) {
					rc.add( Long.valueOf(wiremockPort) );
				}
			}
		} catch (Exception e) {
			throw new Snail4jException(e);
		}
		return rc;
	}
//	private ProcessBuilder getWiremockStopperProcessBuilder() throws Snail4jException {
//
//		CommandLine cmdLine = DefaultFactory.getFactory().createNewCommandLine(
//				this.getConfiguration().getWiremockStopCmd()
//				);
//		
//		ProcessBuilder pb = new ProcessBuilder( cmdLine.getProcessedCommandLine() );
//				
//		pb.directory( this.getConfiguration().getWiremockFilesHome().toFile());
//		File stdoutLogFile = new File( 
//				this.getConfiguration().getLogDir().toFile(),
//				this.getConfiguration().getWiremockStopStdoutLogFileName() 
//			);
//		pb.redirectOutput(stdoutLogFile);
//		
//		return pb;
//	}
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
		
		switch(OS.getOs().getOsFamily()) {
		case Windows:  
			/**
			 * The startup and shutdown of the SUT in the processManager was initially developed/tested on mac.
			 * It worked great....all the sutApp processes (tjp2, h2, wiremock) started and stopped cleanly.
			 * 
			 * But on windows, the following two did not stop cleanly,
			 * so here is code to do that.
			 */
//			wiremockStopper.start();
//			String d = getMessages().getWiremockStopMessage( wiremockStopper.toHumanReadableString() );
			
//			DefaultFactory.getFactory().getEventHistory().getEvents().add( Event.create(d) );
			
			String appName = "sutApp";
			String url = this.getSutAppShutdownUrl();
			String defaultErrMsg = this.getMessages().getDefaultShutdownMessage(url, appName);
			this.shutdownAppViaHttp( url, appName, defaultErrMsg);
			
			this.stopWiremock();
			break;
		default:
		}
		
		String d = getMessages().getSutStopMessage( runner.toHumanReadableString(), killFile.getAbsolutePath(), ynKillFileExistsBefore, ynKillFileExistsAfter);
		DefaultFactory.getFactory().getEventHistory().getEvents().add( Event.create(d) );
	}
	private void stopWiremock() throws Snail4jException {
		
		for(Long wiremockPid : getWiremockPids() ) {
			LOGGER.debug("Killing this wiremock-related pid: " + wiremockPid.longValue());
			OsUtils.killPid( wiremockPid.longValue() );
		}

		// TODO Auto-generated method stub
		
	}
	private Messages getMessages() throws CannotFindSnail4jFactoryClass {
		Messages m = DefaultFactory.getFactory().createMessages();
		return m;
	}

//	/**
//	 * As documented here:  http://wiremock.org/docs/running-standalone/
//	 * @return
//	 */
//	private String getWiremockShutdownUrl() {
//		
//		String url = 
//				"http://" 
//						+ this.getConfiguration().getSutAppHostname() 
//						+ ":" + this.getConfiguration().getSutAppPort() 
//						+ "__admin/shutdown";
//		LOGGER.debug("sutApp shutdown URL: [" + url + "]");
//		return url;
//	}
	private String getSutAppShutdownUrl() {
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
	private void shutdownAppViaHttp(String url, String appName, String defaultMessage) throws Snail4jException {
		
		HttpPost post = new HttpPost( url );

//	        // add request parameter, form parameters
//	        List<NameValuePair> urlParameters = new ArrayList<>();
//	        urlParameters.add(new BasicNameValuePair("username", "abc"));
//	        urlParameters.add(new BasicNameValuePair("password", "123"));
//	        urlParameters.add(new BasicNameValuePair("custom", "secret"));

//	        post.setEntity(new UrlEncodedFormEntity(urlParameters));

		String httpResponse = this.getMessages().getDefaultShutdownMessage(url, appName);
	       
	        try (CloseableHttpClient httpClient = HttpClients.createDefault();
	             CloseableHttpResponse response = httpClient.execute(post)) {

	            httpResponse = EntityUtils.toString(response.getEntity() );
	        } catch (ParseException | IOException e) {
				throw new Snail4jException(e, httpResponse);
			}		
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
