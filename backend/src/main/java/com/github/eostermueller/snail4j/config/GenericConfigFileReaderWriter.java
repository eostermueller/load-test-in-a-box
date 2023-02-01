package com.github.eostermueller.snail4j.config;

import java.nio.file.Path;

import com.github.eostermueller.snail4j.Snail4jException;

/**
 * Not meant for high count of writes to the same file.
 * Not meant for writes of large number of bytes
 * 
 * @author eoste
 *
 */
public interface GenericConfigFileReaderWriter {
	void write(String data, Path targetFile) throws Snail4jException;
	String read(Path targetFile) throws Snail4jException;
}


