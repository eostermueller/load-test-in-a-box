package com.github.eostermueller.havoc.workload.model.json;

import com.github.eostermueller.havoc.workload.HavocException;
import com.github.eostermueller.havoc.workload.model.MethodWrapper;
import com.github.eostermueller.havoc.workload.model.ProcessingUnitImpl;
import com.github.eostermueller.havoc.workload.model.UseCases;
import com.google.gson.JsonSyntaxException;

public interface SerializaionUtil {


	UseCases unmmarshal(String json) throws JsonSyntaxException, HavocException;

	String marshal(UseCases useCases);


}
