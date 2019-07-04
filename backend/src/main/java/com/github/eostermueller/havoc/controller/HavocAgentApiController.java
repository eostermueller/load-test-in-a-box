package com.github.eostermueller.havoc.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RequestMapping("/havocAgent")
@RestController
@EnableAutoConfiguration
public class HavocAgentApiController {
	@Autowired
	private ResourceLoader resourceLoader;

//	@Autowired
//	private ApplicationContext context;	
//	@RequestMapping(
//		    value = "/test", 
//		    method = RequestMethod.POST)	
//	public String havocAgent() {
//		return "hell0";
//	}
//	@GetMapping("/useCases")
//	public String useCases() {
//		//return this.getUseCasesJson();
//		final String uri = "http://localhost:8080/workload/useCases";
//	     
//	    RestTemplate restTemplate = new RestTemplate();
//	    String result = restTemplate.getForObject(uri, String.class);
//	     
//	    return result;		
//	}
	
	public String useCases_RIG() {
		String rc = "nuttin!";
		try {
			Resource resource = this.resourceLoader.getResource("classpath:/assets/UseCases.json");
            File file = resource.getFile();
            rc =  readFile(file);
            
			//InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("/assets/UseCases.json");
			//File file = new File(getClass().getResource("assets/UseCases.json").getFile());
			//rc =  readFile(file);
//			Resource resource = (Resource) this.context.getResource("/assets/useCases.json");
//			rc = resource.getFile().toString() + "@" + resource.getFilename() + "~" + resource.getURL().toString();
			//rc = resource.toString() + "~" + resource.getDescription() + "@" + resource.getFilename();
			//rc = readFile(resource.getFile());
			
//			ClassPathResource classPathResource = new ClassPathResource("/assets/useCases.json");
//			InputStream inputStream = classPathResource.getInputStream();
//			rc = convertStreamToString(inputStream);
			
		} catch (Throwable re) {
			re.printStackTrace();
		}
		return rc;
//		return "useCases!";
	}
	String readFile(File file) {
		StringBuilder sb = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {

			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {
				sb.append(sCurrentLine);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	static String convertStreamToString(java.io.InputStream is) {
	    Scanner s = new Scanner(is).useDelimiter("\\A");
	    return s.hasNext() ? s.next() : "";
	}
	private String getUseCasesJson_() {
		
		return "{\n" + 
				"	\"useCases\": [\n" + 
				"		{\n" + 
				"			\"processingUnits\": [\n" + 
				"				{\n" + 
				"					\"descriptor\": {\n" + 
				"						\"messages\": [\n" + 
				"							{\n" + 
				"								\"locale\": \"en_US\",\n" + 
				"								\"message\": \"busy - table-based Random - 10 items, 10 iterations\"\n" + 
				"							}\n" + 
				"						]\n" + 
				"					},\n" + 
				"					\"useCaseName\": \"busyOptimizedUuid\",\n" + 
				"					\"method\": {\n" + 
				"						\"parameters\": [],\n" + 
				"						\"declaringClassName\": \"com.github.eostermueller.tjp2.BusyProcessor\",\n" + 
				"						\"name\": \"randomTableInt_10_10_optimizedUuid\"\n" + 
				"					}\n" + 
				"				},\n" + 
				"				{\n" + 
				"					\"descriptor\": {\n" + 
				"						\"messages\": [\n" + 
				"							{\n" + 
				"								\"locale\": \"en_US\",\n" + 
				"								\"message\": \"busy - reuse Random - 10 items, 10 iterations\"\n" + 
				"							}\n" + 
				"						]\n" + 
				"					},\n" + 
				"					\"useCaseName\": \"busyOptimizedUuid\",\n" + 
				"					\"method\": {\n" + 
				"						\"parameters\": [],\n" + 
				"						\"declaringClassName\": \"com.github.eostermueller.tjp2.BusyProcessor\",\n" + 
				"						\"name\": \"randomNextInt_10_10_optimizedUuid\"\n" + 
				"					}\n" + 
				"				},\n" + 
				"				{\n" + 
				"					\"descriptor\": {\n" + 
				"						\"messages\": [\n" + 
				"							{\n" + 
				"								\"locale\": \"en_US\",\n" + 
				"								\"message\": \"busy - table-based Random - 1000 items, 1000 iterations\"\n" + 
				"							}\n" + 
				"						]\n" + 
				"					},\n" + 
				"					\"useCaseName\": \"busyOptimizedUuid\",\n" + 
				"					\"method\": {\n" + 
				"						\"parameters\": [],\n" + 
				"						\"declaringClassName\": \"com.github.eostermueller.tjp2.BusyProcessor\",\n" + 
				"						\"name\": \"randomTableInt_1000_1000_optimizedUuid\"\n" + 
				"					}\n" + 
				"				},\n" + 
				"				{\n" + 
				"					\"descriptor\": {\n" + 
				"						\"messages\": [\n" + 
				"							{\n" + 
				"								\"locale\": \"en_US\",\n" + 
				"								\"message\": \"busy - threadLocal Random - 1000 items, 1000 iterations\"\n" + 
				"							}\n" + 
				"						]\n" + 
				"					},\n" + 
				"					\"useCaseName\": \"busyOptimizedUuid\",\n" + 
				"					\"method\": {\n" + 
				"						\"parameters\": [],\n" + 
				"						\"declaringClassName\": \"com.github.eostermueller.tjp2.BusyProcessor\",\n" + 
				"						\"name\": \"randomThreadLocalInt_1000_1000_optimizedUuid\"\n" + 
				"					}\n" + 
				"				},\n" + 
				"				{\n" + 
				"					\"descriptor\": {\n" + 
				"						\"messages\": [\n" + 
				"							{\n" + 
				"								\"locale\": \"en_US\",\n" + 
				"								\"message\": \"busy - reuse Random - 1000 items, 1000 iterations\"\n" + 
				"							}\n" + 
				"						]\n" + 
				"					},\n" + 
				"					\"useCaseName\": \"busyOptimizedUuid\",\n" + 
				"					\"method\": {\n" + 
				"						\"parameters\": [],\n" + 
				"						\"declaringClassName\": \"com.github.eostermueller.tjp2.BusyProcessor\",\n" + 
				"						\"name\": \"randomNextInt_1000_1000_optimizedUuid\"\n" + 
				"					}\n" + 
				"				},\n" + 
				"				{\n" + 
				"					\"descriptor\": {\n" + 
				"						\"messages\": [\n" + 
				"							{\n" + 
				"								\"locale\": \"en_US\",\n" + 
				"								\"message\": \"busy - threadLocal Random - 10 items, 10 iterations\"\n" + 
				"							}\n" + 
				"						]\n" + 
				"					},\n" + 
				"					\"useCaseName\": \"busyOptimizedUuid\",\n" + 
				"					\"method\": {\n" + 
				"						\"parameters\": [],\n" + 
				"						\"declaringClassName\": \"com.github.eostermueller.tjp2.BusyProcessor\",\n" + 
				"						\"name\": \"randomThreadLocalInt_10_10_optimizedUuid\"\n" + 
				"					}\n" + 
				"				}\n" + 
				"			],\n" + 
				"			\"name\": \"busyOptimizedUuid\"\n" + 
				"		},\n" + 
				"		{\n" + 
				"			\"processingUnits\": [\n" + 
				"				{\n" + 
				"					\"descriptor\": {\n" + 
				"						\"messages\": [\n" + 
				"							{\n" + 
				"								\"locale\": \"en_US\",\n" + 
				"								\"message\": \"busy - threadLocal Random - 10 items, 10 iterations\"\n" + 
				"							}\n" + 
				"						]\n" + 
				"					},\n" + 
				"					\"useCaseName\": \"busySlowUuid\",\n" + 
				"					\"method\": {\n" + 
				"						\"parameters\": [],\n" + 
				"						\"declaringClassName\": \"com.github.eostermueller.tjp2.BusyProcessor\",\n" + 
				"						\"name\": \"randomThreadLocalInt_10_10\"\n" + 
				"					}\n" + 
				"				},\n" + 
				"				{\n" + 
				"					\"descriptor\": {\n" + 
				"						\"messages\": [\n" + 
				"							{\n" + 
				"								\"locale\": \"en_US\",\n" + 
				"								\"message\": \"busy - threadLocal Random - 1000 items, 1000 iterations\"\n" + 
				"							}\n" + 
				"						]\n" + 
				"					},\n" + 
				"					\"useCaseName\": \"busySlowUuid\",\n" + 
				"					\"method\": {\n" + 
				"						\"parameters\": [],\n" + 
				"						\"declaringClassName\": \"com.github.eostermueller.tjp2.BusyProcessor\",\n" + 
				"						\"name\": \"randomThreadLocalInt_1000_1000\"\n" + 
				"					}\n" + 
				"				},\n" + 
				"				{\n" + 
				"					\"descriptor\": {\n" + 
				"						\"messages\": [\n" + 
				"							{\n" + 
				"								\"locale\": \"en_US\",\n" + 
				"								\"message\": \"busy - table-based Random - 10 items, 10 iterations\"\n" + 
				"							}\n" + 
				"						]\n" + 
				"					},\n" + 
				"					\"useCaseName\": \"busySlowUuid\",\n" + 
				"					\"method\": {\n" + 
				"						\"parameters\": [],\n" + 
				"						\"declaringClassName\": \"com.github.eostermueller.tjp2.BusyProcessor\",\n" + 
				"						\"name\": \"randomTableInt_10_10\"\n" + 
				"					}\n" + 
				"				},\n" + 
				"				{\n" + 
				"					\"descriptor\": {\n" + 
				"						\"messages\": [\n" + 
				"							{\n" + 
				"								\"locale\": \"en_US\",\n" + 
				"								\"message\": \"busy - reuse Random - 10 items, 10 iterations\"\n" + 
				"							}\n" + 
				"						]\n" + 
				"					},\n" + 
				"					\"useCaseName\": \"busySlowUuid\",\n" + 
				"					\"method\": {\n" + 
				"						\"parameters\": [],\n" + 
				"						\"declaringClassName\": \"com.github.eostermueller.tjp2.BusyProcessor\",\n" + 
				"						\"name\": \"randomNextInt_10_10\"\n" + 
				"					}\n" + 
				"				},\n" + 
				"				{\n" + 
				"					\"descriptor\": {\n" + 
				"						\"messages\": [\n" + 
				"							{\n" + 
				"								\"locale\": \"en_US\",\n" + 
				"								\"message\": \"busy - reuse Random - 1000 items, 1000 iterations\"\n" + 
				"							}\n" + 
				"						]\n" + 
				"					},\n" + 
				"					\"useCaseName\": \"busySlowUuid\",\n" + 
				"					\"method\": {\n" + 
				"						\"parameters\": [],\n" + 
				"						\"declaringClassName\": \"com.github.eostermueller.tjp2.BusyProcessor\",\n" + 
				"						\"name\": \"randomNextInt_1000_1000\"\n" + 
				"					}\n" + 
				"				},\n" + 
				"				{\n" + 
				"					\"descriptor\": {\n" + 
				"						\"messages\": [\n" + 
				"							{\n" + 
				"								\"locale\": \"en_US\",\n" + 
				"								\"message\": \"busy - table-based Random - 1000 items, 1000 iterations\"\n" + 
				"							}\n" + 
				"						]\n" + 
				"					},\n" + 
				"					\"useCaseName\": \"busySlowUuid\",\n" + 
				"					\"method\": {\n" + 
				"						\"parameters\": [],\n" + 
				"						\"declaringClassName\": \"com.github.eostermueller.tjp2.BusyProcessor\",\n" + 
				"						\"name\": \"randomTableInt_1000_1000\"\n" + 
				"					}\n" + 
				"				}\n" + 
				"			],\n" + 
				"			\"name\": \"busySlowUuid\"\n" + 
				"		},\n" + 
				"		{\n" + 
				"			\"processingUnits\": [\n" + 
				"				{\n" + 
				"					\"descriptor\": {\n" + 
				"						\"messages\": [\n" + 
				"							{\n" + 
				"								\"locale\": \"en_US\",\n" + 
				"								\"message\": \"every rq adds 10mb that stays in memory for no more than 60 seconds\"\n" + 
				"							}\n" + 
				"						]\n" + 
				"					},\n" + 
				"					\"useCaseName\": \"memStress\",\n" + 
				"					\"method\": {\n" + 
				"						\"parameters\": [],\n" + 
				"						\"declaringClassName\": \"com.github.eostermueller.tjp2.MemStress\",\n" + 
				"						\"name\": \"memStress_10mb_lasts_60sec\"\n" + 
				"					}\n" + 
				"				},\n" + 
				"				{\n" + 
				"					\"descriptor\": {\n" + 
				"						\"messages\": [\n" + 
				"							{\n" + 
				"								\"locale\": \"en_US\",\n" + 
				"								\"message\": \"every rq adds 100k that stays in memory for no more than 60 sec\"\n" + 
				"							}\n" + 
				"						]\n" + 
				"					},\n" + 
				"					\"useCaseName\": \"memStress\",\n" + 
				"					\"method\": {\n" + 
				"						\"parameters\": [],\n" + 
				"						\"declaringClassName\": \"com.github.eostermueller.tjp2.MemStress\",\n" + 
				"						\"name\": \"memStress_100k_lasts_60sec\"\n" + 
				"					}\n" + 
				"				},\n" + 
				"				{\n" + 
				"					\"descriptor\": {\n" + 
				"						\"messages\": [\n" + 
				"							{\n" + 
				"								\"locale\": \"en_US\",\n" + 
				"								\"message\": \"every rq adds 10k that stays in memory for no more than 5 min\"\n" + 
				"							}\n" + 
				"						]\n" + 
				"					},\n" + 
				"					\"useCaseName\": \"memStress\",\n" + 
				"					\"method\": {\n" + 
				"						\"parameters\": [],\n" + 
				"						\"declaringClassName\": \"com.github.eostermueller.tjp2.MemStress\",\n" + 
				"						\"name\": \"memStress_10k_lasts_5min\"\n" + 
				"					}\n" + 
				"				},\n" + 
				"				{\n" + 
				"					\"descriptor\": {\n" + 
				"						\"messages\": [\n" + 
				"							{\n" + 
				"								\"locale\": \"en_US\",\n" + 
				"								\"message\": \"every rq adds 1mb that stays in memory for no more than 60 seconds\"\n" + 
				"							}\n" + 
				"						]\n" + 
				"					},\n" + 
				"					\"useCaseName\": \"memStress\",\n" + 
				"					\"method\": {\n" + 
				"						\"parameters\": [],\n" + 
				"						\"declaringClassName\": \"com.github.eostermueller.tjp2.MemStress\",\n" + 
				"						\"name\": \"memStress_1mb_lasts_60sec\"\n" + 
				"					}\n" + 
				"				}\n" + 
				"			],\n" + 
				"			\"name\": \"memStress\"\n" + 
				"		},\n" + 
				"		{\n" + 
				"			\"processingUnits\": [\n" + 
				"				{\n" + 
				"					\"descriptor\": {\n" + 
				"						\"messages\": [\n" + 
				"							{\n" + 
				"								\"locale\": \"en_US\",\n" + 
				"								\"message\": \"sleep ms 100\"\n" + 
				"							}\n" + 
				"						]\n" + 
				"					},\n" + 
				"					\"useCaseName\": \"sleep\",\n" + 
				"					\"method\": {\n" + 
				"						\"parameters\": [],\n" + 
				"						\"declaringClassName\": \"com.github.eostermueller.tjp2.SleepDelay\",\n" + 
				"						\"name\": \"simulateSlowCode_sleepMilliseconds_100\"\n" + 
				"					}\n" + 
				"				},\n" + 
				"				{\n" + 
				"					\"descriptor\": {\n" + 
				"						\"messages\": [\n" + 
				"							{\n" + 
				"								\"locale\": \"en_US\",\n" + 
				"								\"message\": \"sleep ms 1\"\n" + 
				"							}\n" + 
				"						]\n" + 
				"					},\n" + 
				"					\"useCaseName\": \"sleep\",\n" + 
				"					\"method\": {\n" + 
				"						\"parameters\": [],\n" + 
				"						\"declaringClassName\": \"com.github.eostermueller.tjp2.SleepDelay\",\n" + 
				"						\"name\": \"simulateSlowCode_sleepMilliseconds_1\"\n" + 
				"					}\n" + 
				"				},\n" + 
				"				{\n" + 
				"					\"descriptor\": {\n" + 
				"						\"messages\": [\n" + 
				"							{\n" + 
				"								\"locale\": \"en_US\",\n" + 
				"								\"message\": \"sync sleep ms 10\"\n" + 
				"							}\n" + 
				"						]\n" + 
				"					},\n" + 
				"					\"useCaseName\": \"sleep\",\n" + 
				"					\"method\": {\n" + 
				"						\"parameters\": [],\n" + 
				"						\"declaringClassName\": \"com.github.eostermueller.tjp2.SleepDelay\",\n" + 
				"						\"name\": \"simulateSynchronizedSlowCode_sleepMilliseconds_10\"\n" + 
				"					}\n" + 
				"				},\n" + 
				"				{\n" + 
				"					\"descriptor\": {\n" + 
				"						\"messages\": [\n" + 
				"							{\n" + 
				"								\"locale\": \"en_US\",\n" + 
				"								\"message\": \"sleep ms 10\"\n" + 
				"							}\n" + 
				"						]\n" + 
				"					},\n" + 
				"					\"useCaseName\": \"sleep\",\n" + 
				"					\"method\": {\n" + 
				"						\"parameters\": [],\n" + 
				"						\"declaringClassName\": \"com.github.eostermueller.tjp2.SleepDelay\",\n" + 
				"						\"name\": \"simulateSlowCode_sleepMilliseconds_10\"\n" + 
				"					}\n" + 
				"				},\n" + 
				"				{\n" + 
				"					\"descriptor\": {\n" + 
				"						\"messages\": [\n" + 
				"							{\n" + 
				"								\"locale\": \"en_US\",\n" + 
				"								\"message\": \"sleep ms 1000\"\n" + 
				"							}\n" + 
				"						]\n" + 
				"					},\n" + 
				"					\"useCaseName\": \"sleep\",\n" + 
				"					\"method\": {\n" + 
				"						\"parameters\": [],\n" + 
				"						\"declaringClassName\": \"com.github.eostermueller.tjp2.SleepDelay\",\n" + 
				"						\"name\": \"simulateSlowCode_sleepMilliseconds_1000\"\n" + 
				"					}\n" + 
				"				},\n" + 
				"				{\n" + 
				"					\"descriptor\": {\n" + 
				"						\"messages\": [\n" + 
				"							{\n" + 
				"								\"locale\": \"en_US\",\n" + 
				"								\"message\": \"sync sleep ms 1000\"\n" + 
				"							}\n" + 
				"						]\n" + 
				"					},\n" + 
				"					\"useCaseName\": \"sleep\",\n" + 
				"					\"method\": {\n" + 
				"						\"parameters\": [],\n" + 
				"						\"declaringClassName\": \"com.github.eostermueller.tjp2.SleepDelay\",\n" + 
				"						\"name\": \"simulateSynchronizedSlowCode_sleepMilliseconds_1000\"\n" + 
				"					}\n" + 
				"				},\n" + 
				"				{\n" + 
				"					\"descriptor\": {\n" + 
				"						\"messages\": [\n" + 
				"							{\n" + 
				"								\"locale\": \"en_US\",\n" + 
				"								\"message\": \"sync sleep ms 1\"\n" + 
				"							}\n" + 
				"						]\n" + 
				"					},\n" + 
				"					\"useCaseName\": \"sleep\",\n" + 
				"					\"method\": {\n" + 
				"						\"parameters\": [],\n" + 
				"						\"declaringClassName\": \"com.github.eostermueller.tjp2.SleepDelay\",\n" + 
				"						\"name\": \"simulateSynchronizedSlowCode_sleepMilliseconds_1\"\n" + 
				"					}\n" + 
				"				},\n" + 
				"				{\n" + 
				"					\"descriptor\": {\n" + 
				"						\"messages\": [\n" + 
				"							{\n" + 
				"								\"locale\": \"en_US\",\n" + 
				"								\"message\": \"sync sleep ms 100\"\n" + 
				"							}\n" + 
				"						]\n" + 
				"					},\n" + 
				"					\"useCaseName\": \"sleep\",\n" + 
				"					\"method\": {\n" + 
				"						\"parameters\": [],\n" + 
				"						\"declaringClassName\": \"com.github.eostermueller.tjp2.SleepDelay\",\n" + 
				"						\"name\": \"simulateSynchronizedSlowCode_sleepMilliseconds_100\"\n" + 
				"					}\n" + 
				"				}\n" + 
				"			],\n" + 
				"			\"name\": \"sleep\"\n" + 
				"		},\n" + 
				"		{\n" + 
				"			\"processingUnits\": [\n" + 
				"				{\n" + 
				"					\"descriptor\": {\n" + 
				"						\"messages\": [\n" + 
				"							{\n" + 
				"								\"locale\": \"en_US\",\n" + 
				"								\"message\": \"Reuse same Transformers from pool\"\n" + 
				"							}\n" + 
				"						]\n" + 
				"					},\n" + 
				"					\"useCaseName\": \"xsltTransform\",\n" + 
				"					\"method\": {\n" + 
				"						\"parameters\": [],\n" + 
				"						\"declaringClassName\": \"com.github.eostermueller.tjp2.xslt.XsltProcessor\",\n" + 
				"						\"name\": \"pooledTransformerXslt\"\n" + 
				"					}\n" + 
				"				},\n" + 
				"				{\n" + 
				"					\"descriptor\": {\n" + 
				"						\"messages\": [\n" + 
				"							{\n" + 
				"								\"locale\": \"en_US\",\n" + 
				"								\"message\": \"Reinstantiate Transformer every time\"\n" + 
				"							}\n" + 
				"						]\n" + 
				"					},\n" + 
				"					\"useCaseName\": \"xsltTransform\",\n" + 
				"					\"method\": {\n" + 
				"						\"parameters\": [],\n" + 
				"						\"declaringClassName\": \"com.github.eostermueller.tjp2.xslt.XsltProcessor\",\n" + 
				"						\"name\": \"unPooledTransformerXslt\"\n" + 
				"					}\n" + 
				"				}\n" + 
				"			],\n" + 
				"			\"name\": \"xsltTransform\"\n" + 
				"		}\n" + 
				"	]\n" + 
				"}";
	}
	private String getUseCasesJson_0LD() {
		return 
				 "{"
				 +"  \"useCases\": ["
				 +"    {"
				 +"      \"processingUnits\": ["
				 +"        {"
				 +"          \"descriptor\": {"
				 +"            \"messages\": ["
				 +"              {"
				 +"                \"locale\": \"en_US\","
				 +"                \"message\": \"InetAddress.getLocalHost()\""
				 +"              }"
				 +"            ]"
				 +"          },"
				 +"          \"useCaseName\": \"get hostname\","
				 +"          \"method\": {"
				 +"            \"parameters\": [],"
				 +"            \"declaringClassName\": \"com.github.eostermueller.havoc.annotation.samples.collection0.HostnameTest\","
				 +"            \"name\": \"hostnameTest\""
				 +"          }"
				 +"        }"
				 +"      ],"
				 +"      \"name\": \"get hostname\""
				 +"    },"
				 +"    {"
				 +"      \"processingUnits\": ["
				 +"        {"
				 +"          \"descriptor\": {"
				 +"            \"messages\": ["
				 +"              {"
				 +"                \"locale\": \"en_US\","
				 +"                \"message\": \"Using static instance of Random, calling nextInt\""
				 +"              }"
				 +"            ]"
				 +"          },"
				 +"          \"useCaseName\": \"Random Number Generation\","
				 +"          \"method\": {"
				 +"            \"parameters\": ["
				 +"              {"
				 +"                \"parameterType\": \"INTEGER\","
				 +"                \"name\": \"min\","
				 +"                \"defaultLongValue\": 0,"
				 +"                \"defaultIntValue\": 0,"
				 +"                \"descriptor\": {"
				 +"                  \"messages\": ["
				 +"                    {"
				 +"                      \"locale\": \"en_US\","
				 +"                      \"message\": \"smallest value to create\""
				 +"                    }"
				 +"                  ]"
				 +"                }"
				 +"              },"
				 +"              {"
				 +"                \"parameterType\": \"INTEGER\","
				 +"                \"name\": \"max\","
				 +"                \"defaultLongValue\": 0,"
				 +"                \"defaultIntValue\": 0,"
				 +"                \"descriptor\": {"
				 +"                  \"messages\": ["
				 +"                    {"
				 +"                      \"locale\": \"en_US\","
				 +"                      \"message\": \"largest value to create\""
				 +"                    }"
				 +"                  ]"
				 +"                }"
				 +"              }"
				 +"            ],"
				 +"            \"declaringClassName\": \"com.github.eostermueller.havoc.annotation.samples.collection0.RandomTest\","
				 +"            \"name\": \"randomTest_01\""
				 +"          }"
				 +"        },"
				 +"        {"
				 +"          \"descriptor\": {"
				 +"            \"messages\": ["
				 +"              {"
				 +"                \"locale\": \"en_US\","
				 +"                \"message\": \"ThreadLocalRandom\""
				 +"              }"
				 +"            ]"
				 +"          },"
				 +"          \"useCaseName\": \"Random Number Generation\","
				 +"          \"method\": {"
				 +"            \"parameters\": ["
				 +"              {"
				 +"                \"parameterType\": \"INTEGER\","
				 +"                \"name\": \"min\","
				 +"                \"defaultLongValue\": 0,"
				 +"                \"defaultIntValue\": 0,"
				 +"                \"descriptor\": {"
				 +"                  \"messages\": ["
				 +"                    {"
				 +"                      \"locale\": \"en_US\","
				 +"                      \"message\": \"smallest value to create\""
				 +"                    }"
				 +"                  ]"
				 +"                }"
				 +"              },"
				 +"              {"
				 +"                \"parameterType\": \"INTEGER\","
				 +"                \"name\": \"max\","
				 +"                \"defaultLongValue\": 0,"
				 +"                \"defaultIntValue\": 0,"
				 +"                \"descriptor\": {"
				 +"                  \"messages\": ["
				 +"                    {"
				 +"                      \"locale\": \"en_US\","
				 +"                      \"message\": \"largest value to create\""
				 +"                    }"
				 +"                  ]"
				 +"                }"
				 +"              }"
				 +"            ],"
				 +"            \"declaringClassName\": \"com.github.eostermueller.havoc.annotation.samples.collection0.RandomTest\","
				 +"            \"name\": \"randomTest_02\""
				 +"          }"
				 +"        }"
				 +"      ],"
				 +"      \"name\": \"Random Number Generation\""
				 +"    },"
				 +"    {"
				 +"      \"processingUnits\": ["
				 +"        {"
				 +"          \"descriptor\": {"
				 +"            \"messages\": ["
				 +"              {"
				 +"                \"locale\": \"en_US\","
				 +"                \"message\": \"reuse a pre-compiled Pattern\""
				 +"              }"
				 +"            ]"
				 +"          },"
				 +"          \"useCaseName\": \"IP Address Regex\","
				 +"          \"method\": {"
				 +"            \"parameters\": ["
				 +"              {"
				 +"                \"parameterType\": \"STRING\","
				 +"                \"name\": \"IP address match test value\","
				 +"                \"defaultLongValue\": 0,"
				 +"                \"defaultIntValue\": 0,"
				 +"                \"defaultStringValue\": \"127.0.0.1\","
				 +"                \"descriptor\": {"
				 +"                  \"messages\": ["
				 +"                    {"
				 +"                      \"locale\": \"en_US\","
				 +"                      \"message\": \"Enter an IP address, and regex will indicate if its valid.\""
				 +"                    }"
				 +"                  ]"
				 +"                }"
				 +"              }"
				 +"            ],"
				 +"            \"declaringClassName\": \"com.github.eostermueller.havoc.annotation.samples.collection0.RegexTest\","
				 +"            \"name\": \"test03\""
				 +"          }"
				 +"        },"
				 +"        {"
				 +"          \"descriptor\": {"
				 +"            \"messages\": ["
				 +"              {"
				 +"                \"locale\": \"en_US\","
				 +"                \"message\": \"Compile Pattern every time.\""
				 +"              }"
				 +"            ]"
				 +"          },"
				 +"          \"useCaseName\": \"IP Address Regex\","
				 +"          \"method\": {"
				 +"            \"parameters\": ["
				 +"              {"
				 +"                \"parameterType\": \"STRING\","
				 +"                \"name\": \"IP address match test value\","
				 +"                \"defaultLongValue\": 0,"
				 +"                \"defaultIntValue\": 0,"
				 +"                \"defaultStringValue\": \"127.0.0.1\","
				 +"                \"descriptor\": {"
				 +"                  \"messages\": ["
				 +"                    {"
				 +"                      \"locale\": \"en_US\","
				 +"                      \"message\": \"Enter an IP address, and regex will indicate if its valid.\""
				 +"                    }"
				 +"                  ]"
				 +"                }"
				 +"              }"
				 +"            ],"
				 +"            \"declaringClassName\": \"com.github.eostermueller.havoc.annotation.samples.collection0.RegexTest\","
				 +"            \"name\": \"test02\""
				 +"          }"
				 +"        },"
				 +"        {"
				 +"          \"descriptor\": {"
				 +"            \"messages\": ["
				 +"              {"
				 +"                \"locale\": \"en_US\","
				 +"                \"message\": \"String.matches\""
				 +"              }"
				 +"            ]"
				 +"          },"
				 +"          \"useCaseName\": \"IP Address Regex\","
				 +"          \"method\": {"
				 +"            \"parameters\": ["
				 +"              {"
				 +"                \"parameterType\": \"STRING\","
				 +"                \"name\": \"IP address match test value\","
				 +"                \"defaultLongValue\": 0,"
				 +"                \"defaultIntValue\": 0,"
				 +"                \"defaultStringValue\": \"127.0.0.1\","
				 +"                \"descriptor\": {"
				 +"                  \"messages\": ["
				 +"                    {"
				 +"                      \"locale\": \"en_US\","
				 +"                      \"message\": \"Enter an IP address, and regex will indicate if its valid.\""
				 +"                    }"
				 +"                  ]"
				 +"                }"
				 +"              }"
				 +"            ],"
				 +"            \"declaringClassName\": \"com.github.eostermueller.havoc.annotation.samples.collection0.RegexTest\","
				 +"            \"name\": \"test01\""
				 +"          }"
				 +"        }"
				 +"      ],"
				 +"      \"name\": \"IP Address Regex\""
				 +"    },"
				 +"    {"
				 +"      \"processingUnits\": ["
				 +"        {"
				 +"          \"descriptor\": {"
				 +"            \"messages\": ["
				 +"              {"
				 +"                \"locale\": \"en_US\","
				 +"                \"message\": \"Insertion Sort\""
				 +"              }"
				 +"            ]"
				 +"          },"
				 +"          \"useCaseName\": \"numericSorting\","
				 +"          \"method\": {"
				 +"            \"parameters\": ["
				 +"              {"
				 +"                \"parameterType\": \"INTEGER\","
				 +"                \"name\": \"arraySize\","
				 +"                \"defaultLongValue\": 0,"
				 +"                \"defaultIntValue\": 10000,"
				 +"                \"descriptor\": {"
				 +"                  \"messages\": ["
				 +"                    {"
				 +"                      \"locale\": \"en_US\","
				 +"                      \"message\": \"number of intergers to be created (ThreadLocalRandom) and sorted for each execution\""
				 +"                    }"
				 +"                  ]"
				 +"                }"
				 +"              }"
				 +"            ],"
				 +"            \"declaringClassName\": \"com.github.eostermueller.havoc.annotation.samples.collection1.NumericSortingUseCase\","
				 +"            \"name\": \"insertionSort\""
				 +"          }"
				 +"        },"
				 +"        {"
				 +"          \"descriptor\": {"
				 +"            \"messages\": ["
				 +"              {"
				 +"                \"locale\": \"en_US\","
				 +"                \"message\": \"Binary Sort\""
				 +"              }"
				 +"            ]"
				 +"          },"
				 +"          \"useCaseName\": \"numericSorting\","
				 +"          \"method\": {"
				 +"            \"parameters\": ["
				 +"              {"
				 +"                \"parameterType\": \"INTEGER\","
				 +"                \"name\": \"arraySize\","
				 +"                \"defaultLongValue\": 0,"
				 +"                \"defaultIntValue\": 10000,"
				 +"                \"descriptor\": {"
				 +"                  \"messages\": ["
				 +"                    {"
				 +"                      \"locale\": \"en_US\","
				 +"                      \"message\": \"number of intergers to be created (ThreadLocalRandom) and sorted for each execution\""
				 +"                    }"
				 +"                  ]"
				 +"                }"
				 +"              }"
				 +"            ],"
				 +"            \"declaringClassName\": \"com.github.eostermueller.havoc.annotation.samples.collection1.NumericSortingUseCase\","
				 +"            \"name\": \"binarySort\""
				 +"          }"
				 +"        },"
				 +"        {"
				 +"          \"descriptor\": {"
				 +"            \"messages\": ["
				 +"              {"
				 +"                \"locale\": \"en_US\","
				 +"                \"message\": \"Merge Sort\""
				 +"              }"
				 +"            ]"
				 +"          },"
				 +"          \"useCaseName\": \"numericSorting\","
				 +"          \"method\": {"
				 +"            \"parameters\": ["
				 +"              {"
				 +"                \"parameterType\": \"INTEGER\","
				 +"                \"name\": \"arraySize\","
				 +"                \"defaultLongValue\": 0,"
				 +"                \"defaultIntValue\": 10000,"
				 +"                \"descriptor\": {"
				 +"                  \"messages\": ["
				 +"                    {"
				 +"                      \"locale\": \"en_US\","
				 +"                      \"message\": \"number of intergers to be created (ThreadLocalRandom) and sorted for each execution\""
				 +"                    }"
				 +"                  ]"
				 +"                }"
				 +"              }"
				 +"            ],"
				 +"            \"declaringClassName\": \"com.github.eostermueller.havoc.annotation.samples.collection1.NumericSortingUseCase\","
				 +"            \"name\": \"mergeSort\""
				 +"          }"
				 +"        },"
				 +"        {"
				 +"          \"descriptor\": {"
				 +"            \"messages\": ["
				 +"              {"
				 +"                \"locale\": \"en_US\","
				 +"                \"message\": \"Quick Sort\""
				 +"              }"
				 +"            ]"
				 +"          },"
				 +"          \"useCaseName\": \"numericSorting\","
				 +"          \"method\": {"
				 +"            \"parameters\": ["
				 +"              {"
				 +"                \"parameterType\": \"INTEGER\","
				 +"                \"name\": \"arraySize\","
				 +"                \"defaultLongValue\": 0,"
				 +"                \"defaultIntValue\": 10000,"
				 +"                \"descriptor\": {"
				 +"                  \"messages\": ["
				 +"                    {"
				 +"                      \"locale\": \"en_US\","
				 +"                      \"message\": \"number of intergers to be created (ThreadLocalRandom) and sorted for each execution\""
				 +"                    }"
				 +"                  ]"
				 +"                }"
				 +"              }"
				 +"            ],"
				 +"            \"declaringClassName\": \"com.github.eostermueller.havoc.annotation.samples.collection1.NumericSortingUseCase\","
				 +"            \"name\": \"quickSort\""
				 +"          }"
				 +"        },"
				 +"        {"
				 +"          \"descriptor\": {"
				 +"            \"messages\": ["
				 +"              {"
				 +"                \"locale\": \"en_US\","
				 +"                \"message\": \"Selection Sort\""
				 +"              }"
				 +"            ]"
				 +"          },"
				 +"          \"useCaseName\": \"numericSorting\","
				 +"          \"method\": {"
				 +"            \"parameters\": ["
				 +"              {"
				 +"                \"parameterType\": \"INTEGER\","
				 +"                \"name\": \"arraySize\","
				 +"                \"defaultLongValue\": 0,"
				 +"                \"defaultIntValue\": 10000,"
				 +"                \"descriptor\": {"
				 +"                  \"messages\": ["
				 +"                    {"
				 +"                      \"locale\": \"en_US\","
				 +"                      \"message\": \"number of intergers to be created (ThreadLocalRandom) and sorted for each execution\""
				 +"                    }"
				 +"                  ]"
				 +"                }"
				 +"              }"
				 +"            ],"
				 +"            \"declaringClassName\": \"com.github.eostermueller.havoc.annotation.samples.collection1.NumericSortingUseCase\","
				 +"            \"name\": \"selectionSort\""
				 +"          }"
				 +"        }"
				 +"      ],"
				 +"      \"name\": \"numericSorting\""
				 +"    },"
				 +"    {"
				 +"      \"processingUnits\": ["
				 +"        {"
				 +"          \"descriptor\": {"
				 +"            \"messages\": ["
				 +"              {"
				 +"                \"locale\": \"en_US\","
				 +"                \"message\": \"UUID.randomUUID()\""
				 +"              }"
				 +"            ]"
				 +"          },"
				 +"          \"useCaseName\": \"creating UUID\","
				 +"          \"method\": {"
				 +"            \"parameters\": [],"
				 +"            \"declaringClassName\": \"com.github.eostermueller.havoc.annotation.samples.collection1.UUIDTest\","
				 +"            \"name\": \"uuid_02\""
				 +"          }"
				 +"        },"
				 +"        {"
				 +"          \"descriptor\": {"
				 +"            \"messages\": ["
				 +"              {"
				 +"                \"locale\": \"en_US\","
				 +"                \"message\": \"UUID(long,long)\""
				 +"              }"
				 +"            ]"
				 +"          },"
				 +"          \"useCaseName\": \"creating UUID\","
				 +"          \"method\": {"
				 +"            \"parameters\": [],"
				 +"            \"declaringClassName\": \"com.github.eostermueller.havoc.annotation.samples.collection1.UUIDTest\","
				 +"            \"name\": \"uuid_01\""
				 +"          }"
				 +"        }"
				 +"      ],"
				 +"      \"name\": \"creating UUID\""
				 +"    }"
				 +"  ]"
				 +"}";				
	}

}
