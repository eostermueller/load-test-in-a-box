package com.github.eostermueller.snail4j.util;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.ReflectionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.eostermueller.snail4j.DefaultFactory;
import com.github.eostermueller.snail4j.Snail4jException;
import com.github.eostermueller.snail4j.launcher.Messages;
import com.github.eostermueller.snail4j.util.OsUtils.OsResult;

public class JdkUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(JdkUtils.class);

	/**
	 * I'll define "java home" as the path in the JDK that is parent to the bin folder where javac or javac.exe resides.
	 * 
	 * sun.boot.library.path is defined as: 
	 * The Java Virtual Machine (JVM) uses the sun.boot.library.path property in order to locate native libraries. This property is part of the system environment used by Java, in order to locate and load native libraries used by an application.  Note that sun.boot.library.path is searched before java.library.path.
	 * 
	 * penJDK:
	 * 
	 * Win:
	 * 
	 *     sun.boot.library.path	C:\Program Files (x86)\Java\jre7\bin
	 *     per http://mirc.luriechildrens.org/system
	 *     
	 *     
	 *     sun.boot.library.path: D:\eclipse\workspace\main\jre\bin
	 *     https://discussions.apple.com/thread/5848338
	 *     
	 *     sun.boot.library.path=c:\program files\java\jre-9.0.1\bin
	 *     per https://community.atlassian.com/t5/Bamboo-questions/Error-when-trying-to-install-with-Java-9/qaq-p/691819
	 *     
	 *     sun.boot.library.path   C:\Program Files (x86)\Java\jre6\bin
	 *     per https://studylibfr.com/doc/2783759/se-runtime-environment-sun.boot.library.path-c--program-f...
	 *     
	 *     sun.boot.library.path	C:\Program Files\Java\jre8\bin
	 *     per https://www.mindprod.com/jgloss/properties.html
	 *     
	 *     sun.boot.library.path:C:\opt\Java\jdk-10\bin
	 *     pr https://mkyong.com/java/how-to-list-all-system-properties-key-and-value-in-java/
	 *     
	 *     sun.boot.library.path	C:\Program Files (x86)\Java\jre1.8.0_241\bin
	 *     per http://mirc.rosalindfranklin.edu/system
	 *     
	 *     
	 * Linux:
	 *      sun.boot.library.path	/usr/lib/jvm/java-1.7.0-openjdk-1.7.0.261-2.6.22.2.el7_8.x86_64/jre/lib/amd64
	 *      per https://mistrprodnew.usask.ca:8443/system
	 * 	   sun.boot.library.path = /usr/lib/jvm/java-7-openjdk-amd64/jre/lib/amd64
	 *     per https://www.eclipse.org/forums/index.php/t/1068254/
	 *  
	 *     sun.boot.library.path: /usr/lib/jvm/java-1.6.0-openjdk-1.6.0.0.x86_64/jre/lib/amd64
	 *     per https://fedoraproject.org/wiki/Java/Troubleshooting
	 *     
	 *     sun.boot.library.path  /oracle_local/u01/jdk1.8.0_191/jre/lib/amd64
	 *     per https://prodenv.dep.state.fl.us/DepNexus/public/systemproperties
	 *     
	 *     sun.boot.library.path=/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.345.b01-1.el7_9.x86_64/jre/lib/amd64
	 *     per https://meme-suite.org/meme/opal2/happyaxis.jsp
	 *     
	 *     sun.boot.library.path=/home/ianhudson/Development/jdk1.8.0_73/jre/lib/amd64
	 *     per https://community.jitsi.org/t/jitsi-dev-problems-running-libjitsi-examples-no-audio-video/10448
	 *     
	 *     sun.boot.library.path = /usr/lib/jvm/java-1.8.0-openjdk-1.8.0.242.b08-0.fc30.x86_64/jre/lib/amd64
	 *     per https://bugzilla.redhat.com/show_bug.cgi?id=1805105
	 *     
	 *     sun.boot.library.path: /usr/lib/jvm/java-1.6.0-openjdk-1.6.0.0.x86_64/jre/lib/amd64
	 *     per https://fedoraproject.org/wiki/User:Brunovernay/Java/Troubleshooting
	 *     
	 *     sun.boot.library.path /usr/lib/jvm/java-6-sun-1.6.0.26/jre/lib/amd64
	 *     per https://issues.jenkins.io/browse/JENKINS-18017?page=com.atlassian.jira.plugin.system.issuetabpanels%3Aall-tabpanel
	 *     
	 *     
	 *  Mac:
	 *  	sun.boot.library.path=/Library/Java/JavaVirtualMachines/jdk1.8.0_321.jdk/Contents/Home/jre/lib
	 *      per https://github.com/jitsi/libjitsi/issues/557
	 *      
	 *      sun.boot.library.path=/Applications/YourKit-Java-Profiler-2019.1.app/Contents/jdk/Contents/Home/jre/lib
	 *      per https://www.yourkit.com/forum/viewtopic.php?t=40702
	 *      
	 *       sun.boot.library.path = /Users/bradley/Library/Java/JavaVirtualMachines/openjdk-18.0.1.1/Contents/Home/lib
	 *       per https://stackoverflow.com/q/72942063/2377579
	 *      
	 *  IBM JDK:
	 *  
	 *  Linux:
	 *  
	 *     sun.boot.library.path = /opt/ibm/java/jre/lib/amd64/compressedrefs
     *     /opt/ibm/java/jre/lib/amd64
     *     per https://knowledge.broadcom.com/external/article/229283/uma-liberty-agent-not-deploying-to-conta.html
	 *     
	 *     
	 * @param currentPath -- value of system property sun.boot.library.path
	 * @return
	 * @throws Snail4jException
	 */
	public static Path getJavaHomeFromSunBootLibraryPath(Path currentPath ) throws Snail4jException {
		
		Path rc = null;
		
		OS.OsFamily osFamily = OS.getOs().getOsFamily();
		
		switch(osFamily) {
		case Mac:
		case Linux:
			//Example:  /Library/Java/JavaVirtualMachines/jdk1.8.0_92.jdk/Contents/Home/jre/lib
			rc = PathUtil.getParentOfSpecificPathElement(currentPath, "jre");
			/*
			 * 
			 */
			break;
		case Windows:
			//Example: C:\java\jdk-14.0.2\bin
			rc = currentPath.getParent();
			break;
				//Example:  /usr/lib/jvm/java-1.8.0-amazon-corretto.x86_64/jre/lib/amd64
			default:
		}
		if (OS.getOs().getOsFamily()==OS.OsFamily.Mac) {
			/**
			 * Example input from mac:
			 *   /Library/Java/JavaVirtualMachines/jdk1.8.0_92.jdk/Contents/Home/jre/lib
			 */
			
		}
		return rc;
	}
	public static Path getJavaHomeFromSunBootLibraryPath() throws Snail4jException {
		Path currentPath  = getCurrentSunBootLibraryPath();
		return getJavaHomeFromSunBootLibraryPath(currentPath);
	}

	/**  I wish geting java_home was as simple as doing "system.getProperties("java.home")
	 * ....but is isn't.
	 * The "load-test-in-a-box" definition java java_home is "The folder from which you can execute 'bin/javac' or 'bin\javac.exe'.
	 * ....and for my macbook, the java.home system property doesn't match up -- whoops!!
	 * 			 *  java.home = /Library/Java/JavaVirtualMachines/jdk1.8.0_202.jdk/Contents/Home/jre

	 */
	public static Path getCurrentSunBootLibraryPath() throws Snail4jException {
		String pathOfRunningJava = System.getProperty("sun.boot.library.path");
		
		Path p = Paths.get(pathOfRunningJava);
		if (p==null || p.toFile().getAbsolutePath()==null) {
			throw new Snail4jException("Bug. No Path returned from JdkUtils.getCurrentJavaPath() for 'sun.boot.library.path' path: " + pathOfRunningJava);
		}
		
		return Paths.get(pathOfRunningJava);		
	}
	public static boolean pointsToCurrentJava(Path javaHome) throws Snail4jException {

		javaHome = javaHome.normalize();
		
		Path currentJava = getCurrentSunBootLibraryPath().normalize();
				
		if (currentJava.endsWith( Paths.get("bin") ))
			currentJava = currentJava.getParent();

		return currentJava.startsWith(javaHome);
	}

	public static boolean isJdk(Path p) {
		Path c = getDirectoryOfJavaCompiler(p);
		return c != null;
	}
	public static boolean isJdk() {
		String path = getInstallPathOfThisJvm();
		return !(getDirectoryOfJavaCompiler(Paths.get(path)) == null);
		
	}
	/*
	 * @stolenFrom: https://stackoverflow.com/a/58737549/2377579
	 * 
	 * Helpful for debugging:
	 * java -XshowSettings:properties -version
	 * 
	 * OpenJDK "JRE+JDK" Packaging Scenario:
	 * C:\java\java-1.8.0-openjdk-1.8.0.252-2.b09.ojdkbuild.windows.x86_64\java-1.8.0-openjdk-1.8.0.252-2.b09.ojdkbuild.windows.x86_64\jre\bin
	 * --> ..\..\bin\javac.exe
<pre>
	 
├───bin                <<<<======= javac.exe
├───include
│   └───win32
│       └───bridge
├───jre
│   ├───bin            <<<<======= sun.boot.library.path
│   │   └───server
│   └───lib
├───lib
└───webstart	  
</pre>
	 */
	public static Path getDirectoryOfJavaCompiler() {
		Path pathToJavaCompiler = null;
		String path = getInstallPathOfThisJvm();
	    if(path != null) {
	        String javacPath = "";
	        if(path.endsWith(File.separator + "bin") || path.endsWith(File.separator + "bin" + File.separator) ) {
	            javacPath = path;
	        } else {
	            int libIndex = path.lastIndexOf(File.separator + "lib");
	            if(libIndex > 0) {
	                javacPath = path.substring(0, libIndex) + File.separator + "bin";
	            }
	        }
	        pathToJavaCompiler = getDirectoryOfJavaCompiler( Paths.get(javacPath) );
	    }
	    if (pathToJavaCompiler==null)
		    LOGGER.debug(String.format("Failed to locate java compiler executable. The  sun.boot.library.path is %s", path));
	    else
    	    LOGGER.debug(String.format("Found javac executable in %s", pathToJavaCompiler.toAbsolutePath().toString() ));
	    	
	    return pathToJavaCompiler;
	}
	
	public static String getInstallPathOfThisJvm() {
	    String path = System.getProperty("sun.boot.library.path");
	    LOGGER.debug(String.format("sun.boot.library.path is %s", path));
	    return path;
	}
	public static Path getDirectoryOfJavaCompiler(Path path) {
		Path pathToJavaCompiler = null;
		String possiblePathToCompiler = path.toAbsolutePath().toString();
        if(!possiblePathToCompiler.isEmpty()) {
    	    LOGGER.debug(String.format("looking for javac executable in %s", possiblePathToCompiler));
    	    
            if ( new File(possiblePathToCompiler, "javac").exists() || new File(possiblePathToCompiler, "javac.exe").exists() )
            	pathToJavaCompiler = Paths.get(possiblePathToCompiler);
            else {
	            /**  The above will work most of the time.
	             *  The following addresses the OpenJDK "JRE+JDK" Packaging Scenario, detailed in method comments.
	             */
            	possiblePathToCompiler = possiblePathToCompiler + File.separator + ".." + File.separator + ".." + File.separator + "bin";
	    	    LOGGER.debug(String.format("looking for javac executable in %s", possiblePathToCompiler));
	            if ( new File(possiblePathToCompiler, "javac").exists() || new File(possiblePathToCompiler, "javac.exe").exists() )
	            	pathToJavaCompiler = Paths.get(possiblePathToCompiler);
            }
        }
        return pathToJavaCompiler;
	}
	
	public static long getPidForProcessNameContains(String processNameCriteria) {
		long rc = -1;
		ProcessDescriptor[] processDescriptors;
		try {
			processDescriptors = getJavaProcesses();
			LOGGER.debug("Found [" + processDescriptors.length + "] running processes");
			for(ProcessDescriptor processDescriptor : processDescriptors ) {
				LOGGER.debug("Does cmd line [" + processDescriptor.commandLine + "] contain [" + processNameCriteria + "]?");
				if ( processDescriptor.commandLine.contains(processNameCriteria)) {
					rc = processDescriptor.pid;
					LOGGER.debug("Does cmd line [" + processDescriptor.commandLine + "] contain [" + processNameCriteria + "]? YES!!!!!");
					break;
				}
			}
		} catch (Snail4jException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		LOGGER.debug("pid = [" + Long.toString(rc) + "] for cmd line containing [" + processNameCriteria + "]");
		
		return rc;
	}
	public static ProcessDescriptor[] getJavaProcesses() throws Snail4jException {
		OsResult osResult = executeJdkBinCmd( JCMD);
		List<ProcessDescriptor> processes = new ArrayList<ProcessDescriptor>();
		
		String[] javaProcesses = osResult.stdout.split("\\r?\\n");
		LOGGER.debug("Count of text lines in jcmd output:  [" + javaProcesses.length + "]");
		for(String oneLine : javaProcesses) {
			LOGGER.debug("Found this one line of text in jcmd output::  [" + oneLine + "]");
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
		LOGGER.debug("Count of processes in jcmd output:  [" + processes.size() + "]");
		return processes.toArray(new ProcessDescriptor[] {});
		
	}
	
	public static OsResult executeJdkBinCmd(String jdkCmd) throws Snail4jException {
		Path jdkBinDir = JdkUtils.getDirectoryOfJavaCompiler();
		OsResult osResult = null;
		Messages m = DefaultFactory.getFactory().getMessages();
		
		if (jdkBinDir.toFile().exists() && jdkBinDir.toFile().isDirectory() ) {
			String jdkBinCommand = jdkBinDir.toFile().getAbsolutePath().toString() + File.separator + jdkCmd;
			osResult = OsUtils.executeProcess(jdkBinCommand);
		} else {
			throw new Snail4jException( m.jdkFolderDoesNotExistOrLackingPermissions(jdkBinDir.toFile()));
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
