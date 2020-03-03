package com.github.eostermueller.snail4j.workload.model;

import java.util.Map;

import com.github.eostermueller.snail4j.workload.Snail4jWorkloadException;
import com.github.eostermueller.snail4j.workload.OnlyStringAndLongAndIntAreAllowedParameterTypes;
import com.github.eostermueller.snail4j.workload.annotations.Load;

import io.github.classgraph.AnnotationInfo;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.MethodInfo;
import io.github.classgraph.MethodInfoList;
import io.github.classgraph.ScanResult;

public class Snail4jLibrary {


	public static UseCases scan(String whiteList) throws Snail4jWorkloadException, OnlyStringAndLongAndIntAreAllowedParameterTypes {
		UseCases snail4jScanResult = new UseCases();
        try (ScanResult scanResult = new ClassGraph().verbose().enableAllInfo().enableMethodInfo().whitelistPackages(whiteList).scan()) {
        	
        	loadAnnotations(scanResult, snail4jScanResult);
        	
        }
		return snail4jScanResult;
	}
	public static UseCases scan() throws Snail4jWorkloadException, OnlyStringAndLongAndIntAreAllowedParameterTypes {
		UseCases snail4jScanResult = new UseCases();
        try (ScanResult scanResult = new ClassGraph().verbose().enableAllInfo().enableMethodInfo().scan()) {
        	
        	loadAnnotations(scanResult, snail4jScanResult);
        	
        }
		return snail4jScanResult;
	}
	public static void loadAnnotations(ScanResult scanResult, UseCases snail4jScanResult) throws Snail4jWorkloadException, OnlyStringAndLongAndIntAreAllowedParameterTypes {
    	ClassInfoList puClassInfoList = scanResult.getClassesWithMethodAnnotation(
    			Load.class.getName().toString()
    			);
        for (ClassInfo puClassInfo : puClassInfoList) {
        	
        	Map<String, MethodInfoList> methodInfoMap = puClassInfo.getMethodInfo().asMap();
        	for (Map.Entry<String, MethodInfoList> entry : methodInfoMap.entrySet()) {
        		String methodName = entry.getKey();
        		MethodInfoList methodInfoList = entry.getValue();
        		
        		for( MethodInfo methodInfo : methodInfoList) {
        			
        			if (methodInfo.hasAnnotation(Load.class.getName())) {
        				
        				AnnotationInfo annotationInfo = methodInfo.getAnnotationInfo(Load.class.getName());
    	                ProcessingUnitImpl processingUnit 
    	                	= DefaultBuilder.getBuilder()
    	                	.createProcessingUnit(
    	                			annotationInfo,
    	                			puClassInfo, 
    	                			methodInfo 
    	                			);
    	                
    	        		snail4jScanResult.addProcessingUnit( processingUnit );
        			}
        		}
            }
        }
		
	}

}
