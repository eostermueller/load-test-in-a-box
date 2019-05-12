package com.github.eostermueller.havoc.workload.model;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.eostermueller.havoc.workload.HavocException;
import com.github.eostermueller.havoc.workload.OnlyStringAndLongAndIntAreAllowedParameterTypes;
import com.github.eostermueller.havoc.workload.annotations.Param;
import com.github.eostermueller.havoc.workload.annotations.ProcessingUnit;
import com.github.eostermueller.havoc.workload.annotations.UserInterfaceDescription;

import io.github.classgraph.AnnotationInfo;
import io.github.classgraph.AnnotationParameterValueList;
import io.github.classgraph.BaseTypeSignature;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassRefTypeSignature;
import io.github.classgraph.MethodInfo;
import io.github.classgraph.MethodParameterInfo;
import io.github.classgraph.TypeSignature;

public class DefaultBuilder implements Builder {
	public static final String BUILDER_SYSTEM_PROPTERY = "com.github.eostermueller.havoc.workload.builder.classname";
	public static final String DEFAULT_BUILDER = "com.github.eostermueller.havoc.workload.model.DefaultBuilder";
	public static Builder factory = null; 
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
	public DefaultBuilder() {}

	
	@Override 
	public Descriptor createDescriptor(AnnotationInfo annotationInfo) throws HavocException {
		Descriptor descriptor = new Descriptor();
		AnnotationParameterValueList parms = annotationInfo.getParameterValues();
		Object[] uiDescriptionAnnArray = (Object[]) parms.get(ProcessingUnit.VALUE);
		for( Object ann : uiDescriptionAnnArray ) {
			AnnotationInfo annInfo = (AnnotationInfo) ann;
			//String descr = (String) annInfo.getParameterValues().get(UserInterfaceDescription.VALUE);
			
			AnnotationParameterValueList values = annInfo.getParameterValues();
			String localeString = (String) values.get(UserInterfaceDescription.LOCALE);
			if (localeString==null) {

				//seems like I need to open a classgraph 'issue' for this.
				//the following returns an NPE, was expecting to see my default locale of en_US.
				//AnnotationParameterValueList defaultValues = annInfo.getDefaultParameterValues();
				
				localeString = "en-US"; //as a workaround, this value must stay in sync with the default specified in UserInterfaceDescription:
								  //	public String locale() default "en-US";

			}
			String description = (String) values.get(UserInterfaceDescription.VALUE);
			Locale aLocale = Locale.forLanguageTag(localeString);
			descriptor.addMessage(aLocale, description);
		}
		return descriptor;
	}
	
	@Override
	public ProcessingUnitImpl createProcessingUnit(AnnotationInfo annotationInfo, ClassInfo classInfo, MethodInfo methodInfo) throws HavocException, OnlyStringAndLongAndIntAreAllowedParameterTypes {
		MethodWrapper methodWrapper = null;
		ProcessingUnitImpl processingUnit = null;
		
		Method method = methodInfo.loadClassAndGetMethod();
		
		if (method!=null) {
			methodWrapper = DefaultBuilder.getBuilder().createMethodWrapper(method);
			methodWrapper.setMethodName(method.getName());
			methodWrapper.setDeclaringClassName(method.getDeclaringClass().getName());
			processingUnit = new ProcessingUnitImpl();
			AnnotationParameterValueList parms = annotationInfo.getParameterValues();
			
			String useCase = (String)parms.get(ProcessingUnit.USE_CASE);
			if (useCase==null || "".equals(useCase)) {
				String error = "The [" + ProcessingUnit.class.getSimpleName() + "] annotation for class [" + methodWrapper.getDeclaringClassName()  + "] must have an attribute named [" + ProcessingUnit.USE_CASE + "]";
				throw new HavocException(error);
			}
			
			processingUnit.setUseCaseName( useCase );
			
			boolean selected = (boolean)parms.get(ProcessingUnit.SELECTED);
			processingUnit.setSelected(selected);
			
			processingUnit.setMethodWrapper(methodWrapper);
			
			processingUnit.setDescriptor(createDescriptor(annotationInfo));
			
            if (methodInfo.getParameterInfo().length < method.getParameterCount() ) {
            	int missingCnt =  method.getParameterCount() - methodInfo.getParameterInfo().length;
            	String error = "@Param annotations must be specified for all paramters of the method. Seems like you have [" + missingCnt + "] parameters without the @Param annotation.  Debug [" + methodInfo.toString() + "]";
            	throw new HavocException(error);
            } else if (methodInfo.getParameterInfo().length > method.getParameterCount() ) {
            	 
            	             	
            	String error = "Hmmm how weird that you have more @Param annotations than you have parameters vn this method.  Every parameter on the method must have one and only on @Param annotation.  Debug.  method parms [" +  method.getParameterCount() + "] annotation parms [" + methodInfo.getParameterInfo().length + "] [" + methodInfo.toString() + "]"; 
            	throw new HavocException(error);
            } else {
                for(MethodParameterInfo parm : methodInfo.getParameterInfo()) {
                	if (parm.hasAnnotation(Param.class.getName())) {
                		processingUnit.getMethodWrapper().getParameters().add(
                				DefaultBuilder.getBuilder().createParameter(method, parm)
                				);
                	}
                }
            }
			
            	
		} 

		
		return processingUnit;
	}

	public static Builder getBuilder() throws HavocException {
		if (factory==null) {
			String factoryClassName = System.getProperty(BUILDER_SYSTEM_PROPTERY, DEFAULT_BUILDER);
			Class<?> clazz;
				try {
					clazz = Class.forName(factoryClassName);
					Constructor<?> constructor;
				constructor = clazz.getConstructor();
				factory = (Builder) constructor.newInstance();			
			} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | ClassNotFoundException e) {
				String error = "Unable to create factory class [" + factoryClassName + "].  Does the system property [" + BUILDER_SYSTEM_PROPTERY + "]point to the right class?  An impl of com.github.eostermueller.havoc.workload.Factory?";
				System.err.println(error);
				e.printStackTrace();
				throw new HavocException(e,error);
			}
		}
		return factory;
	}

	@Override
	public MethodParameter createParameter(Method method, MethodParameterInfo parm) throws HavocException, OnlyStringAndLongAndIntAreAllowedParameterTypes {
		MethodParameter newParam = new MethodParameter();
		AnnotationInfo annotationInfo = parm.getAnnotationInfo().get(0); 
		
		
		String defaultValue = (String) annotationInfo.getParameterValues().get("defaultValue");
		
		TypeSignature type = parm.getTypeSignatureOrTypeDescriptor();
		if (type instanceof ClassRefTypeSignature) {
			ClassRefTypeSignature crts = (ClassRefTypeSignature)type;
			if (crts.getFullyQualifiedClassName().equals("java.lang.String")) {
				newParam.setParameterType(ParameterType.STRING);
				if (defaultValue!=null) {
					newParam.setDefaultStringValue(defaultValue);
				}
			} else {
				throw new OnlyStringAndLongAndIntAreAllowedParameterTypes(method, crts.getFullyQualifiedClassName());
			}
		} else if (type instanceof BaseTypeSignature) {
			BaseTypeSignature bts = (BaseTypeSignature)type;
			
			switch (bts.getTypeStr()) {
			case "int": 
				newParam.setParameterType(ParameterType.INTEGER);
				if (defaultValue!=null) {
					Integer intDefaultValue = Integer.valueOf(defaultValue);
					newParam.setDefaultIntValue( intDefaultValue.intValue() );
				}
				break;
			case "long":
				newParam.setParameterType(ParameterType.LONG);
				if (defaultValue!=null) {
					Long longDefaultValue = Long.valueOf(defaultValue);
					newParam.setDefaultLongValue( longDefaultValue.longValue() );
				}
				break;
			default:
				throw new OnlyStringAndLongAndIntAreAllowedParameterTypes(method, bts.getTypeStr() );
			}
			
		}
		
		
		
		String name = (String) annotationInfo.getParameterValues().get("name");
		newParam.setName(name);

		newParam.setDescriptor( this.createDescriptor( annotationInfo ) );
		
		return newParam;
	}


	@Override
	public MethodWrapper createMethodWrapper(Method method) {
		MethodWrapper methodWrapper = new MethodWrapper();
		methodWrapper.setMethodName(method.getName());
		
		return methodWrapper;
	}

}
