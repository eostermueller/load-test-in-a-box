package com.github.eostermueller.snail4j.systemproperty;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.github.eostermueller.snail4j.Snail4jException;
/**
 * Enables JUnit classes to build tests that validate 'production' functionality dependent upon JVM "-D" (aka 'dash D') parameters.
 * JUnit tests simulate setting "-D" parameters by calling methods on this interface.
 * The SystemPropertyManagerImpl is responsible for discovering when these are set in a junit test.
 * Since this interface and it's only implementing class are in src/test/java, these test values will never make it into 'production' processing.
 * 
 * Once a test instantiates this and sets values on it, the test must make those values available to the code base
 * by passing the test instance into DefaultFactory.getFactory()
 * @author eoste
 *
 */

public class SystemPropertyTestValueRepoImpl implements SystemPropertyManager, SystemPropertyTestValueRepo {
	
	/**
	 * long "-D System Parameters" used by JUnit tests get stored here, with the "-D propername" as the map's key.
	 */
	private Map<String,Long> longSystemProperties = new ConcurrentHashMap<String,Long>();
	
	/**
	 * boolean "-D System Parameters" used by JUnit tests get stored here, with the "-D propername" as the map's key.
	 */
	private Map<String,Boolean> booleanSystemProperties = new ConcurrentHashMap<String,Boolean>();
	
	/**
	 * String "-D System Parameters" used by JUnit tests get stored here, with the "-D propername" as the map's key.
	 */
	private Map<String,String> stringSystemProperties = new ConcurrentHashMap<String,String>();
	
	@Override
	public boolean getBoolean(BooleanSystemProperty systemProperty) throws Snail4jException {
		boolean rc;
		
		if (booleanSystemProperties.containsKey(systemProperty.getDashDProperty()))
			rc = booleanSystemProperties.get(systemProperty.getDashDProperty()); 
		else
			rc = systemProperty.getDefaultValue();
		
		return rc;
	}

	@Override
	public long getLong(LongSystemProperty systemProperty) throws Snail4jException {
		long rc;
		
		if (longSystemProperties.containsKey(systemProperty.getDashDProperty()))
				rc = longSystemProperties.get(systemProperty.getDashDProperty());
		else
			rc = systemProperty.getDefaultValue();
		
		return rc;
	}

	@Override
	public String getString(StringSystemProperty systemProperty) {
		String rc;
		
		if (stringSystemProperties.containsKey(systemProperty.getDashDProperty()))
			rc = stringSystemProperties.get(systemProperty.getDashDProperty());
		else
			rc = systemProperty.getDefaultValue();
		
		return rc;
	}
/**/
	@Override
	public void setBoolean(BooleanSystemProperty systemProperty, boolean val) throws Snail4jException {
		booleanSystemProperties.put(systemProperty.getDashDProperty(),new Boolean(val) );
	}

	@Override
	public void setLong(LongSystemProperty systemProperty, long val) throws Snail4jException {
		longSystemProperties.put(systemProperty.getDashDProperty(),new Long(val));
	}

	@Override
	public void setString(StringSystemProperty systemProperty, String s) {
		stringSystemProperties.put(systemProperty.getDashDProperty(), s);
	}
	
}
