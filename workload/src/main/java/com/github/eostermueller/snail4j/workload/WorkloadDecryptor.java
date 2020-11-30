package com.github.eostermueller.snail4j.workload;

import java.io.IOException;

import com.github.eostermueller.snail4j.workload.engine.WorkloadBuilder;

public interface WorkloadDecryptor {
	String getDecryptedWorkload(String p, String ed) throws DecryptionException;

}
