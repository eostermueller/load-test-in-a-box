package com.github.eostermueller.tjp.launcher.agent;

import static org.junit.Assert.*;

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

import org.junit.Test;


public class UniqueIdTest {

	@Test
	public void canCreateUniqueIds() throws CannotFindTjpFactoryClass {
		
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
		
		assertEquals("i added 3 unique ids, so they should all be here", 3, listOfUniqueIds.size() );
	}
	@Test
	public void canCreateUniqueIdsFromMultipleFactoryInstances() throws CannotFindTjpFactoryClass {
				
		Long one = DefaultFactory.getFactory().getJvmLifetimeUniqueId();
		Long two = DefaultFactory.getFactory().getJvmLifetimeUniqueId();
		Long three = DefaultFactory.getFactory().getJvmLifetimeUniqueId();
		
		Map<String,Long> listOfUniqueIds = new Hashtable<String,Long>();
		listOfUniqueIds.put( String.valueOf(one.intValue()), one);
		listOfUniqueIds.put( String.valueOf(two.intValue()), two);
		listOfUniqueIds.put( String.valueOf(three.intValue()), three);
		
		assertEquals("i added 3 unique ids, so they should all be here", 3, listOfUniqueIds.size() );
	}
	@Test
    public void test01() throws InterruptedException, ExecutionException, CannotFindTjpFactoryClass {
        test(1);
    }
	@Test
    public void test08() throws InterruptedException, ExecutionException, CannotFindTjpFactoryClass {
        test(8);
    }
	@Test
    public void test32() throws InterruptedException, ExecutionException, CannotFindTjpFactoryClass {
        test(32);
    }
	/**
	 * @stolenFrom:
	 * https://garygregory.wordpress.com/2011/09/09/multi-threaded-unit-testing/
	 * 
	 * @param threadCount
	 * @throws InterruptedException
	 * @throws ExecutionException
	 * @throws CannotFindTjpFactoryClass 
	 */
	   private void test(final int threadCount) throws InterruptedException, ExecutionException, CannotFindTjpFactoryClass {
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
	        long start = resultList.get(0);
	        List<Long> expectedList = new ArrayList<Long>(threadCount);
	        for (long i = start; i <= threadCount+start-1; i++) {
	            expectedList.add(i);
	        }
	        Collections.sort(resultList);
	        assertEquals(expectedList, resultList);
	    }
}
