package com.github.eostermueller.havoc.launcher;

import com.github.eostermueller.havoc.PerfGoatException;

public interface ConfigReaderWriter {

	void write() throws PerfGoatException;

	Configuration read() throws PerfGoatException;

}
