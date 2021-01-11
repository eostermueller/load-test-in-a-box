package com.github.eostermueller.snail4j.workload;

public class ApiResponse {
	private long nanoStart = 0L;
	private long nanoStop = 0L;
	private int status = 0;

	public boolean isFailure() {
		boolean rc = false;
		if (this.getStatus() >= Status.FAILURE.getValue())
			rc = true;
		
		return rc;
	}
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
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	public String message = null;
	public Object result = null;
}
