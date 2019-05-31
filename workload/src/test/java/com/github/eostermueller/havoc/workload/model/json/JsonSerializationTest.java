package com.github.eostermueller.havoc.workload.model.json;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Method;
import java.util.Locale;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.github.eostermueller.havoc.workload.DefaultFactory;
import com.github.eostermueller.havoc.workload.HavocException;
import com.github.eostermueller.havoc.workload.model.Descriptor;
import com.github.eostermueller.havoc.workload.model.MethodWrapper;
import com.github.eostermueller.havoc.workload.model.ProcessingUnitImpl;
import com.github.eostermueller.havoc.workload.model.UseCases;
import com.github.eostermueller.havoc.workload.model.WorkloadSpecRq;
import com.github.eostermueller.havoc.workload.model.json.SerializaionUtil;

class JsonSerializationTest {

	private static final String USE_CASE_NAME = "Sorting";
	private static final String BINARY_SORT_METHOD_NAME = "binarySort";
//	private static final String SELECTION_SORT_METHOD_NAME = "selectionSort";
	private static final String DECLARING_CLASS = "com.github.eostermueller.havoc.workload.model.json.Sorting";

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
//	void canCreateProcessingUnitFromJson() throws HavocException {
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
	void canDoRoundTripSerialization() throws HavocException {
		UseCases useCases = new UseCases();
		
		useCases.addProcessingUnit(  this.createTestProcessingUnit_Binary() );
		useCases.addProcessingUnit( this.createTestProcessingUnit_Selection());
		
		
		SerializaionUtil util = DefaultFactory.getFactory().createSerializationUtil();
		String json = util.marshalUseCases(useCases);
		System.out.println(json);
		//String js0n = "{\"useCases\":[{\"processingUnits\":[{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"MyMessage\"}]},\"useCaseName\":\"Sorting\",\"methodWrapper\":{\"parameters\":[],\"descriptor\":{\"messages\":[]},\"declaringClassName\":\"com.github.eostermueller.havoc.workload.model.json.Sorting\",\"name\":\"binarySort\"}},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"MyMessage\"}]},\"useCaseName\":\"Sorting\",\"methodWrapper\":{\"parameters\":[],\"descriptor\":{\"messages\":[]},\"declaringClassName\":\"com.github.eostermueller.havoc.workload.model.json.Sorting\",\"name\":\"selectionSort\"}}],\"name\":\"Sorting\"}]}";
		
		UseCases newUseCases = util.unmmarshalUseCases(json);

		ProcessingUnitImpl processingUnit = newUseCases.getUseCase(USE_CASE_NAME).getProcessingUnits().get(0);
		
		assertEquals( USE_CASE_NAME, processingUnit.getUseCaseName() );
		
		MethodWrapper m = processingUnit.getMethodWrapper();
		
		assertEquals( BINARY_SORT_METHOD_NAME, m.getMethodName() );
		
		Locale locale = Locale.forLanguageTag("en-US");
		assertEquals( "MyMessage", processingUnit
									.getDescriptor()
									.getMessage(locale)
									.getMessage() );
		
		assertEquals( DECLARING_CLASS, m.getDeclaringClassName() );
	}
	@Test
	void canUnmarshallEmptyWorkloadRequestToJson() throws HavocException {
		SerializaionUtil util = DefaultFactory.getFactory().createSerializationUtil();
		String js0n = "{\"useCases\":[]}";
		WorkloadSpecRq rq = util.unmmarshalWorkloadUpdateRq(js0n);
		assertEquals(0,rq.getProcessingUnits().size() );
	}
	
	/**
	 This json test case came from my very first updateWorkload request generated/created by 
	 my angular app. Party down!! Paris, France.  13 Rue Rambuteau.  May 31, 2019.
	 * 
	 * @throws HavocException
	 */
	@Test
	void canUnmarshallWorkloadRequestToJson_real() throws HavocException {
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
	@Test
	void canUnmarshallWorkloadRequestToJson() throws HavocException {
		SerializaionUtil util = DefaultFactory.getFactory().createSerializationUtil();
		String js0n = "{\"processingUnits\":[{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"MyMessage\"}]},\"useCaseName\":\"Sorting\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.havoc.workload.model.json.Sorting\",\"name\":\"binarySort\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"MyMessage\"}]},\"useCaseName\":\"Sorting\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.havoc.workload.model.json.Sorting\",\"name\":\"selectionSort\"},\"selected\":false}]}";
		WorkloadSpecRq rq = util.unmmarshalWorkloadUpdateRq(js0n);
		ProcessingUnitImpl processingUnit = rq.getProcessingUnits().get(0);
		
		assertEquals( USE_CASE_NAME, processingUnit.getUseCaseName() );
		
		MethodWrapper m = processingUnit.getMethodWrapper();
		
		assertEquals( BINARY_SORT_METHOD_NAME, m.getMethodName() );
		
		Locale locale = Locale.forLanguageTag("en-US");
		assertEquals( "MyMessage", processingUnit
									.getDescriptor()
									.getMessage(locale)
									.getMessage() );
		
		assertEquals( DECLARING_CLASS, m.getDeclaringClassName() );
		
	}
	@Test
	void canMarshallWorkloadRequestToJson() throws HavocException {
		WorkloadSpecRq rq = new WorkloadSpecRq();
		
		
		rq.addProcessingUnit(  this.createTestProcessingUnit_Binary() );
		rq.addProcessingUnit( this.createTestProcessingUnit_Selection());
		
		SerializaionUtil util = DefaultFactory.getFactory().createSerializationUtil();
		String actualJson = util.marshalWorkloadSpecRq(rq);
		String expectedJson = "{\"processingUnits\":[{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"MyMessage\"}]},\"useCaseName\":\"Sorting\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.havoc.workload.model.json.Sorting\",\"name\":\"binarySort\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"MyMessage\"}]},\"useCaseName\":\"Sorting\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.havoc.workload.model.json.Sorting\",\"name\":\"selectionSort\"},\"selected\":false}]}";
		
		assertEquals(expectedJson, actualJson);
		
	}
	
	
	@Test
	void canSerializeUseCasesToJson() throws HavocException {
		UseCases useCases = new UseCases();
		
		useCases.addProcessingUnit(  this.createTestProcessingUnit_Binary() );
		useCases.addProcessingUnit( this.createTestProcessingUnit_Selection());
		
		SerializaionUtil util = DefaultFactory.getFactory().createSerializationUtil();
		String actualJson = util.marshalUseCases(useCases);
		String expectedJson = "{\"useCases\":[{\"processingUnits\":[{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"MyMessage\"}]},\"useCaseName\":\"Sorting\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.havoc.workload.model.json.Sorting\",\"name\":\"binarySort\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"MyMessage\"}]},\"useCaseName\":\"Sorting\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.havoc.workload.model.json.Sorting\",\"name\":\"selectionSort\"},\"selected\":false}],\"name\":\"Sorting\"}]}";
		
		assertEquals(expectedJson, actualJson);
		
	}
//{"parameters":[],"descriptions":{},"methodName":"binarySort","declaringClassName":"com.foo.bar.SortingManager"}
//	@Test
//	void canDeSerializeMethod() throws HavocException {
//		String js = "{\"parameters\":[],\"descriptions\":{},\"methodName\":\"binarySort\",\"declaringClassName\":\"com.foo.bar.SortingManager\"}";
//		SerializaionUtil util = DefaultFactory.getFactory().createSerializationUtil();
//		MethodWrapper methodWrapper = util.unmmarshalMethod(js);
//		
//		
//		
//	}
//	@Test
//	void canSerializeMethod() throws HavocException {
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
//	void canSerializeProcessingUnitToJson() throws HavocException {
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
	
	ProcessingUnitImpl createTestProcessingUnit_Binary() throws HavocException {
		ProcessingUnitImpl processingUnit = new ProcessingUnitImpl();
		processingUnit.setUseCaseName(USE_CASE_NAME);
		
		String languageTag = "en-US";
		Locale testLocale = Locale.forLanguageTag(languageTag);
		
		Descriptor d = new Descriptor();
		
		d.addMessage( testLocale, "MyMessage");
		processingUnit.setDescriptor(d);
		
		MethodWrapper testMethod = new MethodWrapper();
		testMethod.setMethodName("binarySort");
		testMethod.setDeclaringClassName(Sorting.class.getName());
		
		
		//testMethod.setMethod(binarySort);
//		testMethod.setDeclaringClassName(DECLARING_CLASS);
//		testMethod.setMethodName(BINARY_SORT_METHOD_NAME);
		
		processingUnit.setMethodWrapper(testMethod);
		
		return processingUnit;
		
	}
	
	ProcessingUnitImpl createTestProcessingUnit_Selection() throws HavocException {
		ProcessingUnitImpl processingUnit = new ProcessingUnitImpl();
		processingUnit.setUseCaseName(USE_CASE_NAME);
		
		String languageTag = "en-US";
		Locale testLocale = Locale.forLanguageTag(languageTag);
		
		Descriptor d = new Descriptor();
		
		d.addMessage( testLocale, "MyMessage");
		processingUnit.setDescriptor(d);
		
		MethodWrapper testMethod = new MethodWrapper();
		testMethod.setMethodName("selectionSort");
		//testMethod.setMethod(selectionSort);
		testMethod.setDeclaringClassName(Sorting.class.getName());
		
		
		processingUnit.setMethodWrapper(testMethod);
		
		return processingUnit;
		
	}

}
