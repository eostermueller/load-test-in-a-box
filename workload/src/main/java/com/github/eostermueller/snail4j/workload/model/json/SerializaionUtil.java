package com.github.eostermueller.snail4j.workload.model.json;

import com.github.eostermueller.snail4j.workload.HavocException;
import com.github.eostermueller.snail4j.workload.model.UseCases;
import com.github.eostermueller.snail4j.workload.model.WorkloadSpecRq;

public interface SerializaionUtil {
	UseCases unmmarshalUseCases(String json) throws HavocException;
	String marshalUseCases(UseCases useCases) throws HavocException;
	WorkloadSpecRq unmmarshalWorkloadUpdateRq(String json) throws HavocException;
	String marshalWorkloadSpecRq(WorkloadSpecRq rq) throws HavocException;
}
