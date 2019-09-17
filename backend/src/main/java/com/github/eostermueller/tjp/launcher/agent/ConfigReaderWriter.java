package com.github.eostermueller.tjp.launcher.agent;

import com.github.eostermueller.havoc.PerfGoatException;

public interface ConfigReaderWriter {

	void write() throws PerfGoatException;

	Configuration read() throws PerfGoatException;

}
