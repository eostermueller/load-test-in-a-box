package com.github.eostermueller.havoc.workload.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

public class Description {
	Map<String,String> desciptions = new HashMap<String,String>();
	
	/**
	 * https://www.baeldung.com/jackson-annotations
	 * for de-serializaition
	 * @param key
	 * @param value
	 */
	@JsonAnySetter
    public void addDescription(String key, String value) {
        this.desciptions.put(key, value);
    }
	
	
	@JsonAnyGetter
    public Map<String, String> getDescriptions() {
        return this.desciptions;
    }	
	public String getDescription(String local_with_underscore) {
		return this.getDescriptions().get(local_with_underscore);
	}

}
