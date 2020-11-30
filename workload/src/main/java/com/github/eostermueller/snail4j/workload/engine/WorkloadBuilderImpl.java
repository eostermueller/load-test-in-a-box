package com.github.eostermueller.snail4j.workload.engine;

import com.github.eostermueller.snail4j.workload.DefaultFactory;
import com.github.eostermueller.snail4j.workload.Snail4jWorkloadException;
import com.github.eostermueller.snail4j.workload.model.ProcessingUnitImpl;
import com.github.eostermueller.snail4j.workload.model.UseCase;
import com.github.eostermueller.snail4j.workload.model.UseCases;
import com.github.eostermueller.snail4j.workload.model.WorkloadSpecRq;

public class WorkloadBuilderImpl implements WorkloadBuilder {

	private String configLocation;

	@Override
	public Workload createWorkload(UseCases useCases) throws Snail4jWorkloadException {
		
		Workload workload = DefaultFactory.getFactory().createEmptyWorkload();
		for(UseCase useCase : useCases.getUseCases() ) {
			for(ProcessingUnitImpl p: useCase.getProcessingUnits() ) {
				
				if (p.isSelected()) {
					MethodExecutor methodExecutor = DefaultFactory.getFactory().createMethodExecutor(
							p.getMethodWrapper() 
							);
					workload.add(methodExecutor);
				}
			}
		}
		return workload;
	}
}
