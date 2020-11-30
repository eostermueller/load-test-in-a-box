package com.github.eostermueller.snail4j.workload;

public class DecryptionException extends Exception {

	private Exception exception;

	public DecryptionException(Exception val) {
		this.exception = val;
	}
	
	@Override
	public Exception getCause() {
		return this.exception;
	}
}
