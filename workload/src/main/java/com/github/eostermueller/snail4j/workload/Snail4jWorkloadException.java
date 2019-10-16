package com.github.eostermueller.snail4j.workload;

import java.io.IOException;
import java.util.Arrays;

import com.fasterxml.jackson.core.JsonProcessingException;

public class Snail4jWorkloadException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	Throwable causedBy = null;
	public Throwable getCausedBy() {
		return causedBy;
	}
	public void setCausedBy(Exception val) {
		val.printStackTrace();
		this.causedBy = causedBy;
	}
	public Snail4jWorkloadException() {
		
	}
	public Snail4jWorkloadException(String val) {
		this.message = val;
	}
	public Snail4jWorkloadException(Exception cause, String message) {
		super(cause);
		this.setCausedBy(cause);
		this.message = message;
	}
	public Snail4jWorkloadException(JsonProcessingException e) {
		super(e);
		this.setCausedBy(e);
	}
	public Snail4jWorkloadException(IOException e) {
		super(e);
		this.setCausedBy(e);
	}
	@Override
	public String getMessage() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.message);
		if (this.getCausedBy() !=null) {
			sb.append("\n");
			sb.append( Arrays.toString(this.getCausedBy().getStackTrace()));
		}
		return sb.toString();
	}

}
