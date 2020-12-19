package com.github.eostermueller.snail4j.workload.crypto;

import java.io.IOException;

import com.github.eostermueller.snail4j.workload.engine.WorkloadBuilder;

public interface WorkloadCrypto {
	String getDecryptedWorkload(String p, String ed) throws DecryptionException;
	String getEncryptedWorkload(String p, String ed);

}
