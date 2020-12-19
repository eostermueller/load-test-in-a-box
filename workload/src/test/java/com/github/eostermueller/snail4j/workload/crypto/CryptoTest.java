package com.github.eostermueller.snail4j.workload.crypto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CryptoTest {

	
	@Test
	void canDecryptWorkload() throws DecryptionException {

		String myPassword = "bl2093j7-1-=3z68fsd9f(%0f375f037";
        String clearText = "{\"useCases\":[{\"processingUnits\":[{\"description\":{\"en_US\":\"sleep ms 100\"},\"useCaseName\":\"03_threads_sleep\",\"selected\":false,\"methodWrapper\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.misc.SleepDelay\",\"methodName\":\"simulateSlowCode_sleepMilliseconds_100\"}},{\"description\":{\"en_US\":\"sleep ms 1\"},\"useCaseName\":\"03_threads_sleep\",\"selected\":false,\"methodWrapper\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.misc.SleepDelay\",\"methodName\":\"simulateSlowCode_sleepMilliseconds_1\"}},{\"description\":{\"en_US\":\"sync sleep ms 10\"},\"useCaseName\":\"03_threads_sleep\",\"selected\":false,\"methodWrapper\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.misc.SleepDelay\",\"methodName\":\"simulateSynchronizedSlowCode_sleepMilliseconds_10\"}},{\"description\":{\"en_US\":\"sleep ms 10\"},\"useCaseName\":\"03_threads_sleep\",\"selected\":false,\"methodWrapper\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.misc.SleepDelay\",\"methodName\":\"simulateSlowCode_sleepMilliseconds_10\"}},{\"description\":{\"en_US\":\"sleep ms 1000\"},\"useCaseName\":\"03_threads_sleep\",\"selected\":false,\"methodWrapper\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.misc.SleepDelay\",\"methodName\":\"simulateSlowCode_sleepMilliseconds_1000\"}},{\"description\":{\"en_US\":\"sync sleep ms 1000\"},\"useCaseName\":\"03_threads_sleep\",\"selected\":false,\"methodWrapper\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.misc.SleepDelay\",\"methodName\":\"simulateSynchronizedSlowCode_sleepMilliseconds_1000\"}},{\"description\":{\"en_US\":\"sync sleep ms 1\"},\"useCaseName\":\"03_threads_sleep\",\"selected\":true,\"methodWrapper\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.misc.SleepDelay\",\"methodName\":\"simulateSynchronizedSlowCode_sleepMilliseconds_1\"}},{\"description\":{\"en_US\":\"sync sleep ms 100\"},\"useCaseName\":\"03_threads_sleep\",\"selected\":false,\"methodWrapper\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.misc.SleepDelay\",\"methodName\":\"simulateSynchronizedSlowCode_sleepMilliseconds_100\"}}],\"name\":\"03_threads_sleep\"}],\"origin\":0}";
        String encrypted = "KbiqpFcE7L+XKX6Mff300pEO7g/DI71ncSvmzNf7Etg67Hjc6UdouI6ViIwQY9P6P3B/Efwg9F8NX38xunlU9E1EuC3uAFih4Kjr4YLWutuiF+7VofNchmUf1idt3i0Vwc3q/qQ5fzk/7IoRrHaP7j4ZLGRawm1W2glRERH7AVsM/pFZctcq41+lcSH49V3alAHiuWC1s8+hPGAbmt2un/HYrcVI8u2Xf3XpwuinzJbAIJEgSsSNYFhTuLmpYPoTMcdVC7OAZmBC8ZclZ1cYJ6nLo+U9pUv7+3scBbXzi3laEOUUNJwvT0Cz4MQhvTiIAflw+HNO5RE/c24a3KSsX3cVE5BDrmsip1fXlKcesG5ZMyooD6NXEkS2UAqqvgWnk0mjlZPa716BxWaxmDaRpeMroGg3HHe7MpDZVFOymrDnkzu85iub2icUW+3mZJu/X7khZKEVcUF8pcjQOxQgGXtkKRPxx0Tdg0mYutbiEdNAHoIJ0TScYlMGnQjRglTYTgaD/Qcur/HdmjjZLB+pM+Y1egOpz9yXSNuaGutG3eI=";
        
        WorkloadCrypto crypto =  new DefaultWorkloadCrypto();

        assertEquals( 
        		clearText,
        		crypto.getDecryptedWorkload(myPassword, encrypted)
        		);
        
        
	}
	@Test
	void canDoRoundTripEncrDecr() throws DecryptionException {
        WorkloadCrypto crypto =  new DefaultWorkloadCrypto();
        
		String myPassword = "bl2093j7-1-=3z68fsd9f(%0f375f037";
        String clearText = "{\"useCases\":[{\"processingUnits\":[{\"description\":{\"en_US\":\"sleep ms 100\"},\"useCaseName\":\"03_threads_sleep\",\"selected\":false,\"methodWrapper\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.misc.SleepDelay\",\"methodName\":\"simulateSlowCode_sleepMilliseconds_100\"}},{\"description\":{\"en_US\":\"sleep ms 1\"},\"useCaseName\":\"03_threads_sleep\",\"selected\":false,\"methodWrapper\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.misc.SleepDelay\",\"methodName\":\"simulateSlowCode_sleepMilliseconds_1\"}},{\"description\":{\"en_US\":\"sync sleep ms 10\"},\"useCaseName\":\"03_threads_sleep\",\"selected\":false,\"methodWrapper\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.misc.SleepDelay\",\"methodName\":\"simulateSynchronizedSlowCode_sleepMilliseconds_10\"}},{\"description\":{\"en_US\":\"sleep ms 10\"},\"useCaseName\":\"03_threads_sleep\",\"selected\":false,\"methodWrapper\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.misc.SleepDelay\",\"methodName\":\"simulateSlowCode_sleepMilliseconds_10\"}},{\"description\":{\"en_US\":\"sleep ms 1000\"},\"useCaseName\":\"03_threads_sleep\",\"selected\":false,\"methodWrapper\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.misc.SleepDelay\",\"methodName\":\"simulateSlowCode_sleepMilliseconds_1000\"}},{\"description\":{\"en_US\":\"sync sleep ms 1000\"},\"useCaseName\":\"03_threads_sleep\",\"selected\":false,\"methodWrapper\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.misc.SleepDelay\",\"methodName\":\"simulateSynchronizedSlowCode_sleepMilliseconds_1000\"}},{\"description\":{\"en_US\":\"sync sleep ms 1\"},\"useCaseName\":\"03_threads_sleep\",\"selected\":true,\"methodWrapper\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.misc.SleepDelay\",\"methodName\":\"simulateSynchronizedSlowCode_sleepMilliseconds_1\"}},{\"description\":{\"en_US\":\"sync sleep ms 100\"},\"useCaseName\":\"03_threads_sleep\",\"selected\":false,\"methodWrapper\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.misc.SleepDelay\",\"methodName\":\"simulateSynchronizedSlowCode_sleepMilliseconds_100\"}}],\"name\":\"03_threads_sleep\"}],\"origin\":0}";
		
        String encrypted = crypto.getEncryptedWorkload(myPassword, clearText);
        

        assertEquals( 
        		clearText,
        		crypto.getDecryptedWorkload(myPassword, encrypted)
        		);
		
	}
	
	/**
	 * If you run the encryption algorithm twice, you'll get different answers each time.
	 * NOt a big problem, but helpful to know.
	 */
	/**
	 * @throws DecryptionException 
	 * 
	 */
	@Test
	void cannotRecreateEncryptedKey() throws DecryptionException {

        WorkloadCrypto crypto =  new DefaultWorkloadCrypto();
        
		String myPassword = "bl2093j7-1-=3z68fsd9f(%0f375f037";
        String clearText = "{\"useCases\":[{\"processingUnits\":[{\"description\":{\"en_US\":\"sleep ms 100\"},\"useCaseName\":\"03_threads_sleep\",\"selected\":false,\"methodWrapper\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.misc.SleepDelay\",\"methodName\":\"simulateSlowCode_sleepMilliseconds_100\"}},{\"description\":{\"en_US\":\"sleep ms 1\"},\"useCaseName\":\"03_threads_sleep\",\"selected\":false,\"methodWrapper\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.misc.SleepDelay\",\"methodName\":\"simulateSlowCode_sleepMilliseconds_1\"}},{\"description\":{\"en_US\":\"sync sleep ms 10\"},\"useCaseName\":\"03_threads_sleep\",\"selected\":false,\"methodWrapper\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.misc.SleepDelay\",\"methodName\":\"simulateSynchronizedSlowCode_sleepMilliseconds_10\"}},{\"description\":{\"en_US\":\"sleep ms 10\"},\"useCaseName\":\"03_threads_sleep\",\"selected\":false,\"methodWrapper\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.misc.SleepDelay\",\"methodName\":\"simulateSlowCode_sleepMilliseconds_10\"}},{\"description\":{\"en_US\":\"sleep ms 1000\"},\"useCaseName\":\"03_threads_sleep\",\"selected\":false,\"methodWrapper\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.misc.SleepDelay\",\"methodName\":\"simulateSlowCode_sleepMilliseconds_1000\"}},{\"description\":{\"en_US\":\"sync sleep ms 1000\"},\"useCaseName\":\"03_threads_sleep\",\"selected\":false,\"methodWrapper\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.misc.SleepDelay\",\"methodName\":\"simulateSynchronizedSlowCode_sleepMilliseconds_1000\"}},{\"description\":{\"en_US\":\"sync sleep ms 1\"},\"useCaseName\":\"03_threads_sleep\",\"selected\":true,\"methodWrapper\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.misc.SleepDelay\",\"methodName\":\"simulateSynchronizedSlowCode_sleepMilliseconds_1\"}},{\"description\":{\"en_US\":\"sync sleep ms 100\"},\"useCaseName\":\"03_threads_sleep\",\"selected\":false,\"methodWrapper\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.misc.SleepDelay\",\"methodName\":\"simulateSynchronizedSlowCode_sleepMilliseconds_100\"}}],\"name\":\"03_threads_sleep\"}],\"origin\":0}";
		
        String firstEncrypted = crypto.getEncryptedWorkload(myPassword, clearText);
        String secondEncrypted = crypto.getEncryptedWorkload(myPassword, clearText);
        
        /**
         * unable to encrypt again and get an identical key.
         */
        assertNotEquals(firstEncrypted,secondEncrypted);

        /**
         * .....even though you can decrypt with either/both keys:
         */
        assertEquals( 
        		clearText,
        		crypto.getDecryptedWorkload(myPassword, firstEncrypted)
        		);
        
        assertEquals( 
        		clearText,
        		crypto.getDecryptedWorkload(myPassword, secondEncrypted)
        		);
        
        
	}

}
