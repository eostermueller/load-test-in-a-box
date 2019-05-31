package com.github.eostermueller.havoc.workload.engine;

import com.github.eostermueller.havoc.havoc.workload.engine.WorkloadBuilder;
import com.github.eostermueller.havoc.workload.DefaultFactory;
import com.github.eostermueller.havoc.workload.HavocException;
import com.github.eostermueller.havoc.workload.model.ProcessingUnitImpl;
import com.github.eostermueller.havoc.workload.model.WorkloadSpecRq;

public class WorkloadBuilderImpl implements WorkloadBuilder {

	@Override
	public Workload createWorkload(WorkloadSpecRq rq) throws HavocException {
		
		Workload workload = DefaultFactory.getFactory().createEmptyWorkload();
		
		for(ProcessingUnitImpl p: rq.getProcessingUnits()) {
			
			if (p.isSelected()) {
				MethodExecutor methodExecutor = DefaultFactory.getFactory().createMethodExecutor(
						p.getMethodWrapper() 
						);
				workload.add(methodExecutor);
			}
		}
		return workload;
	}

}
