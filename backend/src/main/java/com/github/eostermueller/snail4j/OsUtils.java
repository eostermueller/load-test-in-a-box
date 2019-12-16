package com.github.eostermueller.snail4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class OsUtils {
	public static class OsResult {
		int exitCode = UNINITIALIZED;
		String stdout;
	}
	
	public static final int UNINITIALIZED = -9999;
	/**
	 * @stolenFrom: https://www.mkyong.com/java/how-to-execute-shell-command-from-java/
	 * @return
	 */
	public static OsResult executeWindowsCmd(String windowsCommand) {
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
	            }

	            osResult.exitCode = process.waitFor();
	            osResult.stdout = sb.toString();

	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	        return osResult;
	}
	/**
	 * @stolenFrom: https://stackoverflow.com/a/1410779/2377579
	 * @param cmd
	 * @throws Snail4jException 
	 */
	public static OsResult executeBashCmd(String cmd, File dir) throws Snail4jException {
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

}
