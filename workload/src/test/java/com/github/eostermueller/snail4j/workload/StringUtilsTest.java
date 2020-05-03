package com.github.eostermueller.snail4j.workload;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class StringUtilsTest {

	@Test
	void canParseValueFromNameValuePair() throws Snail4jWorkloadException {
		String nameValuePair="foo=bar";
		
		String value = StringUtils.getNvpValue(nameValuePair);
		
		assertEquals("bar",value);
	}
	@Test
	void canReturnEmptyValue() throws Snail4jWorkloadException {
		String nameValuePair="foo=";
		
		String value = StringUtils.getNvpValue(nameValuePair);
		
		assertEquals("",value);
	}
	@Test
	void canDetectBadNameValuePairWithoutEqualSign() {
		String nameValuePair="foo-bar";
		
		String value = null;
		try {
			value = StringUtils.getNvpValue(nameValuePair);
			fail("was expecting an exception triggered by poorly formatted name-value-pair (missing equal size)");
		}catch (Snail4jWorkloadException e) {
			
		}
		
	}

}
