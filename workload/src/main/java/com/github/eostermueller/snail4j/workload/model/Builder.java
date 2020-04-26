package com.github.eostermueller.snail4j.workload.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.github.eostermueller.snail4j.workload.Snail4jWorkloadException;
import com.github.eostermueller.snail4j.workload.OnlyStringAndLongAndIntAreAllowedParameterTypes;

import io.github.classgraph.AnnotationInfo;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.MethodInfo;
import io.github.classgraph.MethodParameterInfo;

public interface Builder {


	ProcessingUnitImpl createProcessingUnit(AnnotationInfo annotationInfo, ClassInfo classInfo, MethodInfo methodInfo) throws Snail4jWorkloadException, OnlyStringAndLongAndIntAreAllowedParameterTypes;

	MethodWrapper createMethodWrapper(Method method);

	MethodParameter createParameter(Method method, MethodParameterInfo parm)
			throws Snail4jWorkloadException, OnlyStringAndLongAndIntAreAllowedParameterTypes;

	void addDescriptions(ProcessingUnitImpl processingUnit, AnnotationInfo annotationInfo) throws Snail4jWorkloadException;

	void addDescriptions(MethodParameter methodParameter, AnnotationInfo annotationInfo) throws Snail4jWorkloadException;
}
