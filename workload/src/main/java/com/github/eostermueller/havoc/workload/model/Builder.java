package com.github.eostermueller.havoc.workload.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.github.eostermueller.havoc.workload.HavocException;
import com.github.eostermueller.havoc.workload.OnlyStringAndLongAndIntAreAllowedParameterTypes;

import io.github.classgraph.AnnotationInfo;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.MethodInfo;
import io.github.classgraph.MethodParameterInfo;

public interface Builder {


	ProcessingUnitImpl createProcessingUnit(AnnotationInfo annotationInfo, ClassInfo classInfo, MethodInfo methodInfo) throws HavocException, OnlyStringAndLongAndIntAreAllowedParameterTypes;

	Descriptor createDescriptor(AnnotationInfo annotationInfo) throws HavocException;

	MethodWrapper createMethodWrapper(Method method);

	MethodParameter createParameter(Method method, MethodParameterInfo parm)
			throws HavocException, OnlyStringAndLongAndIntAreAllowedParameterTypes;
}
