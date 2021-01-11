package com.github.eostermueller.snail4j.workload;

import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

/**
 * @stolenFrom: http://www.javafundu.com/2010/06/propertyignorecase.html
 * @author eoste
 *
 */
public class PropertyIgnoreCase extends Properties {

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
		 if (key==null) {
			 return null;
		 }
		 
		  String value = null;
	 try {
		 value = getProperty(key);
	 } catch (Exception e) {
		 
	 }
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