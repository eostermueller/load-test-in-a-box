package com.github.eostermueller.havoc.workload.model;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class UseCase  {
	List<ProcessingUnitImpl> processingUnits = new CopyOnWriteArrayList<ProcessingUnitImpl>();
	private String name;
	/* (non-Javadoc)
	 * @see com.github.eostermueller.havoc.UseCase#getProcessingUnits()
	 */
	
	public List<ProcessingUnitImpl> getProcessingUnits() {
		return processingUnits;
	}
	/* (non-Javadoc)
	 * @see com.github.eostermueller.havoc.UseCase#setName(java.lang.String)
	 */
	
	public void setName(String val) {
		this.name = val;
		
	}
	public String getName() {
		return this.name;
	}

}
