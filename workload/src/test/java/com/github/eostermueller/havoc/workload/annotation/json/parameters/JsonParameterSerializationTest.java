package com.github.eostermueller.havoc.workload.annotation.json.parameters;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Method;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.github.eostermueller.snail4j.workload.DefaultFactory;
import com.github.eostermueller.snail4j.workload.HavocException;
import com.github.eostermueller.snail4j.workload.model.MethodParameter;
import com.github.eostermueller.snail4j.workload.model.MethodWrapper;
import com.github.eostermueller.snail4j.workload.model.ParameterType;
import com.github.eostermueller.snail4j.workload.model.ProcessingUnitImpl;
import com.github.eostermueller.snail4j.workload.model.UseCases;
import com.github.eostermueller.snail4j.workload.model.json.SerializaionUtil;

/**
 * Annotations were not used in these tests, as they are in the base product.
 * These are slightly lower level tests.
 * The model was built 'by hand' in methods like createTestProcessingUnit_Selection.
 * 
 * 
 * @author erikostermueller
 *
 */
class JsonParameterSerializationTest {

	private static final String USE_CASE_NAME = "Sorting";
	private static final String BINARY_SORT_METHOD_NAME = "binarySort";
	private static final String DECLARING_CLASS = "com.github.eostermueller.havoc.workload.model.json.Sorting";

	static Method[] myMethods = SortingWithParameter.class.getMethods();
	
	static Method selectionSort = null;
	static Method binarySort = null;
	final String languageTag = "en_US";
	final String frLanguageTag = "fr_FR";

	@BeforeAll
	public static void setup() {
		
		for(int i = 0; i < myMethods.length; i++) {
			if (myMethods[i].getName().equals("binarySort"))
				binarySort = myMethods[i];
			else if (myMethods[i].getName().equals("selectionSort"))
				selectionSort = myMethods[i];
		}
		
	}
	
	
	/**
	 * The entire goal of this is to build a GUI that list a bunch of code snippets.
	 * and the GUI user will be able to provide parameters to those snippings (ProcessingUnits).
	 * This test insures that
	 * ---  parm names, data types and default values can be placed into an object model
	 * ---  all the above can be serialized into a json string
	 * ---  ...and then deserialized back into the object model. 
	 * @throws HavocException
	 */

	@Test
	void canDoRoundTripMethodParameterSerialization() throws HavocException {
		UseCases useCases = new UseCases();
		
		useCases.addProcessingUnit( this.createTestProcessingUnit_Selection());
		
		
		SerializaionUtil util = DefaultFactory.getFactory().createSerializationUtil();
		String json = util.marshalUseCases(useCases);
		System.out.println(json);
		
		UseCases newUseCases = util.unmmarshalUseCases(json);
		
		//Destroy this so we don't
		//accidently use it and mistakenly think that
		//we've got good unmarshaled data!
		useCases = null;

		assertEquals(1,newUseCases.getUseCases().size());
		assertEquals(1,newUseCases.getUseCase(USE_CASE_NAME).getProcessingUnits().size());
		
		ProcessingUnitImpl processingUnit = newUseCases.getUseCase(USE_CASE_NAME).getProcessingUnits().get(0);
		
		assertEquals( USE_CASE_NAME, processingUnit.getUseCaseName() );
		
		MethodWrapper m = processingUnit.getMethodWrapper();
		
		assertEquals( "selectionSort", m.getMethodName() );
		
		
		assertEquals( "MyMessage", 
						processingUnit
							.getDescription("en_US") );
		
		assertEquals( "com.github.eostermueller.havoc.workload.annotation.json.parameters.SortingWithParameter", m.getDeclaringClassName() );
		
		assertEquals(3,m.getParameters().size());
		
		MethodParameter parm0 = m.getParameters().get(0);
		assertEquals("hostname",parm0.getName() );
		assertEquals(ParameterType.STRING,parm0.getParameterType());
		assertEquals("mystubserver.com",parm0.getDefaultStringValue() );
		assertEquals("the ProcessingUnit will send HTTP data to this hostname",parm0.getDescription(languageTag));
		assertEquals("FR the ProcessingUnit will send HTTP data to this hostname",parm0.getDescription(frLanguageTag));

		MethodParameter parm1 = m.getParameters().get(1);
		assertEquals("numBytes",parm1.getName() );
		assertEquals(ParameterType.INTEGER,parm1.getParameterType());
		assertEquals(1000,parm1.getDefaultIntValue() );
		assertEquals("The number of bytes that will be added to the heap ever iteration.",parm1.getDescription(languageTag));
		assertEquals("FR The number of bytes that will be added to the heap ever iteration.",parm1.getDescription(frLanguageTag));
		
		MethodParameter parm2 = m.getParameters().get(2);
		assertEquals("duration",parm2.getName() );
		assertEquals(ParameterType.LONG,parm2.getParameterType());
		assertEquals(5500000L,parm2.getDefaultLongValue() );
		assertEquals("the number of milliseconds that each byte will (roughly) spend of the heap",parm2.getDescription(languageTag));
		assertEquals("FR the number of milliseconds that each byte will (roughly) spend of the heap",parm2.getDescription(frLanguageTag));
		
	}
	@Test
	void canSerializeUseCasesToJson() throws HavocException {
		UseCases useCases = new UseCases();
		
		useCases.addProcessingUnit( this.createTestProcessingUnit_Selection());
		
		SerializaionUtil util = DefaultFactory.getFactory().createSerializationUtil();
		String actualJson = util.marshalUseCases(useCases);
		
		System.out.println(actualJson);
		String expectedJson = "{\"useCases\":[{\"processingUnits\":[{\"description\":{\"en_US\":\"MyMessage\"},\"useCaseName\":\"Sorting\",\"selected\":false,\"methodWrapper\":{\"parameters\":[{\"description\":{\"en_US\":\"the ProcessingUnit will send HTTP data to this hostname\",\"fr_FR\":\"FR the ProcessingUnit will send HTTP data to this hostname\"},\"parameterType\":\"STRING\",\"name\":\"hostname\",\"defaultLongValue\":0,\"defaultIntValue\":0,\"defaultStringValue\":\"mystubserver.com\"},{\"description\":{\"en_US\":\"The number of bytes that will be added to the heap ever iteration.\",\"fr_FR\":\"FR The number of bytes that will be added to the heap ever iteration.\"},\"parameterType\":\"INTEGER\",\"name\":\"numBytes\",\"defaultLongValue\":0,\"defaultIntValue\":1000,\"defaultStringValue\":null},{\"description\":{\"en_US\":\"the number of milliseconds that each byte will (roughly) spend of the heap\",\"fr_FR\":\"FR the number of milliseconds that each byte will (roughly) spend of the heap\"},\"parameterType\":\"LONG\",\"name\":\"duration\",\"defaultLongValue\":5500000,\"defaultIntValue\":0,\"defaultStringValue\":null}],\"declaringClassName\":\"com.github.eostermueller.havoc.workload.annotation.json.parameters.SortingWithParameter\",\"methodName\":\"selectionSort\"}}],\"name\":\"Sorting\"}]}";		
		assertEquals(expectedJson, actualJson);
		
	}
	
	
	ProcessingUnitImpl createTestProcessingUnit_Selection() throws HavocException {
		ProcessingUnitImpl processingUnit = new ProcessingUnitImpl();
		processingUnit.setUseCaseName(USE_CASE_NAME);
		
		
		
		processingUnit.addDescription(languageTag,"MyMessage");
		
		MethodWrapper testMethod = new MethodWrapper();
		testMethod.setMethodName("selectionSort");
		testMethod.setDeclaringClassName(SortingWithParameter.class.getName());
		
		MethodParameter parm0 = new MethodParameter();
		parm0.setName("hostname");
		parm0.setParameterType(ParameterType.STRING);
		parm0.setDefaultStringValue("mystubserver.com");
		
		
		parm0.addDescription(languageTag, "the ProcessingUnit will send HTTP data to this hostname");
		parm0.addDescription(frLanguageTag, "FR the ProcessingUnit will send HTTP data to this hostname");
		
		
		testMethod.getParameters().add( parm0 );

		MethodParameter parm1 = new MethodParameter();
		parm1.setName("numBytes");
		parm1.setParameterType(ParameterType.INTEGER);
		parm1.setDefaultIntValue(1000);
		parm1.addDescription(languageTag, "The number of bytes that will be added to the heap ever iteration.");
		parm1.addDescription(frLanguageTag, "FR The number of bytes that will be added to the heap ever iteration.");
		testMethod.getParameters().add( parm1 );

		MethodParameter parm2 = new MethodParameter();
		parm2.setName("duration");
		parm2.setParameterType(ParameterType.LONG);
		parm2.setDefaultLongValue(5500000L);
		parm2.addDescription(languageTag, "the number of milliseconds that each byte will (roughly) spend of the heap");
		parm2.addDescription(frLanguageTag, "FR the number of milliseconds that each byte will (roughly) spend of the heap");

		testMethod.getParameters().add( parm2 );
		
		processingUnit.setMethodWrapper(testMethod);
		
		return processingUnit;
		
	}

}
