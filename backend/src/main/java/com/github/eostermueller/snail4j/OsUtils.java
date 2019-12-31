package com.github.eostermueller.snail4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.eostermueller.snail4j.OsUtils.OsResult;

public class OsUtils {
	static final Logger LOGGER = LoggerFactory.getLogger(OsUtils.class);
	public static class OsResult {
		int exitCode = UNINITIALIZED;
		String stdout;
	}

	
	public static final int UNINITIALIZED = -9999;
	/**
	 * commands for displaying UDP listening ports.
	 */
	static String UDP_PORT_TEST_MS_WIN = "netstat -ano -p UDP";
	static String UDP_PORT_TEST_MAC = "sudo lsof -iUDP -P -n";
	static String UDP_PORT_TEST_LINUX = "netstat -au";
	
	/**
	 * Not ready to put the work into creating a test for this yet, so here is a way to lauch a udp port:
	 * C:\Users\eoste\Documents\src\jdist\jmeter\apache-jmeter-5.2.1\bin>jmeter -Djmeterengine.nongui.port=4455 -n -t C:\Users\eoste\Documents\src\jssource\snail4j\jmeterFiles\load.jmx
	 * @param udpPort
	 * @return
	 */
	public static boolean isUdpPortActive_mswin(int udpPort) {
		boolean active = false;
		OsResult osResult = OsUtils.executeProcess_mswin(UDP_PORT_TEST_MS_WIN);
		String portCriteria = ":" + String.valueOf(udpPort).trim();
		if (osResult.stdout.toString().indexOf(portCriteria) > -1) {
			active = true;
		}
		
		return active;
	}
	public static boolean isUdpPortActive(int udpPort) {
		boolean active = false;
		
		switch(OS.getOs().getOsFamily()) {
		case Windows:
			active = isUdpPortActive_mswin(udpPort);
			break;
		default:
		}
		return active;
	}
	
	/**
	 * @stolenFrom: https://www.mkyong.com/java/how-to-execute-shell-command-from-java/
	 * @return
	 */
	public static OsResult executeProcess_mswin(String windowsCommand) {
	     ProcessBuilder processBuilder = new ProcessBuilder();
	     OsResult osResult = new OsResult();
	        // Windows
	        processBuilder.command("cmd.exe", "/c", windowsCommand);
	        StringBuilder sb = new StringBuilder();

	        try {

	            Process process = processBuilder.start();

	            BufferedReader reader =
	                    new BufferedReader(new InputStreamReader(process.getInputStream()));

	            String line;

	            while ((line = reader.readLine()) != null) {
	                sb.append(line);
	                sb.append("\n");
	            }

	            osResult.exitCode = process.waitFor();
	            osResult.stdout = sb.toString();

	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	        LOGGER.debug("Just executed:     " + windowsCommand);
	        LOGGER.debug("OsResult.exitCode: "+osResult.exitCode);
	        LOGGER.debug(osResult.stdout);
	        return osResult;
	}
	public static OsResult executeProcess_bash(String cmd) throws Snail4jException {
		return executeProcess_bash(cmd,null/* no need to switch to a current directory*/);
	}
	/**
	 * @stolenFrom: https://stackoverflow.com/a/1410779/2377579
	 * @param cmd
	 * @throws Snail4jException 
	 */
	public static OsResult executeProcess_bash(String cmd, File dir) throws Snail4jException {
        OsResult osResult = new OsResult();
		//LOGGER.debug("About to execute bash command [" + cmd + "] in dir [" + dir.toString() + "]");
		
		try {
	        List<String> commands = new ArrayList<String>();
	        commands.add("bash");
	        commands.add("-c");
	        commands.add(cmd);

	        ProcessBuilder pb = new ProcessBuilder(commands);
	        
	        if (dir !=null)
	        	pb.directory(dir);
	        
	        pb.redirectErrorStream(true);
	        Process process = pb.start();

	        StringBuilder out = new StringBuilder();
	        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
	        String line = null, previous = null;
	        while ((line = br.readLine()) != null)
	            if (!line.equals(previous)) {
	                previous = line;
	                out.append(line).append('\n');
	            }

            osResult.exitCode = process.waitFor();
            osResult.stdout = out.toString();
	        
			//LOGGER.debug("rc = [" + rc + "] for bash command [" + cmd + "] in dir [" + dir.toString() + "]");
		} catch (Exception e) {
			throw new Snail4jException(e);
		}

		return osResult;
	}
	public static OsResult killPid(long longValue) throws Snail4jException {
		OsResult osResult = null;		
		switch(OS.getOs().getOsFamily()) {
		case Windows:
			osResult = executeProcess_mswin("taskkill /F /PID " + longValue);
			break;
		default:
			osResult = executeProcess_bash("kill -9 " + longValue);
			break;
		}
		return osResult;
	}

}
