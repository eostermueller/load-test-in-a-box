package com.github.eostermueller.havoc.workload.model.json;

import com.github.eostermueller.havoc.workload.HavocException;
import com.github.eostermueller.havoc.workload.model.UseCases;
import com.github.eostermueller.havoc.workload.model.WorkloadSpecRq;

public interface SerializaionUtil {
	UseCases unmmarshalUseCases(String json) throws HavocException;
	String marshalUseCases(UseCases useCases) throws HavocException;
	WorkloadSpecRq unmmarshalWorkloadUpdateRq(String json) throws HavocException;
	String marshalWorkloadSpecRq(WorkloadSpecRq rq) throws HavocException;
}
