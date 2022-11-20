package com.github.eostermueller.snail4j;

/**
 * should be enhanced to display all -D parameters with some doc.
 * @author eoste
 *
 */
public class DefaultNonPersistentParameters implements NonPersistentParameters {

	/**
	 * Deletes the entire $HOME/.load-test-in-a-box/sutApp or %USERPROFILE%\.load-test-in-a-box\sutApp folder, depending on operating system.
	 */
	public static final String DELETE_SUT_DASH_D_PARAMETER 					= "com.github.eostermueller.snail4j.deleteSut";
	
	/**
	 * Requires internet access.
	 * At snail4j uber jar startup, clones the git repo at the given link into $HOME/.load-test-in-a-box/sutApp or %USERPROFILE%\.load-test-in-a-box\sutApp folder, depending on operating system.
	 * The target sutApp folder must be deleted for this to work.
	 * Sample value:  https://github.com/eostermueller/tjp2.git
	 */
	public static final String SUT_GIT_CLONE_REMOTE_URL_DASH_D_PARAMETER 	= "com.github.eostermueller.snail4j.sutGitCloneUrl";
	private boolean deleteSut = false;
	private String sutGitCloneRemoteUrl;
	
	/**
	 * Should override this (and pre-load test-inputs) for unit tests.
	 * @param propertyName
	 * @return
	 */
	protected String getProperty(String propertyName) {
		 return System.getProperty(propertyName);
	}
	public DefaultNonPersistentParameters() {
		
		this.deleteSut = getBooleanParameter(DELETE_SUT_DASH_D_PARAMETER);
		this.sutGitCloneRemoteUrl = getProperty(SUT_GIT_CLONE_REMOTE_URL_DASH_D_PARAMETER);
		
	}
	private boolean getBooleanParameter(String dashDVariableName) {
		boolean rc = false;
		String value = getProperty(dashDVariableName);
		if (value!=null && "true".equals(value.toLowerCase().trim())) {
			rc = true;
		}
		return rc;
	}
	@Override
	public boolean deleteSut() {
		return this.deleteSut;
	}

	@Override
	public String sutGitCloneRemoteUrl() {
		return this.sutGitCloneRemoteUrl;
	}

}
