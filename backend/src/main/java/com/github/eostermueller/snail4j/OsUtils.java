package com.github.eostermueller.snail4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.health.Health;

import com.github.eostermueller.snail4j.OsUtils.OsResult;
import com.github.eostermueller.snail4j.launcher.Configuration;
import com.github.eostermueller.snail4j.launcher.DefaultConfiguration;
import com.github.eostermueller.snail4j.launcher.Messages;

public class OsUtils {
	static final Logger LOGGER = LoggerFactory.getLogger(OsUtils.class);
	public static class OsResult {
		int exitCode = UNINITIALIZED;
		public String stdout;
	}

	
	public static final int UNINITIALIZED = -9999;
	
	/**
	 * Not ready to put the work into creating a test for this yet, so here is a way to lauch a udp port:
	 * C:\Users\eoste\Documents\src\jdist\jmeter\apache-jmeter-5.2.1\bin>jmeter -Djmeterengine.nongui.port=4455 -n -t C:\Users\eoste\Documents\src\jssource\snail4j\jmeterFiles\load.jmx
	 * @param udpPort
	 * @return
	 */
	public static boolean isUdpPortActive(int udpPort) {
		boolean active = false;

		DatagramSocket tempDatagramSocket = null;
        try {
			tempDatagramSocket = new DatagramSocket(udpPort); //if new open fails, the someone's already listen/active.
		} catch (SocketException e) {
			active = true;
		}
        
        /**
         * More then just being tidy.
         * If this method get calls multiple times testing for the same port, the temp socket opened here will be discovered/tested in subsequent invocations!
         * Close it so testDatagramSocket doesn't interfere with knowing whether other systems are listening on this same port.
         */
        try {
        	if (tempDatagramSocket!=null)
        		tempDatagramSocket.close();
        } catch(Exception e) {
        	//don't care whether close of temp works.
        }
		
		return active;
	}
	public static boolean isTcpPortActive(String hostname, int tcpPort, int timeoutMs) {
		boolean active = false;
	     Socket socket = null;
	     String target = hostname + ":" + tcpPort;
        try {
        	socket = new Socket();
            socket.connect(new InetSocketAddress(hostname, tcpPort), timeoutMs);
            active = true;
        } catch (final Exception e) {
            active = false;
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (final IOException e) {
                    LOGGER.error("Unable to close consumer socket." + target, e);
                }
            }
        }		
        LOGGER.debug("Is tcp [" + target + "] active? " + active);
		return active;
	}
	/**
	 * @stolenFrom https://github.com/apache/jmeter/blob/0bdbe5bd17f6e2385a5fe58216e259dd85a04054/src/core/src/main/java/org/apache/jmeter/JMeter.java#L1490
	 * @param udpPort
	 * @return
	 * @throws Snail4jException 
	 */
	public static int getAvailableUdpPort(int startUdpPort, int udpPortMax) {
		int rc = -1;
        int i = startUdpPort;
        while (i<= udpPortMax) {
        	if (isUdpPortActive(i)) {
        		i++;
        	} else {
    			rc = i;
    			break;
    		}
        }
        	
        return rc;
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
	        //tons of detail here!!!! LOGGER.debug(osResult.stdout);
	        return osResult;
	}
	
	
	/**
	 * @throws Snail4jException 
	 */
	public static OsResult executeProcess(String osCmd) throws Snail4jException {
		
		OsResult osResult = null;
		
		switch(OS.getOs().getOsFamily()) {
		case Windows:
			osResult = executeProcess_mswin(osCmd);
			break;
		default:
			osResult = executeProcess_bash(osCmd);
			break;
		}
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
