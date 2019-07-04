package com.github.eostermueller.havoc.rest;

public class ApiResponse {
	private long nanoStart = 0L;
	private long nanoStop = 0L;
	private int status = 0;

	public long getNanoStart() {
		return nanoStart;
	}
	public void setNanoStart(long nanoStart) {
		this.nanoStart = nanoStart;
	}
	public long getNanoStop() {
		return nanoStop;
	}
	public void setNanoStop(long nanoStop) {
		this.nanoStop = nanoStop;
	}
	public ApiResponse(long nanoTime) {
		this.nanoStart = nanoTime;
	}
	public int getStatus() {
		return this.status;
	}
	public void setStatus(Status val) {
		this.setNanoStop(System.nanoTime() );
		this.status = val.getValue();
	}
	public int getMessage() {
		return message;
	}
	public void setMessage(int message) {
		this.message = message;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	public int message = 0;
	public Object result = null;
}
