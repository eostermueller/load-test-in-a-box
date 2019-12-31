package com.github.eostermueller.snail4j.controller;

public enum AgentStatus {
    SUCCESS(100),
    FAILURE(200);
	private int value; 
	private AgentStatus(int value) { 
		this.value = value; 
	}
	public int getValue() {
		return this.value;
	}
	
    
}
