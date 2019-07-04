package com.github.eostermueller.havoc.rest;

public enum Status {
    SUCCESS(100),
    FAILURE(200);
	private int value; 
	private Status(int value) { 
		this.value = value; 
	}
	public int getValue() {
		return this.value;
	}
	
    
}
