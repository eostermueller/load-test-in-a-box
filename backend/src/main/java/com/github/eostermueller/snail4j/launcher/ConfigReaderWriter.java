package com.github.eostermueller.snail4j.launcher;

import com.github.eostermueller.snail4j.Snail4jException;

public interface ConfigReaderWriter {

	void write() throws Snail4jException;

	Configuration read() throws Snail4jException;

}
