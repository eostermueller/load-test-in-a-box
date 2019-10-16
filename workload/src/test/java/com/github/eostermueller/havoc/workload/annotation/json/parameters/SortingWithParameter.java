package com.github.eostermueller.havoc.workload.annotation.json.parameters;

import java.util.concurrent.ThreadLocalRandom;

import com.github.eostermueller.snail4j.workload.annotations.Param;
import com.github.eostermueller.snail4j.workload.annotations.ProcessingUnit;
import com.github.eostermueller.snail4j.workload.annotations.UserInterfaceDescription;

/**
 * @stolenFrom: https://github.com/akshaybahadur21/Sort/blob/master/Sort.java
 *
 */
public class SortingWithParameter {
	public static final String USE_CASE_NAME = "numericSorting";
	
	
	@ProcessingUnit(
			useCase = "numericSorting", 
			value = {@UserInterfaceDescription("Selection Sort")}
			)
	public  void selectionSort(
			@Param(name="arraySize", defaultValue="10", value=@UserInterfaceDescription("number of intergers to be created (ThreadLocalRandom) and sorted for each execution") ) 
			int arraySize,
			@Param(name="unknown", defaultValue="1000", value=@UserInterfaceDescription("Used for testing only!") ) 
			long someOtherParameter,
			@Param(name="hostname", defaultValue="stubserver.com", value=@UserInterfaceDescription("User for testing network speed.") ) 
			String hostName
			)
	{
		int[] numbersToSort = new int[arraySize];
		
		for(int i = 0; i < arraySize; i++)
			numbersToSort[i] = ThreadLocalRandom.current().nextInt();
			
		
		
		for (int i=0;i<numbersToSort.length-1;i++)
		{
			int imin=i;
			int temp;
			for(int j=i+1;j<numbersToSort.length;j++)
			{
				if(numbersToSort[j]<numbersToSort[imin])
					imin=j;
			}
					temp=numbersToSort[i];
					numbersToSort[i]=numbersToSort[imin];
					numbersToSort[imin]=temp;
		}
		print(numbersToSort,numbersToSort.length);
	}
	
	public  void print(int a[],int n)
	{
		System.out.println();
		for(int i=0;i<n;i++)
			System.out.print(a[i]+"\t");
	}
}
