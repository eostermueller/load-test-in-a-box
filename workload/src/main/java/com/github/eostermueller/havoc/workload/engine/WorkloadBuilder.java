package com.github.eostermueller.havoc.workload.engine;

import com.github.eostermueller.havoc.workload.HavocException;
import com.github.eostermueller.havoc.workload.model.UseCases;
import com.github.eostermueller.havoc.workload.model.WorkloadSpecRq;

public interface WorkloadBuilder {

	Workload createWorkload(UseCases rq) throws HavocException;

}
