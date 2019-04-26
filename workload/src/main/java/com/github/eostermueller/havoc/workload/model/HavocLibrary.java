package com.github.eostermueller.havoc.workload.model;

import java.util.Map;

import com.github.eostermueller.havoc.workload.HavocException;
import com.github.eostermueller.havoc.workload.OnlyStringAndLongAndIntAreAllowedParameterTypes;
import com.github.eostermueller.havoc.workload.annotations.ProcessingUnit;

import io.github.classgraph.AnnotationInfo;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.MethodInfo;
import io.github.classgraph.MethodInfoList;
import io.github.classgraph.ScanResult;

public class HavocLibrary {


	public static UseCases scan(String whiteList) throws HavocException, OnlyStringAndLongAndIntAreAllowedParameterTypes {
		UseCases havocScanResult = new UseCases();
        try (ScanResult scanResult = new ClassGraph().verbose().enableAllInfo().enableMethodInfo().whitelistPackages(whiteList).scan()) {
        	
        	loadAnnotations(scanResult, havocScanResult);
        	
        }
		return havocScanResult;
	}
	public static UseCases scan() throws HavocException, OnlyStringAndLongAndIntAreAllowedParameterTypes {
		UseCases havocScanResult = new UseCases();
        try (ScanResult scanResult = new ClassGraph().verbose().enableAllInfo().enableMethodInfo().scan()) {
        	
        	loadAnnotations(scanResult, havocScanResult);
        	
        }
		return havocScanResult;
	}
	public static void loadAnnotations(ScanResult scanResult, UseCases havocScanResult) throws HavocException, OnlyStringAndLongAndIntAreAllowedParameterTypes {
    	ClassInfoList puClassInfoList = scanResult.getClassesWithMethodAnnotation(
    			ProcessingUnit.class.getName().toString()
    			);
        for (ClassInfo puClassInfo : puClassInfoList) {
        	
        	Map<String, MethodInfoList> methodInfoMap = puClassInfo.getMethodInfo().asMap();
        	for (Map.Entry<String, MethodInfoList> entry : methodInfoMap.entrySet()) {
        		String methodName = entry.getKey();
        		MethodInfoList methodInfoList = entry.getValue();
        		
        		for( MethodInfo methodInfo : methodInfoList) {
        			
        			if (methodInfo.hasAnnotation(ProcessingUnit.class.getName())) {
        				
        				AnnotationInfo annotationInfo = methodInfo.getAnnotationInfo(ProcessingUnit.class.getName());
    	                ProcessingUnitImpl processingUnit 
    	                	= DefaultBuilder.getBuilder()
    	                	.createProcessingUnit(
    	                			annotationInfo,
    	                			puClassInfo, 
    	                			methodInfo 
    	                			);
    	                
    	        		havocScanResult.addProcessingUnit( processingUnit );
        			}
        		}
            }
        }
		
	}

}
