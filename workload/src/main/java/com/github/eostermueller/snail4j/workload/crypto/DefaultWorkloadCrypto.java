package com.github.eostermueller.snail4j.workload.crypto;

import java.io.IOException;

import org.jasypt.util.text.StrongTextEncryptor;

public class DefaultWorkloadCrypto implements WorkloadCrypto {

	/**
	 * Snail4j encrypts some workloads by 
	 * <ol>
	 * 	<li>Compressing the clear text. </ol>
	 * <li> Using Jasypt.org StringTextEncryptor to encrypt it>
	 * </ul>
	 * 
	 * Therefor, decrypting does these operations in reverse:
	 * First decrypts, then decompresses.
	 */
	@Override
	public String getDecryptedWorkload(String p, String ed) throws DecryptionException {
		
        String rc;
		try {
	    	StrongTextEncryptor strongTextEncryptor = new StrongTextEncryptor();
	    	strongTextEncryptor.setPassword(p);
	        
	        String decryptedText = strongTextEncryptor.decrypt(ed);
			rc = Util.decompress(decryptedText);
			
		} catch (IOException e) {
			e.printStackTrace();
			DecryptionException d = new DecryptionException(e);
			throw d;
		}
        
        return rc;
	}

	@Override
	public String getEncryptedWorkload(String p, String clearText) {
        String rc;
		String compressed = Util.compress(clearText);
		
		StrongTextEncryptor strongTextEncryptor = new StrongTextEncryptor();
		strongTextEncryptor.setPassword(p);
		
		String encryptedText = strongTextEncryptor.encrypt(compressed);
		
		rc = encryptedText;
        
        return rc;
	}

}
