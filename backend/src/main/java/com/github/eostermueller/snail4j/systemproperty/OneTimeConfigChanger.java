package com.github.eostermueller.snail4j.systemproperty;

import com.github.eostermueller.snail4j.Snail4jException;
import com.github.eostermueller.snail4j.launcher.Configuration;

public interface OneTimeConfigChanger {
	void oneTimeChange(Configuration cfg) throws Snail4jException;
}
