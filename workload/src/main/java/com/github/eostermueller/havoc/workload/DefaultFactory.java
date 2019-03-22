package com.github.eostermueller.havoc.workload;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Locale;

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

	@Override
	public SerializaionUtil createSerializationUtil() {
		return new DefaultSerializationUtil();
	}

	@Override
	public Locale getDefaultLocale() {
		return Locale.getDefault();
	}

}
