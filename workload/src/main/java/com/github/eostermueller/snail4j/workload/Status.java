package com.github.eostermueller.snail4j.workload;

public enum Status {
    SUCCESS(100),
    FAILURE(1500),
	FAILURE_ALIAS_DOES_NOT_EXIST(1501), 
	FAILURE_WORKLOAD_CREATION(1502), 
	FAILURE_WORKLOAD_CREATION_EXCEPTION(1503);
	private int value; 
	private Status(int value) { 
		this.value = value; 
	}
	public int getValue() {
		return this.value;
	}
	
    
}
