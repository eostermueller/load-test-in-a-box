package com.github.eostermueller.tjp.launcher.agent;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.github.eostermueller.havoc.VariableTokenizer;

/**
 * @author erikostermueller
 *
 */
public class VariableTokenizerTest {

	@Test
	public void canDetectValidJavaName() {
		String str  ="helloWorld";
		VariableTokenizer tokenizer = new VariableTokenizer(str);
	    assertTrue(tokenizer.validJavaName(str));
	}
	@Test
	public void canDetectInvalidJavaName_firstDash() {
		String str  ="-helloWorld";
		VariableTokenizer tokenizer = new VariableTokenizer(str);
	    assertFalse(tokenizer.validJavaName(str));
	}
	@Test
	public void canDetectInvalidJavaName_firstNum() {
		String str  ="4helloWorld";
		VariableTokenizer tokenizer = new VariableTokenizer(str);
	    assertFalse(tokenizer.validJavaName(str));
	}
	@Test
	public void canDetectValidVariable() {
		VariableTokenizer tokenizer = new VariableTokenizer(null);
	    assertTrue(tokenizer.isVariableNameWithPunctuation("#{helloWorld}"));
	    assertTrue(tokenizer.isVariableNameWithPunctuation("#{helloWorld3}"));
	    assertTrue(tokenizer.isVariableNameWithPunctuation("#{helloWorld_}"));
	    assertTrue(tokenizer.isVariableNameWithPunctuation("#{h}"));
	}
	
	@Test
	public void canDetectInvalidVariable_01() {
		VariableTokenizer tokenizer = new VariableTokenizer(null);
	    assertFalse(tokenizer.isVariableNameWithPunctuation(null));
	    assertFalse(tokenizer.isVariableNameWithPunctuation(""));
	    assertFalse(tokenizer.isVariableNameWithPunctuation("#{}"));
	    assertFalse(tokenizer.isVariableNameWithPunctuation("{helloWorld}"));
	    assertFalse(tokenizer.isVariableNameWithPunctuation("{helloWorld"));
	    assertFalse(tokenizer.isVariableNameWithPunctuation("helloWorld}"));
	    assertFalse(tokenizer.isVariableNameWithPunctuation("#{-helloWorld}"));
	    assertFalse(tokenizer.isVariableNameWithPunctuation("{3helloWorld}"));
	    assertFalse(tokenizer.isVariableNameWithPunctuation("{.helloWorld}"));
	}
	
	@Test
	public void canDoNoHarm() {
		String str  ="hello world";
		VariableTokenizer tokenizer = new VariableTokenizer(str);
	    List<String> rc = tokenizer.split();
	    assertEquals(str,rc.get(0));
	}
	@Test
	public void canDoNoHarm_justWhitespace() {
		String str  =" \n";
		VariableTokenizer tokenizer = new VariableTokenizer(str);
		List<String> rc = tokenizer.split();
	    
	    assertEquals(str,rc.get(0));
	    
	}
	@Test
	public void canDoNoHarm_emptyString() {
		String str  ="";
		VariableTokenizer tokenizer = new VariableTokenizer(str);
		List<String> rc = tokenizer.split();
	    assertEquals(str,rc.get(0));
	}
	@Test
	public void canLocateSimpleVariable_spaceIsPreserved() {
		String str  ="hello #{world}";
		VariableTokenizer tokenizer = new VariableTokenizer(str);
		List<String> rc = tokenizer.split();
	    
		assertEquals("hello ",rc.get(0));
	    assertEquals("#{world}",rc.get(1));
	    	
	}
	@Test
	public void canLocateSimpleVariableWithoutSpaces() {
		String str  ="hello#{world}";
		VariableTokenizer tokenizer = new VariableTokenizer(str);
		List<String> rc = tokenizer.split();
		assertEquals("hello",rc.get(0));
	    assertEquals("#{world}",rc.get(1));
	}
	@Test
	public void canLocateTwoVariables() {
		String str  ="#{hello}#{world}";
		VariableTokenizer tokenizer = new VariableTokenizer(str);
		List<String> rc = tokenizer.split();
		assertEquals("#{hello}",rc.get(0));
	    assertEquals("#{world}",rc.get(1));
	}
	@Test
	public void canLocateTwoVariablesAmidstWords() {
		String str  ="abc#{hello}def#{world}ghi";
		VariableTokenizer tokenizer = new VariableTokenizer(str);
		List<String> rc = tokenizer.split();
		assertEquals("abc",rc.get(0));
		assertEquals("#{hello}",rc.get(1));
		assertEquals("def",rc.get(2));
	    assertEquals("#{world}",rc.get(3));
	    assertEquals("ghi",rc.get(4));
	}
	/**
	 * Take some time to understand one.
	 * The primary objective was achieved!!! 
	 * Insure the two golden nuggets show up in their own buckets!!!
	 * all the other splilt tokens will need to be stitched together by the user of this class!!!!!
	 */
	@Test
	public void canLocateTwoVariablesWithPunctuation() {
		String str  =".#!3&)(#{hello}~`!@##%/\\#{world}\'\'<>";
		VariableTokenizer tokenizer = new VariableTokenizer(str);
		List<String> rc = tokenizer.split();
		assertEquals("."		,rc.get(0));
		assertEquals("#"		,rc.get(1));
		assertEquals("!3&)("	,rc.get(2));
		assertEquals("#{hello}"	,rc.get(3));    //  <<<<<<========  Golden Nugget!!!
		assertEquals("~`!@"		,rc.get(4));
		assertEquals("#"		,rc.get(5));
		assertEquals("#"		,rc.get(6));
		assertEquals("%/\\"		,rc.get(7));
		assertEquals("#{world}"	,rc.get(8));    //  <<<<<<========  Golden Nugget!!!
		assertEquals("\'\'<>"	,rc.get(9));
	    
	}

}
