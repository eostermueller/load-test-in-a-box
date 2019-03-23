package com.github.eostermueller.havoc.workload.model;

import java.util.ArrayList;
import java.util.List;

/**
 * "Workload Specification Request"
 * enumerate the list of Java methods that should start processing immediately. 
 * @author erikostermueller
 *
 */
public class WorkloadSpecRq {
	private List<ProcessingUnitImpl> processingUnits = new ArrayList<ProcessingUnitImpl>();
	
	public List<ProcessingUnitImpl> getProcessingUnits() {
		return processingUnits;
	}

	public void addProcessingUnit(ProcessingUnitImpl val) {
		this.getProcessingUnits().add(val);
	}
}
