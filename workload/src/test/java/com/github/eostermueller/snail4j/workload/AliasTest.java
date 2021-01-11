package com.github.eostermueller.snail4j.workload;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AliasTest {

	@Test
	void canFindAliasWithMatchingCase() {
		PropertyIgnoreCase aliases = new PropertyIgnoreCase();
		aliases.setProperty("foo", "bar");
		
		assertEquals( "bar", aliases.getPropertyIgnoreCase("foo")); //trivial case, added as "foo", retrieved using same case (all lowers -- f-o-o
		
	}
	
	@Test
	void canFindAliasWithCaseInsensitiveLookup() {
		PropertyIgnoreCase aliases = new PropertyIgnoreCase();
		aliases.setProperty("FOO", "bar");
		
		assertEquals( "bar", aliases.getPropertyIgnoreCase("foo")); 
		
	}

}
