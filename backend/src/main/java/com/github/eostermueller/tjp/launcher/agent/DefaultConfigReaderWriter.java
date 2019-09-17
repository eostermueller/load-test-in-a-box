package com.github.eostermueller.tjp.launcher.agent;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.eostermueller.havoc.PerfGoatException;

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
	private String getFileName() {
		return "perfGoat.json";
	}
	public File getExtendedPath() {
		return new File(folder,getFileName() );
	}
	
	public DefaultConfigReaderWriter(Configuration cfg, File tmpFolder) {
		this.cfg = cfg;
		this.folder = tmpFolder;
	}
	
	   @Override
	public void write() throws PerfGoatException {
		      ObjectMapper mapper = new ObjectMapper();	
		      mapper.enable(SerializationFeature.INDENT_OUTPUT);
		      try {
				mapper.writeValue(getExtendedPath(), this.cfg);
			} catch (IOException e) {
				throw new PerfGoatException(e);
			}
		   }

   @Override
	public Configuration read() throws PerfGoatException {
	      ObjectMapper mapper = new ObjectMapper();
	      
	       Configuration cfg;
		try {
			cfg = mapper.readValue( this.getExtendedPath(), DefaultFactory.getFactory().getConfiguration().getClass() );
		} catch (CannotFindTjpFactoryClass | IOException e) {
			throw new PerfGoatException(e);
		}
	      return cfg;
	   }	

}
