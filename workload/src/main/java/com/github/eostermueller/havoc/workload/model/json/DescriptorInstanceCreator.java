package com.github.eostermueller.havoc.workload.model.json;

import java.lang.reflect.Type;

import com.github.eostermueller.havoc.workload.HavocException;
import com.github.eostermueller.havoc.workload.model.Descriptor;
import com.google.gson.InstanceCreator;

public class DescriptorInstanceCreator implements InstanceCreator<Descriptor> {

	@Override
	public Descriptor createInstance(Type type) {
		Descriptor d = null;
		d = new Descriptor();
		return d;
	}


}
