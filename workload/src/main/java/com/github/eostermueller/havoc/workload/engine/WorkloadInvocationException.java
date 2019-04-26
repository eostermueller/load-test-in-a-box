package com.github.eostermueller.havoc.workload.engine;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.github.eostermueller.havoc.workload.model.MethodWrapper;

public class WorkloadInvocationException extends Exception {

	private Exception cause;
	private MethodWrapper methodWrapper;

	public Exception getCause() {
		return cause;
	}

	public void setCause(Exception cause) {
		this.cause = cause;
	}

	public WorkloadInvocationException(MethodWrapper methodWrapper, Exception val) {
		this.methodWrapper = methodWrapper;
		this.cause = val;
	}
	public String getMessage() {
		String error = "Unable to execute[" + this.methodWrapper.humanReadable() + "] exception [" + this.cause.getClass().getName() + "][" + getCausStackTraceString() + "]";
		return error;
	}
	public String getCausStackTraceString() {
		StringWriter sw = new StringWriter();
		cause.printStackTrace( new PrintWriter(sw));
		return sw.toString();
	}
}
