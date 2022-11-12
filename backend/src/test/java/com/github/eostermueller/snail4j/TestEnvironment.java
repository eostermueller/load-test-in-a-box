package com.github.eostermueller.snail4j;

import java.util.Hashtable;

import com.github.eostermueller.snail4j.util.OsEnvWrapper;

public class TestEnvironment implements OsEnvWrapper {

	Hashtable<String,String> env = new Hashtable<String,String>();
	@Override
	public String get(String variable) {
		return env.get(variable);
	}
	public void set(String variable, String value) {
		this.env.put(variable, value);
	}

}
