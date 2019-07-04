package com.github.eostermueller.havoc.workload;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.eostermueller.havoc.workload.DefaultFactory;
import com.github.eostermueller.havoc.workload.HavocException;
import com.github.eostermueller.havoc.workload.annotations.ProcessingUnit;
import com.github.eostermueller.havoc.workload.engine.MethodExecutor;
import com.github.eostermueller.havoc.workload.engine.Workload;
import com.github.eostermueller.havoc.workload.engine.WorkloadBuilder;
import com.github.eostermueller.havoc.workload.engine.WorkloadImpl;
import com.github.eostermueller.havoc.workload.model.ProcessingUnitImpl;
import com.github.eostermueller.havoc.workload.model.UseCase;
import com.github.eostermueller.havoc.workload.model.UseCases;
import com.github.eostermueller.havoc.workload.model.WorkloadSpecRq;
import com.github.eostermueller.havoc.workload.model.json.SerializaionUtil;

class WorkloadBuilderTest {
	private final String USE_CASE_NAME = "busyOptimizedUuid";
	UseCases rq = null;
	@BeforeEach
	void setUp() throws Exception {
		SerializaionUtil util = DefaultFactory.getFactory().createSerializationUtil();
		
		//String js0n = "[{\"processingUnits\":[{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"Reuse same Transformers from pool\"}]},\"useCaseName\":\"xsltTransform\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.xslt.XsltProcessor\",\"name\":\"pooledTransformerXslt\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"Reinstantiate Transformer every time\"}]},\"useCaseName\":\"xsltTransform\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.xslt.XsltProcessor\",\"name\":\"unPooledTransformerXslt\"},\"selected\":true}],\"name\":\"xsltTransform\"}]";

		String js0n = "{\"useCases\":[{\"processingUnits\":[{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"busy - table-based Random - 10 items, 10 iterations\"}]},\"useCaseName\":\"busyOptimizedUuid\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.BusyProcessor\",\"name\":\"randomTableInt_10_10_optimizedUuid\"},\"selected\":true},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"busy - reuse Random - 10 items, 10 iterations\"}]},\"useCaseName\":\"busyOptimizedUuid\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.BusyProcessor\",\"name\":\"randomNextInt_10_10_optimizedUuid\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"busy - table-based Random - 1000 items, 1000 iterations\"}]},\"useCaseName\":\"busyOptimizedUuid\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.BusyProcessor\",\"name\":\"randomTableInt_1000_1000_optimizedUuid\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"busy - threadLocal Random - 1000 items, 1000 iterations\"}]},\"useCaseName\":\"busyOptimizedUuid\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.BusyProcessor\",\"name\":\"randomThreadLocalInt_1000_1000_optimizedUuid\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"busy - reuse Random - 1000 items, 1000 iterations\"}]},\"useCaseName\":\"busyOptimizedUuid\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.BusyProcessor\",\"name\":\"randomNextInt_1000_1000_optimizedUuid\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"busy - threadLocal Random - 10 items, 10 iterations\"}]},\"useCaseName\":\"busyOptimizedUuid\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.BusyProcessor\",\"name\":\"randomThreadLocalInt_10_10_optimizedUuid\"},\"selected\":false}],\"name\":\"busyOptimizedUuid\"},{\"processingUnits\":[{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"busy - threadLocal Random - 10 items, 10 iterations\"}]},\"useCaseName\":\"busySlowUuid\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.BusyProcessor\",\"name\":\"randomThreadLocalInt_10_10\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"busy - threadLocal Random - 1000 items, 1000 iterations\"}]},\"useCaseName\":\"busySlowUuid\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.BusyProcessor\",\"name\":\"randomThreadLocalInt_1000_1000\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"busy - table-based Random - 10 items, 10 iterations\"}]},\"useCaseName\":\"busySlowUuid\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.BusyProcessor\",\"name\":\"randomTableInt_10_10\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"busy - reuse Random - 10 items, 10 iterations\"}]},\"useCaseName\":\"busySlowUuid\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.BusyProcessor\",\"name\":\"randomNextInt_10_10\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"busy - reuse Random - 1000 items, 1000 iterations\"}]},\"useCaseName\":\"busySlowUuid\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.BusyProcessor\",\"name\":\"randomNextInt_1000_1000\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"busy - table-based Random - 1000 items, 1000 iterations\"}]},\"useCaseName\":\"busySlowUuid\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.BusyProcessor\",\"name\":\"randomTableInt_1000_1000\"},\"selected\":false}],\"name\":\"busySlowUuid\"},{\"processingUnits\":[{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"every rq adds 10mb that stays in memory for no more than 60 seconds\"}]},\"useCaseName\":\"memStress\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.MemStress\",\"name\":\"memStress_10mb_lasts_60sec\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"every rq adds 100k that stays in memory for no more than 60 sec\"}]},\"useCaseName\":\"memStress\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.MemStress\",\"name\":\"memStress_100k_lasts_60sec\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"every rq adds 10k that stays in memory for no more than 5 min\"}]},\"useCaseName\":\"memStress\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.MemStress\",\"name\":\"memStress_10k_lasts_5min\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"every rq adds 1mb that stays in memory for no more than 60 seconds\"}]},\"useCaseName\":\"memStress\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.MemStress\",\"name\":\"memStress_1mb_lasts_60sec\"},\"selected\":false}],\"name\":\"memStress\"},{\"processingUnits\":[{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"sleep ms 100\"}]},\"useCaseName\":\"sleep\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.SleepDelay\",\"name\":\"simulateSlowCode_sleepMilliseconds_100\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"sleep ms 1\"}]},\"useCaseName\":\"sleep\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.SleepDelay\",\"name\":\"simulateSlowCode_sleepMilliseconds_1\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"sync sleep ms 10\"}]},\"useCaseName\":\"sleep\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.SleepDelay\",\"name\":\"simulateSynchronizedSlowCode_sleepMilliseconds_10\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"sleep ms 10\"}]},\"useCaseName\":\"sleep\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.SleepDelay\",\"name\":\"simulateSlowCode_sleepMilliseconds_10\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"sleep ms 1000\"}]},\"useCaseName\":\"sleep\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.SleepDelay\",\"name\":\"simulateSlowCode_sleepMilliseconds_1000\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"sync sleep ms 1000\"}]},\"useCaseName\":\"sleep\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.SleepDelay\",\"name\":\"simulateSynchronizedSlowCode_sleepMilliseconds_1000\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"sync sleep ms 1\"}]},\"useCaseName\":\"sleep\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.SleepDelay\",\"name\":\"simulateSynchronizedSlowCode_sleepMilliseconds_1\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"sync sleep ms 100\"}]},\"useCaseName\":\"sleep\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.SleepDelay\",\"name\":\"simulateSynchronizedSlowCode_sleepMilliseconds_100\"},\"selected\":false}],\"name\":\"sleep\"},{\"processingUnits\":[{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"Reuse same Transformers from pool\"}]},\"useCaseName\":\"xsltTransform\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.xslt.XsltProcessor\",\"name\":\"pooledTransformerXslt\"},\"selected\":false},{\"descriptor\":{\"messages\":[{\"locale\":\"en_US\",\"message\":\"Reinstantiate Transformer every time\"}]},\"useCaseName\":\"xsltTransform\",\"method\":{\"parameters\":[],\"declaringClassName\":\"com.github.eostermueller.tjp2.xslt.XsltProcessor\",\"name\":\"unPooledTransformerXslt\"},\"selected\":false}],\"name\":\"xsltTransform\"}]}\n"; 
				
		rq = util.unmmarshalUseCases(js0n);
		
		//This is tested elsewhere
		assertEquals(5,rq.getUseCases().size() );
		
		UseCase useCase = rq.getUseCase(this.USE_CASE_NAME);
		assertEquals( this.USE_CASE_NAME, useCase.getName() );
		
		assertEquals( useCase.getProcessingUnits().size(),6);
		
		ProcessingUnitImpl processingUnit = useCase.getSelectedProcessingUnit();
		
		assertNotNull( processingUnit );
		
		assertTrue( processingUnit.isSelected() );
		
		assertEquals( this.USE_CASE_NAME, processingUnit.getUseCaseName() );
		
		assertEquals( "randomTableInt_10_10_optimizedUuid", processingUnit.getMethodWrapper().getMethodName() );
	
		//only one of the above two is selected.
	}

	@Test
	void canCreateWorkloadFromRq() throws HavocException {
		
		WorkloadBuilder workloadBuilder = DefaultFactory.getFactory().createWorkloadBuilder();
		
		Workload w = workloadBuilder.createWorkload(rq);
		
		assertEquals(1,w.size() );
		
	}

}
