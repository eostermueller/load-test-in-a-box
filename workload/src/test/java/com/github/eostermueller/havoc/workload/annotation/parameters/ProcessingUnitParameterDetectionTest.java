package com.github.eostermueller.havoc.workload.annotation.parameters;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.Test;

import com.github.eostermueller.havoc.workload.HavocException;
import com.github.eostermueller.havoc.workload.OnlyStringAndLongAndIntAreAllowedParameterTypes;
import com.github.eostermueller.havoc.workload.model.HavocLibrary;
import com.github.eostermueller.havoc.workload.model.Message;
import com.github.eostermueller.havoc.workload.model.MethodParameter;
import com.github.eostermueller.havoc.workload.model.ProcessingUnitImpl;
import com.github.eostermueller.havoc.workload.model.UseCase;
import com.github.eostermueller.havoc.workload.model.UseCases;


class ProcessingUnitParameterDetectionTest {

	/**
	 * ProcessingUnit descriptions in the unit tests, ones that would ultimately end up in the GUI,
	 * are specified in these tests as en_US, so that's the locale we'll ask for using this constant.
	 */
	private static final Locale DEFAULT_LOCALE = Locale.US;
	@Test
	public void canIdentifyProcessingUnits_twoParameters() throws HavocException, OnlyStringAndLongAndIntAreAllowedParameterTypes {
		
		UseCases scanResult = HavocLibrary
						.scan("com.github.eostermueller.havoc.workload.annotation.parameters");  //limit to package of this test case.
		
		assertEquals(1,scanResult.getUseCases().size());
		
		UseCase sortingUseCase = scanResult.getUseCase(SortingWithParameter.USE_CASE_NAME);
		
		List<ProcessingUnitImpl> processingUnits = sortingUseCase.getProcessingUnits();
		
		assertEquals(1,processingUnits.size() );
				
		ProcessingUnitImpl selectionSortProcessingUnit = processingUnits.get(0);
		assertEquals(selectionSortProcessingUnit.getMethodWrapper().getMethodName(),"selectionSort");
		Message message = selectionSortProcessingUnit.getDescriptor().getMessage(DEFAULT_LOCALE);
		assertEquals("Selection Sort", message.getMessage());
		
		
		MethodParameter parm0 = selectionSortProcessingUnit.getMethodWrapper().getParameter(0);
		assertEquals( "arraySize", parm0.getName());
		assertEquals( "10", parm0.getDefaultValue() );
		assertEquals( "number of intergers to be created (ThreadLocalRandom) and sorted for each execution", parm0.getDescriptor().getMessage(DEFAULT_LOCALE).getMessage() );

		MethodParameter parm1 = selectionSortProcessingUnit.getMethodWrapper().getParameter(1);
		assertEquals( "unknown", parm1.getName());
		assertEquals( "1000", parm1.getDefaultValue() );
		assertEquals( "Used for testing only!", parm1.getDescriptor().getMessage(DEFAULT_LOCALE).getMessage() );

		MethodParameter parm2 = selectionSortProcessingUnit.getMethodWrapper().getParameter(2);
		assertEquals( "hostname", parm2.getName());
		assertEquals( "stubserver.com", parm2.getDefaultValue() );
		assertEquals( "User for testing network speed.", parm2.getDescriptor().getMessage(DEFAULT_LOCALE).getMessage() );
	}

}
