package com.github.eostermueller.havoc.workload;

import java.util.Locale;

import com.github.eostermueller.havoc.workload.model.json.SerializaionUtil;

public interface Factory {

	SerializaionUtil createSerializationUtil();
	Locale getDefaultLocale();

}
