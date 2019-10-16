package com.github.eostermueller.snail4j.launcher;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.eostermueller.snail4j.Snail4jException;

/**
 * Given a string variable name from configuration, return return the corresponding getter value from the Configuration class.
 * @st0lenFr0m: https://www.baeldung.com/jackson-json-node-tree-model
 */
public class DefaultConfigLookup implements ConfigLookup {

	private static final String FILE_PROTOCOL = "file://";
	private String unresolvedText = null;
	private Configuration cfg;
	JsonNode rootNode = null;
	
	@Override
	public void setConfiguration(Configuration val) {
		this.cfg = val;
	    ObjectMapper mapper = new ObjectMapper();	
		 
	    rootNode = mapper.valueToTree(this.cfg);
	}

	@Override
	public String getValue(String variableName) throws ConfigVariableNotFoundException, Snail4jException {
		JsonNode myNode = rootNode.get(variableName);
		String rc = null;
		if (myNode==null) {
			String msg = DefaultFactory.getFactory().getMessages().noValueForVariable(variableName,this.getAllFieldNames());
			ConfigVariableNotFoundException e = new ConfigVariableNotFoundException(msg);
			e.setVariableName(variableName);
			e.setAllVariableNames(this.getAllFieldNames());
			throw e;
		} else {
			rc = myNode.asText();
		}
		
		if (rc.startsWith(FILE_PROTOCOL))
			rc = rc.substring(FILE_PROTOCOL.length());
		
		
		if (rc.endsWith("/"))
			rc = rc.substring(0, rc.length()-1);
		
		return rc.trim();
	}

	@Override
	public List<String> getAllFieldNames() {
		List<String> alFieldNames = new ArrayList<String>();
		
		Iterator<String> fieldNames = rootNode.fieldNames();

		while(fieldNames.hasNext()) {
		    alFieldNames.add( fieldNames.next() );
		}
		return alFieldNames;
	}

}
