package com.github.eostermueller.snail4j.launcher.agent;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.github.eostermueller.snail4j.DefaultFactory;
import com.github.eostermueller.snail4j.launcher.CannotFindSnail4jFactoryClass;
import com.github.eostermueller.snail4j.launcher.Factory;


public class UniqueIdTest {

	@Test
	public void canCreateUniqueIds() throws CannotFindSnail4jFactoryClass {
		
		Factory f = DefaultFactory.getFactory();
		
		Long one = null;
		Long two = null;
		Long three = null;
		Map<String,Long> listOfUniqueIds = new Hashtable<String,Long>();
		one = f.getJvmLifetimeUniqueId();
		two = f.getJvmLifetimeUniqueId();
		three = f.getJvmLifetimeUniqueId();

		listOfUniqueIds.put(String.valueOf(one.intValue()).trim(), one);
		listOfUniqueIds.put(String.valueOf(two.intValue()).trim(), two);
		listOfUniqueIds.put(String.valueOf(three.intValue()).trim(), three);
		
		Assertions.assertEquals( 3, listOfUniqueIds.size(), "i added 3 unique ids, so they should all be here" );
	}
	@Test
	public void canCreateUniqueIdsFromMultipleFactoryInstances() throws CannotFindSnail4jFactoryClass {
				
		Long one = DefaultFactory.getFactory().getJvmLifetimeUniqueId();
		Long two = DefaultFactory.getFactory().getJvmLifetimeUniqueId();
		Long three = DefaultFactory.getFactory().getJvmLifetimeUniqueId();
		
		Map<String,Long> listOfUniqueIds = new Hashtable<String,Long>();
		listOfUniqueIds.put( String.valueOf(one.intValue()), one);
		listOfUniqueIds.put( String.valueOf(two.intValue()), two);
		listOfUniqueIds.put( String.valueOf(three.intValue()), three);
		
		Assertions.assertEquals( 3, listOfUniqueIds.size(), "i added 3 unique ids, so they should all be here" );
	}
	@Test
    public void test01() throws InterruptedException, ExecutionException, CannotFindSnail4jFactoryClass {
        test(1);
    }
	@Test
    public void test08() throws InterruptedException, ExecutionException, CannotFindSnail4jFactoryClass {
        test(8);
    }
	@Test
    public void test32() throws InterruptedException, ExecutionException, CannotFindSnail4jFactoryClass {
        test(32);
    }
	/**
	 * 
	 *   todo:  figure out why I got this intermittent failure one time:
	 * 
	 * 
[INFO] Results:
[INFO]
[ERROR] Failures:
[ERROR]   UniqueIdTest.test08:60->test:100 expected:<[4, 5, 6, 7, 8, 9, 10, 11]> but was:<[3, 4, 5, 6, 7, 8, 9, 10]>                                 
[INFO]
[ERROR] Tests run: 24, Failures: 1, Errors: 0, Skipped: 1
[INFO]
	 *
	 *
	 * 
	 * @stolenFrom:
	 * https://garygregory.wordpress.com/2011/09/09/multi-threaded-unit-testing/
	 * 
	 * @param threadCount
	 * @throws InterruptedException
	 * @throws ExecutionException
	 * @throws CannotFindSnail4jFactoryClass 
	 */
	   private void test(final int threadCount) throws InterruptedException, ExecutionException, CannotFindSnail4jFactoryClass {
	        Factory f = DefaultFactory.getFactory();
	        Callable<Long> task = new Callable<Long>() {
	            @Override
	            public Long call() {
	                return f.getJvmLifetimeUniqueId();
	            }
	        };
	        List<Callable<Long>> tasks = Collections.nCopies(threadCount, task);
	        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
	        List<Future<Long>> futures = executorService.invokeAll(tasks);
	        List<Long> resultList = new ArrayList<Long>(futures.size());
	        // Check for exceptions
	        for (Future<Long> future : futures) {
	            // Throws an exception if an exception was thrown by the task.
	            resultList.add(future.get());
	        }
	        // Validate the IDs
	        assertEquals(threadCount, futures.size());
	        
	        Collections.sort(resultList); //in case the smallest atomic long does not get added first.
	        long start = resultList.get(0);
	        List<Long> expectedList = new ArrayList<Long>(threadCount);
	        for (long i = start; i <= threadCount+start-1; i++) {
	            expectedList.add(i);
	        }
	        Collections.sort(resultList);
	        assertEquals(expectedList, resultList);
	    }
}
