package com.github.eostermueller.snail4j.install;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;

import com.github.eostermueller.snail4j.Context;
import com.github.eostermueller.snail4j.DefaultContext;
import com.github.eostermueller.snail4j.DefaultNonPersistentParameters;
import com.github.eostermueller.snail4j.Snail4jException;
import com.github.eostermueller.snail4j.launcher.CannotFindSnail4jFactoryClass;
import com.github.eostermueller.snail4j.util.PathUtil;

public class DefaultSutInstaller implements Installer {
	Context ctx = null;
	public DefaultSutInstaller() throws CannotFindSnail4jFactoryClass, Snail4jException {
		ctx = new DefaultContext();
	}
	Context getContext() {
		return ctx;
	}
	@Override
	public void install() throws Snail4jException {

		if (this.getContext().getNonPersistentParameters().deleteSut()) {
			File sutAppFolder = this.getContext().getConfiguration().getSutAppHome().toFile();
			String strSutAppFolder;
			try {
				strSutAppFolder = sutAppFolder.getCanonicalPath().toString();
			} catch (IOException e1) {
				throw new Snail4jException(e1,"Unable to format the " + INSTALL_ROOT + "/sutApp folder");
			}
			if (this.getContext().getConfiguration().getSutAppHome().toFile().exists()) {
				if (thisIsDefaultSnail4jSutAppPath(sutAppFolder) ) {
					try {
						new PathUtil().delete( sutAppFolder );
					} catch (IOException e) {
						String msg = getContext().getMessages().unableToRecursivelyDeleteFolder(strSutAppFolder);
						throw new Snail4jException(e,msg);
					}
				} else {
					String msg = getContext().getMessages().pleaseManuallyDeleteFolderAndAllContents(strSutAppFolder);
					throw new Snail4jException(msg);
				}
			}
		}
		
		if (this
				.getContext()
				.getNonPersistentParameters()
				.sutGitCloneRemoteUrl() !=null 
			&&	!this.getContext().getNonPersistentParameters().sutGitCloneRemoteUrl().trim().isEmpty()) {
			getContext().getLogger().info("Found URL of git repo to use for SUT Application: " + 
					this.getContext().getNonPersistentParameters().sutGitCloneRemoteUrl() );
			if ( !this.getContext().getConfiguration().getSutAppHome().toFile().exists()  ) {
				cloneSutGitRepo();
				getContext().getLogger().info("After git repo has been cloned.");
			} else {
				getContext().getLogger().error("Will not clone repo because sutApp folder exists here: " + this.getContext().getConfiguration().getSutAppHome().toFile().toString() );
				getContext().getLogger().info("Restart the Snail4j uber jar with -D parameter [" + DefaultNonPersistentParameters.DELETE_SUT_DASH_D_PARAMETER + "] to delete the sutApp folder.");
			}
		} else {
			getContext().getLogger().info("About to install the native sutApp.");
			installNativeSutApp();
		}
		
	}
	private boolean thisIsDefaultSnail4jSutAppPath(File sutAppFolder) {
		boolean rc = false;
		this.getContext().getLogger().debug("@@@ sutAppFolder.getName().toString() [" + sutAppFolder.getName().toString() + "] ");
		this.getContext().getLogger().debug("@@@ sutAppFolder.getParentFile().getName() [" + sutAppFolder.getParentFile().getName() + "] ");
		if (sutAppFolder.getName().toString().equals("sutApp") 
				&& sutAppFolder.getParentFile().getName().equals(INSTALL_ROOT) ) {
			rc = true;
		}
		try {
			this.getContext().getLogger().error("Is directory [" + sutAppFolder.getCanonicalPath().toString() + "] the default snail4j sutApp folder?" + rc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rc;
	}
	protected void cloneSutGitRepo() throws CannotFindSnail4jFactoryClass, InstallException {
		try {
			this.getContext().getConfiguration().getSutAppHome().toFile().mkdir();
			
			CloneCommand cloneCommand = Git.cloneRepository();
			cloneCommand.setURI( this.getContext().getNonPersistentParameters().sutGitCloneRemoteUrl() );
			//The following might be a nice feature enhancement in the future
			//cloneCommand.setCredentialsProvider( new UsernamePasswordCredentialsProvider( "user", "pwd" ) );
			cloneCommand.setDirectory(this.getContext().getConfiguration().getSutAppHome().toFile());
			Git cloneGit = cloneCommand.call();
			System.out.println("Clone HEAD: " + cloneGit.log().call().iterator().next());
			cloneGit.close();
			
		} catch (GitAPIException e) {
			String msg = getContext().getMessages()
					.unableToCloneSutRepo(
							this.getContext().getNonPersistentParameters().sutGitCloneRemoteUrl(),
							this.getContext().getConfiguration().getSutAppHome().toFile().toString() );
					
			throw new InstallException(e, msg );
		}
		
	}
	protected void installNativeSutApp() throws Snail4jException {
		PathUtil pathUtil = new PathUtil();
		String path = pathUtil.getBaseClassspath();
		String cleansedPath;
		
		try {
			Path targetSutAppZipFile = Paths.get( getContext().getConfiguration().getSutAppHome().toString(), getContext().getConfiguration().getSutAppZipFileName() );
			
			if (this.getContext().getConfiguration().getSutAppHome().toFile().exists()) {
				getContext().getLogger().info("dir for sutApp files already exists.");
			} else {
				getContext().getLogger().info("Creating dir for sut java app.");
				getContext().getConfiguration().getSutAppHome().toFile().mkdirs();
			}
			
			if (pathUtil.isUberJar()) {
				
				/**
				 * sutApp files must be extracted from a zip.
				 */
				cleansedPath = pathUtil.cleanPath(path);
				
		    	if ( targetSutAppZipFile.toFile().exists() ) {
		    		getContext().getLogger().info(targetSutAppZipFile.toFile().toString() + " exists. will not overwrite it.");
		    	} else {
		    		getContext().getLogger().info("About to unzip [" + targetSutAppZipFile.toString() + "] from [" + cleansedPath + "] to [" + targetSutAppZipFile.toString() + "]");
		    		pathUtil.extractZipFromZip(cleansedPath, getContext().getConfiguration().getSutAppZipFileName(), targetSutAppZipFile.toString() );
		    	}
		    	
		    	String[] fileNames=getContext().getConfiguration().getSutAppHome().toFile().list();
		    	
	    		getContext().getLogger().info("[" + fileNames.length  + "] file(s) exist(s) in [" + getContext().getConfiguration().getSutAppHome() + "]");
	    		
	    		if (fileNames.length<1) {
	    			throw new Snail4jException("Install failed.  Tried to unzip [" + getContext().getConfiguration().getSutAppZipFileName() + "] to [" + getContext().getConfiguration().getSutAppHome() + "] but [" + targetSutAppZipFile.toString() + "] does not exist." );
	    		} else if (fileNames.length==1 && fileNames[0].equals(getContext().getConfiguration().getSutAppZipFileName()) ) {
	        		pathUtil.unzip(targetSutAppZipFile.toFile(), getContext().getConfiguration().getSutAppHome().toString() );
	        		targetSutAppZipFile.toFile().delete(); // don't need anymore because we just unzipped its contents.
	    		} else {
	        		getContext().getLogger().info("Will not unzip [" + getContext().getConfiguration().getSutAppZipFileName() + "] to avoid overwriting local changes to unzipped files. Delete all files in " + getContext().getConfiguration().getSutAppHome() + " and restart snail4j executable jar");
	    		}
				
			} else {
				getContext().getLogger().error("launch as 'java -jar <snail4j.jar> to get maven to install");
			}
			
		} catch (Exception e) {
			throw new Snail4jException(e);
		}		
	}
}
