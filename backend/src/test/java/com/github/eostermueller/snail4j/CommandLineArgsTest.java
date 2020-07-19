package com.github.eostermueller.snail4j;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CommandLineArgsTest {

	@Test
	void canDetectForceLaunchArg() {
		CommandLineArgs args = CommandLineArgs.create( new String[] { CommandLineArgs.FORCE_LAUNCH} );
		assertTrue (args.isForceLaunch());
		
		args = CommandLineArgs.create(new String[]{ });
		assertFalse (args.isForceLaunch() );
		
		args = CommandLineArgs.create(new String[] { "foo","bar"});
		assertFalse (args.isForceLaunch());
		
		args = CommandLineArgs.create( new String[] { "--ForceLAUNCH" } );
		assertTrue (args.isForceLaunch());
	}

}
