package com.github.eostermueller.snail4j.install;

import com.github.eostermueller.snail4j.Context;
import com.github.eostermueller.snail4j.Snail4jException;
import com.github.eostermueller.snail4j.launcher.Configuration;

public interface Installer  {
	public static String INSTALL_ROOT = ".load-test-in-a-box";
	public void install() throws InstallException, Snail4jException;
}
