package com.github.eostermueller.havoc.workload;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;

public class HavocException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	Exception causedBy = null;
	public Exception getCausedBy() {
		return causedBy;
	}
	public void setCausedBy(Exception val) {
		val.printStackTrace();
		this.causedBy = causedBy;
	}
	public HavocException() {
		
	}
	public HavocException(String val) {
		this.message = val;
	}
	public HavocException(Exception cause, String message) {
		super(cause);
		this.message = message;
	}
	public HavocException(JsonProcessingException e) {
		this.setCausedBy(e);
	}
	public HavocException(IOException e) {
		this.setCausedBy(e);
	}
	@Override
	public String getMessage() {
		return message;
	}

}
