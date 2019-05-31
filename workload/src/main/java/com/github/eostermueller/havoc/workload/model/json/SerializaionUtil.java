package com.github.eostermueller.havoc.workload.model.json;

import com.github.eostermueller.havoc.workload.HavocException;
import com.github.eostermueller.havoc.workload.model.MethodWrapper;
import com.github.eostermueller.havoc.workload.model.ProcessingUnitImpl;
import com.github.eostermueller.havoc.workload.model.UseCases;
import com.github.eostermueller.havoc.workload.model.WorkloadSpecRq;
import com.google.gson.JsonSyntaxException;

public interface SerializaionUtil {
	UseCases unmmarshalUseCases(String json) throws JsonSyntaxException, HavocException;
	String marshalUseCases(UseCases useCases);
	WorkloadSpecRq unmmarshalWorkloadUpdateRq(String json) throws JsonSyntaxException, HavocException;
	String marshalWorkloadSpecRq(WorkloadSpecRq rq)  throws JsonSyntaxException, HavocException;
}
