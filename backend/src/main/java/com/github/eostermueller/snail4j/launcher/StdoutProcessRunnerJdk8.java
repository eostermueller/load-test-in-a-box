package com.github.eostermueller.snail4j.launcher;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

import com.github.eostermueller.snail4j.Snail4jException;

public class StdoutProcessRunnerJdk8 extends StdoutProcessRunner {

	public StdoutProcessRunnerJdk8(ProcessKey processKey) throws Snail4jException {
		super(processKey);
	}

	@Override
	/**
	 * @stolenFrom:  https://kodejava.org/how-do-i-get-process-id-of-a-java-application/ 
	 * 
	 */
	public long getPid() {
		 RuntimeMXBean bean = ManagementFactory.getRuntimeMXBean();

	        // Get name representing the running Java virtual machine.
	        // It returns something like 6460@AURORA. Where the value
	        // before the @ symbol is the PID.
	        String jvmName = bean.getName();

	        // Extract the PID by splitting the string returned by the
	        // bean.getName() method.
	        long pid = Long.valueOf(jvmName.split("@")[0]);
	        return pid;
	}

}
