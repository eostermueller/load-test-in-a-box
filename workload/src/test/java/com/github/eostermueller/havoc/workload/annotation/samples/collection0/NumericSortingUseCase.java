package com.github.eostermueller.havoc.workload.annotation.samples.collection0;

import java.util.concurrent.ThreadLocalRandom;

import com.github.eostermueller.snail4j.workload.annotations.Param;
import com.github.eostermueller.snail4j.workload.annotations.ProcessingUnit;
import com.github.eostermueller.snail4j.workload.annotations.UserInterfaceDescription;

public class NumericSortingUseCase {
	private static final String DEFAULT_ARRAY_SIZE = "10000";
	private static final String ARRAY_SIZE_DESC_EN_US = "number of intergers to be created (ThreadLocalRandom) and sorted for each execution";
	
	private int[] getArrayWithSize(int arraySize) {
		int[] numbersToSort = new int[arraySize];
		
		for(int i = 0; i < arraySize; i++)
			numbersToSort[i] = ThreadLocalRandom.current().nextInt();
			
		return numbersToSort;
	}
	@ProcessingUnit(
			useCase = "numericSorting", 
			value = {@UserInterfaceDescription("Binary Sort")}
			)
	public  void binarySort(
			@Param(name="arraySize", defaultValue=DEFAULT_ARRAY_SIZE, value=@UserInterfaceDescription(ARRAY_SIZE_DESC_EN_US) ) 
			int arraySize
			) {
			
		int[] numbersToSort = this.getArrayWithSize(arraySize);
		
		Sort.BinarySort(numbersToSort, numbersToSort.length);
	}
	
	@ProcessingUnit(
			useCase = "numericSorting", 
			value = {@UserInterfaceDescription("Selection Sort")}
			)
	public  void selectionSort(
			@Param(name="arraySize", defaultValue=DEFAULT_ARRAY_SIZE, value=@UserInterfaceDescription(ARRAY_SIZE_DESC_EN_US) ) 
			int arraySize
			) {
			
		int[] numbersToSort = this.getArrayWithSize(arraySize);
		
		Sort.SelectionSort(numbersToSort, numbersToSort.length);
	}
	@ProcessingUnit(
			useCase = "numericSorting", 
			value = {@UserInterfaceDescription("Insertion Sort")}
			)
	public  void insertionSort(
			@Param(name="arraySize", defaultValue=DEFAULT_ARRAY_SIZE, value=@UserInterfaceDescription(ARRAY_SIZE_DESC_EN_US) ) 
			int arraySize
			) {
			
		int[] numbersToSort = this.getArrayWithSize(arraySize);
		
		Sort.InsertionSort(numbersToSort, numbersToSort.length);
	}
	@ProcessingUnit(
			useCase = "numericSorting", 
			value = {@UserInterfaceDescription("Merge Sort")}
			)
	public  void mergeSort(
			@Param(name="arraySize", defaultValue=DEFAULT_ARRAY_SIZE, value=@UserInterfaceDescription(ARRAY_SIZE_DESC_EN_US) ) 
			int arraySize
			) {
			
		int[] numbersToSort = this.getArrayWithSize(arraySize);
		
		Sort.MergeSort(numbersToSort, numbersToSort.length);
	}
	@ProcessingUnit(
			useCase = "numericSorting", 
			value = {@UserInterfaceDescription("Quick Sort")}
			)
	public  void quickSort(
			@Param(name="arraySize", defaultValue=DEFAULT_ARRAY_SIZE, value=@UserInterfaceDescription("number of intergers to be created (ThreadLocalRandom) and sorted for each execution") ) 
			int arraySize
			) {
			
		int[] numbersToSort = this.getArrayWithSize(arraySize);
		
		Sort.QuickSort(numbersToSort, 0, numbersToSort.length-1);
	}
}
