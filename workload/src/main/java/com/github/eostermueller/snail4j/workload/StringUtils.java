package com.github.eostermueller.snail4j.workload;

public class StringUtils {

	/**
	 * Nvp=Name Value Pair
	 * 
	 * @param nameValuePair
	 * @return
	 * @throws Snail4jWorkloadException
	 */
	public static String getNvpValue(String nameValuePair) throws Snail4jWorkloadException {
		
		int equalSignIndex = nameValuePair.indexOf('=');
		
		if (equalSignIndex <0) {
			throw new Snail4jWorkloadException("Expected an equal sign in this name-value-pair:" + nameValuePair);
		}
		
		
		return nameValuePair.substring(equalSignIndex+1);
	}

}
