package com.github.eostermueller.snail4j.workload;

import java.util.Locale;

import com.github.eostermueller.snail4j.workload.crypto.WorkloadCrypto;
import com.github.eostermueller.snail4j.workload.engine.MethodExecutor;
import com.github.eostermueller.snail4j.workload.engine.Workload;
import com.github.eostermueller.snail4j.workload.engine.WorkloadBuilder;
import com.github.eostermueller.snail4j.workload.model.MethodWrapper;
import com.github.eostermueller.snail4j.workload.model.json.SerializaionUtil;

public interface Factory {

	SerializaionUtil createSerializationUtil();
	Locale getDefaultLocale();
	Workload getWorkloadSingleton();
	void setWorkloadSingleton(Workload val);
	Workload createEmptyWorkload();
	MethodExecutor createMethodExecutor(MethodWrapper methodWrapper);
	WorkloadBuilder createWorkloadBuilder();
	WorkloadCrypto getWorkloadCrypto();

}
