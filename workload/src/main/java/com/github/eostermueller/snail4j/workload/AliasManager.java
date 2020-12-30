package com.github.eostermueller.snail4j.workload;

public interface AliasManager {

	String resolve(String alias);

	int load();

	int size();

}
