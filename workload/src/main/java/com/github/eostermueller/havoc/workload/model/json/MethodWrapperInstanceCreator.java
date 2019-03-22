package com.github.eostermueller.havoc.workload.model.json;

import java.lang.reflect.Type;

import com.github.eostermueller.havoc.workload.HavocException;
import com.github.eostermueller.havoc.workload.model.MethodWrapper;
import com.google.gson.InstanceCreator;

public class MethodWrapperInstanceCreator implements InstanceCreator<MethodWrapper> {

	@Override
	public MethodWrapper createInstance(Type type) {
		System.out.println(type.toString());
		return new MethodWrapper();
	}


}
