package com.github.eostermueller.snail4j;


import java.io.IOException;

import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.eostermueller.snail4j.util.JsonPatchUtils;
import com.github.fge.jsonpatch.JsonPatchException;

public class JsonPatchTests {
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(JsonPatchTests.class);

	/**
	 * all json taken from 
	 * https://github.com/json-patch/json-patch-tests/blob/master/spec_tests.json
	 * @throws Exception
	 */
	@Test
	void testReplace() throws Exception {
		
		String src = "{\r\n" + 
				"  \"baz\": \"qux\",\r\n" + 
				"  \"foo\": \"bar\"\r\n" + 
				"}";
		String patch = "[\r\n" + 
				"  { \"op\": \"replace\", \"path\": \"/baz\", \"value\": \"boo\" }\r\n" + 
				"]";
		
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode srcNode = objectMapper.readTree(src);		
		JsonNode patchNode = objectMapper.readTree(patch);		

		JsonPatchUtils utils = new JsonPatchUtils();
		
		JsonNode actual = utils.patch(srcNode,patchNode);

		String expected = "{\r\n" + 
				"  \"baz\": \"boo\",\r\n" + 
				"  \"foo\": \"bar\"\r\n" + 
				"}";
		
		JSONAssert.assertEquals(actual.toString(), expected.toString(),false );
		
	}
	/**
	 * all json taken from 
	 * https://github.com/json-patch/json-patch-tests/blob/master/spec_tests.json
	 * @throws JSONException 
	 * @throws Exception
	 */
	@Test
	void confirmThatReplaceFailsWhenEleNotFound() throws JSONException {
		
		String src = "{\r\n" + 
				"  \"foo\": \"bar\"\r\n" + 
				"}";
		String patch = "[\r\n" + 
				"  { \"op\": \"replace\", \"path\": \"/baz\", \"value\": \"boo\" }\r\n" + 
				"]";
		
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode srcNode = null;
		JsonNode patchNode = null;
		JsonNode actual = null;
		try {
			srcNode = objectMapper.readTree(src);
			patchNode = objectMapper.readTree(patch);		

			JsonPatchUtils utils = new JsonPatchUtils();
			
			actual = utils.patch(srcNode,patchNode);
			Assertions.fail("Result of 'replace' should be JsonProcessingException when element is not found.");
		} catch (JsonProcessingException e) {
			Assertions.fail("Result of 'replace' should be JsonProcessingException when element is not found.");
		} catch (IOException e) {
			Assertions.fail("Result of 'replace' should be JsonProcessingException when element is not found.");
		} catch (JsonPatchException e) {
			//Success!!  patching a non-existing element should not be allowed
		}		

	}
	
	/**
	 * all json taken from 
	 * https://github.com/json-patch/json-patch-tests/blob/master/spec_tests.json
	 * @throws Exception
	 */

	@Test
	void testAdd() throws Exception {
		
		String src = "{\r\n" + 
				"  \"foo\": \"bar\"\r\n" + 
				"}";
		String patch = "[\r\n" + 
				"  { \"op\": \"add\", \"path\": \"/baz\", \"value\": \"boo\" }\r\n" + 
				"]";
		
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode srcNode = objectMapper.readTree(src);		
		JsonNode patchNode = objectMapper.readTree(patch);		

		JsonPatchUtils utils = new JsonPatchUtils();
		
		JsonNode actual = utils.patch(srcNode,patchNode);
		
		String expected = "{\r\n" + 
				"  \"baz\": \"boo\",\r\n" + 
				"  \"foo\": \"bar\"\r\n" + 
				"}";
		JSONAssert.assertEquals(actual.toString(), expected.toString(),false );
		
	}

	@Test 
	void canPatchGlowrootHostname_smallSizedFile() throws IOException, JSONException {
		JsonPatchUtils utils = new JsonPatchUtils();
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode srcNode = objectMapper.readTree(smallGlowrootAdminJsonFileWithQuadsOfZeroes);

		JsonNode fullyPatched = utils.setGlowrootBindAddress(srcNode,"127.0.0.1");
		JsonNode expected = objectMapper.readTree(smallGlowrootAdminJsonFileWith_127_0_0_1);
		JSONAssert.assertEquals(fullyPatched.toString(), expected.toString(),false );
	}
	@Test 
	void canPatchGlowrootHostname_smallSizedFileWithoutBindAddress() throws IOException, JSONException {
		JsonPatchUtils utils = new JsonPatchUtils();
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode srcNode = objectMapper.readTree(smallGlowrootAdminJsonFileWithoutBindAddress);

		JsonNode fullyPatched = utils.setGlowrootBindAddress(srcNode,"0.0.0.0");
		JsonNode expected = objectMapper.readTree(smallGlowrootAdminJsonFileWithoutBindAddress_expected);
		JSONAssert.assertEquals(fullyPatched.toString(), expected.toString(),false );
	}
	
	
	@Test 
	void canPatchGlowrootHostname_mediumSizedFile() throws IOException, JSONException {
		JsonPatchUtils utils = new JsonPatchUtils();
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode srcNode = objectMapper.readTree(glowrootAdminJsonFileWith_127_0_0_1);

		JsonNode fullyPatched = utils.setGlowrootBindAddress(srcNode,"0.0.0.0");
		JsonNode expected = objectMapper.readTree(glowrootAdminJsonFileWithQuadsOfZeroes);
		JSONAssert.assertEquals(fullyPatched.toString(), expected.toString(),false );
	}
	
	private static final String smallGlowrootAdminJsonFileWith_127_0_0_1 
		= "{\r\n"
				+ "  \"web\": {\r\n"
				+ "    \"bindAddress\": \"127.0.0.1\"\r\n"
				+ "  }\r\n"
				+ "}"; 
	private static final String smallGlowrootAdminJsonFileWithQuadsOfZeroes 
	= "{\r\n"
			+ "  \"web\": {\r\n"
			+ "    \"bindAddress\": \"0.0.0.0\"\r\n"
			+ "  }\r\n"
			+ "}"; 
	private static final String smallGlowrootAdminJsonFileWithoutBindAddress 
	= "{\r\n"
			+ "  \"web\": {\r\n"
			+ "    \"foo\": \"bar\"\r\n"
			+ "  }\r\n"
			+ "}"; 
	private static final String smallGlowrootAdminJsonFileWithoutBindAddress_expected 
	= "{\r\n"
			+ "  \"web\": {\r\n"
			+ "    \"foo\": \"bar\",\r\n"
			+ "    \"bindAddress\": \"0.0.0.0\"\r\n"
			+ "  }\r\n"
			+ "}"; 
	private static final String glowrootAdminJsonFileWith_127_0_0_1 
		= "{\r\n"
			+ "  \"users\": [\r\n"
			+ "    {\r\n"
			+ "      \"username\": \"anonymous\",\r\n"
			+ "      \"roles\": [\r\n"
			+ "        \"Administrator\"\r\n"
			+ "      ]\r\n"
			+ "    }\r\n"
			+ "  ],\r\n"
			+ "  \"roles\": [\r\n"
			+ "    {\r\n"
			+ "      \"name\": \"Administrator\",\r\n"
			+ "      \"permissions\": [\r\n"
			+ "        \"agent:transaction\",\r\n"
			+ "        \"agent:error\",\r\n"
			+ "        \"agent:jvm\",\r\n"
			+ "        \"agent:incident\",\r\n"
			+ "        \"agent:config\",\r\n"
			+ "        \"admin\"\r\n"
			+ "      ]\r\n"
			+ "    }\r\n"
			+ "  ],\r\n"
			+ "  \"web\": {\r\n"
			+ "    \"port\": 12675,\r\n"
			+ "    \"bindAddress\": \"0.0.0.0\",\r\n"
			+ "    \"contextPath\": \"/\",\r\n"
			+ "    \"sessionTimeoutMinutes\": 30,\r\n"
			+ "    \"sessionCookieName\": \"GLOWROOT_SESSION_ID\"\r\n"
			+ "  },\r\n"
			+ "  \"storage\": {\r\n"
			+ "    \"rollupExpirationHours\": [\r\n"
			+ "      72,\r\n"
			+ "      336,\r\n"
			+ "      2160,\r\n"
			+ "      2160\r\n"
			+ "    ],\r\n"
			+ "    \"traceExpirationHours\": 336,\r\n"
			+ "    \"fullQueryTextExpirationHours\": 336,\r\n"
			+ "    \"rollupCappedDatabaseSizesMb\": [\r\n"
			+ "      500,\r\n"
			+ "      500,\r\n"
			+ "      500,\r\n"
			+ "      500\r\n"
			+ "    ],\r\n"
			+ "    \"traceCappedDatabaseSizeMb\": 500\r\n"
			+ "  }\r\n"
			+ "}\r\n";
	
	
	private static final String glowrootAdminJsonFileWithQuadsOfZeroes
	= "{\r\n"
			+ "  \"users\": [\r\n"
			+ "    {\r\n"
			+ "      \"username\": \"anonymous\",\r\n"
			+ "      \"roles\": [\r\n"
			+ "        \"Administrator\"\r\n"
			+ "      ]\r\n"
			+ "    }\r\n"
			+ "  ],\r\n"
			+ "  \"roles\": [\r\n"
			+ "    {\r\n"
			+ "      \"name\": \"Administrator\",\r\n"
			+ "      \"permissions\": [\r\n"
			+ "        \"agent:transaction\",\r\n"
			+ "        \"agent:error\",\r\n"
			+ "        \"agent:jvm\",\r\n"
			+ "        \"agent:incident\",\r\n"
			+ "        \"agent:config\",\r\n"
			+ "        \"admin\"\r\n"
			+ "      ]\r\n"
			+ "    }\r\n"
			+ "  ],\r\n"
			+ "  \"web\": {\r\n"
			+ "    \"port\": 12675,\r\n"
			+ "    \"bindAddress\": \"0.0.0.0\",\r\n"
			+ "    \"contextPath\": \"/\",\r\n"
			+ "    \"sessionTimeoutMinutes\": 30,\r\n"
			+ "    \"sessionCookieName\": \"GLOWROOT_SESSION_ID\"\r\n"
			+ "  },\r\n"
			+ "  \"storage\": {\r\n"
			+ "    \"rollupExpirationHours\": [\r\n"
			+ "      72,\r\n"
			+ "      336,\r\n"
			+ "      2160,\r\n"
			+ "      2160\r\n"
			+ "    ],\r\n"
			+ "    \"traceExpirationHours\": 336,\r\n"
			+ "    \"fullQueryTextExpirationHours\": 336,\r\n"
			+ "    \"rollupCappedDatabaseSizesMb\": [\r\n"
			+ "      500,\r\n"
			+ "      500,\r\n"
			+ "      500,\r\n"
			+ "      500\r\n"
			+ "    ],\r\n"
			+ "    \"traceCappedDatabaseSizeMb\": 500\r\n"
			+ "  }\r\n"
			+ "}\r\n";
	
}
