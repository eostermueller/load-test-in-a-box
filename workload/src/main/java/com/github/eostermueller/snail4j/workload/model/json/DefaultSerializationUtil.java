package com.github.eostermueller.snail4j.workload.model.json;


import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.eostermueller.snail4j.workload.Snail4jWorkloadException;
import com.github.eostermueller.snail4j.workload.model.UseCases;
import com.github.eostermueller.snail4j.workload.model.WorkloadSpecRq;

/**
 * Why I chose gson:
 * "If your environment primarily deals with lots of small JSON requests, 
 * such as in a micro services or distributed architecture setup, then GSON is your library of interest. 
 * Jackson struggles the most with small files."
 * 
 * the above is take from here:
 * 	https://blog.overops.com/the-ultimate-json-library-json-simple-vs-gson-vs-jackson-vs-json/
 * @author erikostermueller
 *
 */
public class DefaultSerializationUtil implements SerializaionUtil {

	private ObjectMapper  getMapper() {
		ObjectMapper mapper = new ObjectMapper();
		return mapper;
	}
	

	@Override
	public String marshalUseCases(UseCases useCases) throws Snail4jWorkloadException {
		String rc = null;
		try {
			rc = this.getMapper().writeValueAsString(useCases);
		} catch (JsonProcessingException e) {
			Snail4jWorkloadException he = new Snail4jWorkloadException(e,"Unable to serialize UseCases");
			throw he;
		}
		return rc;
	}

	@Override
	public UseCases unmmarshalUseCases(String json) throws Snail4jWorkloadException {
		UseCases useCases = null;
		try {
			useCases = this.getMapper()			      
				.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
				.readerFor(UseCases.class)
				.readValue(json);

		} catch (IOException e) {
			Snail4jWorkloadException he = new Snail4jWorkloadException(e,"Unable to unmarshal UseCases");
			throw he;
		}

		return useCases;
	}


	@Override
	public WorkloadSpecRq unmmarshalWorkloadUpdateRq(String json) throws Snail4jWorkloadException {
		
		String myJs0n = json.trim();
		
		
		if (myJs0n.startsWith("[") && myJs0n.endsWith("]") ) { //String off wrapping square braces
			myJs0n = myJs0n.substring(1, myJs0n.length()-1 );
		}
				
		WorkloadSpecRq rq = null;
		try {
			rq = this
				.getMapper()
				.readerFor(WorkloadSpecRq.class)
				.readValue(json);
		} catch (IOException e) {
			Snail4jWorkloadException he = new Snail4jWorkloadException(e,"Unable to unmarshal WorkloadSpecRq");
			throw he;
		}
			 
		return rq;
	}
	
	@Override
	public String marshalWorkloadSpecRq(WorkloadSpecRq rq) throws Snail4jWorkloadException {
		try {
			return this.getMapper().writeValueAsString(rq);
		} catch (JsonProcessingException e) {
			Snail4jWorkloadException he = new Snail4jWorkloadException(e,"Unable to unmarshal WorkloadSpecRq");
			throw he;
		}
	}

}
