package com.github.eostermueller.havoc.workload;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.github.eostermueller.havoc.workload.engine.MethodExecutor;
import com.github.eostermueller.havoc.workload.engine.MethodExecutorImpl;
import com.github.eostermueller.havoc.workload.engine.Workload;
import com.github.eostermueller.havoc.workload.engine.WorkloadBuilder;
import com.github.eostermueller.havoc.workload.engine.WorkloadBuilderImpl;
import com.github.eostermueller.havoc.workload.engine.WorkloadImpl;
import com.github.eostermueller.havoc.workload.model.MethodWrapper;
import com.github.eostermueller.havoc.workload.model.json.DefaultSerializationUtil;
import com.github.eostermueller.havoc.workload.model.json.SerializaionUtil;

public class DefaultFactory implements Factory {
    public static final String FACTORY_SYSTEM_PROPTERY = "com.github.eostermueller.havoc.workload.factory.classname";
    public static final String DEFAULT_FACTORY = "com.github.eostermueller.havoc.workload.DefaultFactory";
	private static Factory factory;

    public static Factory getFactory() throws HavocException {
            if (factory==null) {
                    String factoryClassName = System.getProperty(FACTORY_SYSTEM_PROPTERY, DEFAULT_FACTORY);
                    Class<?> clazz;
                            try {
                                    clazz = Class.forName(factoryClassName);
                                    Constructor<?> constructor;
                            constructor = clazz.getConstructor();
                            factory = (Factory) constructor.newInstance();    
                    } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | ClassNotFoundException e) {
                            String error = "Unable to create factory class [" + factoryClassName + "].  Does the system property [" + FACTORY_SYSTEM_PROPTERY + "]point to the right class?  An impl of com.github.eostermueller.havoc.workload.Factory?";
                            System.err.println(error);
                            e.printStackTrace();
                            throw new HavocException(e,error);
                    }   
            }   
            return factory;
    }

	private static Workload WORKLOAD_INSTANCE = null;
	private java.util.concurrent.locks.ReadWriteLock workloadReadWriteLock = new ReentrantReadWriteLock();  

	@Override
	public SerializaionUtil createSerializationUtil() {
		return new DefaultSerializationUtil();
	}

	@Override
	public Locale getDefaultLocale() {
		return Locale.getDefault();
	}

	@Override
	/**
	 * @stolenFrom https://stackoverflow.com/a/8195633/2377579
	 */
	public void setWorkloadSingleton(Workload val) {
		
		   Lock writeLock = workloadReadWriteLock.writeLock();
		    writeLock.lock();
		    try {
		        WORKLOAD_INSTANCE = val;
		    } finally {
		        writeLock.unlock();
		    }	
		
	}
	public WorkloadBuilder createWorkloadBuilder() {
		return new WorkloadBuilderImpl();
	}

	@Override
	/**
	 * @stolenFrom https://stackoverflow.com/a/8195633/2377579
	 */
	public Workload getWorkloadSingleton() {
		
		   Lock readLock = workloadReadWriteLock.readLock();
		    readLock.lock();
		    try {
		        return WORKLOAD_INSTANCE;
		    } finally {
		        readLock.unlock();
		    }	
	 }

	@Override
	public MethodExecutor createMethodExecutor(MethodWrapper methodWrapper) {
		MethodExecutor methodExecutor = new MethodExecutorImpl(methodWrapper);
		return methodExecutor;
	}

	@Override
	public Workload createEmptyWorkload() {
		return new WorkloadImpl();
	}

}
