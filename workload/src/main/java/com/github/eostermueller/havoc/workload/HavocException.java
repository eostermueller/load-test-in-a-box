package com.github.eostermueller.havoc.workload;

public class HavocException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	public HavocException() {
		
	}
	public HavocException(String val) {
		this.message = val;
	}
	public HavocException(Exception cause, String message) {
		super(cause);
		this.message = message;
	}
	@Override
	public String getMessage() {
		return message;
	}

}
