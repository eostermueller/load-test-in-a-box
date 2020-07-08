package com.github.eostermueller.snail4j.workload;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.github.eostermueller.snail4j.workload.model.ProcessingUnitImpl;
import com.github.eostermueller.snail4j.workload.model.UseCase;
import com.github.eostermueller.snail4j.workload.model.UseCases;
import com.github.eostermueller.snail4j.workload.model.json.SerializaionUtil;

public class WorkloadBuilderTest {
	private final String USE_CASE_NAME = "03_threads_sleep";
	@Test
	public void canValidateUseCase() throws Exception {
		UseCases rq = getUseCases();
		
		assertEquals(1,rq.getUseCases().size() );
		
		assertTrue( rq.validate() );
		
		
	}
	@Test
	public void canCreateWorkloadFromRq() throws Exception {
		UseCases rq = getUseCases();
		
		//This is tested elsewhere
		assertEquals(1,rq.getUseCases().size() );
		
		UseCase useCase = rq.getUseCase(this.USE_CASE_NAME);
		assertEquals( this.USE_CASE_NAME, useCase.getName() );
		
		assertEquals( 8,useCase.getProcessingUnits().size());
		
		ProcessingUnitImpl processingUnit = useCase.getSelectedProcessingUnit();
		
		assertNotNull( processingUnit );
		
		assertTrue( processingUnit.isSelected() );
		
		assertEquals( this.USE_CASE_NAME, processingUnit.getUseCaseName() );
		
		assertEquals( "simulateSynchronizedSlowCode_sleepMilliseconds_1000", processingUnit.getMethodWrapper().getMethodName() );
	
		//only one of the above two is selected.
	}
	private UseCases getUseCases() throws Snail4jWorkloadException {
		SerializaionUtil util = DefaultFactory.getFactory().createSerializationUtil();
		
		String json2 = "{\r\n" + 
				"    \"useCases\": [\r\n" + 
				"      {\r\n" + 
				"        \"processingUnits\": [\r\n" + 
				"          {\r\n" + 
				"            \"description\": {\r\n" + 
				"              \"en_US\": \"sleep ms 100\"\r\n" + 
				"            },\r\n" + 
				"            \"useCaseName\": \"03_threads_sleep\",\r\n" + 
				"            \"selected\": false,\r\n" + 
				"            \"methodWrapper\": {\r\n" + 
				"              \"parameters\": [],\r\n" + 
				"              \"declaringClassName\": \"com.github.eostermueller.tjp2.misc.SleepDelay\",\r\n" + 
				"              \"methodName\": \"simulateSlowCode_sleepMilliseconds_100\"\r\n" + 
				"            }\r\n" + 
				"          },\r\n" + 
				"          {\r\n" + 
				"            \"description\": {\r\n" + 
				"              \"en_US\": \"sleep ms 1\"\r\n" + 
				"            },\r\n" + 
				"            \"useCaseName\": \"03_threads_sleep\",\r\n" + 
				"            \"selected\": false,\r\n" + 
				"            \"methodWrapper\": {\r\n" + 
				"              \"parameters\": [],\r\n" + 
				"              \"declaringClassName\": \"com.github.eostermueller.tjp2.misc.SleepDelay\",\r\n" + 
				"              \"methodName\": \"simulateSlowCode_sleepMilliseconds_1\"\r\n" + 
				"            }\r\n" + 
				"          },\r\n" + 
				"          {\r\n" + 
				"            \"description\": {\r\n" + 
				"              \"en_US\": \"sync sleep ms 10\"\r\n" + 
				"            },\r\n" + 
				"            \"useCaseName\": \"03_threads_sleep\",\r\n" + 
				"            \"selected\": false,\r\n" + 
				"            \"methodWrapper\": {\r\n" + 
				"              \"parameters\": [],\r\n" + 
				"              \"declaringClassName\": \"com.github.eostermueller.tjp2.misc.SleepDelay\",\r\n" + 
				"              \"methodName\": \"simulateSynchronizedSlowCode_sleepMilliseconds_10\"\r\n" + 
				"            }\r\n" + 
				"          },\r\n" + 
				"          {\r\n" + 
				"            \"description\": {\r\n" + 
				"              \"en_US\": \"sleep ms 10\"\r\n" + 
				"            },\r\n" + 
				"            \"useCaseName\": \"03_threads_sleep\",\r\n" + 
				"            \"selected\": false,\r\n" + 
				"            \"methodWrapper\": {\r\n" + 
				"              \"parameters\": [],\r\n" + 
				"              \"declaringClassName\": \"com.github.eostermueller.tjp2.misc.SleepDelay\",\r\n" + 
				"              \"methodName\": \"simulateSlowCode_sleepMilliseconds_10\"\r\n" + 
				"            }\r\n" + 
				"          },\r\n" + 
				"          {\r\n" + 
				"            \"description\": {\r\n" + 
				"              \"en_US\": \"sleep ms 1000\"\r\n" + 
				"            },\r\n" + 
				"            \"useCaseName\": \"03_threads_sleep\",\r\n" + 
				"            \"selected\": false,\r\n" + 
				"            \"methodWrapper\": {\r\n" + 
				"              \"parameters\": [],\r\n" + 
				"              \"declaringClassName\": \"com.github.eostermueller.tjp2.misc.SleepDelay\",\r\n" + 
				"              \"methodName\": \"simulateSlowCode_sleepMilliseconds_1000\"\r\n" + 
				"            }\r\n" + 
				"          },\r\n" + 
				"          {\r\n" + 
				"            \"description\": {\r\n" + 
				"              \"en_US\": \"sync sleep ms 1000\"\r\n" + 
				"            },\r\n" + 
				"            \"useCaseName\": \"03_threads_sleep\",\r\n" + 
				"            \"selected\": true,\r\n" + 
				"            \"methodWrapper\": {\r\n" + 
				"              \"parameters\": [],\r\n" + 
				"              \"declaringClassName\": \"com.github.eostermueller.tjp2.misc.SleepDelay\",\r\n" + 
				"              \"methodName\": \"simulateSynchronizedSlowCode_sleepMilliseconds_1000\"\r\n" + 
				"            }\r\n" + 
				"          },\r\n" + 
				"          {\r\n" + 
				"            \"description\": {\r\n" + 
				"              \"en_US\": \"sync sleep ms 1\"\r\n" + 
				"            },\r\n" + 
				"            \"useCaseName\": \"03_threads_sleep\",\r\n" + 
				"            \"selected\": false,\r\n" + 
				"            \"methodWrapper\": {\r\n" + 
				"              \"parameters\": [],\r\n" + 
				"              \"declaringClassName\": \"com.github.eostermueller.tjp2.misc.SleepDelay\",\r\n" + 
				"              \"methodName\": \"simulateSynchronizedSlowCode_sleepMilliseconds_1\"\r\n" + 
				"            }\r\n" + 
				"          },\r\n" + 
				"          {\r\n" + 
				"            \"description\": {\r\n" + 
				"              \"en_US\": \"sync sleep ms 100\"\r\n" + 
				"            },\r\n" + 
				"            \"useCaseName\": \"03_threads_sleep\",\r\n" + 
				"            \"selected\": false,\r\n" + 
				"            \"methodWrapper\": {\r\n" + 
				"              \"parameters\": [],\r\n" + 
				"              \"declaringClassName\": \"com.github.eostermueller.tjp2.misc.SleepDelay\",\r\n" + 
				"              \"methodName\": \"simulateSynchronizedSlowCode_sleepMilliseconds_100\"\r\n" + 
				"            }\r\n" + 
				"          }\r\n" + 
				"        ],\r\n" + 
				"        \"name\": \"03_threads_sleep\"\r\n" + 
				"      }\r\n" + 
				"    ]\r\n" + 
				"  }";		
		return( util.unmmarshalUseCases(json2) );
	}

//	@Test
//	void canCreateWorkloadFromRq() throws HavocException {
//		
//		WorkloadBuilder workloadBuilder = DefaultFactory.getFactory().createWorkloadBuilder();
//		
//		Workload w = workloadBuilder.createWorkload(rq);
//		
//		assertEquals(1,w.size() );
//		
//	}

}
