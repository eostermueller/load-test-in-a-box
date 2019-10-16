package com.github.eostermueller.snail4j.workload.engine;

import com.github.eostermueller.snail4j.workload.Snail4jWorkloadException;
import com.github.eostermueller.snail4j.workload.model.UseCases;
import com.github.eostermueller.snail4j.workload.model.WorkloadSpecRq;

public interface WorkloadBuilder {

	Workload createWorkload(UseCases rq) throws Snail4jWorkloadException;

}
