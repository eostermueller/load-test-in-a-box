package com.github.eostermueller.snail4j.processmodel;

import java.io.IOException;

import com.github.eostermueller.snail4j.Snail4jException;
import com.github.eostermueller.snail4j.launcher.ConfigVariableNotFoundException;

public interface LoadGenerator {
	void start() throws ConfigVariableNotFoundException, IOException, Snail4jException;
	void sendCommand(String hostOrIp, int port, String command) throws Snail4jException;
	void shutdown() throws Snail4jException;
	void stopTestNow() throws Snail4jException;
	void threadDump() throws Snail4jException;
	void heapDump() throws Snail4jException;

}
