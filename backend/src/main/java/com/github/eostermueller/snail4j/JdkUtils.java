package com.github.eostermueller.snail4j;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;

import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.ReflectionException;

import com.github.eostermueller.snail4j.OsUtils.OsResult;
import com.github.eostermueller.snail4j.DefaultFactory;
import com.github.eostermueller.snail4j.launcher.Messages;

public class JdkUtils {

	public static Path get_JAVA_HOME() throws Snail4jException {
		String javaHomeEnvVar = System.getenv("JAVA_HOME");
		//String javaHomeEnvVar = "C:\\java\\java-1.8.0-openjdk-1.8.0.252-2.b09.ojdkbuild.windows.x86_64\\java-1.8.0-openjdk-1.8.0.252-2.b09.ojdkbuild.windows.x86_64";
		Messages m = DefaultFactory.getFactory().getMessages();
		Path p = null;
		if (javaHomeEnvVar!=null && javaHomeEnvVar.trim().length()>0) {
			p = Paths.get(javaHomeEnvVar);
			
			if (!p.toFile().exists() || !p.toFile().isDirectory() ) {
				throw new Snail4jException( m.javaHomeFolderDoesNotExistOrLackingPermissions(p.toFile()) );
			}
		} else {
			throw new Snail4jException(m.javaHomeEnvVarNotSet());
		}
		return p;
	}
	public static ProcessDescriptor[] getJavaProcesses(Path jdkHome) throws Snail4jException {
		OsResult osResult = executeJdkBinCmd(jdkHome,JCMD);
		List<ProcessDescriptor> processes = new ArrayList<ProcessDescriptor>();
		
		String[] javaProcesses = osResult.stdout.split("\\r?\\n");
		for(String oneLine : javaProcesses) {
			int indexOfSpace = oneLine.indexOf(' ');
			if (indexOfSpace > 1) {
				
				long pid = 0;
				try {
					pid = Long.parseLong(
						oneLine.substring(0,indexOfSpace).trim()
						);
				} catch (NumberFormatException e) {
					throw new Snail4jException(String.format("Unable to locate PID after first space char in this JCMD output line [%s]", oneLine));
				}
				processes.add( ProcessDescriptor.create(
						pid, 
						oneLine.substring(indexOfSpace+1, oneLine.length() ).trim()
						) );
			}
		}
		return processes.toArray(new ProcessDescriptor[] {});
		
	}
	public static OsResult executeJdkBinCmd(Path jdkHome, String jdkCmd) throws Snail4jException {
		OsResult osResult = null;
		Messages m = DefaultFactory.getFactory().getMessages();
		
		if (jdkHome.toFile().exists() && jdkHome.toFile().isDirectory() ) {
			String jdkBinCommand = jdkHome.toFile().getAbsolutePath().toString() + File.separator + "bin" + File.separator + jdkCmd;
			osResult = OsUtils.executeProcess(jdkBinCommand);
		} else {
			throw new Snail4jException( m.javaHomeFolderDoesNotExistOrLackingPermissions(jdkHome.toFile()));
		}
		
		return osResult;
	}
	public static class ProcessDescriptor {
		public static ProcessDescriptor create(long pid, String commandLine) {
			ProcessDescriptor pd = new ProcessDescriptor();
			pd.pid = pid;
			pd.commandLine = commandLine;
			
			return pd;
		}
		public String toString() {
			return String.format("pid=%s commandLine=%s", this.pid,this.commandLine);
		}
		public long pid = 0;
		public String commandLine = null;
	}

	/** Object Name of DiagnosticCommandMBean. */
	   public final static String DIAGNOSTIC_COMMAND_MBEAN_NAME =
	      "com.sun.management:type=DiagnosticCommand";

	public static final String JCMD = "jcmd";
	 
	   /** My MBean Server. */
	   private final MBeanServer server = ManagementFactory.getPlatformMBeanServer();
	 
	   private final ObjectName objectName = null;
	 
	   //  . . .
	 
	   /**
	    * Invoke operation on the DiagnosticCommandMBean that accepts
	    *    String array argument but does not require any String
	    *    argument and returns a String.
	    *
	    * @param operationName Name of operation on DiagnosticCommandMBean.
	    * @param operationDescription Description of operation being invoked
	    *    on the DiagnosticCommandMBean.
	    * @return String returned by DiagnosticCommandMBean operation.
	    */
	   private String invokeNoStringArgumentsCommand(
	      final String operationName, final String operationDescription)
	   {
	      String result;
	      try
	      {
	         result = (String) server.invoke(objectName, operationName, new Object[] {null}, new String[]{String[].class.getName()});
	      }
	      catch (InstanceNotFoundException | ReflectionException | MBeanException exception)
	      {
	         result = "ERROR: Unable to access '" + operationDescription + "' - " + exception;
	      }
	      return result;
	   }
	   /**
	    * @param criteria
	    * @param listOfSpecifications
	    * @return
	    */
	public static boolean isJavaSpecificationInList(String criteria,
			String[] listOfSpecifications) throws Snail4jException {
		
		
		if (criteria==null || criteria.length()==0)
			throw new Snail4jException("Snail4j bug.  checking whether the java.specification.version is valid, and was passed a null/zero length string");
		
		if (listOfSpecifications==null || listOfSpecifications.length==0)
			throw new Snail4jException("Snail4j bug.  checking whether the java.specification.version is valid, and was passed a null/zero array");
		
		boolean rc = false;
		
		for (String javaSpecification : listOfSpecifications) {
			if (javaSpecification.equals(criteria))
				rc = true;
		}
		
		return rc;
	}
}
