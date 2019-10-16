package com.github.eostermueller.snail4j.workload.annotation.parameters;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.Test;

import com.github.eostermueller.snail4j.workload.Snail4jWorkloadException;
import com.github.eostermueller.snail4j.workload.OnlyStringAndLongAndIntAreAllowedParameterTypes;
import com.github.eostermueller.snail4j.workload.model.Snail4jLibrary;
import com.github.eostermueller.snail4j.workload.model.Message;
import com.github.eostermueller.snail4j.workload.model.MethodParameter;
import com.github.eostermueller.snail4j.workload.model.ProcessingUnitImpl;
import com.github.eostermueller.snail4j.workload.model.UseCase;
import com.github.eostermueller.snail4j.workload.model.UseCases;


class ProcessingUnitParameterDetectionTest {

	/**
	 * ProcessingUnit descriptions in the unit tests, ones that would ultimately end up in the GUI,
	 * are specified in these tests as en_US, so that's the locale we'll ask for using this constant.
	 */
	private static final Locale DEFAULT_LOCALE = Locale.US;
	@Test
	public void canIdentifyProcessingUnits_twoParameters() throws Snail4jWorkloadException, OnlyStringAndLongAndIntAreAllowedParameterTypes {
		
		UseCases scanResult = Snail4jLibrary
						.scan("com.github.eostermueller.snail4j.workload.annotation.parameters");  //limit to package of this test case.
		
		assertEquals(1,scanResult.getUseCases().size());
		
		UseCase sortingUseCase = scanResult.getUseCase(SortingWithParameter.USE_CASE_NAME);
		
		List<ProcessingUnitImpl> processingUnits = sortingUseCase.getProcessingUnits();
		
		assertEquals(1,processingUnits.size() );
				
		ProcessingUnitImpl selectionSortProcessingUnit = processingUnits.get(0);
		assertEquals(selectionSortProcessingUnit.getMethodWrapper().getMethodName(),"selectionSort");
		
		assertEquals("Selection Sort", selectionSortProcessingUnit.getDescription("en_US") );
		
		
		MethodParameter parm0 = selectionSortProcessingUnit.getMethodWrapper().getParameter(0);
		assertEquals( "arraySize", parm0.getName());
		assertEquals( "10", parm0.getDefaultValue() );
		assertEquals( "number of intergers to be created (ThreadLocalRandom) and sorted for each execution", 
				parm0.getDescription("en_US") );

		MethodParameter parm1 = selectionSortProcessingUnit.getMethodWrapper().getParameter(1);
		assertEquals( "unknown", parm1.getName());
		assertEquals( "1000", parm1.getDefaultValue() );
		assertEquals( "Used for testing only!", parm1.getDescription("en_US") );

		MethodParameter parm2 = selectionSortProcessingUnit.getMethodWrapper().getParameter(2);
		assertEquals( "hostname", parm2.getName());
		assertEquals( "stubserver.com", parm2.getDefaultValue() );
		assertEquals( "User for testing network speed.", parm2.getDescription("en_US") );
	}

}
