package com.github.eostermueller.snail4j.workload.annotations.basic;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.Test;

import com.github.eostermueller.snail4j.workload.Snail4jWorkloadException;
import com.github.eostermueller.snail4j.workload.OnlyStringAndLongAndIntAreAllowedParameterTypes;
import com.github.eostermueller.snail4j.workload.model.WorkloadLibrary;
import com.github.eostermueller.snail4j.workload.model.Message;
import com.github.eostermueller.snail4j.workload.model.ProcessingUnitImpl;
import com.github.eostermueller.snail4j.workload.model.UseCase;
import com.github.eostermueller.snail4j.workload.model.UseCases;


class ProcessingUnitDetectionTest {

	/**
	 * ProcessingUnit descriptions in the unit tests, ones that would ultimately end up in the GUI,
	 * are specified in these tests as en_US, so that's the locale we'll ask for using this constant.
	 */
//	private static final Locale DEFAULT_LOCALE = Locale.US;
	@Test
	public void canIdentifyProcessingUnits_zeroParameters() throws Snail4jWorkloadException, OnlyStringAndLongAndIntAreAllowedParameterTypes {
		
		UseCases scanResult = WorkloadLibrary
						.scan("com.github.eostermueller.snail4j.workload.annotations.basic");  //limit to package used in this test case.
		
		assertEquals(scanResult.getUseCases().size(),1);
		
		UseCase sortingUseCase = scanResult.getUseCase(Sorting.USE_CASE_NAME);
		
		List<ProcessingUnitImpl> processingUnits = sortingUseCase.getProcessingUnits();
		
		assertEquals(2,processingUnits.size() );
		
		ProcessingUnitImpl binarySortProcessingUnit = processingUnits.get(0);
		assertEquals("binarySort",binarySortProcessingUnit.getMethodWrapper().getMethodName() );
		
		
		assertEquals("Binary Sort in American English",binarySortProcessingUnit.getDescription("en_US") );
		
		assertEquals("Binary Sort in French",binarySortProcessingUnit.getDescription("fr_FR"));
		
		ProcessingUnitImpl selectionSortProcessingUnit = processingUnits.get(1);
		assertEquals("selectionSort",selectionSortProcessingUnit.getMethodWrapper().getMethodName() );
		
		assertEquals("Selection Sort", selectionSortProcessingUnit.getDescription("en_US") );
	}

}
