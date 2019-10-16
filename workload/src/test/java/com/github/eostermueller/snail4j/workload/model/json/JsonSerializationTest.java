package com.github.eostermueller.snail4j.workload.model.json;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Method;
import java.util.Locale;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.github.eostermueller.snail4j.workload.DefaultFactory;
import com.github.eostermueller.snail4j.workload.Snail4jWorkloadException;
import com.github.eostermueller.snail4j.workload.model.MethodWrapper;
import com.github.eostermueller.snail4j.workload.model.ProcessingUnitImpl;
import com.github.eostermueller.snail4j.workload.model.UseCases;
import com.github.eostermueller.snail4j.workload.model.WorkloadSpecRq;
import com.github.eostermueller.snail4j.workload.model.json.SerializaionUtil;

class JsonSerializationTest {

	private static final String USE_CASE_NAME = "Sorting";
	private static final String BINARY_SORT_METHOD_NAME = "binarySort";
//	private static final String SELECTION_SORT_METHOD_NAME = "selectionSort";
	private static final String DECLARING_CLASS = "com.github.eostermueller.snail4j.workload.model.json.Sorting";

	static Method[] myMethods = Sorting.class.getMethods();
	
	static Method selectionSort = null;
	static Method binarySort = null;

	@BeforeAll
	public static void setup() {
		
		for(int i = 0; i < myMethods.length; i++) {
			if (myMethods[i].getName().equals("binarySort"))
				binarySort = myMethods[i];
			else if (myMethods[i].getName().equals("selectionSort"))
				selectionSort = myMethods[i];
		}
		
	}
	
//	@Test
//	void canCreateProcessingUnitFromJson() throws snail4jException {
//		//String js = "{\"parameters\":[],\"descriptions\":{},\"methodName\":\"binarySort\",\"declaringClassName\":\"com.foo.bar.SortingManager\"}";
//
//		String js = "{\"descriptor\":{\"descriptions\":{\"en-US\":\"MyMessage\"}},\"useCaseName\":\"Sorting\",\"methodWrapper\":{\"parameters\":[],\"descriptions\":{},\"methodName\":\"binarySort\",\"declaringClassName\":\"com.foo.bar.SortingManager\"}}";
//		
//		
//		SerializaionUtil util = DefaultFactory.getFactory().createSerializationUtil();
//		
//		ProcessingUnitImpl processingUnit = util.unmmarshal(js);
//		
//		assertEquals( USE_CASE_NAME, processingUnit.getUseCaseName() );
//		
//		MethodWrapper m = processingUnit.getMethodWrapper();
//		
//		assertEquals( BINARY_SORT_METHOD_NAME, m.getMethodName() );
//		
//		Locale locale = Locale.forLanguageTag("en-US");
//		assertEquals( "MyMessage", m.getDescription(locale) );
//		
//		assertEquals( DECLARING_CLASS, m.getDeclaringClassName() );
//	}

	//@Test
	void canUnmarallUseCases() throws Snail4jWorkloadException {
		String js0n = "{\"useCases\":[{\"processingUnits\":[{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"busy - table-based Random - 10 items, 10 iterations\"}]},\"useCaseName\":\"busyOptimizedUuid\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.BusyProcessor\",\"name\":\"randomTableInt_10_10_optimizedUuid\"},\"selected\":true},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"busy - reuse Random - 10 items, 10 iterations\"}]},\"useCaseName\":\"busyOptimizedUuid\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.BusyProcessor\",\"name\":\"randomNextInt_10_10_optimizedUuid\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"busy - table-based Random - 1000 items, 1000 iterations\"}]},\"useCaseName\":\"busyOptimizedUuid\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.BusyProcessor\",\"name\":\"randomTableInt_1000_1000_optimizedUuid\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"busy - threadLocal Random - 1000 items, 1000 iterations\"}]},\"useCaseName\":\"busyOptimizedUuid\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.BusyProcessor\",\"name\":\"randomThreadLocalInt_1000_1000_optimizedUuid\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"busy - reuse Random - 1000 items, 1000 iterations\"}]},\"useCaseName\":\"busyOptimizedUuid\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.BusyProcessor\",\"name\":\"randomNextInt_1000_1000_optimizedUuid\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"busy - threadLocal Random - 10 items, 10 iterations\"}]},\"useCaseName\":\"busyOptimizedUuid\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.BusyProcessor\",\"name\":\"randomThreadLocalInt_10_10_optimizedUuid\"},\"selected\":false}],\"name\":\"busyOptimizedUuid\"},{\"processingUnits\":[{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"busy - threadLocal Random - 10 items, 10 iterations\"}]},\"useCaseName\":\"busySlowUuid\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.BusyProcessor\",\"name\":\"randomThreadLocalInt_10_10\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"busy - threadLocal Random - 1000 items, 1000 iterations\"}]},\"useCaseName\":\"busySlowUuid\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.BusyProcessor\",\"name\":\"randomThreadLocalInt_1000_1000\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"busy - table-based Random - 10 items, 10 iterations\"}]},\"useCaseName\":\"busySlowUuid\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.BusyProcessor\",\"name\":\"randomTableInt_10_10\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"busy - reuse Random - 10 items, 10 iterations\"}]},\"useCaseName\":\"busySlowUuid\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.BusyProcessor\",\"name\":\"randomNextInt_10_10\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"busy - reuse Random - 1000 items, 1000 iterations\"}]},\"useCaseName\":\"busySlowUuid\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.BusyProcessor\",\"name\":\"randomNextInt_1000_1000\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"busy - table-based Random - 1000 items, 1000 iterations\"}]},\"useCaseName\":\"busySlowUuid\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.BusyProcessor\",\"name\":\"randomTableInt_1000_1000\"},\"selected\":false}],\"name\":\"busySlowUuid\"},{\"processingUnits\":[{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"every rq adds 10mb that stays in memory for no more than 60 seconds\"}]},\"useCaseName\":\"memStress\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.MemStress\",\"name\":\"memStress_10mb_lasts_60sec\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"every rq adds 100k that stays in memory for no more than 60 sec\"}]},\"useCaseName\":\"memStress\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.MemStress\",\"name\":\"memStress_100k_lasts_60sec\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"every rq adds 10k that stays in memory for no more than 5 min\"}]},\"useCaseName\":\"memStress\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.MemStress\",\"name\":\"memStress_10k_lasts_5min\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"every rq adds 1mb that stays in memory for no more than 60 seconds\"}]},\"useCaseName\":\"memStress\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.MemStress\",\"name\":\"memStress_1mb_lasts_60sec\"},\"selected\":false}],\"name\":\"memStress\"},{\"processingUnits\":[{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"sleep ms 100\"}]},\"useCaseName\":\"sleep\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.SleepDelay\",\"name\":\"simulateSlowCode_sleepMilliseconds_100\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"sleep ms 1\"}]},\"useCaseName\":\"sleep\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.SleepDelay\",\"name\":\"simulateSlowCode_sleepMilliseconds_1\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"sync sleep ms 10\"}]},\"useCaseName\":\"sleep\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.SleepDelay\",\"name\":\"simulateSynchronizedSlowCode_sleepMilliseconds_10\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"sleep ms 10\"}]},\"useCaseName\":\"sleep\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.SleepDelay\",\"name\":\"simulateSlowCode_sleepMilliseconds_10\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"sleep ms 1000\"}]},\"useCaseName\":\"sleep\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.SleepDelay\",\"name\":\"simulateSlowCode_sleepMilliseconds_1000\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"sync sleep ms 1000\"}]},\"useCaseName\":\"sleep\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.SleepDelay\",\"name\":\"simulateSynchronizedSlowCode_sleepMilliseconds_1000\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"sync sleep ms 1\"}]},\"useCaseName\":\"sleep\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.SleepDelay\",\"name\":\"simulateSynchronizedSlowCode_sleepMilliseconds_1\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"sync sleep ms 100\"}]},\"useCaseName\":\"sleep\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.SleepDelay\",\"name\":\"simulateSynchronizedSlowCode_sleepMilliseconds_100\"},\"selected\":false}],\"name\":\"sleep\"},{\"processingUnits\":[{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"Reuse same Transformers from pool\"}]},\"useCaseName\":\"xsltTransform\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.xslt.XsltProcessor\",\"name\":\"pooledTransformerXslt\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"Reinstantiate Transformer every time\"}]},\"useCaseName\":\"xsltTransform\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.xslt.XsltProcessor\",\"name\":\"unPooledTransformerXslt\"},\"selected\":false}],\"name\":\"xsltTransform\"}]}";
		SerializaionUtil util = DefaultFactory.getFactory().createSerializationUtil();
		UseCases rq = util.unmmarshalUseCases(js0n);
		assertEquals(5,rq.getUseCases().size() );
	}
	//@Test
	void canUnmarallUseCasesRoundTrip() throws Snail4jWorkloadException {
		String js0n = "{\"useCases\":[{\"processingUnits\":[{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"busy - table-based Random - 10 items, 10 iterations\"}]},\"useCaseName\":\"busyOptimizedUuid\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.BusyProcessor\",\"name\":\"randomTableInt_10_10_optimizedUuid\"},\"selected\":true},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"busy - reuse Random - 10 items, 10 iterations\"}]},\"useCaseName\":\"busyOptimizedUuid\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.BusyProcessor\",\"name\":\"randomNextInt_10_10_optimizedUuid\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"busy - table-based Random - 1000 items, 1000 iterations\"}]},\"useCaseName\":\"busyOptimizedUuid\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.BusyProcessor\",\"name\":\"randomTableInt_1000_1000_optimizedUuid\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"busy - threadLocal Random - 1000 items, 1000 iterations\"}]},\"useCaseName\":\"busyOptimizedUuid\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.BusyProcessor\",\"name\":\"randomThreadLocalInt_1000_1000_optimizedUuid\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"busy - reuse Random - 1000 items, 1000 iterations\"}]},\"useCaseName\":\"busyOptimizedUuid\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.BusyProcessor\",\"name\":\"randomNextInt_1000_1000_optimizedUuid\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"busy - threadLocal Random - 10 items, 10 iterations\"}]},\"useCaseName\":\"busyOptimizedUuid\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.BusyProcessor\",\"name\":\"randomThreadLocalInt_10_10_optimizedUuid\"},\"selected\":false}],\"name\":\"busyOptimizedUuid\"},{\"processingUnits\":[{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"busy - threadLocal Random - 10 items, 10 iterations\"}]},\"useCaseName\":\"busySlowUuid\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.BusyProcessor\",\"name\":\"randomThreadLocalInt_10_10\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"busy - threadLocal Random - 1000 items, 1000 iterations\"}]},\"useCaseName\":\"busySlowUuid\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.BusyProcessor\",\"name\":\"randomThreadLocalInt_1000_1000\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"busy - table-based Random - 10 items, 10 iterations\"}]},\"useCaseName\":\"busySlowUuid\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.BusyProcessor\",\"name\":\"randomTableInt_10_10\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"busy - reuse Random - 10 items, 10 iterations\"}]},\"useCaseName\":\"busySlowUuid\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.BusyProcessor\",\"name\":\"randomNextInt_10_10\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"busy - reuse Random - 1000 items, 1000 iterations\"}]},\"useCaseName\":\"busySlowUuid\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.BusyProcessor\",\"name\":\"randomNextInt_1000_1000\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"busy - table-based Random - 1000 items, 1000 iterations\"}]},\"useCaseName\":\"busySlowUuid\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.BusyProcessor\",\"name\":\"randomTableInt_1000_1000\"},\"selected\":false}],\"name\":\"busySlowUuid\"},{\"processingUnits\":[{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"every rq adds 10mb that stays in memory for no more than 60 seconds\"}]},\"useCaseName\":\"memStress\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.MemStress\",\"name\":\"memStress_10mb_lasts_60sec\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"every rq adds 100k that stays in memory for no more than 60 sec\"}]},\"useCaseName\":\"memStress\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.MemStress\",\"name\":\"memStress_100k_lasts_60sec\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"every rq adds 10k that stays in memory for no more than 5 min\"}]},\"useCaseName\":\"memStress\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.MemStress\",\"name\":\"memStress_10k_lasts_5min\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"every rq adds 1mb that stays in memory for no more than 60 seconds\"}]},\"useCaseName\":\"memStress\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.MemStress\",\"name\":\"memStress_1mb_lasts_60sec\"},\"selected\":false}],\"name\":\"memStress\"},{\"processingUnits\":[{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"sleep ms 100\"}]},\"useCaseName\":\"sleep\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.SleepDelay\",\"name\":\"simulateSlowCode_sleepMilliseconds_100\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"sleep ms 1\"}]},\"useCaseName\":\"sleep\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.SleepDelay\",\"name\":\"simulateSlowCode_sleepMilliseconds_1\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"sync sleep ms 10\"}]},\"useCaseName\":\"sleep\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.SleepDelay\",\"name\":\"simulateSynchronizedSlowCode_sleepMilliseconds_10\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"sleep ms 10\"}]},\"useCaseName\":\"sleep\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.SleepDelay\",\"name\":\"simulateSlowCode_sleepMilliseconds_10\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"sleep ms 1000\"}]},\"useCaseName\":\"sleep\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.SleepDelay\",\"name\":\"simulateSlowCode_sleepMilliseconds_1000\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"sync sleep ms 1000\"}]},\"useCaseName\":\"sleep\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.SleepDelay\",\"name\":\"simulateSynchronizedSlowCode_sleepMilliseconds_1000\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"sync sleep ms 1\"}]},\"useCaseName\":\"sleep\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.SleepDelay\",\"name\":\"simulateSynchronizedSlowCode_sleepMilliseconds_1\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"sync sleep ms 100\"}]},\"useCaseName\":\"sleep\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.SleepDelay\",\"name\":\"simulateSynchronizedSlowCode_sleepMilliseconds_100\"},\"selected\":false}],\"name\":\"sleep\"},{\"processingUnits\":[{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"Reuse same Transformers from pool\"}]},\"useCaseName\":\"xsltTransform\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.xslt.XsltProcessor\",\"name\":\"pooledTransformerXslt\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"Reinstantiate Transformer every time\"}]},\"useCaseName\":\"xsltTransform\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.xslt.XsltProcessor\",\"name\":\"unPooledTransformerXslt\"},\"selected\":false}],\"name\":\"xsltTransform\"}]}";
		SerializaionUtil util = DefaultFactory.getFactory().createSerializationUtil();
		UseCases rq = util.unmmarshalUseCases(js0n);
		assertEquals(5,rq.getUseCases().size() );
		
		String newJson = util.marshalUseCases(rq);
		System.out.println("serialized usecases to:"  + newJson);
		
	}
	@Test
	void canDoRoundTripSerialization() throws Snail4jWorkloadException {
		UseCases useCases = new UseCases();
		
		useCases.addProcessingUnit(  this.createTestProcessingUnit_Binary() );
		useCases.addProcessingUnit( this.createTestProcessingUnit_Selection());
		
		
		SerializaionUtil util = DefaultFactory.getFactory().createSerializationUtil();
		String json = util.marshalUseCases(useCases);
		System.out.println(json);
		
		UseCases newUseCases = util.unmmarshalUseCases(json);

		ProcessingUnitImpl processingUnit = newUseCases.getUseCase(USE_CASE_NAME).getProcessingUnits().get(0);
		
		assertEquals( USE_CASE_NAME, processingUnit.getUseCaseName() );
		
		MethodWrapper m = processingUnit.getMethodWrapper();
		
		assertEquals( BINARY_SORT_METHOD_NAME, m.getMethodName() );
		
		
		Locale locale = Locale.forLanguageTag("en_US");
		
		assertEquals( "MyMessage", processingUnit
									.getDescription("en_US") );
		
		assertEquals( DECLARING_CLASS, m.getDeclaringClassName() );
	}
//	@Test
//	void canUnmarshallEmptyWorkloadRequestToJson() throws snail4jException {
//		SerializaionUtil util = DefaultFactory.getFactory().createSerializationUtil();
//		String js0n = "{\"useCases\":[]}";
//		WorkloadSpecRq rq = util.unmmarshalWorkloadUpdateRq(js0n);
//		assertEquals(0,rq.getProcessingUnits().size() );
//	}
	
	/**
	 This json test case came from my very first updateWorkload request generated/created by 
	 my angular app. Party down!! Paris, France.  13 Rue Rambuteau.  May 31, 2019.
	 * 
	 * @throws Snail4jWorkloadException
	 */
	//@Test
	void canUnmarshallWorkloadRequestToJson_real() throws Snail4jWorkloadException {
		SerializaionUtil util = DefaultFactory.getFactory().createSerializationUtil();
		String js0n = "[{\"processingUnits\":[{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"Reuse same Transformers from pool\"}]},\"useCaseName\":\"xsltTransform\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.xslt.XsltProcessor\",\"name\":\"pooledTransformerXslt\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"Reinstantiate Transformer every time\"}]},\"useCaseName\":\"xsltTransform\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.xslt.XsltProcessor\",\"name\":\"unPooledTransformerXslt\"},\"selected\":true}],\"name\":\"xsltTransform\"}]";

		WorkloadSpecRq rq = util.unmmarshalWorkloadUpdateRq(js0n);
		assertEquals(2,rq.getProcessingUnits().size() );
		ProcessingUnitImpl processingUnit = rq.getProcessingUnits().get(0);
		
		assertEquals( "xsltTransform", processingUnit.getUseCaseName() );
		
		MethodWrapper m = processingUnit.getMethodWrapper();
		
		assertEquals( "pooledTransformerXslt", m.getMethodName() );
		assertFalse( processingUnit.isSelected() );
		
		/**
		 * 
		 */
		processingUnit = rq.getProcessingUnits().get(1);
		
		assertEquals( "xsltTransform", processingUnit.getUseCaseName() );
		
		m = processingUnit.getMethodWrapper();
		
		assertEquals( "unPooledTransformerXslt", m.getMethodName() );
		assertTrue( processingUnit.isSelected() );
		

		
	}	
	//x @Test
	void canUnmarshallWorkloadRequestToJson() throws Snail4jWorkloadException {
		SerializaionUtil util = DefaultFactory.getFactory().createSerializationUtil();
		String js0n = "{\"processingUnits\":[{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"MyMessage\"}]},\"useCaseName\":\"Sorting\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.snail4j.workload.model.json.Sorting\",\"name\":\"binarySort\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"MyMessage\"}]},\"useCaseName\":\"Sorting\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.snail4j.workload.model.json.Sorting\",\"name\":\"selectionSort\"},\"selected\":false}]}";
		WorkloadSpecRq rq = util.unmmarshalWorkloadUpdateRq(js0n);
		ProcessingUnitImpl processingUnit = rq.getProcessingUnits().get(0);
		
		assertEquals( USE_CASE_NAME, processingUnit.getUseCaseName() );
		
		MethodWrapper m = processingUnit.getMethodWrapper();
		
		assertEquals( BINARY_SORT_METHOD_NAME, m.getMethodName() );
		
		assertEquals( "MyMessage", processingUnit.getDescription("en_US") );
		
		assertEquals( DECLARING_CLASS, m.getDeclaringClassName() );
		
	}
	@Test
	void canMarshallWorkloadRequestToJson() throws Snail4jWorkloadException {
		WorkloadSpecRq rq = new WorkloadSpecRq();
		
		
		rq.addProcessingUnit(  this.createTestProcessingUnit_Binary() );
		rq.addProcessingUnit( this.createTestProcessingUnit_Selection());
		
		SerializaionUtil util = DefaultFactory.getFactory().createSerializationUtil();
		String actualJson = util.marshalWorkloadSpecRq(rq);
		String expectedJson = "{\"processingUnits\":[{\"description\":{\"en_US\":\"MyMessage\"},\"useCaseName\":\"Sorting\",\"selected\":false,\"methodWrapper\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.snail4j.workload.model.json.Sorting\",\"methodName\":\"binarySort\"}},{\"description\":{\"en_US\":\"MyMessage\"},\"useCaseName\":\"Sorting\",\"selected\":false,\"methodWrapper\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.snail4j.workload.model.json.Sorting\",\"methodName\":\"selectionSort\"}}]}";
		System.out.println(actualJson);
		assertEquals(expectedJson, actualJson);
	}
	
	
	@Test
	void canSerializeUseCasesToJson() throws Snail4jWorkloadException {
		UseCases useCases = new UseCases();
		
		useCases.addProcessingUnit(  this.createTestProcessingUnit_Binary() );
		useCases.addProcessingUnit( this.createTestProcessingUnit_Selection());
		
		SerializaionUtil util = DefaultFactory.getFactory().createSerializationUtil();
		String actualJson = util.marshalUseCases(useCases);
		System.out.println(actualJson);
		String expectedJs0n = "{\"useCases\":[{\"processingUnits\":[{\"description\":{\"en_US\":\"MyMessage\"},\"useCaseName\":\"Sorting\",\"selected\":false,\"methodWrapper\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.snail4j.workload.model.json.Sorting\",\"methodName\":\"binarySort\"}},{\"description\":{\"en_US\":\"MyMessage\"},\"useCaseName\":\"Sorting\",\"selected\":false,\"methodWrapper\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.snail4j.workload.model.json.Sorting\",\"methodName\":\"selectionSort\"}}],\"name\":\"Sorting\"}]}";
		
		assertEquals(expectedJs0n, actualJson);
		
	}
//{"parameters":[],"descriptions":{},"methodName":"binarySort","declaringClassName":"com.foo.bar.SortingManager"}
//	@Test
//	void canDeSerializeMethod() throws snail4jException {
//		String js = "{\"parameters\":[],\"descriptions\":{},\"methodName\":\"binarySort\",\"declaringClassName\":\"com.foo.bar.SortingManager\"}";
//		SerializaionUtil util = DefaultFactory.getFactory().createSerializationUtil();
//		MethodWrapper methodWrapper = util.unmmarshalMethod(js);
//		
//		
//		
//	}
//	@Test
//	void canSerializeMethod() throws snail4jException {
//		SerializaionUtil util = DefaultFactory.getFactory().createSerializationUtil();
//		MethodWrapper testMethod = new MethodWrapper();
//		testMethod.setDeclaringClassName(DECLARING_CLASS);
//		testMethod.setMethodName(BINARY_SORT_METHOD_NAME);
//		String json = util.marshalMethod(testMethod);
//		
//		System.out.println(json);
//		
//	}
	
//	@Test
//	void canSerializeProcessingUnitToJson() throws snail4jException {
//		
//		SerializaionUtil util = DefaultFactory.getFactory().createSerializationUtil();
//		
//		ProcessingUnitImpl processingUnit = this.createTestProcessingUnitBinary();
//		
//		String json = util.marshalProcessingUnit(processingUnit);
//		
//		System.out.println("xx" + json);
//		
//		
//		//String json = "{\"descriptor\":{\"descriptions\":{\"en-US\":\"MyMessage\"}},\"useCaseName\":\"foo\",\"methodWrapper\":{\"parameters\":[],\"descriptions\":{},\"name\":\"doSomething\",\"declaringClassName\":\"com.foo.bar.Hello\"}}";
//	}
	
	ProcessingUnitImpl createTestProcessingUnit_Binary() throws Snail4jWorkloadException {
		ProcessingUnitImpl processingUnit = new ProcessingUnitImpl();
		processingUnit.setUseCaseName(USE_CASE_NAME);
		
		processingUnit.addDescription("en_US", "MyMessage");
		
		MethodWrapper testMethod = new MethodWrapper();
		testMethod.setMethodName("binarySort");
		testMethod.setDeclaringClassName(Sorting.class.getName());
		
		
		//testMethod.setMethod(binarySort);
//		testMethod.setDeclaringClassName(DECLARING_CLASS);
//		testMethod.setMethodName(BINARY_SORT_METHOD_NAME);
		
		processingUnit.setMethodWrapper(testMethod);
		
		return processingUnit;
		
	}
	
	ProcessingUnitImpl createTestProcessingUnit_Selection() throws Snail4jWorkloadException {
		ProcessingUnitImpl processingUnit = new ProcessingUnitImpl();
		processingUnit.setUseCaseName(USE_CASE_NAME);
		
		processingUnit.addDescription("en_US", "MyMessage");
		
		MethodWrapper testMethod = new MethodWrapper();
		testMethod.setMethodName("selectionSort");
		//testMethod.setMethod(selectionSort);
		testMethod.setDeclaringClassName(Sorting.class.getName());
		
		
		processingUnit.setMethodWrapper(testMethod);
		
		return processingUnit;
		
	}

}
