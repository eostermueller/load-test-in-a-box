package com.github.eostermueller.snail4j;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;
import com.github.eostermueller.snail4j.util.StringUtils;




public class StringUtilsTest {

	@Test
	public void canUncapitalizeFirstLetterOfWord() {
		
		String rc = StringUtils.uncapitalize("Joanie");
		assertEquals("joanie",rc);
		
		rc = StringUtils.uncapitalize("joanie");
		assertEquals("joanie",rc);
		
	}
	@Test
	public void canUncapitalizeFailCorrectly() {
		
		String rc = StringUtils.uncapitalize(null);
		assertNull(rc);

		rc = StringUtils.uncapitalize("");
		assertNull(rc);
	
	}
	@Test
	public void canDeriveSpringHealthIndicatorName() {
		String rc = StringUtils.getSpringHealthIndicatorName("H2HealthIndicator");
		assertEquals("h2",rc);

		rc = StringUtils.getSpringHealthIndicatorName("SutAppHealthIndicator");
		assertEquals("sutApp",rc);
		
	}
	@Test
	public void canFailDerivingSpringHealthIndicatorName() {
		String rc = StringUtils.getSpringHealthIndicatorName("");
		assertNull(rc);

		rc = StringUtils.getSpringHealthIndicatorName(null);
		assertNull(rc);
		
	}
}
