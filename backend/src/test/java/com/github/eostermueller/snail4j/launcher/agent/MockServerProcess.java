package com.github.eostermueller.snail4j.launcher.agent;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;

import com.github.eostermueller.snail4j.DefaultFactory;
import com.github.eostermueller.snail4j.Snail4jException;
import com.github.eostermueller.snail4j.util.JdkUtils;
import com.github.eostermueller.snail4j.util.OS;


/**
 * Test process that creates a ProcessBuilder that invokes javac compiler.
 * Used to validate/exercise this API, which launches/stops groups of dependent processes, manages stdout/err of those processes.
 * @author erikostermueller
 *
 */
public class MockServerProcess {
	private File currentWorkingDir = null;
	private long tinyId = -1;
	private Path javaHome;
	public Path getJavaHome() {
		return javaHome;
	}
	public void setJavaHome(Path javaHome) {
		this.javaHome = javaHome;
	}
	public ProcessBuilder getProcessBuilder() throws Snail4jException {
		
		String fileExtension = "";
		if (OS.getOs().getOsFamily() == OS.OsFamily.Windows) {
			fileExtension = ".exe";
		}
		
		List<String> attemptedPaths = new ArrayList<String>();
		
		String binAndJavaExecutable = "bin" + File.separator + "java" + fileExtension;
		Path java_Executable = this.getJavaHome().resolve( Paths.get(binAndJavaExecutable) );
 
		
		if (!java_Executable.toFile().exists() ) {
			//Checking to see if java is here:
			//openjdk-8u242-b01/jre is java home
			//openjdk-8u242-b01/bin/java.exe is here.
			attemptedPaths.add(binAndJavaExecutable);
			binAndJavaExecutable = ".." + File.separator + "bin" + File.separator + "java" + fileExtension;
			java_Executable = this.getJavaHome().resolve( Paths.get(binAndJavaExecutable) );
			if (!java_Executable.toFile().exists() ) {
				throw new Snail4jException("Was expecting java to be in one of these paths: [" + attemptedPaths.toString() + "]");
			}
		}
		//System.out.println("My java exe: " + java_Executable.toAbsolutePath().toString() );
		ProcessBuilder pb = new ProcessBuilder(
				java_Executable.toAbsolutePath().toString(),
				"-classpath",".",this.getClassName() 
				);
		
		pb.directory( this.getCurrentWorkingDir() );
		
		return pb;
	}
	public long getTinyId() {
		return tinyId;
	}
	public void setTinyId(long tinyId) {
		this.tinyId = tinyId;
	}
	public File getCurrentWorkingDir() throws Snail4jException {
		if (currentWorkingDir == null) {
			String error = "current working dir cannot be null";
			DefaultFactory.getFactory().getEventHistory().addException(error, new Snail4jException(error) );
		}
			
		//System.out.println("My cwd [" + this.currentWorkingDir.getAbsolutePath() + "]");
		return currentWorkingDir;
	}
	public void setCurrentWorkingDir(File tempFolder) {
		if (tempFolder == null)
			throw new RuntimeException("working dir cannot be null");
		this.currentWorkingDir = tempFolder;
	}
	public MockServerProcess(File tempFolder, Path javaHome, long tinyId) {
		if (tempFolder==null) 
			throw new RuntimeException("current working directory cannot be null");
		this.setCurrentWorkingDir(tempFolder);
		this.setTinyId(tinyId);
		this.setJavaHome(javaHome);
	}
	public String getClassName() {
		return "TjpTestProcess_" + String.valueOf(this.getTinyId()).trim();
	}
	/**
	 * Needs to be unique, because were trying to orchestrate ups/downs of multiple processes.
	 * @return
	 */
	public String getSourceFileName() {
		return this.getClassName() + ".java";
	}
	public String getClassFileName() {
		return this.getClassName() + ".class";
	}
	public File getAbsoluteClassFileName() throws Snail4jException {
		File myClassFile 
		= new File(
				this.getCurrentWorkingDir(),
				getClassFileName() );
		return myClassFile;
	}
	public void compile() throws Exception {
		File mySourceFile 
			= new File(
					this.getCurrentWorkingDir(),
					getSourceFileName() );
		
		writeTextToFile(getSourceFileText(), mySourceFile);
		
		Path javaHome = JdkUtils.getJavaHomeFromSunBootLibraryPath();
		
		Assertions.assertNotNull( javaHome );
		
		String fileExtension = "";
		if (OS.getOs().getOsFamily() == OS.OsFamily.Windows) {
			fileExtension = ".exe";
		}
		String pathSuffix = "bin" + File.separator + "javac" + fileExtension;
		
		Path java_c_Executable = javaHome.resolve( Paths.get(pathSuffix) );
//		if (!java_c_Executable.toFile().exists() ) {
//			throw new Snail4jException("Was expecting [" + java_c_Executable.toString() + "] to be the path to a javac executable.");
//		}
		List<String> attemptedPaths = new ArrayList<String>();
		
		if (!java_c_Executable.toFile().exists() ) {
			//Checking to see if java is here:
			//openjdk-8u242-b01/jre is java home
			//openjdk-8u242-b01/bin/java.exe is here.
			attemptedPaths.add(java_c_Executable.toString());
			pathSuffix = ".." + File.separator + "bin" + File.separator + "javac" + fileExtension;
			java_c_Executable = this.getJavaHome().resolve( Paths.get(pathSuffix) );
			if (!java_c_Executable.toFile().exists() ) {
				attemptedPaths.add(java_c_Executable.toString());
				throw new Snail4jException("Was expecting java to be in one of these paths: [" + attemptedPaths.toString() + "]");
			}
		}

		ProcessBuilder pb = new ProcessBuilder(
				java_c_Executable.toAbsolutePath().toString(),
				mySourceFile.toPath().toAbsolutePath().toString() 
				);
		pb.directory( this.getCurrentWorkingDir() );
	    Process process;
		try {
			process = pb.start();
		    int status = process.waitFor();
			if (status !=0) {
				throw new Exception("The following source code failed to compile [" + this.getSourceFileText() + "]");
			} else {
				//System.out.println(".class was created after compile?[" + getAbsoluteClassFileName().exists()  + "] javac output: [" + getAbsoluteClassFileName() + "]");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	        
		
	}
	private long sleepMsAfterStartup = 1000000;
	public long getSleepMsAfterStartup() {
		return sleepMsAfterStartup;
	}
	public void setSleepMsAfterStartup(long sleepMsAfterStartup) {
		this.sleepMsAfterStartup = sleepMsAfterStartup;
	}
	private long sleepMsBeforeStartup = 100;
	public long getSleepMsBeforeStartup() {
		return sleepMsBeforeStartup;
	}
	public void setSleepMsBeforeStartup(long sleepMsBeforeStartup) {
		this.sleepMsBeforeStartup = sleepMsBeforeStartup;
	}
	private String getSourceFileText() {
		String myJavaSource =
		"public class " + this.getClassName()  + "{"
		+  "   public static void main(String args[]) {"
		+  "       System.out.println(\"foo and bar\");"
		+  "       System.out.println(\"hello \" );"
		+  "       try { Thread.sleep("+ String.valueOf(this.getSleepMsBeforeStartup()) + "); } catch(Exception e) {};"
		+  "       System.out.println(\"" + this.getStartupCompleteMessage() + " \" );"
		+  "       try { Thread.sleep("+ String.valueOf(this.getSleepMsAfterStartup()) + "); } catch(Exception e) {};"
		+  "   }"
		+  "}";
		
		return myJavaSource;
	}
	public String getStartupCompleteMessage() {
		return "Startup Complete";
	}
	private void writeTextToFile(String content, File file) {
		BufferedWriter bw = null;
		FileWriter fw = null;
		try {
			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			bw.write(content);

			//System.out.println("File [" + file.toString() + "] written.");

		} catch (IOException e) {

			e.printStackTrace();

		} finally {
			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}		
	}
	

}
