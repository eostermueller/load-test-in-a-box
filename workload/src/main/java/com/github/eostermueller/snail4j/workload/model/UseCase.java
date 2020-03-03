package com.github.eostermueller.snail4j.workload.model;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.eostermueller.snail4j.workload.annotations.Load;

public class UseCase  {
	List<ProcessingUnitImpl> processingUnits = new CopyOnWriteArrayList<ProcessingUnitImpl>();
	private String name;
	/* (non-Javadoc)
	 * @see com.github.eostermueller.snail4j.UseCase#getProcessingUnits()
	 */
	
	public List<ProcessingUnitImpl> getProcessingUnits() {
		return processingUnits;
	}
	/* (non-Javadoc)
	 * @see com.github.eostermueller.snail4j.UseCase#setName(java.lang.String)
	 */
	
	public void setName(String val) {
		this.name = val;
		
	}
	public String getName() {
		return this.name;
	}
	
	@JsonIgnore
	public ProcessingUnitImpl getSelectedProcessingUnit() {
		ProcessingUnitImpl rc = null;
		for (ProcessingUnitImpl processingUnit : this.getProcessingUnits() ) {
			if (processingUnit.isSelected() ) 
				rc = processingUnit;
		}
		return rc;
	}

}
