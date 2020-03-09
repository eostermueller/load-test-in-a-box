package com.github.eostermueller.snail4j.controller;

public enum Status2 {
    SUCCESS(100),
    FAILURE(200);
	private int value; 
	private Status2(int value) { 
		this.value = value; 
	}
	public int getValue() {
		return this.value;
	}

}
