package com.github.eostermueller.havoc.workload.model;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.github.eostermueller.havoc.workload.HavocException;
import com.google.gson.annotations.SerializedName;

public class UseCases {

	private List<UseCase> useCases = new CopyOnWriteArrayList<UseCase>();
	
	/* (non-Javadoc)
	 * @see com.github.eostermueller.havoc.annotations.UseCases#getUseCase(java.lang.String)
	 */
	public UseCase getUseCase(String useCaseCriteria) {
		UseCase rc = null;
		for(UseCase useCase : this.getUseCases( )) {
			if (useCase.getName().equals(useCaseCriteria))
				rc = useCase;
		}
		return rc;
	}
	public List<UseCase> getUseCases() {
		return this.useCases;
	}


	/* (non-Javadoc)
	 * @see com.github.eostermueller.havoc.annotations.UseCases#addProcessingUnit(com.github.eostermueller.havoc.annotations.IProcessingUnit)
	 */
	
	public void addProcessingUnit(ProcessingUnitImpl processingUnit) throws HavocException {
		
		UseCase useCase = this.getUseCase( processingUnit.getUseCaseName() );
		if (useCase==null) {
			useCase = new UseCase();
			useCase.setName(processingUnit.getUseCaseName());
			this.getUseCases().add( useCase);
		}
		useCase.getProcessingUnits().add(processingUnit);
	}

	public void setUseCases(List<UseCase> val) {
		this.useCases = val;
	}
}
