package com.github.eostermueller.snail4j.workload.model.json;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.github.eostermueller.snail4j.workload.DefaultFactory;
import com.github.eostermueller.snail4j.workload.Snail4jWorkloadException;
import com.github.eostermueller.snail4j.workload.markdown.MarkdownLoader;
import com.github.eostermueller.snail4j.workload.markdown.MarkdownLocator;
import com.github.eostermueller.snail4j.workload.markdown.ParentMarkdownFile;
import com.github.eostermueller.snail4j.workload.model.MethodWrapper;
import com.github.eostermueller.snail4j.workload.model.ProcessingUnitImpl;
import com.github.eostermueller.snail4j.workload.model.UseCases;
import com.github.eostermueller.snail4j.workload.model.WorkloadSpecRq;

public class JsonSerializationTest {

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

	@Test
	public void canUnmarshallUseCase_real() throws Snail4jWorkloadException {
		String js0n = "{\"useCases\":[{\"processingUnits\":[{\"description\":{\"en_US\":\"sleep ms 100\"},\"useCaseName\":\"03_threads_sleep\",\"selected\":false,\"methodWrapper\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.misc.SleepDelay\",\"methodName\":\"simulateSlowCode_sleepMilliseconds_100\"}},{\"description\":{\"en_US\":\"sleep ms 1\"},\"useCaseName\":\"03_threads_sleep\",\"selected\":false,\"methodWrapper\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.misc.SleepDelay\",\"methodName\":\"simulateSlowCode_sleepMilliseconds_1\"}},{\"description\":{\"en_US\":\"sync sleep ms 10\"},\"useCaseName\":\"03_threads_sleep\",\"selected\":false,\"methodWrapper\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.misc.SleepDelay\",\"methodName\":\"simulateSynchronizedSlowCode_sleepMilliseconds_10\"}},{\"description\":{\"en_US\":\"sleep ms 10\"},\"useCaseName\":\"03_threads_sleep\",\"selected\":false,\"methodWrapper\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.misc.SleepDelay\",\"methodName\":\"simulateSlowCode_sleepMilliseconds_10\"}},{\"description\":{\"en_US\":\"sleep ms 1000\"},\"useCaseName\":\"03_threads_sleep\",\"selected\":false,\"methodWrapper\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.misc.SleepDelay\",\"methodName\":\"simulateSlowCode_sleepMilliseconds_1000\"}},{\"description\":{\"en_US\":\"sync sleep ms 1000\"},\"useCaseName\":\"03_threads_sleep\",\"selected\":true,\"methodWrapper\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.misc.SleepDelay\",\"methodName\":\"simulateSynchronizedSlowCode_sleepMilliseconds_1000\"}},{\"description\":{\"en_US\":\"sync sleep ms 1\"},\"useCaseName\":\"03_threads_sleep\",\"selected\":false,\"methodWrapper\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.misc.SleepDelay\",\"methodName\":\"simulateSynchronizedSlowCode_sleepMilliseconds_1\"}},{\"description\":{\"en_US\":\"sync sleep ms 100\"},\"useCaseName\":\"03_threads_sleep\",\"selected\":false,\"methodWrapper\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.misc.SleepDelay\",\"methodName\":\"simulateSynchronizedSlowCode_sleepMilliseconds_100\"}}],\"name\":\"03_threads_sleep\"}],\"origin\":2}";
		SerializaionUtil util = DefaultFactory.getFactory().createSerializationUtil();
		UseCases rq = util.unmmarshalUseCases(js0n);
		assertEquals(1,rq.getUseCases().size() );
		assertEquals(8,rq.getUseCases().get(0).getProcessingUnits().size() );
		assertEquals( 2,rq.getOrigin());
	}

	@Test
	void canUnmarallUseCases() throws Snail4jWorkloadException {
		String js0n = "{\"origin\":2,\"useCases\":[{\"processingUnits\":[{\"description\":{\"en_US\":\"MyMessage\"},\"useCaseName\":\"Sorting\",\"selected\":false,\"methodWrapper\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.snail4j.workload.model.json.Sorting\",\"methodName\":\"binarySort\"}},{\"description\":{\"en_US\":\"MyMessage\"},\"useCaseName\":\"Sorting\",\"selected\":false,\"methodWrapper\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.snail4j.workload.model.json.Sorting\",\"methodName\":\"selectionSort\"}}],\"name\":\"Sorting\"}]}";
		SerializaionUtil util = DefaultFactory.getFactory().createSerializationUtil();
		UseCases rq = util.unmmarshalUseCases(js0n);
		assertEquals(1,rq.getUseCases().size() );
		assertEquals(2,rq.getUseCases().get(0).getProcessingUnits().size() );
		assertEquals( 2,rq.getOrigin());
		
	}
	@Test
	void canUnmarallUseCasesWithDifferentOrder() throws Snail4jWorkloadException {
		String js0n = "{\"useCases\":[{\"processingUnits\":[{\"description\":{\"en_US\":\"MyMessage\"},\"useCaseName\":\"Sorting\",\"selected\":false,\"methodWrapper\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.snail4j.workload.model.json.Sorting\",\"methodName\":\"binarySort\"}},{\"description\":{\"en_US\":\"MyMessage\"},\"useCaseName\":\"Sorting\",\"selected\":false,\"methodWrapper\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.snail4j.workload.model.json.Sorting\",\"methodName\":\"selectionSort\"}}],\"name\":\"Sorting\"}],\"origin\":2}";
		SerializaionUtil util = DefaultFactory.getFactory().createSerializationUtil();
		UseCases rq = util.unmmarshalUseCases(js0n);
		assertEquals(1,rq.getUseCases().size() );
		assertEquals(2,rq.getUseCases().get(0).getProcessingUnits().size() );
		assertEquals( 2,rq.getOrigin());
		
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
	public void canDoRoundTripSerialization_processingUnit() throws Snail4jWorkloadException {
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
		
		
//		Locale locale = Locale.forLanguageTag("en_US");
		
		assertEquals( "MyMessage", processingUnit
									.getDescription("en_US") );
		
		assertEquals( DECLARING_CLASS, m.getDeclaringClassName() );
	}
	
	protected static final String PATH_1 = "com/github/eostermueller/tjp2/md1";
	protected static final String PARENT_1 = PATH_1 + "/index.md";
	protected static final String PARENT_1_CONTENT = "H1 Hello Parent World!";
	protected static final String CHILD_1 = PATH_1 + "/child.md";
	protected static final String CHILD_1_CONTENT = "H1 Hello Child World!";
	Map<String,String> mapOfExpectedFileNames = new Hashtable<String,String>();
	

	private MarkdownLocator getTrivialMarkdownLocator() {
		return new MarkdownLocator() {

			@Override
			public void loadParentFiles(MarkdownLoader loader) throws Snail4jWorkloadException {
				loader.loadMarkdownFile(  Paths.get(PARENT_1), PARENT_1_CONTENT);
			}

			@Override
			public void loadChildFiles(MarkdownLoader loader) throws Snail4jWorkloadException {
				loader.loadMarkdownFile(  Paths.get(CHILD_1), CHILD_1_CONTENT);
			}
			
		};
	}

	
	@Test
	public void canDoRoundTripSerializationOfCms(@TempDir Path tmpFolder) throws Exception {
		
		
		MarkdownLoader loader = DefaultFactory.getFactory().createMarkdownLoader();
		
		loader.setLocator( getTrivialMarkdownLocator() );
		List<ParentMarkdownFile> parentFiles = loader.getMarkdownFiles();
		
		SerializaionUtil util = DefaultFactory.getFactory().createSerializationUtil();
		String json = util.marshalMarkdownFileGroups(parentFiles);

		
		parentFiles=null;
		parentFiles = util.unMarshalMarkdownFileGroups(json);
		
		assertEquals(1,parentFiles.size() );
		assertEquals(1,parentFiles.get(0).getChildMarkdownFiles().size());
		
	}
	
	@Test
	public void canMarshallCmsFiles(@TempDir Path tmpFolder) throws Exception {
		
		MarkdownLoader loader = DefaultFactory.getFactory().createMarkdownLoader();
		
		loader.setLocator( getTrivialMarkdownLocator() );
		List<ParentMarkdownFile> parentFiles = loader.getMarkdownFiles();
		
		SerializaionUtil util = DefaultFactory.getFactory().createSerializationUtil();
		String json = util.marshalMarkdownFileGroups(parentFiles);
		assertTrue( json.length() > 200);
		assertEquals( 1, countInstances( 		"index.md",json) );
		assertEquals( 1, countInstances( 		"child.md"        ,json) );
		assertEquals( 0, countInstances( 		"foo",json) );
		
		
	}	

	@Test
	public void canCountInstancesInString()  {
		
		assertEquals( 0, countInstances( "foo", "bar"				)	);
		assertEquals( 1, countInstances( "foo", "foo"				)	);
		assertEquals( 2, countInstances( "foo", "foo foo"			)	);
		assertEquals( 2, countInstances( "foo", "foofoo"			)	);

		//edge cases     countInstances( 		                    )   );
		assertEquals( 0, countInstances( "foo", ""					)	);
		assertEquals( 0, countInstances( "", ""						)	);
		assertEquals( 0, countInstances( "", "foo"					)	);
		assertEquals( 0, countInstances( "foo", (String)null		)	);
		assertEquals( 0, countInstances( (String)null, "bar"		)	);
		assertEquals( 0, countInstances( (String)null, (String)null	)	);
		assertEquals( 0, countInstances( "foo", "bar"				)	);
		
	}
	int countInstances(String criteria, String src) {
		
		if (  criteria==null
			||src==null
			||criteria.length() > src.length()
			||criteria.length()==0
			||src.length()==0)
			return 0;
		
		int count = 0;
		
		int startIndex = 0;
		while(startIndex != -1) {
			startIndex = src.indexOf(criteria, startIndex);
			if (startIndex>-1) {
				count++;
				startIndex = startIndex+criteria.length();
			}
		}
		return count;
	}
	
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
	public void canMarshallWorkloadRequestToJson() throws Snail4jWorkloadException {
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
	public void canSerializeUseCasesToJson() throws Snail4jWorkloadException {
		UseCases useCases = new UseCases();
		
		useCases.addProcessingUnit(  this.createTestProcessingUnit_Binary() );
		useCases.addProcessingUnit( this.createTestProcessingUnit_Selection());
		
		SerializaionUtil util = DefaultFactory.getFactory().createSerializationUtil();
		String actualJson = util.marshalUseCases(useCases);
		System.out.println(actualJson);
		String expectedJs0n = "{\"origin\":0,\"encryptedKey\":null,\"alias\":null,\"useCases\":[{\"processingUnits\":[{\"description\":{\"en_US\":\"MyMessage\"},\"useCaseName\":\"Sorting\",\"selected\":false,\"methodWrapper\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.snail4j.workload.model.json.Sorting\",\"methodName\":\"binarySort\"}},{\"description\":{\"en_US\":\"MyMessage\"},\"useCaseName\":\"Sorting\",\"selected\":false,\"methodWrapper\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.snail4j.workload.model.json.Sorting\",\"methodName\":\"selectionSort\"}}],\"name\":\"Sorting\"}]}";
		
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
