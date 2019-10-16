package com.github.eostermueller.snail4j.launcher;

public enum State {
	START_IN_PROGRESS, 
	STARTED, 
	PROCESSING_ITEM,
	STOP_IN_PROGRESS, 
	STOPPED, 
	ABEND /* ended due to error */
}
