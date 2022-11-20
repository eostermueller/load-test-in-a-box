package com.github.eostermueller.snail4j.launcher;


import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.github.eostermueller.snail4j.DefaultFactory;
import com.github.eostermueller.snail4j.install.Installer;

/**
 * just the bare minimum required to find and read the snail4j.json config file.
 * Currently, user cannot change any of these settings, like ".load-test-i-a-box" folder and "snail4j.json" config file name.
 * @author erikostermueller
 *
 */
public class BootstrapConfig {
	private Path userHomeDir;
	private Path Snail4jHomeDir;
	private String configFileName;

	public BootstrapConfig() {
		
		this(
				Installer.INSTALL_ROOT,
				"snail4j.json");
	}
	  public void createSnail4jHomeIfNotExist() throws CannotFindSnail4jFactoryClass {
		  File snail4jHomeDir = getSnail4jHome().toFile();
		  if (!snail4jHomeDir.exists())
			  snail4jHomeDir.mkdirs();
	  }
	
	public BootstrapConfig(String snail4jHomeDirName, String configFileName) {
		this.setSnail4jHome(
				Paths.get( this.getUserHomeDirString(), snail4jHomeDirName )
				);
		this.setConfigFileName(configFileName);
	}
	public Path getFullPathToConfigFile() {
		return Paths.get( this.getSnail4jHome().toString(), this.getConfigFileName() );
	}

	public void setConfigFileName(String configFileName) {
		this.configFileName = configFileName;
	}
	public String getConfigFileName() {
		return this.configFileName;
	}

	public Path getUserHomeDir() {
		return this.userHomeDir;
	}
	public void setUserHomeDir(Path val) {
		this.userHomeDir = val;
	}
	public String getUserHomeDirString() {
		return System.getProperty("user.home");
	}		
	public Path getSnail4jHome() {
		return this.Snail4jHomeDir;
	}
	public void setSnail4jHome(Path  val) {
		this.Snail4jHomeDir = val;
	}	
}
