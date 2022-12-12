package com.github.eostermueller.snail4j.processmodel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProcessIdsSingleton {
	public static final long UNINIT_PID = -1;

	/**
	 * @st0lenFr0m: https://stackoverflow.com/questions/7855700/why-is-volatile-used-in-double-checked-locking
	 */
   private volatile static ProcessIdsSingleton instance;
   
   private Map<String,Long> processIds = null;
   public void reset(String key) {
	   this.processIds.remove(key);
   }
   public void put(String key, long processId) {
	   this.processIds.put(key, new Long(processId));
   }
   public long get(String key) {
	   long rc = UNINIT_PID;
	   if (this.processIds.get(key) !=null)
		   rc = this.processIds.get(key).longValue();
	   return rc;
   }
   
   private ProcessIdsSingleton() {
	   processIds = new ConcurrentHashMap<String,Long>();
   }
   public static ProcessIdsSingleton getInstance() {
        if (instance == null) {
            synchronized (ProcessIdsSingleton.class) {
                if (instance == null) {
                    instance = new ProcessIdsSingleton();
                }
            }
        }
        return instance;
    }	

}
