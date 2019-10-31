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

	private Configuration cfg = null;
	private File folder;
	private String fileName = null;
	
	   @Override
	public String getFileName() {
		return fileName;
	}
	   @Override
	public void setFileName(String val) {
		this.fileName = val;
	}
	public File getExtendedPath() {
		return new File(folder,getFileName() );
	}
	
	public DefaultConfigReaderWriter(Configuration cfg, File tmpFolder) {
		this.cfg = cfg;
		this.folder = tmpFolder;
		this.setFileName("snail4j.json");
	}
	
	   @Override
	public void write() throws Snail4jException {
		      ObjectMapper mapper = new ObjectMapper();	
		      mapper.enable(SerializationFeature.INDENT_OUTPUT);
		      try {
				mapper.writeValue(getExtendedPath(), this.cfg);
			} catch (IOException e) {
				throw new Snail4jException(e);
			}
		   }

   @Override
	public Configuration read() throws Snail4jException {
	      ObjectMapper mapper = new ObjectMapper();
	      
	       Configuration cfg;
		try {
			cfg = mapper.readValue( this.getExtendedPath(), DefaultFactory.getFactory().getConfiguration().getClass() );
		} catch (CannotFindTjpFactoryClass | IOException e) {
			throw new Snail4jException(e);
		}
	      return cfg;
	   }	

}
