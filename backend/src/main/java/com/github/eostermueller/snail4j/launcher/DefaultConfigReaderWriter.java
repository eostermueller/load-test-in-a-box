package com.github.eostermueller.snail4j.launcher;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.eostermueller.snail4j.DefaultFactory;
import com.github.eostermueller.snail4j.Snail4jException;

/**
 * Sample configuration file.
 * Note the "file:///" prefix for all the subdirectories!!
 * <pre>
 * 
{
  "mavenHome" : "file:///foo",
  "userHomeDir" : "file:///Users/erikostermueller/",
  "maxExceptionCountPerEvent" : 8675309,
  "mavenZipFileNameWithoutExtension" : "foo",
  "sutHome" : "file:///foo",
  "perfGoatHome" : "file:///foo"
} 
* </pre
 * @author erikostermueller
 *
 */
public class DefaultConfigReaderWriter implements ConfigReaderWriter {

	   @Override
	public void write(File configFile, Configuration cfg) throws Snail4jException {
		      ObjectMapper mapper = new ObjectMapper();	
		      mapper.enable(SerializationFeature.INDENT_OUTPUT);
		      try {
				mapper.writeValue(configFile, cfg);
			} catch (IOException e) {
				throw new Snail4jException(e);
			}
		   }

   @Override
	public Configuration read(File configFile, Class cfgClass) throws Snail4jException {
	      ObjectMapper mapper = new ObjectMapper();
	      
	       Configuration cfg;
		try {
			cfg = (Configuration) mapper.readValue( configFile, cfgClass );
		} catch (IOException e) {
			throw new Snail4jException(e);
		}
	      return cfg;
	   }

   @Override
   public Configuration toObject(String json, Class cfgClass) throws Snail4jException {
	      ObjectMapper mapper = new ObjectMapper();
	      
	       Configuration cfg;
		try {
			cfg = (Configuration) mapper.readValue( json, cfgClass );
		} catch (IOException e) {
			throw new Snail4jException(e);
		}
	      return cfg;
   }
   @Override
	public String toJson(Configuration configuration) throws Snail4jException {
		String rc = null;
	    ObjectMapper mapper = new ObjectMapper();	
	    mapper.enable(SerializationFeature.INDENT_OUTPUT);
	    try {
			rc = mapper.writeValueAsString(configuration);
		} catch (IOException e) {
			throw new Snail4jException(e);
		}
	    return rc;
	}	

}
