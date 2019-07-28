package com.github.eostermueller.havoc.workload.model.json;


import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.eostermueller.havoc.workload.HavocException;
import com.github.eostermueller.havoc.workload.model.UseCases;
import com.github.eostermueller.havoc.workload.model.WorkloadSpecRq;

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
	public String marshalUseCases(UseCases useCases) throws HavocException {
		String rc = null;
		try {
			rc = this.getMapper().writeValueAsString(useCases);
		} catch (JsonProcessingException e) {
			HavocException he = new HavocException(e,"Unable to serialize UseCases");
			throw he;
		}
		return rc;
	}

	@Override
	public UseCases unmmarshalUseCases(String json) throws HavocException {
		UseCases useCases = null;
		try {
			useCases = this.getMapper()			      
				.readerFor(UseCases.class)
				.readValue(json);
		} catch (IOException e) {
			HavocException he = new HavocException(e,"Unable to unmarshal UseCases");
			throw he;
		}

		return useCases;
	}


	@Override
	public WorkloadSpecRq unmmarshalWorkloadUpdateRq(String json) throws HavocException {
		
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
			HavocException he = new HavocException(e,"Unable to unmarshal WorkloadSpecRq");
			throw he;
		}
			 
		return rq;
	}
	
	@Override
	public String marshalWorkloadSpecRq(WorkloadSpecRq rq) throws HavocException {
		try {
			return this.getMapper().writeValueAsString(rq);
		} catch (JsonProcessingException e) {
			HavocException he = new HavocException(e,"Unable to unmarshal WorkloadSpecRq");
			throw he;
		}
	}

}
