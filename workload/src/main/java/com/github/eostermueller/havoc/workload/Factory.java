package com.github.eostermueller.havoc.workload;

import java.util.Locale;

import com.github.eostermueller.havoc.workload.engine.MethodExecutor;
import com.github.eostermueller.havoc.workload.engine.Workload;
import com.github.eostermueller.havoc.workload.engine.WorkloadBuilder;
import com.github.eostermueller.havoc.workload.model.MethodWrapper;
import com.github.eostermueller.havoc.workload.model.json.SerializaionUtil;

public interface Factory {

	SerializaionUtil createSerializationUtil();
	Locale getDefaultLocale();
	Workload getWorkloadSingleton();
	void setWorkloadSingleton(Workload val);
	Workload createEmptyWorkload();
	MethodExecutor createMethodExecutor(MethodWrapper methodWrapper);
	WorkloadBuilder createWorkloadBuilder();

}
