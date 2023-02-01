package com.github.eostermueller.snail4j.util;

import com.github.eostermueller.snail4j.DefaultFactory;
import com.github.eostermueller.snail4j.Snail4jException;

public class StringUtils {

	/**
	 * Ensure the first character of string is lower case
	 * @stolenFrom: https://stackoverflow.com/a/853044/2377579 and https://commons.apache.org/proper/commons-lang/javadocs/api-3.9/org/apache/commons/lang3/StringUtils.html#uncapitalize-java.lang.String-
	 * @param str
	 * @return
	 */
	public static String uncapitalize(String str) {
	    int strLen;
	    if (str == null || (strLen = str.length()) == 0) {
	        return null;
	    }
	    return new StringBuffer(strLen)
	        .append(Character.toLowerCase(str.charAt(0)))
	        .append(str.substring(1))
	        .toString();
	}	

	
	public static String getSpringHealthIndicatorName(String classNameWithoutPackage) {
		String rc = null;
		if (classNameWithoutPackage!=null && classNameWithoutPackage.length() > 0) {
			int indexOfHealthIndicator = classNameWithoutPackage.indexOf("HealthIndicator");
			if (indexOfHealthIndicator > 0) {
				String withoutHealthIndicator = classNameWithoutPackage.substring(0, indexOfHealthIndicator);
				if (withoutHealthIndicator !=null) {
					rc = uncapitalize(withoutHealthIndicator);
				}
			}
		}
		return rc;
	}


	/**
	 * The method java.lang.Boolean.parseBoolean("falseN") returns false....that's not acceptable because a user needs to know 
	 * that they should correct "falseN" (nonsensical for bool) with "false"
	 * This method will throw an exception unless it gets "true" or "false". 
	 * @param trueOrFalse
	 * @return
	 * @throws Snail4jException
	 */
	public static boolean parseTrueOrFalse(String trueOrFalse) throws Snail4jException {
		boolean rc = false;
		
		if (trueOrFalse !=null && trueOrFalse.length() > 0) {
			String cleaned = trueOrFalse.trim().toLowerCase();
			
			if ( "true".equals(cleaned))
				rc = true;
			else if ( "false".equals(cleaned))
				rc = false;
			else {
				String m = DefaultFactory.getFactory().getMessages().wasExpectingTrueOrFalse(trueOrFalse);
				throw new Snail4jException(m);
			}
			
		} else {
			String m = DefaultFactory.getFactory().getMessages().wasExpectingTrueOrFalse(trueOrFalse);
			throw new Snail4jException(m);
		}
		return rc;
	}
}
