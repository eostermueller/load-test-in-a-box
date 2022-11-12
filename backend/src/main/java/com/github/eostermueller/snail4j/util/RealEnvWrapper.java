package com.github.eostermueller.snail4j.util;

public class RealEnvWrapper implements OsEnvWrapper {

	@Override
	public String get(String variable) {
		return System.getenv(variable);
	}
}
