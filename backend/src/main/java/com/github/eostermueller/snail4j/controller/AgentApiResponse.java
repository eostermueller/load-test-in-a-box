package com.github.eostermueller.snail4j.controller;

/**
 * REST wrapper.
 * Ideally, this and AgentStatus would be in some kind of a shared package that is also used by the workload agent.
 * My short term solution is to have separate-but-identical classes:  
 * <ul>
 * 		<li>AgentStatus and Status</li>
 * 		<li>ApiResponse and AgentApiResponse</li>
 * </ul>
 * The mid-term solution should be to create/package/distribute a package that is shared by the agent and the workload agent.
 * @author eoste
 *
 */
public class AgentApiResponse {
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
	public AgentApiResponse(long nanoTime) {
		this.nanoStart = nanoTime;
	}
	public int getStatus() {
		return this.status;
	}
	public void setStatus(AgentStatus val) {
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
	/**
	 * This is result detail, like the result of an inquiry.
	 * @param result
	 */
	public void setResult(Object result) {
		this.result = result;
	}
	public int message = 0;
	public Object result = null;
}
