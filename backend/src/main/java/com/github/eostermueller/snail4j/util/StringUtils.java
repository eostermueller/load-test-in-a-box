package com.github.eostermueller.snail4j.util;

public class StringUtils {

	/**
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
}
