package com.github.eostermueller.snail4j.workload;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultAliasManager implements AliasManager {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
	private static final String FILE_NAME="alias.properties";
	PropertyIgnoreCase aliases = new PropertyIgnoreCase();
	
	@Override
	public String resolve(String alias) {
		
		return aliases.getPropertyIgnoreCase(alias);
	}
	@Override()
	public int size() {
		int count = 0;
		Enumeration<?> enumeration =aliases.propertyNames();
		while (enumeration.hasMoreElements()) {
			String key = (String) enumeration.nextElement();
			LOGGER.debug("Key: "+key+"   Value: "+aliases.getProperty(key));
			count++;
		}
		return count;
	}
		
	@Override
	public int load() {
		int count = 0;
		try {
			InputStream in = this.getClass().getClassLoader().getResourceAsStream(FILE_NAME);
			aliases.load(in);
			in.close();
			//Print all value from property file
			Enumeration<?> enumeration =aliases.propertyNames();
			while (enumeration.hasMoreElements()) {
				String key = (String) enumeration.nextElement();
				LOGGER.debug("Key: "+key+"   Value: "+aliases.getProperty(key));
				count++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return count;
		
	}

}

/**
 * @stolenFrom: http://www.javafundu.com/2010/06/propertyignorecase.html
 * @author eoste
 *
 */
class PropertyIgnoreCase extends Properties {

	 private static final long serialVersionUID = 7511088737858527084L;

	 /**
	  * get value from {Properties}
	  * 
	  * @param props
	  * @param key
	  * @return
	  */
	 public String getPropertyIgnoreCase(String key) {
	  return getPropertyIgnoreCase(key, null);
	 }

	 /**
	  * get value from {Properties}, if no key exist then return default value.
	  * 
	  * @param props
	  * @param key
	  * @param defaultV
	  * @return
	  */
	 public String getPropertyIgnoreCase(String key, String defaultV) {
	  String value = getProperty(key);
	  if (null != value)
	   return value;

	  // Not matching with the actual key then
	  Set<Entry<Object, Object>> s = entrySet();
	  Iterator<Entry<Object, Object>> it = s.iterator();
	  while (it.hasNext()) {
	   Entry<Object, Object> entry = it.next();
	   if (key.equalsIgnoreCase((String) entry.getKey())) {
	    return (String) entry.getValue();
	   }
	  }
	  return defaultV;
	 }
}