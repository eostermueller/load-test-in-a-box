package com.github.eostermueller.snail4j.launcher;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.github.eostermueller.snail4j.DefaultFactory;

/**
 * This key makes a unique identifier for one of the managed processes.
 * @author erikostermueller
 *
 */
public class ProcessKey {

	private static final String DELIMITER = "!";
	private static final long UNDEFINED = -1;
	private String hostName;
	public long getTinyId() {
		return tinyId;
	}
	public void setTinyId(long tinyId) {
		this.tinyId = tinyId;
	}
	private long tinyId = UNDEFINED; 
	public String getHostName() {
		return hostName.trim();
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getSuite() {
		return suite;
	}
	public void setSuite(String suite) {
		this.suite = suite;
	}
	public Level getLevel() {
		return level;
	}
	public void setLevel(Level level) {
		this.level = level;
	}
	public void setPid(long pid) {
		this.pid = pid;
	}
	private long pid =  UNDEFINED;
	String processType = null;
	private String suite;
	private Level level;
	public String getProcessType() {
		return processType;
	}
	public void setProcessType(String processType) {
		this.processType = processType;
	}
	public long getPid() {
		return pid;
	}
	private ProcessKey(String suite, Level level, String processType, String myHostName, long tinyId) {
		this.setSuite(suite);
		this.setLevel(level);
		this.setProcessType(processType);
		this.setHostName(myHostName);
		this.setTinyId(tinyId);
	}
	private ProcessKey(String suite, Level level, String processType, String myHostName, long tinyId, long pid) {
		this.setSuite(suite);
		this.setLevel(level);
		this.setProcessType(processType);
		this.setHostName(myHostName);
		this.setTinyId(tinyId);
		this.setPid(pid);
	}
	
	public String getKey() {
		return suite 
				+ DELIMITER + this.getLevel()
				+ DELIMITER + this.getProcessType()
				+ DELIMITER + this.getHostName() 
				+ DELIMITER + String.valueOf(this.getTinyId()).trim()
				+ DELIMITER + String.valueOf(this.getPid()).trim();
	}
	public static ProcessKey create(String suite, Level level, String processType) throws CannotFindTjpFactoryClass {
		Factory factory = DefaultFactory.getFactory();
		long tinyId = factory.getJvmLifetimeUniqueId();
		return new ProcessKey(suite, level, processType, ProcessKey.getLocalHost().toString(), tinyId);
	}
	
	public static ProcessKey create(String suite, Level level, String processType, String myHostName) throws CannotFindTjpFactoryClass {
		Factory factory = DefaultFactory.getFactory();
		long tinyId = factory.getJvmLifetimeUniqueId();
		return new ProcessKey(suite, level, processType, myHostName, tinyId);
	}
	public static ProcessKey create(String suite, Level level, String processType, String myHostName, long tinyId, long pid) throws CannotFindTjpFactoryClass {
		return new ProcessKey(suite, level, processType, myHostName, tinyId, pid);
	}
	public static ProcessKey create(String processKey) throws NumberFormatException, CannotFindTjpFactoryClass {
		String[] parts = processKey.split(ProcessKey.DELIMITER);
		ProcessKey newKey = ProcessKey.create(
				parts[0], 
				Level.valueOf(parts[1]), 
				parts[2], 
				parts[3], 
				Long.parseLong(parts[4]),
				Long.parseLong(parts[5])
				);
		return newKey;
	}
	public static InetAddress getLocalHost() {
	    InetAddress inetAddress = null;
		try {
			inetAddress = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return inetAddress;
	}

}
