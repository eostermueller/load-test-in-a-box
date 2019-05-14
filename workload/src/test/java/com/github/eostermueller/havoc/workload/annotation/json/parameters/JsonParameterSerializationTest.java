package com.github.eostermueller.havoc.workload.annotation.json.parameters;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.github.eostermueller.havoc.workload.DefaultFactory;
import com.github.eostermueller.havoc.workload.HavocException;
import com.github.eostermueller.havoc.workload.model.Descriptor;
import com.github.eostermueller.havoc.workload.model.MethodParameter;
import com.github.eostermueller.havoc.workload.model.MethodWrapper;
import com.github.eostermueller.havoc.workload.model.ParameterType;
import com.github.eostermueller.havoc.workload.model.ProcessingUnitImpl;
import com.github.eostermueller.havoc.workload.model.UseCases;
import com.github.eostermueller.havoc.workload.model.json.SerializaionUtil;

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
	final String languageTag = "en-US";
	final Locale testLocale = Locale.forLanguageTag(languageTag);
	final String frLanguageTag = "fr-FR";
	final Locale frLocale = Locale.forLanguageTag(frLanguageTag);

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
		
		Locale locale = Locale.forLanguageTag("en-US");
		assertEquals( "MyMessage", processingUnit
									.getDescriptor()
									.getMessage(locale)
									.getMessage() );
		
		assertEquals( "com.github.eostermueller.havoc.workload.annotation.json.parameters.SortingWithParameter", m.getDeclaringClassName() );
		
		assertEquals(3,m.getParameters().size());
		
		MethodParameter parm0 = m.getParameters().get(0);
		assertEquals("hostname",parm0.getName() );
		assertEquals(ParameterType.STRING,parm0.getParameterType());
		assertEquals("mystubserver.com",parm0.getDefaultStringValue() );
		assertEquals("the ProcessingUnit will send HTTP data to this hostname",parm0.getMessage(testLocale));
		assertEquals("FR the ProcessingUnit will send HTTP data to this hostname",parm0.getMessage(frLocale));

		MethodParameter parm1 = m.getParameters().get(1);
		assertEquals("numBytes",parm1.getName() );
		assertEquals(ParameterType.INTEGER,parm1.getParameterType());
		assertEquals(1000,parm1.getDefaultIntValue() );
		assertEquals("The number of bytes that will be added to the heap ever iteration.",parm1.getMessage(testLocale));
		assertEquals("FR The number of bytes that will be added to the heap ever iteration.",parm1.getMessage(frLocale));
		
		MethodParameter parm2 = m.getParameters().get(2);
		assertEquals("duration",parm2.getName() );
		assertEquals(ParameterType.LONG,parm2.getParameterType());
		assertEquals(5500000L,parm2.getDefaultLongValue() );
		assertEquals("the number of milliseconds that each byte will (roughly) spend of the heap",parm2.getMessage(testLocale));
		assertEquals("FR the number of milliseconds that each byte will (roughly) spend of the heap",parm2.getMessage(frLocale));
		
	}
	@Test
	void canSerializeUseCasesToJson() throws HavocException {
		UseCases useCases = new UseCases();
		
		useCases.addProcessingUnit( this.createTestProcessingUnit_Selection());
		
		SerializaionUtil util = DefaultFactory.getFactory().createSerializationUtil();
		String actualJson = util.marshalUseCases(useCases);
		
		System.out.println(actualJson);
		String expectedJson = "{\"useCases\":[{\"processingUnits\":[{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"MyMessage\"}]},\"useCaseName\":\"Sorting\",\"method\":{\"parameters\":[{\"parameterType\":\"STRING\",\"name\":\"hostname\",\"defaultLongValue\":0,\"defaultIntValue\":0,\"defaultStringValue\":\"mystubserver.com\",\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"the ProcessingUnit will send HTTP data to this hostname\"},{\"locale\":\"fr_FR\",\"message\":\"FR the ProcessingUnit will send HTTP data to this hostname\"}]}},{\"parameterType\":\"INTEGER\",\"name\":\"numBytes\",\"defaultLongValue\":0,\"defaultIntValue\":1000,\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"The number of bytes that will be added to the heap ever iteration.\"},{\"locale\":\"fr_FR\",\"message\":\"FR The number of bytes that will be added to the heap ever iteration.\"}]}},{\"parameterType\":\"LONG\",\"name\":\"duration\",\"defaultLongValue\":5500000,\"defaultIntValue\":0,\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"the number of milliseconds that each byte will (roughly) spend of the heap\"},{\"locale\":\"fr_FR\",\"message\":\"FR the number of milliseconds that each byte will (roughly) spend of the heap\"}]}}],\"declaringClassName\":\"com.github.eostermueller.havoc.workload.annotation.json.parameters.SortingWithParameter\",\"name\":\"selectionSort\"},\"selected\":false}],\"name\":\"Sorting\"}]}";
		
		assertEquals(expectedJson, actualJson);
		
	}
	
	
	ProcessingUnitImpl createTestProcessingUnit_Selection() throws HavocException {
		ProcessingUnitImpl processingUnit = new ProcessingUnitImpl();
		processingUnit.setUseCaseName(USE_CASE_NAME);
		
		
		Descriptor d = new Descriptor();
		
		d.addMessage( testLocale, "MyMessage");
		processingUnit.setDescriptor(d);
		
		MethodWrapper testMethod = new MethodWrapper();
		testMethod.setMethodName("selectionSort");
		testMethod.setDeclaringClassName(SortingWithParameter.class.getName());
		
		MethodParameter parm0 = new MethodParameter();
		parm0.setName("hostname");
		parm0.setParameterType(ParameterType.STRING);
		parm0.setDefaultStringValue("mystubserver.com");
		parm0.addMessage(testLocale, "the ProcessingUnit will send HTTP data to this hostname");
		parm0.addMessage(frLocale, "FR the ProcessingUnit will send HTTP data to this hostname");
		
		
		testMethod.getParameters().add( parm0 );

		MethodParameter parm1 = new MethodParameter();
		parm1.setName("numBytes");
		parm1.setParameterType(ParameterType.INTEGER);
		parm1.setDefaultIntValue(1000);
		parm1.addMessage(testLocale, "The number of bytes that will be added to the heap ever iteration.");
		parm1.addMessage(frLocale, "FR The number of bytes that will be added to the heap ever iteration.");
		testMethod.getParameters().add( parm1 );

		MethodParameter parm2 = new MethodParameter();
		parm2.setName("duration");
		parm2.setParameterType(ParameterType.LONG);
		parm2.setDefaultLongValue(5500000L);
		parm2.addMessage(testLocale, "the number of milliseconds that each byte will (roughly) spend of the heap");
		parm2.addMessage(frLocale, "FR the number of milliseconds that each byte will (roughly) spend of the heap");

		testMethod.getParameters().add( parm2 );
		
		processingUnit.setMethodWrapper(testMethod);
		
		return processingUnit;
		
	}

}
