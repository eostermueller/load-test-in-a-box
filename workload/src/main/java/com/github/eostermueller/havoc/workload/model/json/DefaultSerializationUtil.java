package com.github.eostermueller.havoc.workload.model.json;


import com.github.eostermueller.havoc.workload.HavocException;
import com.github.eostermueller.havoc.workload.model.UseCases;
import com.github.eostermueller.havoc.workload.model.WorkloadSpecRq;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

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

	private Gson getGson() {
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		
		return gson;
	}
	

	@Override
	public String marshalUseCases(UseCases useCases) {
		return getGson().toJson(useCases);
	}

	@Override
	public UseCases unmmarshalUseCases(String json) throws HavocException {
		UseCases useCases 
		= this.getGson().fromJson(
			json,
			UseCases.class
				); 
		return useCases;
	}


	@Override
	public WorkloadSpecRq unmmarshalWorkloadSpecRq(String json) throws JsonSyntaxException, HavocException {
		WorkloadSpecRq rq 
		= this.getGson().fromJson(
			json,
			WorkloadSpecRq.class
				); 
		return rq;
	}
	
	@Override
	public String marshalWorkloadSpecRq(WorkloadSpecRq rq) throws JsonSyntaxException, HavocException {
		return getGson().toJson(rq);
	}

}
