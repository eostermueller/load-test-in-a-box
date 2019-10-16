package com.github.eostermueller.snail4j.workload.model.json;

import com.github.eostermueller.snail4j.workload.Snail4jWorkloadException;
import com.github.eostermueller.snail4j.workload.model.UseCases;
import com.github.eostermueller.snail4j.workload.model.WorkloadSpecRq;

public interface SerializaionUtil {
	UseCases unmmarshalUseCases(String json) throws Snail4jWorkloadException;
	String marshalUseCases(UseCases useCases) throws Snail4jWorkloadException;
	WorkloadSpecRq unmmarshalWorkloadUpdateRq(String json) throws Snail4jWorkloadException;
	String marshalWorkloadSpecRq(WorkloadSpecRq rq) throws Snail4jWorkloadException;
}
