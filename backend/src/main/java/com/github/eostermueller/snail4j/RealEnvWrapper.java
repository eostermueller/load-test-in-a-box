package com.github.eostermueller.snail4j;

public class RealEnvWrapper implements OsEnvWrapper {

	@Override
	public String get(String variable) {
		return System.getenv(variable);
	}
}
