package com.github.eostermueller.havoc.workload.model.json;

import java.lang.reflect.Type;

import com.github.eostermueller.havoc.workload.HavocException;
import com.github.eostermueller.havoc.workload.model.Descriptor;
import com.github.eostermueller.havoc.workload.model.ProcessingUnitImpl;
import com.google.gson.InstanceCreator;

public class ProcessingUnitInstanceCreator implements InstanceCreator<ProcessingUnitImpl> {

	@Override
	public ProcessingUnitImpl createInstance(Type type) {
		return new ProcessingUnitImpl();
	}


}
