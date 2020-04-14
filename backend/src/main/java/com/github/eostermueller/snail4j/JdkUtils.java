package com.github.eostermueller.snail4j;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.ReflectionException;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

public class JdkUtils {

	public static void main(String args[]) {
	}
	
	public static class ProcessDescriptor {
		public static ProcessDescriptor create(String pid, String commandLine) {
			ProcessDescriptor pd = new ProcessDescriptor();
			pd.pid = pid;
			pd.commandLine = commandLine;
			
			return pd;
		}
		public String toString() {
			return String.format("pid=%s commandLine=%s", this.pid,this.commandLine);
		}
		public String pid = null;
		public String commandLine = null;
	}
	public static ProcessDescriptor[] getJavaProcesses() {
		List<ProcessDescriptor> processDescriptors = new ArrayList<ProcessDescriptor>();
        List<VirtualMachineDescriptor> vms = VirtualMachine.list();
        for (VirtualMachineDescriptor virtualMachineDescriptor : vms) {
        	processDescriptors.add(  ProcessDescriptor.create(virtualMachineDescriptor.id(), virtualMachineDescriptor.displayName()));
//           System.out.println( String.format("============ Show JVM: pid = %s --> %s ", virtualMachineDescriptor.id(), virtualMachineDescriptor.displayName()) );
        }
        return processDescriptors.toArray( new ProcessDescriptor[processDescriptors.size()] );
	}

	/** Object Name of DiagnosticCommandMBean. */
	   public final static String DIAGNOSTIC_COMMAND_MBEAN_NAME =
	      "com.sun.management:type=DiagnosticCommand";
	 
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
}
