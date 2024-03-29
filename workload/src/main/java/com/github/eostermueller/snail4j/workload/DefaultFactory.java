package com.github.eostermueller.snail4j.workload;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.github.eostermueller.snail4j.workload.crypto.DefaultWorkloadCrypto;
import com.github.eostermueller.snail4j.workload.crypto.WorkloadCrypto;
import com.github.eostermueller.snail4j.workload.engine.MethodExecutor;
import com.github.eostermueller.snail4j.workload.engine.MethodExecutorImpl;
import com.github.eostermueller.snail4j.workload.engine.Workload;
import com.github.eostermueller.snail4j.workload.engine.WorkloadBuilder;
import com.github.eostermueller.snail4j.workload.engine.WorkloadBuilderImpl;
import com.github.eostermueller.snail4j.workload.engine.WorkloadImpl;
import com.github.eostermueller.snail4j.workload.markdown.DefaultLoader;
import com.github.eostermueller.snail4j.workload.markdown.DefaultLocator;
import com.github.eostermueller.snail4j.workload.markdown.MarkdownLoader;
import com.github.eostermueller.snail4j.workload.markdown.MarkdownLocator;
import com.github.eostermueller.snail4j.workload.markdown.reader.DefaultMarkdownReader;
import com.github.eostermueller.snail4j.workload.markdown.reader.MarkdownReader;
import com.github.eostermueller.snail4j.workload.markdown.reader.MarkdownReaderFilter;
import com.github.eostermueller.snail4j.workload.markdown.reader.DefaultMetadataFilter;
import com.github.eostermueller.snail4j.workload.model.MethodWrapper;
import com.github.eostermueller.snail4j.workload.model.json.DefaultSerializationUtil;
import com.github.eostermueller.snail4j.workload.model.json.SerializaionUtil;

public class DefaultFactory implements Factory {
    public static final String FACTORY_SYSTEM_PROPTERY = "com.github.eostermueller.snail4j.workload.factory.classname";
    public static final String DEFAULT_FACTORY = "com.github.eostermueller.snail4j.workload.DefaultFactory";
	private static Factory factory;
	private static AliasManager aliasManager = null;


	public static Factory getFactory() throws Snail4jWorkloadException {
            if (factory==null) {
                    String factoryClassName = System.getProperty(FACTORY_SYSTEM_PROPTERY, DEFAULT_FACTORY);
                    Class<?> clazz;
                            try {
                                    clazz = Class.forName(factoryClassName);
                                    Constructor<?> constructor;
                            constructor = clazz.getConstructor();
                            factory = (Factory) constructor.newInstance();    
                    } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | ClassNotFoundException e) {
                            String error = "Unable to create factory class [" + factoryClassName + "].  Does the system property [" + FACTORY_SYSTEM_PROPTERY + "]point to the right class?  An impl of com.github.eostermueller.snail4j.workload.Factory?";
                            System.err.println(error);
                            e.printStackTrace();
                            throw new Snail4jWorkloadException(e,error);
                    }   
            }   
            return factory;
    }

	private static Workload WORKLOAD_INSTANCE = null;
	private static String configLocation = "snaIl3j";
	private java.util.concurrent.locks.ReadWriteLock workloadReadWriteLock = new ReentrantReadWriteLock();  

	@Override
	public WorkloadCrypto getWorkloadCrypto() {
		return new DefaultWorkloadCrypto();
	}

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
    public static String getConfigLocation() {
		return configLocation;
	}

	public static void setConfigLocation(String configLocation) {
		DefaultFactory.configLocation = configLocation;
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

	@Override
	public AliasManager getAliasManager() {
		if (this.aliasManager==null) {
			this.aliasManager= new DefaultAliasManager();
			this.aliasManager.load();
		}
		
		return this.aliasManager;
	}


	@Override
	public MarkdownReader createMarkdownReader() {
		return new DefaultMarkdownReader();
	}

	@Override
	public MarkdownLocator createMarkdownLocator() {
		return new DefaultLocator();
	}

	@Override
	public MarkdownLoader createMarkdownLoader() throws Snail4jWorkloadException {
		return new DefaultLoader();
	}
	@Override
	public MarkdownReaderFilter createMetadataFilter() throws Snail4jWorkloadException {
		return new DefaultMetadataFilter();
	}
	@Override
	public MarkdownReaderFilter createClickToFailFilter() throws Snail4jWorkloadException {
		return new DefaultMetadataFilter();
	}





}
