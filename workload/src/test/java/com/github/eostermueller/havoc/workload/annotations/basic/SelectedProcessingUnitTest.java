package com.github.eostermueller.havoc.workload.annotations.basic;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.Test;

import com.github.eostermueller.snail4j.workload.HavocException;
import com.github.eostermueller.snail4j.workload.OnlyStringAndLongAndIntAreAllowedParameterTypes;
import com.github.eostermueller.snail4j.workload.model.HavocLibrary;
import com.github.eostermueller.snail4j.workload.model.Message;
import com.github.eostermueller.snail4j.workload.model.ProcessingUnitImpl;
import com.github.eostermueller.snail4j.workload.model.UseCase;
import com.github.eostermueller.snail4j.workload.model.UseCases;

class SelectedProcessingUnitTest {
	private static final Locale DEFAULT_LOCALE = Locale.US;

	@Test
	void canDetectSelctedProcessingUnit() throws HavocException, OnlyStringAndLongAndIntAreAllowedParameterTypes {
		
		UseCases scanResult = HavocLibrary
				.scan("com.github.eostermueller.havoc.workload.annotation.samples.defaultselection");  //limit to package used in this test case.

		assertEquals(scanResult.getUseCases().size(),1);
		
		UseCase sortingUseCase = scanResult.getUseCase(Sorting.USE_CASE_NAME);
		
		List<ProcessingUnitImpl> processingUnits = sortingUseCase.getProcessingUnits();
		
		assertEquals(5,processingUnits.size() );
		
		
		ProcessingUnitImpl insertionSortProcessingUnit = processingUnits.get(0);
		assertEquals("insertionSort",insertionSortProcessingUnit.getMethodWrapper().getMethodName() );
		assertTrue( insertionSortProcessingUnit.isSelected() );
		
		ProcessingUnitImpl binarySortProcessingUnit = processingUnits.get(1);
		assertEquals("binarySort",binarySortProcessingUnit.getMethodWrapper().getMethodName() );
		assertFalse( binarySortProcessingUnit.isSelected() );
		
		
		ProcessingUnitImpl mergeSortProcessingUnit = processingUnits.get(2);
		assertEquals("mergeSort",mergeSortProcessingUnit.getMethodWrapper().getMethodName() );
		assertFalse( mergeSortProcessingUnit.isSelected() );
		
		
	}

}
