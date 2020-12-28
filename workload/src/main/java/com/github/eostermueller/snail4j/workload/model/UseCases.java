package com.github.eostermueller.snail4j.workload.model;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BooleanSupplier;

import com.github.eostermueller.snail4j.workload.Snail4jWorkloadException;

public class UseCases {
	int origin = 0;
	
	private String encryptedKey;
	private String alias;
	

	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getEncryptedKey() {
		return encryptedKey;
	}
	public void setEncryptedKey(String encryptedKey) {
		this.encryptedKey = encryptedKey;
	}
	public int getOrigin() {
		return origin;
	}
	public void setOrigin(int origin) {
		this.origin = origin;
	}
	public UseCases sort(Comparator<UseCase> c) {
		useCases.sort(c);
		return this;
	}
	private List<UseCase> useCases = new CopyOnWriteArrayList<UseCase>();
	
	/* (non-Javadoc)
	 * @see com.github.eostermueller.snail4j.annotations.UseCases#getUseCase(java.lang.String)
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
	 * @see com.github.eostermueller.snail4j.annotations.UseCases#addProcessingUnit(com.github.eostermueller.snail4j.annotations.IProcessingUnit)
	 */
	
	public void addProcessingUnit(ProcessingUnitImpl processingUnit) throws Snail4jWorkloadException {
		
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
	/**
	 * confirming that if isEncrypted(), then ecrypted data exists and is valid.
	 * @return
	 */
	public boolean validate() {
		boolean rc = true;
		if (rc) 
				throw new UnsupportedOperationException("not yet implemented");
		return rc;
	}
}
