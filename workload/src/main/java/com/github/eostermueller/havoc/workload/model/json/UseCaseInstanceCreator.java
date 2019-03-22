package com.github.eostermueller.havoc.workload.model.json;

import java.lang.reflect.Type;

import com.github.eostermueller.havoc.workload.model.UseCase;
import com.google.gson.InstanceCreator;

public class UseCaseInstanceCreator implements InstanceCreator<UseCase> {

	@Override
	public UseCase createInstance(Type type) {
		return new UseCase();
	}


}
