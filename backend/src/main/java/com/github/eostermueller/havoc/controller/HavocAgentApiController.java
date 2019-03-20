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

@RequestMapping("/havocAgent")
@RestController
@EnableAutoConfiguration
public class HavocAgentApiController {
	@Autowired
	private ResourceLoader resourceLoader;

//	@Autowired
//	private ApplicationContext context;	
	@RequestMapping(
		    value = "/test", 
		    method = RequestMethod.POST)	
	public String havocAgent() {
		return "hell0";
	}
	@GetMapping("/elements.json")
	public String elements() {
		return getElements();
	}
	@GetMapping("/useCases")
	public String useCases() {
		return this.getUseCasesJson();
	}
	
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
	private String getUseCasesJson() {
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

	private String getElements() {
		StringBuilder sb = new StringBuilder();
	    sb.append("  {");
	    sb.append("      \"elements\": [");
	    sb.append("        {");
	    sb.append("            \"name\": \"Hydrogen\",");
	    sb.append("            \"appearance\": \"colorless gas\",");
	    sb.append("            \"atomic_mass\": 1.008,");
	    sb.append("            \"boil\": 20.271,");
	    sb.append("            \"category\": \"diatomic nonmetal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 0.08988,");
	    sb.append("            \"discovered_by\": \"Henry Cavendish\",");
	    sb.append("            \"melt\": 13.99,");
	    sb.append("            \"molar_heat\": 28.836,");
	    sb.append("            \"named_by\": \"Antoine Lavoisier\",");
	    sb.append("            \"number\": 1,");
	    sb.append("            \"period\": 1,");
	    sb.append("            \"phase\": \"Gas\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Hydrogen\",");
	    sb.append("            \"spectral_img\": \"https://en.wikipedia.org/wiki/File:Hydrogen_Spectra.jpg\",");
	    sb.append("            \"summary\": \"Hydrogen is a chemical element with chemical symbol H and atomic number 1. With an atomic weight of 1.00794 u, hydrogen is the lightest element on the periodic table. Its monatomic form (H) is the most abundant chemical substance in the Universe, constituting roughly 75% of all baryonic mass.\",");
	    sb.append("            \"symbol\": \"H\",");
	    sb.append("            \"xpos\": 1,");
	    sb.append("            \"ypos\": 1,");
	    sb.append("            \"shells\": [");
	    sb.append("                1");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Helium\",");
	    sb.append("            \"appearance\": \"colorless gas, exhibiting a red-orange glow when placed in a high-voltage electric field\",");
	    sb.append("            \"atomic_mass\": 4.0026022,");
	    sb.append("            \"boil\": 4.222,");
	    sb.append("            \"category\": \"noble gas\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 0.1786,");
	    sb.append("            \"discovered_by\": \"Pierre Janssen\",");
	    sb.append("            \"melt\": 0.95,");
	    sb.append("            \"molar_heat\": null,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 2,");
	    sb.append("            \"period\": 1,");
	    sb.append("            \"phase\": \"Gas\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Helium\",");
	    sb.append("            \"spectral_img\": \"https://en.wikipedia.org/wiki/File:Helium_spectrum.jpg\",");
	    sb.append("            \"summary\": \"Helium is a chemical element with symbol He and atomic number 2. It is a colorless, odorless, tasteless, non-toxic, inert, monatomic gas that heads the noble gas group in the periodic table. Its boiling and melting points are the lowest among all the elements.\",");
	    sb.append("            \"symbol\": \"He\",");
	    sb.append("            \"xpos\": 18,");
	    sb.append("            \"ypos\": 1,");
	    sb.append("            \"shells\": [");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Lithium\",");
	    sb.append("            \"appearance\": \"silvery-white\",");
	    sb.append("            \"atomic_mass\": 6.94,");
	    sb.append("            \"boil\": 1603,");
	    sb.append("            \"category\": \"alkali metal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 0.534,");
	    sb.append("            \"discovered_by\": \"Johan August Arfwedson\",");
	    sb.append("            \"melt\": 453.65,");
	    sb.append("            \"molar_heat\": 24.86,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 3,");
	    sb.append("            \"period\": 2,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Lithium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Lithium (from Greek:\u03bb\u03af\u03b8\u03bf\u03c2 lithos, 'stone') is a chemical element with the symbol Li and atomic number 3. It is a soft, silver-white metal belonging to the alkali metal group of chemical elements. Under standard conditions it is the lightest metal and the least dense solid element.\",");
	    sb.append("            \"symbol\": \"Li\",");
	    sb.append("            \"xpos\": 1,");
	    sb.append("            \"ypos\": 2,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                1");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Beryllium\",");
	    sb.append("            \"appearance\": \"white-gray metallic\",");
	    sb.append("            \"atomic_mass\": 9.01218315,");
	    sb.append("            \"boil\": 2742,");
	    sb.append("            \"category\": \"alkaline earth metal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 1.85,");
	    sb.append("            \"discovered_by\": \"Louis Nicolas Vauquelin\",");
	    sb.append("            \"melt\": 1560,");
	    sb.append("            \"molar_heat\": 16.443,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 4,");
	    sb.append("            \"period\": 2,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Beryllium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Beryllium is a chemical element with symbol Be and atomic number 4. It is created through stellar nucleosynthesis and is a relatively rare element in the universe. It is a divalent element which occurs naturally only in combination with other elements in minerals.\",");
	    sb.append("            \"symbol\": \"Be\",");
	    sb.append("            \"xpos\": 2,");
	    sb.append("            \"ypos\": 2,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Boron\",");
	    sb.append("            \"appearance\": \"black-brown\",");
	    sb.append("            \"atomic_mass\": 10.81,");
	    sb.append("            \"boil\": 4200,");
	    sb.append("            \"category\": \"metalloid\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 2.08,");
	    sb.append("            \"discovered_by\": \"Joseph Louis Gay-Lussac\",");
	    sb.append("            \"melt\": 2349,");
	    sb.append("            \"molar_heat\": 11.087,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 5,");
	    sb.append("            \"period\": 2,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Boron\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Boron is a metalloid chemical element with symbol B and atomic number 5. Produced entirely by cosmic ray spallation and supernovae and not by stellar nucleosynthesis, it is a low-abundance element in both the Solar system and the Earth's crust. Boron is concentrated on Earth by the water-solubility of its more common naturally occurring compounds, the borate minerals.\",");
	    sb.append("            \"symbol\": \"B\",");
	    sb.append("            \"xpos\": 13,");
	    sb.append("            \"ypos\": 2,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                3");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Carbon\",");
	    sb.append("            \"appearance\": null,");
	    sb.append("            \"atomic_mass\": 12.011,");
	    sb.append("            \"boil\": null,");
	    sb.append("            \"category\": \"polyatomic nonmetal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 1.821,");
	    sb.append("            \"discovered_by\": \"Ancient Egypt\",");
	    sb.append("            \"melt\": null,");
	    sb.append("            \"molar_heat\": 8.517,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 6,");
	    sb.append("            \"period\": 2,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Carbon\",");
	    sb.append("            \"spectral_img\": \"https://en.wikipedia.org/wiki/File:Carbon_Spectra.jpg\",");
	    sb.append("            \"summary\": \"Carbon (from Latin:carbo 'coal') is a chemical element with symbol C and atomic number 6. On the periodic table, it is the first (row 2) of six elements in column (group) 14, which have in common the composition of their outer electron shell. It is nonmetallic and tetravalent\u2014making four electrons available to form covalent chemical bonds.\",");
	    sb.append("            \"symbol\": \"C\",");
	    sb.append("            \"xpos\": 14,");
	    sb.append("            \"ypos\": 2,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                4");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Nitrogen\",");
	    sb.append("            \"appearance\": \"colorless gas, liquid or solid\",");
	    sb.append("            \"atomic_mass\": 14.007,");
	    sb.append("            \"boil\": 77.355,");
	    sb.append("            \"category\": \"diatomic nonmetal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 1.251,");
	    sb.append("            \"discovered_by\": \"Daniel Rutherford\",");
	    sb.append("            \"melt\": 63.15,");
	    sb.append("            \"molar_heat\": null,");
	    sb.append("            \"named_by\": \"Jean-Antoine Chaptal\",");
	    sb.append("            \"number\": 7,");
	    sb.append("            \"period\": 2,");
	    sb.append("            \"phase\": \"Gas\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Nitrogen\",");
	    sb.append("            \"spectral_img\": \"https://en.wikipedia.org/wiki/File:Nitrogen_Spectra.jpg\",");
	    sb.append("            \"summary\": \"Nitrogen is a chemical element with symbol N and atomic number 7. It is the lightest pnictogen and at room temperature, it is a transparent, odorless diatomic gas. Nitrogen is a common element in the universe, estimated at about seventh in total abundance in the Milky Way and the Solar System.\",");
	    sb.append("            \"symbol\": \"N\",");
	    sb.append("            \"xpos\": 15,");
	    sb.append("            \"ypos\": 2,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                5");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Oxygen\",");
	    sb.append("            \"appearance\": null,");
	    sb.append("            \"atomic_mass\": 15.999,");
	    sb.append("            \"boil\": 90.188,");
	    sb.append("            \"category\": \"diatomic nonmetal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 1.429,");
	    sb.append("            \"discovered_by\": \"Carl Wilhelm Scheele\",");
	    sb.append("            \"melt\": 54.36,");
	    sb.append("            \"molar_heat\": null,");
	    sb.append("            \"named_by\": \"Antoine Lavoisier\",");
	    sb.append("            \"number\": 8,");
	    sb.append("            \"period\": 2,");
	    sb.append("            \"phase\": \"Gas\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Oxygen\",");
	    sb.append("            \"spectral_img\": \"https://en.wikipedia.org/wiki/File:Oxygen_spectre.jpg\",");
	    sb.append("            \"summary\": \"Oxygen is a chemical element with symbol O and atomic number 8. It is a member of the chalcogen group on the periodic table and is a highly reactive nonmetal and oxidizing agent that readily forms compounds (notably oxides) with most elements. By mass, oxygen is the third-most abundant element in the universe, after hydrogen and helium.\",");
	    sb.append("            \"symbol\": \"O\",");
	    sb.append("            \"xpos\": 16,");
	    sb.append("            \"ypos\": 2,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                6");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Fluorine\",");
	    sb.append("            \"appearance\": null,");
	    sb.append("            \"atomic_mass\": 18.9984031636,");
	    sb.append("            \"boil\": 85.03,");
	    sb.append("            \"category\": \"diatomic nonmetal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 1.696,");
	    sb.append("            \"discovered_by\": \"Andr\u00e9-Marie Amp\u00e8re\",");
	    sb.append("            \"melt\": 53.48,");
	    sb.append("            \"molar_heat\": null,");
	    sb.append("            \"named_by\": \"Humphry Davy\",");
	    sb.append("            \"number\": 9,");
	    sb.append("            \"period\": 2,");
	    sb.append("            \"phase\": \"Gas\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Fluorine\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Fluorine is a chemical element with symbol F and atomic number 9. It is the lightest halogen and exists as a highly toxic pale yellow diatomic gas at standard conditions. As the most electronegative element, it is extremely reactive:almost all other elements, including some noble gases, form compounds with fluorine.\",");
	    sb.append("            \"symbol\": \"F\",");
	    sb.append("            \"xpos\": 17,");
	    sb.append("            \"ypos\": 2,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                7");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Neon\",");
	    sb.append("            \"appearance\": \"colorless gas exhibiting an orange-red glow when placed in a high voltage electric field\",");
	    sb.append("            \"atomic_mass\": 20.17976,");
	    sb.append("            \"boil\": 27.104,");
	    sb.append("            \"category\": \"noble gas\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 0.9002,");
	    sb.append("            \"discovered_by\": \"Morris Travers\",");
	    sb.append("            \"melt\": 24.56,");
	    sb.append("            \"molar_heat\": null,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 10,");
	    sb.append("            \"period\": 2,");
	    sb.append("            \"phase\": \"Gas\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Neon\",");
	    sb.append("            \"spectral_img\": \"https://en.wikipedia.org/wiki/File:Neon_spectra.jpg\",");
	    sb.append("            \"summary\": \"Neon is a chemical element with symbol Ne and atomic number 10. It is in group 18 (noble gases) of the periodic table. Neon is a colorless, odorless, inert monatomic gas under standard conditions, with about two-thirds the density of air.\",");
	    sb.append("            \"symbol\": \"Ne\",");
	    sb.append("            \"xpos\": 18,");
	    sb.append("            \"ypos\": 2,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Sodium\",");
	    sb.append("            \"appearance\": \"silvery white metallic\",");
	    sb.append("            \"atomic_mass\": 22.989769282,");
	    sb.append("            \"boil\": 1156.09,");
	    sb.append("            \"category\": \"alkali metal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 0.968,");
	    sb.append("            \"discovered_by\": \"Humphry Davy\",");
	    sb.append("            \"melt\": 370.944,");
	    sb.append("            \"molar_heat\": 28.23,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 11,");
	    sb.append("            \"period\": 3,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Sodium\",");
	    sb.append("            \"spectral_img\": \"https://en.wikipedia.org/wiki/File:Sodium_Spectra.jpg\",");
	    sb.append("            \"summary\": \"Sodium /\u02c8so\u028adi\u0259m/ is a chemical element with symbol Na (from Ancient Greek \u039d\u03ac\u03c4\u03c1\u03b9\u03bf) and atomic number 11. It is a soft, silver-white, highly reactive metal. In the Periodic table it is in column 1 (alkali metals), and shares with the other six elements in that column that it has a single electron in its outer shell, which it readily donates, creating a positively charged atom - a cation.\",");
	    sb.append("            \"symbol\": \"Na\",");
	    sb.append("            \"xpos\": 1,");
	    sb.append("            \"ypos\": 3,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                1");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Magnesium\",");
	    sb.append("            \"appearance\": \"shiny grey solid\",");
	    sb.append("            \"atomic_mass\": 24.305,");
	    sb.append("            \"boil\": 1363,");
	    sb.append("            \"category\": \"alkaline earth metal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 1.738,");
	    sb.append("            \"discovered_by\": \"Joseph Black\",");
	    sb.append("            \"melt\": 923,");
	    sb.append("            \"molar_heat\": 24.869,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 12,");
	    sb.append("            \"period\": 3,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Magnesium\",");
	    sb.append("            \"spectral_img\": \"https://en.wikipedia.org/wiki/File:Magnesium_Spectra.jpg\",");
	    sb.append("            \"summary\": \"Magnesium is a chemical element with symbol Mg and atomic number 12. It is a shiny gray solid which bears a close physical resemblance to the other five elements in the second column (Group 2, or alkaline earth metals) of the periodic table:they each have the same electron configuration in their outer electron shell producing a similar crystal structure. Magnesium is the ninth most abundant element in the universe.\",");
	    sb.append("            \"symbol\": \"Mg\",");
	    sb.append("            \"xpos\": 2,");
	    sb.append("            \"ypos\": 3,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Aluminium\",");
	    sb.append("            \"appearance\": \"silvery gray metallic\",");
	    sb.append("            \"atomic_mass\": 26.98153857,");
	    sb.append("            \"boil\": 2743,");
	    sb.append("            \"category\": \"post-transition metal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 2.7,");
	    sb.append("            \"discovered_by\": null,");
	    sb.append("            \"melt\": 933.47,");
	    sb.append("            \"molar_heat\": 24.2,");
	    sb.append("            \"named_by\": \"Humphry Davy\",");
	    sb.append("            \"number\": 13,");
	    sb.append("            \"period\": 3,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Aluminium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Aluminium (or aluminum; see different endings) is a chemical element in the boron group with symbol Al and atomic number 13. It is a silvery-white, soft, nonmagnetic, ductile metal. Aluminium is the third most abundant element (after oxygen and silicon), and the most abundant metal, in the Earth's crust.\",");
	    sb.append("            \"symbol\": \"Al\",");
	    sb.append("            \"xpos\": 13,");
	    sb.append("            \"ypos\": 3,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                3");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Silicon\",");
	    sb.append("            \"appearance\": \"crystalline, reflective with bluish-tinged faces\",");
	    sb.append("            \"atomic_mass\": 28.085,");
	    sb.append("            \"boil\": 3538,");
	    sb.append("            \"category\": \"metalloid\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 2.329,");
	    sb.append("            \"discovered_by\": \"J\u00f6ns Jacob Berzelius\",");
	    sb.append("            \"melt\": 1687,");
	    sb.append("            \"molar_heat\": 19.789,");
	    sb.append("            \"named_by\": \"Thomas Thomson (chemist)\",");
	    sb.append("            \"number\": 14,");
	    sb.append("            \"period\": 3,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Silicon\",");
	    sb.append("            \"spectral_img\": \"https://en.wikipedia.org/wiki/File:Silicon_Spectra.jpg\",");
	    sb.append("            \"summary\": \"Silicon is a chemical element with symbol Si and atomic number 14. It is a tetravalent metalloid, more reactive than germanium, the metalloid directly below it in the table. Controversy about silicon's character dates to its discovery.\",");
	    sb.append("            \"symbol\": \"Si\",");
	    sb.append("            \"xpos\": 14,");
	    sb.append("            \"ypos\": 3,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                4");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Phosphorus\",");
	    sb.append("            \"appearance\": \"colourless, waxy white, yellow, scarlet, red, violet, black\",");
	    sb.append("            \"atomic_mass\": 30.9737619985,");
	    sb.append("            \"boil\": null,");
	    sb.append("            \"category\": \"polyatomic nonmetal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 1.823,");
	    sb.append("            \"discovered_by\": \"Hennig Brand\",");
	    sb.append("            \"melt\": null,");
	    sb.append("            \"molar_heat\": 23.824,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 15,");
	    sb.append("            \"period\": 3,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Phosphorus\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Phosphorus is a chemical element with symbol P and atomic number 15. As an element, phosphorus exists in two major forms\u2014white phosphorus and red phosphorus\u2014but due to its high reactivity, phosphorus is never found as a free element on Earth. Instead phosphorus-containing minerals are almost always present in their maximally oxidised state, as inorganic phosphate rocks.\",");
	    sb.append("            \"symbol\": \"P\",");
	    sb.append("            \"xpos\": 15,");
	    sb.append("            \"ypos\": 3,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                5");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Sulfur\",");
	    sb.append("            \"appearance\": \"lemon yellow sintered microcrystals\",");
	    sb.append("            \"atomic_mass\": 32.06,");
	    sb.append("            \"boil\": 717.8,");
	    sb.append("            \"category\": \"polyatomic nonmetal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 2.07,");
	    sb.append("            \"discovered_by\": \"Ancient china\",");
	    sb.append("            \"melt\": 388.36,");
	    sb.append("            \"molar_heat\": 22.75,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 16,");
	    sb.append("            \"period\": 3,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Sulfur\",");
	    sb.append("            \"spectral_img\": \"https://en.wikipedia.org/wiki/File:Sulfur_Spectrum.jpg\",");
	    sb.append("            \"summary\": \"Sulfur or sulphur (see spelling differences) is a chemical element with symbol S and atomic number 16. It is an abundant, multivalent non-metal. Under normal conditions, sulfur atoms form cyclic octatomic molecules with chemical formula S8.\",");
	    sb.append("            \"symbol\": \"S\",");
	    sb.append("            \"xpos\": 16,");
	    sb.append("            \"ypos\": 3,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                6");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Chlorine\",");
	    sb.append("            \"appearance\": \"pale yellow-green gas\",");
	    sb.append("            \"atomic_mass\": 35.45,");
	    sb.append("            \"boil\": 239.11,");
	    sb.append("            \"category\": \"diatomic nonmetal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 3.2,");
	    sb.append("            \"discovered_by\": \"Carl Wilhelm Scheele\",");
	    sb.append("            \"melt\": 171.6,");
	    sb.append("            \"molar_heat\": null,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 17,");
	    sb.append("            \"period\": 3,");
	    sb.append("            \"phase\": \"Gas\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Chlorine\",");
	    sb.append("            \"spectral_img\": \"https://en.wikipedia.org/wiki/File:Chlorine_spectrum_visible.png\",");
	    sb.append("            \"summary\": \"Chlorine is a chemical element with symbol Cl and atomic number 17. It also has a relative atomic mass of 35.5. Chlorine is in the halogen group (17) and is the second lightest halogen following fluorine.\",");
	    sb.append("            \"symbol\": \"Cl\",");
	    sb.append("            \"xpos\": 17,");
	    sb.append("            \"ypos\": 3,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                7");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Argon\",");
	    sb.append("            \"appearance\": \"colorless gas exhibiting a lilac/violet glow when placed in a high voltage electric field\",");
	    sb.append("            \"atomic_mass\": 39.9481,");
	    sb.append("            \"boil\": 87.302,");
	    sb.append("            \"category\": \"noble gas\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 1.784,");
	    sb.append("            \"discovered_by\": \"Lord Rayleigh\",");
	    sb.append("            \"melt\": 83.81,");
	    sb.append("            \"molar_heat\": null,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 18,");
	    sb.append("            \"period\": 3,");
	    sb.append("            \"phase\": \"Gas\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Argon\",");
	    sb.append("            \"spectral_img\": \"https://en.wikipedia.org/wiki/File:Argon_Spectrum.png\",");
	    sb.append("            \"summary\": \"Argon is a chemical element with symbol Ar and atomic number 18. It is in group 18 of the periodic table and is a noble gas. Argon is the third most common gas in the Earth's atmosphere, at 0.934% (9,340 ppmv), making it over twice as abundant as the next most common atmospheric gas, water vapor (which averages about 4000 ppmv, but varies greatly), and 23 times as abundant as the next most common non-condensing atmospheric gas, carbon dioxide (400 ppmv), and more than 500 times as abundant as the next most common noble gas, neon (18 ppmv).\",");
	    sb.append("            \"symbol\": \"Ar\",");
	    sb.append("            \"xpos\": 18,");
	    sb.append("            \"ypos\": 3,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                8");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Potassium\",");
	    sb.append("            \"appearance\": \"silvery gray\",");
	    sb.append("            \"atomic_mass\": 39.09831,");
	    sb.append("            \"boil\": 1032,");
	    sb.append("            \"category\": \"alkali metal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 0.862,");
	    sb.append("            \"discovered_by\": \"Humphry Davy\",");
	    sb.append("            \"melt\": 336.7,");
	    sb.append("            \"molar_heat\": 29.6,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 19,");
	    sb.append("            \"period\": 4,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Potassium\",");
	    sb.append("            \"spectral_img\": \"https://en.wikipedia.org/wiki/File:Potassium_Spectrum.jpg\",");
	    sb.append("            \"summary\": \"Potassium is a chemical element with symbol K (derived from Neo-Latin, kalium) and atomic number 19. It was first isolated from potash, the ashes of plants, from which its name is derived. In the Periodic table, potassium is one of seven elements in column (group) 1 (alkali metals):they all have a single valence electron in their outer electron shell, which they readily give up to create an atom with a positive charge - a cation, and combine with anions to form salts.\",");
	    sb.append("            \"symbol\": \"K\",");
	    sb.append("            \"xpos\": 1,");
	    sb.append("            \"ypos\": 4,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                8,");
	    sb.append("                1");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Calcium\",");
	    sb.append("            \"appearance\": null,");
	    sb.append("            \"atomic_mass\": 40.0784,");
	    sb.append("            \"boil\": 1757,");
	    sb.append("            \"category\": \"alkaline earth metal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 1.55,");
	    sb.append("            \"discovered_by\": \"Humphry Davy\",");
	    sb.append("            \"melt\": 1115,");
	    sb.append("            \"molar_heat\": 25.929,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 20,");
	    sb.append("            \"period\": 4,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Calcium\",");
	    sb.append("            \"spectral_img\": \"https://en.wikipedia.org/wiki/File:Calcium_Spectrum.png\",");
	    sb.append("            \"summary\": \"Calcium is a chemical element with symbol Ca and atomic number 20. Calcium is a soft gray alkaline earth metal, fifth-most-abundant element by mass in the Earth's crust. The ion Ca2+ is also the fifth-most-abundant dissolved ion in seawater by both molarity and mass, after sodium, chloride, magnesium, and sulfate.\",");
	    sb.append("            \"symbol\": \"Ca\",");
	    sb.append("            \"xpos\": 2,");
	    sb.append("            \"ypos\": 4,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                8,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Scandium\",");
	    sb.append("            \"appearance\": \"silvery white\",");
	    sb.append("            \"atomic_mass\": 44.9559085,");
	    sb.append("            \"boil\": 3109,");
	    sb.append("            \"category\": \"transition metal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 2.985,");
	    sb.append("            \"discovered_by\": \"Lars Fredrik Nilson\",");
	    sb.append("            \"melt\": 1814,");
	    sb.append("            \"molar_heat\": 25.52,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 21,");
	    sb.append("            \"period\": 4,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Scandium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Scandium is a chemical element with symbol Sc and atomic number 21. A silvery-white metallic d-block element, it has historically been sometimes classified as a rare earth element, together with yttrium and the lanthanoids. It was discovered in 1879 by spectral analysis of the minerals euxenite and gadolinite from Scandinavia.\",");
	    sb.append("            \"symbol\": \"Sc\",");
	    sb.append("            \"xpos\": 3,");
	    sb.append("            \"ypos\": 4,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                9,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Titanium\",");
	    sb.append("            \"appearance\": \"silvery grey-white metallic\",");
	    sb.append("            \"atomic_mass\": 47.8671,");
	    sb.append("            \"boil\": 3560,");
	    sb.append("            \"category\": \"transition metal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 4.506,");
	    sb.append("            \"discovered_by\": \"William Gregor\",");
	    sb.append("            \"melt\": 1941,");
	    sb.append("            \"molar_heat\": 25.06,");
	    sb.append("            \"named_by\": \"Martin Heinrich Klaproth\",");
	    sb.append("            \"number\": 22,");
	    sb.append("            \"period\": 4,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Titanium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Titanium is a chemical element with symbol Ti and atomic number 22. It is a lustrous transition metal with a silver color, low density and high strength. It is highly resistant to corrosion in sea water, aqua regia and chlorine.\",");
	    sb.append("            \"symbol\": \"Ti\",");
	    sb.append("            \"xpos\": 4,");
	    sb.append("            \"ypos\": 4,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                10,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Vanadium\",");
	    sb.append("            \"appearance\": \"blue-silver-grey metal\",");
	    sb.append("            \"atomic_mass\": 50.94151,");
	    sb.append("            \"boil\": 3680,");
	    sb.append("            \"category\": \"transition metal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 6.0,");
	    sb.append("            \"discovered_by\": \"Andr\u00e9s Manuel del R\u00edo\",");
	    sb.append("            \"melt\": 2183,");
	    sb.append("            \"molar_heat\": 24.89,");
	    sb.append("            \"named_by\": \"Isotopes of vanadium\",");
	    sb.append("            \"number\": 23,");
	    sb.append("            \"period\": 4,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Vanadium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Vanadium is a chemical element with symbol V and atomic number 23. It is a hard, silvery grey, ductile and malleable transition metal. The element is found only in chemically combined form in nature, but once isolated artificially, the formation of an oxide layer stabilizes the free metal somewhat against further oxidation.\",");
	    sb.append("            \"symbol\": \"V\",");
	    sb.append("            \"xpos\": 5,");
	    sb.append("            \"ypos\": 4,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                11,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Chromium\",");
	    sb.append("            \"appearance\": \"silvery metallic\",");
	    sb.append("            \"atomic_mass\": 51.99616,");
	    sb.append("            \"boil\": 2944,");
	    sb.append("            \"category\": \"transition metal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 7.19,");
	    sb.append("            \"discovered_by\": \"Louis Nicolas Vauquelin\",");
	    sb.append("            \"melt\": 2180,");
	    sb.append("            \"molar_heat\": 23.35,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 24,");
	    sb.append("            \"period\": 4,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Chromium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Chromium is a chemical element with symbol Cr and atomic number 24. It is the first element in Group 6. It is a steely-gray, lustrous, hard and brittle metal which takes a high polish, resists tarnishing, and has a high melting point.\",");
	    sb.append("            \"symbol\": \"Cr\",");
	    sb.append("            \"xpos\": 6,");
	    sb.append("            \"ypos\": 4,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                13,");
	    sb.append("                1");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Manganese\",");
	    sb.append("            \"appearance\": \"silvery metallic\",");
	    sb.append("            \"atomic_mass\": 54.9380443,");
	    sb.append("            \"boil\": 2334,");
	    sb.append("            \"category\": \"transition metal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 7.21,");
	    sb.append("            \"discovered_by\": \"Torbern Olof Bergman\",");
	    sb.append("            \"melt\": 1519,");
	    sb.append("            \"molar_heat\": 26.32,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 25,");
	    sb.append("            \"period\": 4,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Manganese\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Manganese is a chemical element with symbol Mn and atomic number 25. It is not found as a free element in nature; it is often found in combination with iron, and in many minerals. Manganese is a metal with important industrial metal alloy uses, particularly in stainless steels.\",");
	    sb.append("            \"symbol\": \"Mn\",");
	    sb.append("            \"xpos\": 7,");
	    sb.append("            \"ypos\": 4,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                13,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Iron\",");
	    sb.append("            \"appearance\": \"lustrous metallic with a grayish tinge\",");
	    sb.append("            \"atomic_mass\": 55.8452,");
	    sb.append("            \"boil\": 3134,");
	    sb.append("            \"category\": \"transition metal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 7.874,");
	    sb.append("            \"discovered_by\": \"5000 BC\",");
	    sb.append("            \"melt\": 1811,");
	    sb.append("            \"molar_heat\": 25.1,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 26,");
	    sb.append("            \"period\": 4,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Iron\",");
	    sb.append("            \"spectral_img\": \"https://en.wikipedia.org/wiki/File:Iron_Spectrum.jpg\",");
	    sb.append("            \"summary\": \"Iron is a chemical element with symbol Fe (from Latin:ferrum) and atomic number 26. It is a metal in the first transition series. It is by mass the most common element on Earth, forming much of Earth's outer and inner core.\",");
	    sb.append("            \"symbol\": \"Fe\",");
	    sb.append("            \"xpos\": 8,");
	    sb.append("            \"ypos\": 4,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                14,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Cobalt\",");
	    sb.append("            \"appearance\": \"hard lustrous gray metal\",");
	    sb.append("            \"atomic_mass\": 58.9331944,");
	    sb.append("            \"boil\": 3200,");
	    sb.append("            \"category\": \"transition metal\",");
	    sb.append("            \"color\": \"metallic gray\",");
	    sb.append("            \"density\": 8.9,");
	    sb.append("            \"discovered_by\": \"Georg Brandt\",");
	    sb.append("            \"melt\": 1768,");
	    sb.append("            \"molar_heat\": 24.81,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 27,");
	    sb.append("            \"period\": 4,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Cobalt\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Cobalt is a chemical element with symbol Co and atomic number 27. Like nickel, cobalt in the Earth's crust is found only in chemically combined form, save for small deposits found in alloys of natural meteoric iron. The free element, produced by reductive smelting, is a hard, lustrous, silver-gray metal.\",");
	    sb.append("            \"symbol\": \"Co\",");
	    sb.append("            \"xpos\": 9,");
	    sb.append("            \"ypos\": 4,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                15,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Nickel\",");
	    sb.append("            \"appearance\": \"lustrous, metallic, and silver with a gold tinge\",");
	    sb.append("            \"atomic_mass\": 58.69344,");
	    sb.append("            \"boil\": 3003,");
	    sb.append("            \"category\": \"transition metal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 8.908,");
	    sb.append("            \"discovered_by\": \"Axel Fredrik Cronstedt\",");
	    sb.append("            \"melt\": 1728,");
	    sb.append("            \"molar_heat\": 26.07,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 28,");
	    sb.append("            \"period\": 4,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Nickel\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Nickel is a chemical element with symbol Ni and atomic number 28. It is a silvery-white lustrous metal with a slight golden tinge. Nickel belongs to the transition metals and is hard and ductile.\",");
	    sb.append("            \"symbol\": \"Ni\",");
	    sb.append("            \"xpos\": 10,");
	    sb.append("            \"ypos\": 4,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                16,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Copper\",");
	    sb.append("            \"appearance\": \"red-orange metallic luster\",");
	    sb.append("            \"atomic_mass\": 63.5463,");
	    sb.append("            \"boil\": 2835,");
	    sb.append("            \"category\": \"transition metal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 8.96,");
	    sb.append("            \"discovered_by\": \"Middle East\",");
	    sb.append("            \"melt\": 1357.77,");
	    sb.append("            \"molar_heat\": 24.44,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 29,");
	    sb.append("            \"period\": 4,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Copper\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Copper is a chemical element with symbol Cu (from Latin:cuprum) and atomic number 29. It is a soft, malleable and ductile metal with very high thermal and electrical conductivity. A freshly exposed surface of pure copper has a reddish-orange color.\",");
	    sb.append("            \"symbol\": \"Cu\",");
	    sb.append("            \"xpos\": 11,");
	    sb.append("            \"ypos\": 4,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                1");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Zinc\",");
	    sb.append("            \"appearance\": \"silver-gray\",");
	    sb.append("            \"atomic_mass\": 65.382,");
	    sb.append("            \"boil\": 1180,");
	    sb.append("            \"category\": \"transition metal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 7.14,");
	    sb.append("            \"discovered_by\": \"India\",");
	    sb.append("            \"melt\": 692.68,");
	    sb.append("            \"molar_heat\": 25.47,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 30,");
	    sb.append("            \"period\": 4,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Zinc\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Zinc, in commerce also spelter, is a chemical element with symbol Zn and atomic number 30. It is the first element of group 12 of the periodic table. In some respects zinc is chemically similar to magnesium:its ion is of similar size and its only common oxidation state is +2.\",");
	    sb.append("            \"symbol\": \"Zn\",");
	    sb.append("            \"xpos\": 12,");
	    sb.append("            \"ypos\": 4,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Gallium\",");
	    sb.append("            \"appearance\": \"silver-white\",");
	    sb.append("            \"atomic_mass\": 69.7231,");
	    sb.append("            \"boil\": 2673,");
	    sb.append("            \"category\": \"post-transition metal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 5.91,");
	    sb.append("            \"discovered_by\": \"Lecoq de Boisbaudran\",");
	    sb.append("            \"melt\": 302.9146,");
	    sb.append("            \"molar_heat\": 25.86,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 31,");
	    sb.append("            \"period\": 4,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Gallium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Gallium is a chemical element with symbol Ga and atomic number 31. Elemental gallium does not occur in free form in nature, but as the gallium(III) compounds that are in trace amounts in zinc ores and in bauxite. Gallium is a soft, silvery metal, and elemental gallium is a brittle solid at low temperatures, and melts at 29.76 \u00b0C (85.57 \u00b0F) (slightly above room temperature).\",");
	    sb.append("            \"symbol\": \"Ga\",");
	    sb.append("            \"xpos\": 13,");
	    sb.append("            \"ypos\": 4,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                3");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Germanium\",");
	    sb.append("            \"appearance\": \"grayish-white\",");
	    sb.append("            \"atomic_mass\": 72.6308,");
	    sb.append("            \"boil\": 3106,");
	    sb.append("            \"category\": \"metalloid\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 5.323,");
	    sb.append("            \"discovered_by\": \"Clemens Winkler\",");
	    sb.append("            \"melt\": 1211.4,");
	    sb.append("            \"molar_heat\": 23.222,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 32,");
	    sb.append("            \"period\": 4,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Germanium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Germanium is a chemical element with symbol Ge and atomic number 32. It is a lustrous, hard, grayish-white metalloid in the carbon group, chemically similar to its group neighbors tin and silicon. Purified germanium is a semiconductor, with an appearance most similar to elemental silicon.\",");
	    sb.append("            \"symbol\": \"Ge\",");
	    sb.append("            \"xpos\": 14,");
	    sb.append("            \"ypos\": 4,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                4");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Arsenic\",");
	    sb.append("            \"appearance\": \"metallic grey\",");
	    sb.append("            \"atomic_mass\": 74.9215956,");
	    sb.append("            \"boil\": null,");
	    sb.append("            \"category\": \"metalloid\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 5.727,");
	    sb.append("            \"discovered_by\": \"Bronze Age\",");
	    sb.append("            \"melt\": null,");
	    sb.append("            \"molar_heat\": 24.64,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 33,");
	    sb.append("            \"period\": 4,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Arsenic\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Arsenic is a chemical element with symbol As and atomic number 33. Arsenic occurs in many minerals, usually in conjunction with sulfur and metals, and also as a pure elemental crystal. Arsenic is a metalloid.\",");
	    sb.append("            \"symbol\": \"As\",");
	    sb.append("            \"xpos\": 15,");
	    sb.append("            \"ypos\": 4,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                5");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Selenium\",");
	    sb.append("            \"appearance\": \"black, red, and gray (not pictured) allotropes\",");
	    sb.append("            \"atomic_mass\": 78.9718,");
	    sb.append("            \"boil\": 958,");
	    sb.append("            \"category\": \"polyatomic nonmetal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 4.81,");
	    sb.append("            \"discovered_by\": \"J\u00f6ns Jakob Berzelius\",");
	    sb.append("            \"melt\": 494,");
	    sb.append("            \"molar_heat\": 25.363,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 34,");
	    sb.append("            \"period\": 4,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Selenium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Selenium is a chemical element with symbol Se and atomic number 34. It is a nonmetal with properties that are intermediate between those of its periodic table column-adjacent chalcogen elements sulfur and tellurium. It rarely occurs in its elemental state in nature, or as pure ore compounds.\",");
	    sb.append("            \"symbol\": \"Se\",");
	    sb.append("            \"xpos\": 16,");
	    sb.append("            \"ypos\": 4,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                6");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Bromine\",");
	    sb.append("            \"appearance\": null,");
	    sb.append("            \"atomic_mass\": 79.904,");
	    sb.append("            \"boil\": 332.0,");
	    sb.append("            \"category\": \"diatomic nonmetal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 23.1028,");
	    sb.append("            \"discovered_by\": \"Antoine J\u00e9r\u00f4me Balard\",");
	    sb.append("            \"melt\": 265.8,");
	    sb.append("            \"molar_heat\": null,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 35,");
	    sb.append("            \"period\": 4,");
	    sb.append("            \"phase\": \"Liquid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Bromine\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Bromine (from Ancient Greek:\u03b2\u03c1\u1ff6\u03bc\u03bf\u03c2, br\u00f3mos, meaning 'stench') is a chemical element with symbol Br, and atomic number 35. It is a halogen. The element was isolated independently by two chemists, Carl Jacob L\u00f6wig and Antoine Jerome Balard, in 1825\u20131826.\",");
	    sb.append("            \"symbol\": \"Br\",");
	    sb.append("            \"xpos\": 17,");
	    sb.append("            \"ypos\": 4,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                7");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Krypton\",");
	    sb.append("            \"appearance\": \"colorless gas, exhibiting a whitish glow in a high electric field\",");
	    sb.append("            \"atomic_mass\": 83.7982,");
	    sb.append("            \"boil\": 119.93,");
	    sb.append("            \"category\": \"noble gas\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 3.749,");
	    sb.append("            \"discovered_by\": \"William Ramsay\",");
	    sb.append("            \"melt\": 115.78,");
	    sb.append("            \"molar_heat\": null,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 36,");
	    sb.append("            \"period\": 4,");
	    sb.append("            \"phase\": \"Gas\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Krypton\",");
	    sb.append("            \"spectral_img\": \"https://en.wikipedia.org/wiki/File:Krypton_Spectrum.jpg\",");
	    sb.append("            \"summary\": \"Krypton (from Greek:\u03ba\u03c1\u03c5\u03c0\u03c4\u03cc\u03c2 kryptos 'the hidden one') is a chemical element with symbol Kr and atomic number 36. It is a member of group 18 (noble gases) elements. A colorless, odorless, tasteless noble gas, krypton occurs in trace amounts in the atmosphere, is isolated by fractionally distilling liquefied air, and is often used with other rare gases in fluorescent lamps.\",");
	    sb.append("            \"symbol\": \"Kr\",");
	    sb.append("            \"xpos\": 18,");
	    sb.append("            \"ypos\": 4,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                8");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Rubidium\",");
	    sb.append("            \"appearance\": \"grey white\",");
	    sb.append("            \"atomic_mass\": 85.46783,");
	    sb.append("            \"boil\": 961,");
	    sb.append("            \"category\": \"alkali metal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 1.532,");
	    sb.append("            \"discovered_by\": \"Robert Bunsen\",");
	    sb.append("            \"melt\": 312.45,");
	    sb.append("            \"molar_heat\": 31.06,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 37,");
	    sb.append("            \"period\": 5,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Rubidium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Rubidium is a chemical element with symbol Rb and atomic number 37. Rubidium is a soft, silvery-white metallic element of the alkali metal group, with an atomic mass of 85.4678. Elemental rubidium is highly reactive, with properties similar to those of other alkali metals, such as very rapid oxidation in air.\",");
	    sb.append("            \"symbol\": \"Rb\",");
	    sb.append("            \"xpos\": 1,");
	    sb.append("            \"ypos\": 5,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                8,");
	    sb.append("                1");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Strontium\",");
	    sb.append("            \"appearance\": null,");
	    sb.append("            \"atomic_mass\": 87.621,");
	    sb.append("            \"boil\": 1650,");
	    sb.append("            \"category\": \"alkaline earth metal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 2.64,");
	    sb.append("            \"discovered_by\": \"William Cruickshank (chemist)\",");
	    sb.append("            \"melt\": 1050,");
	    sb.append("            \"molar_heat\": 26.4,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 38,");
	    sb.append("            \"period\": 5,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Strontium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Strontium is a chemical element with symbol Sr and atomic number 38. An alkaline earth metal, strontium is a soft silver-white or yellowish metallic element that is highly reactive chemically. The metal turns yellow when it is exposed to air.\",");
	    sb.append("            \"symbol\": \"Sr\",");
	    sb.append("            \"xpos\": 2,");
	    sb.append("            \"ypos\": 5,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                8,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Yttrium\",");
	    sb.append("            \"appearance\": \"silvery white\",");
	    sb.append("            \"atomic_mass\": 88.905842,");
	    sb.append("            \"boil\": 3203,");
	    sb.append("            \"category\": \"transition metal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 4.472,");
	    sb.append("            \"discovered_by\": \"Johan Gadolin\",");
	    sb.append("            \"melt\": 1799,");
	    sb.append("            \"molar_heat\": 26.53,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 39,");
	    sb.append("            \"period\": 5,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Yttrium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Yttrium is a chemical element with symbol Y and atomic number 39. It is a silvery-metallic transition metal chemically similar to the lanthanides and it has often been classified as a 'rare earth element'. Yttrium is almost always found combined with the lanthanides in rare earth minerals and is never found in nature as a free element.\",");
	    sb.append("            \"symbol\": \"Y\",");
	    sb.append("            \"xpos\": 3,");
	    sb.append("            \"ypos\": 5,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                9,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Zirconium\",");
	    sb.append("            \"appearance\": \"silvery white\",");
	    sb.append("            \"atomic_mass\": 91.2242,");
	    sb.append("            \"boil\": 4650,");
	    sb.append("            \"category\": \"transition metal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 6.52,");
	    sb.append("            \"discovered_by\": \"Martin Heinrich Klaproth\",");
	    sb.append("            \"melt\": 2128,");
	    sb.append("            \"molar_heat\": 25.36,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 40,");
	    sb.append("            \"period\": 5,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Zirconium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Zirconium is a chemical element with symbol Zr and atomic number 40. The name of zirconium is taken from the name of the mineral zircon, the most important source of zirconium. The word zircon comes from the Persian word zargun \u0632\u0631\u06af\u0648\u0646, meaning 'gold-colored'.\",");
	    sb.append("            \"symbol\": \"Zr\",");
	    sb.append("            \"xpos\": 4,");
	    sb.append("            \"ypos\": 5,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                10,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Niobium\",");
	    sb.append("            \"appearance\": \"gray metallic, bluish when oxidized\",");
	    sb.append("            \"atomic_mass\": 92.906372,");
	    sb.append("            \"boil\": 5017,");
	    sb.append("            \"category\": \"transition metal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 8.57,");
	    sb.append("            \"discovered_by\": \"Charles Hatchett\",");
	    sb.append("            \"melt\": 2750,");
	    sb.append("            \"molar_heat\": 24.6,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 41,");
	    sb.append("            \"period\": 5,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Niobium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Niobium, formerly columbium, is a chemical element with symbol Nb (formerly Cb) and atomic number 41. It is a soft, grey, ductile transition metal, which is often found in the pyrochlore mineral, the main commercial source for niobium, and columbite. The name comes from Greek mythology:Niobe, daughter of Tantalus since it is so similar to tantalum.\",");
	    sb.append("            \"symbol\": \"Nb\",");
	    sb.append("            \"xpos\": 5,");
	    sb.append("            \"ypos\": 5,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                12,");
	    sb.append("                1");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Molybdenum\",");
	    sb.append("            \"appearance\": \"gray metallic\",");
	    sb.append("            \"atomic_mass\": 95.951,");
	    sb.append("            \"boil\": 4912,");
	    sb.append("            \"category\": \"transition metal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 10.28,");
	    sb.append("            \"discovered_by\": \"Carl Wilhelm Scheele\",");
	    sb.append("            \"melt\": 2896,");
	    sb.append("            \"molar_heat\": 24.06,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 42,");
	    sb.append("            \"period\": 5,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Molybdenum\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Molybdenum is a chemical element with symbol Mo and atomic number 42. The name is from Neo-Latin molybdaenum, from Ancient Greek \u039c\u03cc\u03bb\u03c5\u03b2\u03b4\u03bf\u03c2 molybdos, meaning lead, since its ores were confused with lead ores. Molybdenum minerals have been known throughout history, but the element was discovered (in the sense of differentiating it as a new entity from the mineral salts of other metals) in 1778 by Carl Wilhelm Scheele.\",");
	    sb.append("            \"symbol\": \"Mo\",");
	    sb.append("            \"xpos\": 6,");
	    sb.append("            \"ypos\": 5,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                13,");
	    sb.append("                1");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Technetium\",");
	    sb.append("            \"appearance\": \"shiny gray metal\",");
	    sb.append("            \"atomic_mass\": 98,");
	    sb.append("            \"boil\": 4538,");
	    sb.append("            \"category\": \"transition metal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 11,");
	    sb.append("            \"discovered_by\": \"Emilio Segr\u00e8\",");
	    sb.append("            \"melt\": 2430,");
	    sb.append("            \"molar_heat\": 24.27,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 43,");
	    sb.append("            \"period\": 5,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Technetium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Technetium (/t\u025bk\u02c8ni\u02d0\u0283i\u0259m/) is a chemical element with symbol Tc and atomic number 43. It is the element with the lowest atomic number in the periodic table that has no stable isotopes:every form of it is radioactive. Nearly all technetium is produced synthetically, and only minute amounts are found in nature.\",");
	    sb.append("            \"symbol\": \"Tc\",");
	    sb.append("            \"xpos\": 7,");
	    sb.append("            \"ypos\": 5,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                13,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Ruthenium\",");
	    sb.append("            \"appearance\": \"silvery white metallic\",");
	    sb.append("            \"atomic_mass\": 101.072,");
	    sb.append("            \"boil\": 4423,");
	    sb.append("            \"category\": \"transition metal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 12.45,");
	    sb.append("            \"discovered_by\": \"Karl Ernst Claus\",");
	    sb.append("            \"melt\": 2607,");
	    sb.append("            \"molar_heat\": 24.06,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 44,");
	    sb.append("            \"period\": 5,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Ruthenium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Ruthenium is a chemical element with symbol Ru and atomic number 44. It is a rare transition metal belonging to the platinum group of the periodic table. Like the other metals of the platinum group, ruthenium is inert to most other chemicals.\",");
	    sb.append("            \"symbol\": \"Ru\",");
	    sb.append("            \"xpos\": 8,");
	    sb.append("            \"ypos\": 5,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                15,");
	    sb.append("                1");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Rhodium\",");
	    sb.append("            \"appearance\": \"silvery white metallic\",");
	    sb.append("            \"atomic_mass\": 102.905502,");
	    sb.append("            \"boil\": 3968,");
	    sb.append("            \"category\": \"transition metal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 12.41,");
	    sb.append("            \"discovered_by\": \"William Hyde Wollaston\",");
	    sb.append("            \"melt\": 2237,");
	    sb.append("            \"molar_heat\": 24.98,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 45,");
	    sb.append("            \"period\": 5,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Rhodium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Rhodium is a chemical element with symbol Rh and atomic number 45. It is a rare, silvery-white, hard, and chemically inert transition metal. It is a member of the platinum group.\",");
	    sb.append("            \"symbol\": \"Rh\",");
	    sb.append("            \"xpos\": 9,");
	    sb.append("            \"ypos\": 5,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                16,");
	    sb.append("                1");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Palladium\",");
	    sb.append("            \"appearance\": \"silvery white\",");
	    sb.append("            \"atomic_mass\": 106.421,");
	    sb.append("            \"boil\": 3236,");
	    sb.append("            \"category\": \"transition metal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 12.023,");
	    sb.append("            \"discovered_by\": \"William Hyde Wollaston\",");
	    sb.append("            \"melt\": 1828.05,");
	    sb.append("            \"molar_heat\": 25.98,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 46,");
	    sb.append("            \"period\": 5,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Palladium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Palladium is a chemical element with symbol Pd and atomic number 46. It is a rare and lustrous silvery-white metal discovered in 1803 by William Hyde Wollaston. He named it after the asteroid Pallas, which was itself named after the epithet of the Greek goddess Athena, acquired by her when she slew Pallas.\",");
	    sb.append("            \"symbol\": \"Pd\",");
	    sb.append("            \"xpos\": 10,");
	    sb.append("            \"ypos\": 5,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                18");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Silver\",");
	    sb.append("            \"appearance\": \"lustrous white metal\",");
	    sb.append("            \"atomic_mass\": 107.86822,");
	    sb.append("            \"boil\": 2435,");
	    sb.append("            \"category\": \"transition metal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 10.49,");
	    sb.append("            \"discovered_by\": \"unknown, before 5000 BC\",");
	    sb.append("            \"melt\": 1234.93,");
	    sb.append("            \"molar_heat\": 25.35,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 47,");
	    sb.append("            \"period\": 5,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Silver\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Silver is a chemical element with symbol Ag (Greek:\u03ac\u03c1\u03b3\u03c5\u03c1\u03bf\u03c2 \u00e1rguros, Latin:argentum, both from the Indo-European root *h\u2082er\u01f5- for 'grey' or 'shining') and atomic number 47. A soft, white, lustrous transition metal, it possesses the highest electrical conductivity, thermal conductivity and reflectivity of any metal. The metal occurs naturally in its pure, free form (native silver), as an alloy with gold and other metals, and in minerals such as argentite and chlorargyrite.\",");
	    sb.append("            \"symbol\": \"Ag\",");
	    sb.append("            \"xpos\": 11,");
	    sb.append("            \"ypos\": 5,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                18,");
	    sb.append("                1");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Cadmium\",");
	    sb.append("            \"appearance\": \"silvery bluish-gray metallic\",");
	    sb.append("            \"atomic_mass\": 112.4144,");
	    sb.append("            \"boil\": 1040,");
	    sb.append("            \"category\": \"transition metal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 8.65,");
	    sb.append("            \"discovered_by\": \"Karl Samuel Leberecht Hermann\",");
	    sb.append("            \"melt\": 594.22,");
	    sb.append("            \"molar_heat\": 26.02,");
	    sb.append("            \"named_by\": \"Isotopes of cadmium\",");
	    sb.append("            \"number\": 48,");
	    sb.append("            \"period\": 5,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Cadmium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Cadmium is a chemical element with symbol Cd and atomic number 48. This soft, bluish-white metal is chemically similar to the two other stable metals in group 12, zinc and mercury. Like zinc, it prefers oxidation state +2 in most of its compounds and like mercury it shows a low melting point compared to transition metals.\",");
	    sb.append("            \"symbol\": \"Cd\",");
	    sb.append("            \"xpos\": 12,");
	    sb.append("            \"ypos\": 5,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                18,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Indium\",");
	    sb.append("            \"appearance\": \"silvery lustrous gray\",");
	    sb.append("            \"atomic_mass\": 114.8181,");
	    sb.append("            \"boil\": 2345,");
	    sb.append("            \"category\": \"post-transition metal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 7.31,");
	    sb.append("            \"discovered_by\": \"Ferdinand Reich\",");
	    sb.append("            \"melt\": 429.7485,");
	    sb.append("            \"molar_heat\": 26.74,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 49,");
	    sb.append("            \"period\": 5,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Indium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Indium is a chemical element with symbol In and atomic number 49. It is a post-transition metallic element that is rare in Earth's crust. The metal is very soft, malleable and easily fusible, with a melting point higher than sodium, but lower than lithium or tin.\",");
	    sb.append("            \"symbol\": \"In\",");
	    sb.append("            \"xpos\": 13,");
	    sb.append("            \"ypos\": 5,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                18,");
	    sb.append("                3");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Tin\",");
	    sb.append("            \"appearance\": \"silvery-white (beta, \u03b2) or gray (alpha, \u03b1)\",");
	    sb.append("            \"atomic_mass\": 118.7107,");
	    sb.append("            \"boil\": 2875,");
	    sb.append("            \"category\": \"post-transition metal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 7.365,");
	    sb.append("            \"discovered_by\": \"unknown, before 3500 BC\",");
	    sb.append("            \"melt\": 505.08,");
	    sb.append("            \"molar_heat\": 27.112,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 50,");
	    sb.append("            \"period\": 5,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Tin\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Tin is a chemical element with the symbol Sn (for Latin:stannum) and atomic number 50. It is a main group metal in group 14 of the periodic table. Tin shows a chemical similarity to both neighboring group-14 elements, germanium and lead, and has two possible oxidation states, +2 and the slightly more stable +4.\",");
	    sb.append("            \"symbol\": \"Sn\",");
	    sb.append("            \"xpos\": 14,");
	    sb.append("            \"ypos\": 5,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                18,");
	    sb.append("                4");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Antimony\",");
	    sb.append("            \"appearance\": \"silvery lustrous gray\",");
	    sb.append("            \"atomic_mass\": 121.7601,");
	    sb.append("            \"boil\": 1908,");
	    sb.append("            \"category\": \"metalloid\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 6.697,");
	    sb.append("            \"discovered_by\": \"unknown, before 3000 BC\",");
	    sb.append("            \"melt\": 903.78,");
	    sb.append("            \"molar_heat\": 25.23,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 51,");
	    sb.append("            \"period\": 5,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Antimony\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Antimony is a chemical element with symbol Sb (from Latin:stibium) and atomic number 51. A lustrous gray metalloid, it is found in nature mainly as the sulfide mineral stibnite (Sb2S3). Antimony compounds have been known since ancient times and were used for cosmetics; metallic antimony was also known, but it was erroneously identified as lead upon its discovery.\",");
	    sb.append("            \"symbol\": \"Sb\",");
	    sb.append("            \"xpos\": 15,");
	    sb.append("            \"ypos\": 5,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                18,");
	    sb.append("                5");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Tellurium\",");
	    sb.append("            \"appearance\": null,");
	    sb.append("            \"atomic_mass\": 127.603,");
	    sb.append("            \"boil\": 1261,");
	    sb.append("            \"category\": \"metalloid\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 6.24,");
	    sb.append("            \"discovered_by\": \"Franz-Joseph M\u00fcller von Reichenstein\",");
	    sb.append("            \"melt\": 722.66,");
	    sb.append("            \"molar_heat\": 25.73,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 52,");
	    sb.append("            \"period\": 5,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Tellurium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Tellurium is a chemical element with symbol Te and atomic number 52. It is a brittle, mildly toxic, rare, silver-white metalloid. Tellurium is chemically related to selenium and sulfur.\",");
	    sb.append("            \"symbol\": \"Te\",");
	    sb.append("            \"xpos\": 16,");
	    sb.append("            \"ypos\": 5,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                18,");
	    sb.append("                6");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Iodine\",");
	    sb.append("            \"appearance\": \"lustrous metallic gray, violet as a gas\",");
	    sb.append("            \"atomic_mass\": 126.904473,");
	    sb.append("            \"boil\": 457.4,");
	    sb.append("            \"category\": \"diatomic nonmetal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 4.933,");
	    sb.append("            \"discovered_by\": \"Bernard Courtois\",");
	    sb.append("            \"melt\": 386.85,");
	    sb.append("            \"molar_heat\": null,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 53,");
	    sb.append("            \"period\": 5,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Iodine\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Iodine is a chemical element with symbol I and atomic number 53. The name is from Greek \u1f30\u03bf\u03b5\u03b9\u03b4\u03ae\u03c2 ioeid\u0113s, meaning violet or purple, due to the color of iodine vapor. Iodine and its compounds are primarily used in nutrition, and industrially in the production of acetic acid and certain polymers.\",");
	    sb.append("            \"symbol\": \"I\",");
	    sb.append("            \"xpos\": 17,");
	    sb.append("            \"ypos\": 5,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                18,");
	    sb.append("                7");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Xenon\",");
	    sb.append("            \"appearance\": \"colorless gas, exhibiting a blue glow when placed in a high voltage electric field\",");
	    sb.append("            \"atomic_mass\": 131.2936,");
	    sb.append("            \"boil\": 165.051,");
	    sb.append("            \"category\": \"noble gas\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 5.894,");
	    sb.append("            \"discovered_by\": \"William Ramsay\",");
	    sb.append("            \"melt\": 161.4,");
	    sb.append("            \"molar_heat\": null,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 54,");
	    sb.append("            \"period\": 5,");
	    sb.append("            \"phase\": \"Gas\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Xenon\",");
	    sb.append("            \"spectral_img\": \"https://en.wikipedia.org/wiki/File:Xenon_Spectrum.jpg\",");
	    sb.append("            \"summary\": \"Xenon is a chemical element with symbol Xe and atomic number 54. It is a colorless, dense, odorless noble gas, that occurs in the Earth's atmosphere in trace amounts. Although generally unreactive, xenon can undergo a few chemical reactions such as the formation of xenon hexafluoroplatinate, the first noble gas compound to be synthesized.\",");
	    sb.append("            \"symbol\": \"Xe\",");
	    sb.append("            \"xpos\": 18,");
	    sb.append("            \"ypos\": 5,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                18,");
	    sb.append("                8");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Cesium\",");
	    sb.append("            \"appearance\": \"silvery gold\",");
	    sb.append("            \"atomic_mass\": 132.905451966,");
	    sb.append("            \"boil\": 944,");
	    sb.append("            \"category\": \"alkali metal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 1.93,");
	    sb.append("            \"discovered_by\": \"Robert Bunsen\",");
	    sb.append("            \"melt\": 301.7,");
	    sb.append("            \"molar_heat\": 32.21,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 55,");
	    sb.append("            \"period\": 6,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Cesium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Caesium or cesium is a chemical element with symbol Cs and atomic number 55. It is a soft, silvery-gold alkali metal with a melting point of 28 \u00b0C (82 \u00b0F), which makes it one of only five elemental metals that are liquid at or near room temperature. Caesium is an alkali metal and has physical and chemical properties similar to those of rubidium and potassium.\",");
	    sb.append("            \"symbol\": \"Cs\",");
	    sb.append("            \"xpos\": 1,");
	    sb.append("            \"ypos\": 6,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                18,");
	    sb.append("                8,");
	    sb.append("                1");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Barium\",");
	    sb.append("            \"appearance\": null,");
	    sb.append("            \"atomic_mass\": 137.3277,");
	    sb.append("            \"boil\": 2118,");
	    sb.append("            \"category\": \"alkaline earth metal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 3.51,");
	    sb.append("            \"discovered_by\": \"Carl Wilhelm Scheele\",");
	    sb.append("            \"melt\": 1000,");
	    sb.append("            \"molar_heat\": 28.07,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 56,");
	    sb.append("            \"period\": 6,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Barium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Barium is a chemical element with symbol Ba and atomic number 56. It is the fifth element in Group 2, a soft silvery metallic alkaline earth metal. Because of its high chemical reactivity barium is never found in nature as a free element.\",");
	    sb.append("            \"symbol\": \"Ba\",");
	    sb.append("            \"xpos\": 2,");
	    sb.append("            \"ypos\": 6,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                18,");
	    sb.append("                8,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Lanthanum\",");
	    sb.append("            \"appearance\": \"silvery white\",");
	    sb.append("            \"atomic_mass\": 138.905477,");
	    sb.append("            \"boil\": 3737,");
	    sb.append("            \"category\": \"lanthanide\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 6.162,");
	    sb.append("            \"discovered_by\": \"Carl Gustaf Mosander\",");
	    sb.append("            \"melt\": 1193,");
	    sb.append("            \"molar_heat\": 27.11,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 57,");
	    sb.append("            \"period\": 6,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Lanthanum\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Lanthanum is a soft, ductile, silvery-white metallic chemical element with symbol La and atomic number 57. It tarnishes rapidly when exposed to air and is soft enough to be cut with a knife. It gave its name to the lanthanide series, a group of 15 similar elements between lanthanum and lutetium in the periodic table:it is also sometimes considered the first element of the 6th-period transition metals.\",");
	    sb.append("            \"symbol\": \"La\",");
	    sb.append("            \"xpos\": 3,");
	    sb.append("            \"ypos\": 9,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                18,");
	    sb.append("                9,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Cerium\",");
	    sb.append("            \"appearance\": \"silvery white\",");
	    sb.append("            \"atomic_mass\": 140.1161,");
	    sb.append("            \"boil\": 3716,");
	    sb.append("            \"category\": \"lanthanide\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 6.77,");
	    sb.append("            \"discovered_by\": \"Martin Heinrich Klaproth\",");
	    sb.append("            \"melt\": 1068,");
	    sb.append("            \"molar_heat\": 26.94,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 58,");
	    sb.append("            \"period\": 6,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Cerium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Cerium is a chemical element with symbol Ce and atomic number 58. It is a soft, silvery, ductile metal which easily oxidizes in air. Cerium was named after the dwarf planet Ceres (itself named after the Roman goddess of agriculture).\",");
	    sb.append("            \"symbol\": \"Ce\",");
	    sb.append("            \"xpos\": 4,");
	    sb.append("            \"ypos\": 9,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                19,");
	    sb.append("                9,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Praseodymium\",");
	    sb.append("            \"appearance\": \"grayish white\",");
	    sb.append("            \"atomic_mass\": 140.907662,");
	    sb.append("            \"boil\": 3403,");
	    sb.append("            \"category\": \"lanthanide\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 6.77,");
	    sb.append("            \"discovered_by\": \"Carl Auer von Welsbach\",");
	    sb.append("            \"melt\": 1208,");
	    sb.append("            \"molar_heat\": 27.2,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 59,");
	    sb.append("            \"period\": 6,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Praseodymium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Praseodymium is a chemical element with symbol Pr and atomic number 59. Praseodymium is a soft, silvery, malleable and ductile metal in the lanthanide group. It is valued for its magnetic, electrical, chemical, and optical properties.\",");
	    sb.append("            \"symbol\": \"Pr\",");
	    sb.append("            \"xpos\": 5,");
	    sb.append("            \"ypos\": 9,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                21,");
	    sb.append("                8,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Neodymium\",");
	    sb.append("            \"appearance\": \"silvery white\",");
	    sb.append("            \"atomic_mass\": 144.2423,");
	    sb.append("            \"boil\": 3347,");
	    sb.append("            \"category\": \"lanthanide\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 7.01,");
	    sb.append("            \"discovered_by\": \"Carl Auer von Welsbach\",");
	    sb.append("            \"melt\": 1297,");
	    sb.append("            \"molar_heat\": 27.45,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 60,");
	    sb.append("            \"period\": 6,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Neodymium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Neodymium is a chemical element with symbol Nd and atomic number 60. It is a soft silvery metal that tarnishes in air. Neodymium was discovered in 1885 by the Austrian chemist Carl Auer von Welsbach.\",");
	    sb.append("            \"symbol\": \"Nd\",");
	    sb.append("            \"xpos\": 6,");
	    sb.append("            \"ypos\": 9,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                22,");
	    sb.append("                8,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Promethium\",");
	    sb.append("            \"appearance\": \"metallic\",");
	    sb.append("            \"atomic_mass\": 145,");
	    sb.append("            \"boil\": 3273,");
	    sb.append("            \"category\": \"lanthanide\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 7.26,");
	    sb.append("            \"discovered_by\": \"Chien Shiung Wu\",");
	    sb.append("            \"melt\": 1315,");
	    sb.append("            \"molar_heat\": null,");
	    sb.append("            \"named_by\": \"Isotopes of promethium\",");
	    sb.append("            \"number\": 61,");
	    sb.append("            \"period\": 6,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Promethium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Promethium, originally prometheum, is a chemical element with the symbol Pm and atomic number 61. All of its isotopes are radioactive; it is one of only two such elements that are followed in the periodic table by elements with stable forms, a distinction shared with technetium. Chemically, promethium is a lanthanide, which forms salts when combined with other elements.\",");
	    sb.append("            \"symbol\": \"Pm\",");
	    sb.append("            \"xpos\": 7,");
	    sb.append("            \"ypos\": 9,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                23,");
	    sb.append("                8,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Samarium\",");
	    sb.append("            \"appearance\": \"silvery white\",");
	    sb.append("            \"atomic_mass\": 150.362,");
	    sb.append("            \"boil\": 2173,");
	    sb.append("            \"category\": \"lanthanide\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 7.52,");
	    sb.append("            \"discovered_by\": \"Lecoq de Boisbaudran\",");
	    sb.append("            \"melt\": 1345,");
	    sb.append("            \"molar_heat\": 29.54,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 62,");
	    sb.append("            \"period\": 6,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Samarium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Samarium is a chemical element with symbol Sm and atomic number 62. It is a moderately hard silvery metal that readily oxidizes in air. Being a typical member of the lanthanide series, samarium usually assumes the oxidation state +3.\",");
	    sb.append("            \"symbol\": \"Sm\",");
	    sb.append("            \"xpos\": 8,");
	    sb.append("            \"ypos\": 9,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                24,");
	    sb.append("                8,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Europium\",");
	    sb.append("            \"appearance\": null,");
	    sb.append("            \"atomic_mass\": 151.9641,");
	    sb.append("            \"boil\": 1802,");
	    sb.append("            \"category\": \"lanthanide\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 5.264,");
	    sb.append("            \"discovered_by\": \"Eug\u00e8ne-Anatole Demar\u00e7ay\",");
	    sb.append("            \"melt\": 1099,");
	    sb.append("            \"molar_heat\": 27.66,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 63,");
	    sb.append("            \"period\": 6,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Europium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Europium is a chemical element with symbol Eu and atomic number 63. It was isolated in 1901 and is named after the continent of Europe. It is a moderately hard, silvery metal which readily oxidizes in air and water.\",");
	    sb.append("            \"symbol\": \"Eu\",");
	    sb.append("            \"xpos\": 9,");
	    sb.append("            \"ypos\": 9,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                25,");
	    sb.append("                8,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Gadolinium\",");
	    sb.append("            \"appearance\": \"silvery white\",");
	    sb.append("            \"atomic_mass\": 157.253,");
	    sb.append("            \"boil\": 3273,");
	    sb.append("            \"category\": \"lanthanide\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 7.9,");
	    sb.append("            \"discovered_by\": \"Jean Charles Galissard de Marignac\",");
	    sb.append("            \"melt\": 1585,");
	    sb.append("            \"molar_heat\": 37.03,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 64,");
	    sb.append("            \"period\": 6,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Gadolinium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Gadolinium is a chemical element with symbol Gd and atomic number 64. It is a silvery-white, malleable and ductile rare-earth metal. It is found in nature only in combined (salt) form.\",");
	    sb.append("            \"symbol\": \"Gd\",");
	    sb.append("            \"xpos\": 10,");
	    sb.append("            \"ypos\": 9,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                25,");
	    sb.append("                9,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Terbium\",");
	    sb.append("            \"appearance\": \"silvery white\",");
	    sb.append("            \"atomic_mass\": 158.925352,");
	    sb.append("            \"boil\": 3396,");
	    sb.append("            \"category\": \"lanthanide\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 8.23,");
	    sb.append("            \"discovered_by\": \"Carl Gustaf Mosander\",");
	    sb.append("            \"melt\": 1629,");
	    sb.append("            \"molar_heat\": 28.91,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 65,");
	    sb.append("            \"period\": 6,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Terbium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Terbium is a chemical element with symbol Tb and atomic number 65. It is a silvery-white rare earth metal that is malleable, ductile and soft enough to be cut with a knife. Terbium is never found in nature as a free element, but it is contained in many minerals, including cerite, gadolinite, monazite, xenotime and euxenite.\",");
	    sb.append("            \"symbol\": \"Tb\",");
	    sb.append("            \"xpos\": 11,");
	    sb.append("            \"ypos\": 9,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                27,");
	    sb.append("                8,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Dysprosium\",");
	    sb.append("            \"appearance\": \"silvery white\",");
	    sb.append("            \"atomic_mass\": 162.5001,");
	    sb.append("            \"boil\": 2840,");
	    sb.append("            \"category\": \"lanthanide\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 8.54,");
	    sb.append("            \"discovered_by\": \"Lecoq de Boisbaudran\",");
	    sb.append("            \"melt\": 1680,");
	    sb.append("            \"molar_heat\": 27.7,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 66,");
	    sb.append("            \"period\": 6,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Dysprosium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Dysprosium is a chemical element with the symbol Dy and atomic number 66. It is a rare earth element with a metallic silver luster. Dysprosium is never found in nature as a free element, though it is found in various minerals, such as xenotime.\",");
	    sb.append("            \"symbol\": \"Dy\",");
	    sb.append("            \"xpos\": 12,");
	    sb.append("            \"ypos\": 9,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                28,");
	    sb.append("                8,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Holmium\",");
	    sb.append("            \"appearance\": \"silvery white\",");
	    sb.append("            \"atomic_mass\": 164.930332,");
	    sb.append("            \"boil\": 2873,");
	    sb.append("            \"category\": \"lanthanide\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 8.79,");
	    sb.append("            \"discovered_by\": \"Marc Delafontaine\",");
	    sb.append("            \"melt\": 1734,");
	    sb.append("            \"molar_heat\": 27.15,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 67,");
	    sb.append("            \"period\": 6,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Holmium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Holmium is a chemical element with symbol Ho and atomic number 67. Part of the lanthanide series, holmium is a rare earth element. Holmium was discovered by Swedish chemist Per Theodor Cleve.\",");
	    sb.append("            \"symbol\": \"Ho\",");
	    sb.append("            \"xpos\": 13,");
	    sb.append("            \"ypos\": 9,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                29,");
	    sb.append("                8,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Erbium\",");
	    sb.append("            \"appearance\": \"silvery white\",");
	    sb.append("            \"atomic_mass\": 167.2593,");
	    sb.append("            \"boil\": 3141,");
	    sb.append("            \"category\": \"lanthanide\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 9.066,");
	    sb.append("            \"discovered_by\": \"Carl Gustaf Mosander\",");
	    sb.append("            \"melt\": 1802,");
	    sb.append("            \"molar_heat\": 28.12,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 68,");
	    sb.append("            \"period\": 6,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Erbium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Erbium is a chemical element in the lanthanide series, with symbol Er and atomic number 68. A silvery-white solid metal when artificially isolated, natural erbium is always found in chemical combination with other elements on Earth. As such, it is a rare earth element which is associated with several other rare elements in the mineral gadolinite from Ytterby in Sweden, where yttrium, ytterbium, and terbium were discovered.\",");
	    sb.append("            \"symbol\": \"Er\",");
	    sb.append("            \"xpos\": 14,");
	    sb.append("            \"ypos\": 9,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                30,");
	    sb.append("                8,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Thulium\",");
	    sb.append("            \"appearance\": \"silvery gray\",");
	    sb.append("            \"atomic_mass\": 168.934222,");
	    sb.append("            \"boil\": 2223,");
	    sb.append("            \"category\": \"lanthanide\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 9.32,");
	    sb.append("            \"discovered_by\": \"Per Teodor Cleve\",");
	    sb.append("            \"melt\": 1818,");
	    sb.append("            \"molar_heat\": 27.03,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 69,");
	    sb.append("            \"period\": 6,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Thulium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Thulium is a chemical element with symbol Tm and atomic number 69. It is the thirteenth and antepenultimate (third-last) element in the lanthanide series. Like the other lanthanides, the most common oxidation state is +3, seen in its oxide, halides and other compounds.\",");
	    sb.append("            \"symbol\": \"Tm\",");
	    sb.append("            \"xpos\": 15,");
	    sb.append("            \"ypos\": 9,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                31,");
	    sb.append("                8,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Ytterbium\",");
	    sb.append("            \"appearance\": null,");
	    sb.append("            \"atomic_mass\": 173.0451,");
	    sb.append("            \"boil\": 1469,");
	    sb.append("            \"category\": \"lanthanide\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 6.9,");
	    sb.append("            \"discovered_by\": \"Jean Charles Galissard de Marignac\",");
	    sb.append("            \"melt\": 1097,");
	    sb.append("            \"molar_heat\": 26.74,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 70,");
	    sb.append("            \"period\": 6,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Ytterbium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Ytterbium is a chemical element with symbol Yb and atomic number 70. It is the fourteenth and penultimate element in the lanthanide series, which is the basis of the relative stability of its +2 oxidation state. However, like the other lanthanides, its most common oxidation state is +3, seen in its oxide, halides and other compounds.\",");
	    sb.append("            \"symbol\": \"Yb\",");
	    sb.append("            \"xpos\": 16,");
	    sb.append("            \"ypos\": 9,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                32,");
	    sb.append("                8,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Lutetium\",");
	    sb.append("            \"appearance\": \"silvery white\",");
	    sb.append("            \"atomic_mass\": 174.96681,");
	    sb.append("            \"boil\": 3675,");
	    sb.append("            \"category\": \"lanthanide\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 9.841,");
	    sb.append("            \"discovered_by\": \"Georges Urbain\",");
	    sb.append("            \"melt\": 1925,");
	    sb.append("            \"molar_heat\": 26.86,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 71,");
	    sb.append("            \"period\": 6,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Lutetium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Lutetium is a chemical element with symbol Lu and atomic number 71. It is a silvery white metal, which resists corrosion in dry, but not in moist air. It is considered the first element of the 6th-period transition metals and the last element in the lanthanide series, and is traditionally counted among the rare earths.\",");
	    sb.append("            \"symbol\": \"Lu\",");
	    sb.append("            \"xpos\": 17,");
	    sb.append("            \"ypos\": 9,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                32,");
	    sb.append("                9,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Hafnium\",");
	    sb.append("            \"appearance\": \"steel gray\",");
	    sb.append("            \"atomic_mass\": 178.492,");
	    sb.append("            \"boil\": 4876,");
	    sb.append("            \"category\": \"transition metal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 13.31,");
	    sb.append("            \"discovered_by\": \"Dirk Coster\",");
	    sb.append("            \"melt\": 2506,");
	    sb.append("            \"molar_heat\": 25.73,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 72,");
	    sb.append("            \"period\": 6,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Hafnium\",");
	    sb.append("            \"spectral_img\": \"https://en.wikipedia.org/wiki/File:Hafnium_spectrum_visible.png\",");
	    sb.append("            \"summary\": \"Hafnium is a chemical element with symbol Hf and atomic number 72. A lustrous, silvery gray, tetravalent transition metal, hafnium chemically resembles zirconium and is found in zirconium minerals. Its existence was predicted by Dmitri Mendeleev in 1869, though it was not identified until 1923, making it the penultimate stable element to be discovered (rhenium was identified two years later).\",");
	    sb.append("            \"symbol\": \"Hf\",");
	    sb.append("            \"xpos\": 4,");
	    sb.append("            \"ypos\": 6,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                32,");
	    sb.append("                10,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Tantalum\",");
	    sb.append("            \"appearance\": \"gray blue\",");
	    sb.append("            \"atomic_mass\": 180.947882,");
	    sb.append("            \"boil\": 5731,");
	    sb.append("            \"category\": \"transition metal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 16.69,");
	    sb.append("            \"discovered_by\": \"Anders Gustaf Ekeberg\",");
	    sb.append("            \"melt\": 3290,");
	    sb.append("            \"molar_heat\": 25.36,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 73,");
	    sb.append("            \"period\": 6,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Tantalum\",");
	    sb.append("            \"spectral_img\": \"https://en.wikipedia.org/wiki/File:Tantalum_spectrum_visible.png\",");
	    sb.append("            \"summary\": \"Tantalum is a chemical element with symbol Ta and atomic number 73. Previously known as tantalium, its name comes from Tantalus, an antihero from Greek mythology. Tantalum is a rare, hard, blue-gray, lustrous transition metal that is highly corrosion-resistant.\",");
	    sb.append("            \"symbol\": \"Ta\",");
	    sb.append("            \"xpos\": 5,");
	    sb.append("            \"ypos\": 6,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                32,");
	    sb.append("                11,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Tungsten\",");
	    sb.append("            \"appearance\": \"grayish white, lustrous\",");
	    sb.append("            \"atomic_mass\": 183.841,");
	    sb.append("            \"boil\": 6203,");
	    sb.append("            \"category\": \"transition metal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 19.25,");
	    sb.append("            \"discovered_by\": \"Carl Wilhelm Scheele\",");
	    sb.append("            \"melt\": 3695,");
	    sb.append("            \"molar_heat\": 24.27,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 74,");
	    sb.append("            \"period\": 6,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Tungsten\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Tungsten, also known as wolfram, is a chemical element with symbol W and atomic number 74. The word tungsten comes from the Swedish language tung sten, which directly translates to heavy stone. Its name in Swedish is volfram, however, in order to distinguish it from scheelite, which in Swedish is alternatively named tungsten.\",");
	    sb.append("            \"symbol\": \"W\",");
	    sb.append("            \"xpos\": 6,");
	    sb.append("            \"ypos\": 6,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                32,");
	    sb.append("                12,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Rhenium\",");
	    sb.append("            \"appearance\": \"silvery-grayish\",");
	    sb.append("            \"atomic_mass\": 186.2071,");
	    sb.append("            \"boil\": 5869,");
	    sb.append("            \"category\": \"transition metal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 21.02,");
	    sb.append("            \"discovered_by\": \"Masataka Ogawa\",");
	    sb.append("            \"melt\": 3459,");
	    sb.append("            \"molar_heat\": 25.48,");
	    sb.append("            \"named_by\": \"Walter Noddack\",");
	    sb.append("            \"number\": 75,");
	    sb.append("            \"period\": 6,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Rhenium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Rhenium is a chemical element with symbol Re and atomic number 75. It is a silvery-white, heavy, third-row transition metal in group 7 of the periodic table. With an estimated average concentration of 1 part per billion (ppb), rhenium is one of the rarest elements in the Earth's crust.\",");
	    sb.append("            \"symbol\": \"Re\",");
	    sb.append("            \"xpos\": 7,");
	    sb.append("            \"ypos\": 6,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                32,");
	    sb.append("                13,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Osmium\",");
	    sb.append("            \"appearance\": \"silvery, blue cast\",");
	    sb.append("            \"atomic_mass\": 190.233,");
	    sb.append("            \"boil\": 5285,");
	    sb.append("            \"category\": \"transition metal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 22.59,");
	    sb.append("            \"discovered_by\": \"Smithson Tennant\",");
	    sb.append("            \"melt\": 3306,");
	    sb.append("            \"molar_heat\": 24.7,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 76,");
	    sb.append("            \"period\": 6,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Osmium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Osmium (from Greek osme (\u1f40\u03c3\u03bc\u03ae) meaning 'smell') is a chemical element with symbol Os and atomic number 76. It is a hard, brittle, bluish-white transition metal in the platinum group that is found as a trace element in alloys, mostly in platinum ores. Osmium is the densest naturally occurring element, with a density of 22.59 g/cm3.\",");
	    sb.append("            \"symbol\": \"Os\",");
	    sb.append("            \"xpos\": 8,");
	    sb.append("            \"ypos\": 6,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                32,");
	    sb.append("                14,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Iridium\",");
	    sb.append("            \"appearance\": \"silvery white\",");
	    sb.append("            \"atomic_mass\": 192.2173,");
	    sb.append("            \"boil\": 4403,");
	    sb.append("            \"category\": \"transition metal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 22.56,");
	    sb.append("            \"discovered_by\": \"Smithson Tennant\",");
	    sb.append("            \"melt\": 2719,");
	    sb.append("            \"molar_heat\": 25.1,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 77,");
	    sb.append("            \"period\": 6,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Iridium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Iridium is a chemical element with symbol Ir and atomic number 77. A very hard, brittle, silvery-white transition metal of the platinum group, iridium is generally credited with being the second densest element (after osmium) based on measured density, although calculations involving the space lattices of the elements show that iridium is denser. It is also the most corrosion-resistant metal, even at temperatures as high as 2000 \u00b0C. Although only certain molten salts and halogens are corrosive to solid iridium, finely divided iridium dust is much more reactive and can be flammable.\",");
	    sb.append("            \"symbol\": \"Ir\",");
	    sb.append("            \"xpos\": 9,");
	    sb.append("            \"ypos\": 6,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                32,");
	    sb.append("                15,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Platinum\",");
	    sb.append("            \"appearance\": \"silvery white\",");
	    sb.append("            \"atomic_mass\": 195.0849,");
	    sb.append("            \"boil\": 4098,");
	    sb.append("            \"category\": \"transition metal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 21.45,");
	    sb.append("            \"discovered_by\": \"Antonio de Ulloa\",");
	    sb.append("            \"melt\": 2041.4,");
	    sb.append("            \"molar_heat\": 25.86,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 78,");
	    sb.append("            \"period\": 6,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Platinum\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Platinum is a chemical element with symbol Pt and atomic number 78. It is a dense, malleable, ductile, highly unreactive, precious, gray-white transition metal. Its name is derived from the Spanish term platina, which is literally translated into 'little silver'.\",");
	    sb.append("            \"symbol\": \"Pt\",");
	    sb.append("            \"xpos\": 10,");
	    sb.append("            \"ypos\": 6,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                32,");
	    sb.append("                17,");
	    sb.append("                1");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Gold\",");
	    sb.append("            \"appearance\": \"metallic yellow\",");
	    sb.append("            \"atomic_mass\": 196.9665695,");
	    sb.append("            \"boil\": 3243,");
	    sb.append("            \"category\": \"transition metal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 19.3,");
	    sb.append("            \"discovered_by\": \"Middle East\",");
	    sb.append("            \"melt\": 1337.33,");
	    sb.append("            \"molar_heat\": 25.418,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 79,");
	    sb.append("            \"period\": 6,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Gold\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Gold is a chemical element with symbol Au (from Latin:aurum) and atomic number 79. In its purest form, it is a bright, slightly reddish yellow, dense, soft, malleable and ductile metal. Chemically, gold is a transition metal and a group 11 element.\",");
	    sb.append("            \"symbol\": \"Au\",");
	    sb.append("            \"xpos\": 11,");
	    sb.append("            \"ypos\": 6,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                32,");
	    sb.append("                18,");
	    sb.append("                1");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Mercury\",");
	    sb.append("            \"appearance\": \"silvery\",");
	    sb.append("            \"atomic_mass\": 200.5923,");
	    sb.append("            \"boil\": 629.88,");
	    sb.append("            \"category\": \"transition metal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 13.534,");
	    sb.append("            \"discovered_by\": \"unknown, before 2000 BCE\",");
	    sb.append("            \"melt\": 234.321,");
	    sb.append("            \"molar_heat\": 27.983,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 80,");
	    sb.append("            \"period\": 6,");
	    sb.append("            \"phase\": \"Liquid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Mercury (Element)\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Mercury is a chemical element with symbol Hg and atomic number 80. It is commonly known as quicksilver and was formerly named hydrargyrum (/ha\u026a\u02c8dr\u0251\u02d0rd\u0292\u0259r\u0259m/). A heavy, silvery d-block element, mercury is the only metallic element that is liquid at standard conditions for temperature and pressure; the only other element that is liquid under these conditions is bromine, though metals such as caesium, gallium, and rubidium melt just above room temperature.\",");
	    sb.append("            \"symbol\": \"Hg\",");
	    sb.append("            \"xpos\": 12,");
	    sb.append("            \"ypos\": 6,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                32,");
	    sb.append("                18,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Thallium\",");
	    sb.append("            \"appearance\": \"silvery white\",");
	    sb.append("            \"atomic_mass\": 204.38,");
	    sb.append("            \"boil\": 1746,");
	    sb.append("            \"category\": \"post-transition metal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 11.85,");
	    sb.append("            \"discovered_by\": \"William Crookes\",");
	    sb.append("            \"melt\": 577,");
	    sb.append("            \"molar_heat\": 26.32,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 81,");
	    sb.append("            \"period\": 6,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Thallium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Thallium is a chemical element with symbol Tl and atomic number 81. This soft gray post-transition metal is not found free in nature. When isolated, it resembles tin, but discolors when exposed to air.\",");
	    sb.append("            \"symbol\": \"Tl\",");
	    sb.append("            \"xpos\": 13,");
	    sb.append("            \"ypos\": 6,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                32,");
	    sb.append("                18,");
	    sb.append("                3");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Lead\",");
	    sb.append("            \"appearance\": \"metallic gray\",");
	    sb.append("            \"atomic_mass\": 207.21,");
	    sb.append("            \"boil\": 2022,");
	    sb.append("            \"category\": \"post-transition metal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 11.34,");
	    sb.append("            \"discovered_by\": \"Middle East\",");
	    sb.append("            \"melt\": 600.61,");
	    sb.append("            \"molar_heat\": 26.65,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 82,");
	    sb.append("            \"period\": 6,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Lead_(element)\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Lead (/l\u025bd/) is a chemical element in the carbon group with symbol Pb (from Latin:plumbum) and atomic number 82. Lead is a soft, malleable and heavy post-transition metal. Metallic lead has a bluish-white color after being freshly cut, but it soon tarnishes to a dull grayish color when exposed to air.\",");
	    sb.append("            \"symbol\": \"Pb\",");
	    sb.append("            \"xpos\": 14,");
	    sb.append("            \"ypos\": 6,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                32,");
	    sb.append("                18,");
	    sb.append("                4");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Bismuth\",");
	    sb.append("            \"appearance\": \"lustrous silver\",");
	    sb.append("            \"atomic_mass\": 208.980401,");
	    sb.append("            \"boil\": 1837,");
	    sb.append("            \"category\": \"post-transition metal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 9.78,");
	    sb.append("            \"discovered_by\": \"Claude Fran\u00e7ois Geoffroy\",");
	    sb.append("            \"melt\": 544.7,");
	    sb.append("            \"molar_heat\": 25.52,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 83,");
	    sb.append("            \"period\": 6,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Bismuth\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Bismuth is a chemical element with symbol Bi and atomic number 83. Bismuth, a pentavalent post-transition metal, chemically resembles arsenic and antimony. Elemental bismuth may occur naturally, although its sulfide and oxide form important commercial ores.\",");
	    sb.append("            \"symbol\": \"Bi\",");
	    sb.append("            \"xpos\": 15,");
	    sb.append("            \"ypos\": 6,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                32,");
	    sb.append("                18,");
	    sb.append("                5");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Polonium\",");
	    sb.append("            \"appearance\": \"silvery\",");
	    sb.append("            \"atomic_mass\": 209,");
	    sb.append("            \"boil\": 1235,");
	    sb.append("            \"category\": \"post-transition metal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 9.196,");
	    sb.append("            \"discovered_by\": \"Pierre Curie\",");
	    sb.append("            \"melt\": 527,");
	    sb.append("            \"molar_heat\": 26.4,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 84,");
	    sb.append("            \"period\": 6,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Polonium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Polonium is a chemical element with symbol Po and atomic number 84, discovered in 1898 by Marie Curie and Pierre Curie. A rare and highly radioactive element with no stable isotopes, polonium is chemically similar to bismuth and tellurium, and it occurs in uranium ores. Applications of polonium are few.\",");
	    sb.append("            \"symbol\": \"Po\",");
	    sb.append("            \"xpos\": 16,");
	    sb.append("            \"ypos\": 6,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                32,");
	    sb.append("                18,");
	    sb.append("                6");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Astatine\",");
	    sb.append("            \"appearance\": \"unknown, probably metallic\",");
	    sb.append("            \"atomic_mass\": 210,");
	    sb.append("            \"boil\": 610,");
	    sb.append("            \"category\": \"metalloid\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 26.35,");
	    sb.append("            \"discovered_by\": \"Dale R. Corson\",");
	    sb.append("            \"melt\": 575,");
	    sb.append("            \"molar_heat\": null,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 85,");
	    sb.append("            \"period\": 6,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Astatine\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Astatine is a very rare radioactive chemical element with the chemical symbol At and atomic number 85. It occurs on Earth as the decay product of various heavier elements. All its isotopes are short-lived; the most stable is astatine-210, with a half-life of 8.1 hours.\",");
	    sb.append("            \"symbol\": \"At\",");
	    sb.append("            \"xpos\": 17,");
	    sb.append("            \"ypos\": 6,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                32,");
	    sb.append("                18,");
	    sb.append("                7");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Radon\",");
	    sb.append("            \"appearance\": \"colorless gas, occasionally glows green or red in discharge tubes\",");
	    sb.append("            \"atomic_mass\": 222,");
	    sb.append("            \"boil\": 211.5,");
	    sb.append("            \"category\": \"noble gas\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 9.73,");
	    sb.append("            \"discovered_by\": \"Friedrich Ernst Dorn\",");
	    sb.append("            \"melt\": 202,");
	    sb.append("            \"molar_heat\": null,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 86,");
	    sb.append("            \"period\": 6,");
	    sb.append("            \"phase\": \"Gas\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Radon\",");
	    sb.append("            \"spectral_img\": \"https://en.wikipedia.org/wiki/File:Radon_spectrum.png\",");
	    sb.append("            \"summary\": \"Radon is a chemical element with symbol Rn and atomic number 86. It is a radioactive, colorless, odorless, tasteless noble gas, occurring naturally as a decay product of radium. Its most stable isotope, 222Rn, has a half-life of 3.8 days.\",");
	    sb.append("            \"symbol\": \"Rn\",");
	    sb.append("            \"xpos\": 18,");
	    sb.append("            \"ypos\": 6,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                32,");
	    sb.append("                18,");
	    sb.append("                8");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Francium\",");
	    sb.append("            \"appearance\": null,");
	    sb.append("            \"atomic_mass\": 223,");
	    sb.append("            \"boil\": 950,");
	    sb.append("            \"category\": \"alkali metal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 1.87,");
	    sb.append("            \"discovered_by\": \"Marguerite Perey\",");
	    sb.append("            \"melt\": 300,");
	    sb.append("            \"molar_heat\": null,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 87,");
	    sb.append("            \"period\": 7,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Francium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Francium is a chemical element with symbol Fr and atomic number 87. It used to be known as eka-caesium and actinium K. It is the second-least electronegative element, behind only caesium. Francium is a highly radioactive metal that decays into astatine, radium, and radon.\",");
	    sb.append("            \"symbol\": \"Fr\",");
	    sb.append("            \"xpos\": 1,");
	    sb.append("            \"ypos\": 7,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                32,");
	    sb.append("                18,");
	    sb.append("                8,");
	    sb.append("                1");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Radium\",");
	    sb.append("            \"appearance\": \"silvery white metallic\",");
	    sb.append("            \"atomic_mass\": 226,");
	    sb.append("            \"boil\": 2010,");
	    sb.append("            \"category\": \"alkaline earth metal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 5.5,");
	    sb.append("            \"discovered_by\": \"Pierre Curie\",");
	    sb.append("            \"melt\": 1233,");
	    sb.append("            \"molar_heat\": null,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 88,");
	    sb.append("            \"period\": 7,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Radium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Radium is a chemical element with symbol Ra and atomic number 88. It is the sixth element in group 2 of the periodic table, also known as the alkaline earth metals. Pure radium is almost colorless, but it readily combines with nitrogen (rather than oxygen) on exposure to air, forming a black surface layer of radium nitride (Ra3N2).\",");
	    sb.append("            \"symbol\": \"Ra\",");
	    sb.append("            \"xpos\": 2,");
	    sb.append("            \"ypos\": 7,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                32,");
	    sb.append("                18,");
	    sb.append("                8,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Actinium\",");
	    sb.append("            \"appearance\": null,");
	    sb.append("            \"atomic_mass\": 227,");
	    sb.append("            \"boil\": 3500,");
	    sb.append("            \"category\": \"actinide\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 10,");
	    sb.append("            \"discovered_by\": \"Friedrich Oskar Giesel\",");
	    sb.append("            \"melt\": 1500,");
	    sb.append("            \"molar_heat\": 27.2,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 89,");
	    sb.append("            \"period\": 7,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Actinium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Actinium is a radioactive chemical element with symbol Ac (not to be confused with the abbreviation for an acetyl group) and atomic number 89, which was discovered in 1899. It was the first non-primordial radioactive element to be isolated. Polonium, radium and radon were observed before actinium, but they were not isolated until 1902.\",");
	    sb.append("            \"symbol\": \"Ac\",");
	    sb.append("            \"xpos\": 3,");
	    sb.append("            \"ypos\": 10,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                32,");
	    sb.append("                18,");
	    sb.append("                9,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Thorium\",");
	    sb.append("            \"appearance\": \"silvery, often with black tarnish\",");
	    sb.append("            \"atomic_mass\": 232.03774,");
	    sb.append("            \"boil\": 5061,");
	    sb.append("            \"category\": \"actinide\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 11.724,");
	    sb.append("            \"discovered_by\": \"J\u00f6ns Jakob Berzelius\",");
	    sb.append("            \"melt\": 2023,");
	    sb.append("            \"molar_heat\": 26.23,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 90,");
	    sb.append("            \"period\": 7,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Thorium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Thorium is a chemical element with symbol Th and atomic number 90. A radioactive actinide metal, thorium is one of only two significantly radioactive elements that still occur naturally in large quantities as a primordial element (the other being uranium). It was discovered in 1828 by the Norwegian Reverend and amateur mineralogist Morten Thrane Esmark and identified by the Swedish chemist J\u00f6ns Jakob Berzelius, who named it after Thor, the Norse god of thunder.\",");
	    sb.append("            \"symbol\": \"Th\",");
	    sb.append("            \"xpos\": 4,");
	    sb.append("            \"ypos\": 10,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                32,");
	    sb.append("                18,");
	    sb.append("                10,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Protactinium\",");
	    sb.append("            \"appearance\": \"bright, silvery metallic luster\",");
	    sb.append("            \"atomic_mass\": 231.035882,");
	    sb.append("            \"boil\": 4300,");
	    sb.append("            \"category\": \"actinide\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 15.37,");
	    sb.append("            \"discovered_by\": \"William Crookes\",");
	    sb.append("            \"melt\": 1841,");
	    sb.append("            \"molar_heat\": null,");
	    sb.append("            \"named_by\": \"Otto Hahn\",");
	    sb.append("            \"number\": 91,");
	    sb.append("            \"period\": 7,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Protactinium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Protactinium is a chemical element with symbol Pa and atomic number 91. It is a dense, silvery-gray metal which readily reacts with oxygen, water vapor and inorganic acids. It forms various chemical compounds where protactinium is usually present in the oxidation state +5, but can also assume +4 and even +2 or +3 states.\",");
	    sb.append("            \"symbol\": \"Pa\",");
	    sb.append("            \"xpos\": 5,");
	    sb.append("            \"ypos\": 10,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                32,");
	    sb.append("                20,");
	    sb.append("                9,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Uranium\",");
	    sb.append("            \"appearance\": null,");
	    sb.append("            \"atomic_mass\": 238.028913,");
	    sb.append("            \"boil\": 4404,");
	    sb.append("            \"category\": \"actinide\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 19.1,");
	    sb.append("            \"discovered_by\": \"Martin Heinrich Klaproth\",");
	    sb.append("            \"melt\": 1405.3,");
	    sb.append("            \"molar_heat\": 27.665,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 92,");
	    sb.append("            \"period\": 7,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Uranium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Uranium is a chemical element with symbol U and atomic number 92. It is a silvery-white metal in the actinide series of the periodic table. A uranium atom has 92 protons and 92 electrons, of which 6 are valence electrons.\",");
	    sb.append("            \"symbol\": \"U\",");
	    sb.append("            \"xpos\": 6,");
	    sb.append("            \"ypos\": 10,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                32,");
	    sb.append("                21,");
	    sb.append("                9,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Neptunium\",");
	    sb.append("            \"appearance\": \"silvery metallic\",");
	    sb.append("            \"atomic_mass\": 237,");
	    sb.append("            \"boil\": 4447,");
	    sb.append("            \"category\": \"actinide\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 20.45,");
	    sb.append("            \"discovered_by\": \"Edwin McMillan\",");
	    sb.append("            \"melt\": 912,");
	    sb.append("            \"molar_heat\": 29.46,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 93,");
	    sb.append("            \"period\": 7,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Neptunium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Neptunium is a chemical element with symbol Np and atomic number 93. A radioactive actinide metal, neptunium is the first transuranic element. Its position in the periodic table just after uranium, named after the planet Uranus, led to it being named after Neptune, the next planet beyond Uranus.\",");
	    sb.append("            \"symbol\": \"Np\",");
	    sb.append("            \"xpos\": 7,");
	    sb.append("            \"ypos\": 10,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                32,");
	    sb.append("                22,");
	    sb.append("                9,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Plutonium\",");
	    sb.append("            \"appearance\": \"silvery white, tarnishing to dark gray in air\",");
	    sb.append("            \"atomic_mass\": 244,");
	    sb.append("            \"boil\": 3505,");
	    sb.append("            \"category\": \"actinide\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 19.816,");
	    sb.append("            \"discovered_by\": \"Glenn T. Seaborg\",");
	    sb.append("            \"melt\": 912.5,");
	    sb.append("            \"molar_heat\": 35.5,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 94,");
	    sb.append("            \"period\": 7,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Plutonium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Plutonium is a transuranic radioactive chemical element with symbol Pu and atomic number 94. It is an actinide metal of silvery-gray appearance that tarnishes when exposed to air, and forms a dull coating when oxidized. The element normally exhibits six allotropes and four oxidation states.\",");
	    sb.append("            \"symbol\": \"Pu\",");
	    sb.append("            \"xpos\": 8,");
	    sb.append("            \"ypos\": 10,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                32,");
	    sb.append("                24,");
	    sb.append("                8,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Americium\",");
	    sb.append("            \"appearance\": \"silvery white\",");
	    sb.append("            \"atomic_mass\": 243,");
	    sb.append("            \"boil\": 2880,");
	    sb.append("            \"category\": \"actinide\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 12,");
	    sb.append("            \"discovered_by\": \"Glenn T. Seaborg\",");
	    sb.append("            \"melt\": 1449,");
	    sb.append("            \"molar_heat\": 62.7,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 95,");
	    sb.append("            \"period\": 7,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Americium\",");
	    sb.append("            \"spectral_img\": \"https://en.wikipedia.org/wiki/File:Americium_spectrum_visible.png\",");
	    sb.append("            \"summary\": \"Americium is a radioactive transuranic chemical element with symbol Am and atomic number 95. This member of the actinide series is located in the periodic table under the lanthanide element europium, and thus by analogy was named after the Americas. Americium was first produced in 1944 by the group of Glenn T.Seaborg from Berkeley, California, at the metallurgical laboratory of University of Chicago.\",");
	    sb.append("            \"symbol\": \"Am\",");
	    sb.append("            \"xpos\": 9,");
	    sb.append("            \"ypos\": 10,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                32,");
	    sb.append("                25,");
	    sb.append("                8,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Curium\",");
	    sb.append("            \"appearance\": \"silvery metallic, glows purple in the dark\",");
	    sb.append("            \"atomic_mass\": 247,");
	    sb.append("            \"boil\": 3383,");
	    sb.append("            \"category\": \"actinide\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 13.51,");
	    sb.append("            \"discovered_by\": \"Glenn T. Seaborg\",");
	    sb.append("            \"melt\": 1613,");
	    sb.append("            \"molar_heat\": null,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 96,");
	    sb.append("            \"period\": 7,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Curium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Curium is a transuranic radioactive chemical element with symbol Cm and atomic number 96. This element of the actinide series was named after Marie and Pierre Curie \u2013 both were known for their research on radioactivity. Curium was first intentionally produced and identified in July 1944 by the group of Glenn T. Seaborg at the University of California, Berkeley.\",");
	    sb.append("            \"symbol\": \"Cm\",");
	    sb.append("            \"xpos\": 10,");
	    sb.append("            \"ypos\": 10,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                32,");
	    sb.append("                25,");
	    sb.append("                9,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Berkelium\",");
	    sb.append("            \"appearance\": \"silvery\",");
	    sb.append("            \"atomic_mass\": 247,");
	    sb.append("            \"boil\": 2900,");
	    sb.append("            \"category\": \"actinide\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 14.78,");
	    sb.append("            \"discovered_by\": \"Lawrence Berkeley National Laboratory\",");
	    sb.append("            \"melt\": 1259,");
	    sb.append("            \"molar_heat\": null,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 97,");
	    sb.append("            \"period\": 7,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Berkelium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Berkelium is a transuranic radioactive chemical element with symbol Bk and atomic number 97. It is a member of the actinide and transuranium element series. It is named after the city of Berkeley, California, the location of the University of California Radiation Laboratory where it was discovered in December 1949.\",");
	    sb.append("            \"symbol\": \"Bk\",");
	    sb.append("            \"xpos\": 11,");
	    sb.append("            \"ypos\": 10,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                32,");
	    sb.append("                27,");
	    sb.append("                8,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Californium\",");
	    sb.append("            \"appearance\": \"silvery\",");
	    sb.append("            \"atomic_mass\": 251,");
	    sb.append("            \"boil\": 1743,");
	    sb.append("            \"category\": \"actinide\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 15.1,");
	    sb.append("            \"discovered_by\": \"Lawrence Berkeley National Laboratory\",");
	    sb.append("            \"melt\": 1173,");
	    sb.append("            \"molar_heat\": null,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 98,");
	    sb.append("            \"period\": 7,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Californium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Californium is a radioactive metallic chemical element with symbol Cf and atomic number 98. The element was first made in 1950 at the University of California Radiation Laboratory in Berkeley, by bombarding curium with alpha particles (helium-4 ions). It is an actinide element, the sixth transuranium element to be synthesized, and has the second-highest atomic mass of all the elements that have been produced in amounts large enough to see with the unaided eye (after einsteinium).\",");
	    sb.append("            \"symbol\": \"Cf\",");
	    sb.append("            \"xpos\": 12,");
	    sb.append("            \"ypos\": 10,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                32,");
	    sb.append("                28,");
	    sb.append("                8,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Einsteinium\",");
	    sb.append("            \"appearance\": \"silver-colored\",");
	    sb.append("            \"atomic_mass\": 252,");
	    sb.append("            \"boil\": 1269,");
	    sb.append("            \"category\": \"actinide\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 8.84,");
	    sb.append("            \"discovered_by\": \"Lawrence Berkeley National Laboratory\",");
	    sb.append("            \"melt\": 1133,");
	    sb.append("            \"molar_heat\": null,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 99,");
	    sb.append("            \"period\": 7,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Einsteinium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Einsteinium is a synthetic element with symbol Es and atomic number 99. It is the seventh transuranic element, and an actinide. Einsteinium was discovered as a component of the debris of the first hydrogen bomb explosion in 1952, and named after Albert Einstein.\",");
	    sb.append("            \"symbol\": \"Es\",");
	    sb.append("            \"xpos\": 13,");
	    sb.append("            \"ypos\": 10,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                32,");
	    sb.append("                29,");
	    sb.append("                8,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Fermium\",");
	    sb.append("            \"appearance\": null,");
	    sb.append("            \"atomic_mass\": 257,");
	    sb.append("            \"boil\": null,");
	    sb.append("            \"category\": \"actinide\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": null,");
	    sb.append("            \"discovered_by\": \"Lawrence Berkeley National Laboratory\",");
	    sb.append("            \"melt\": 1800,");
	    sb.append("            \"molar_heat\": null,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 100,");
	    sb.append("            \"period\": 7,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Fermium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Fermium is a synthetic element with symbol Fm and atomic number 100. It is a member of the actinide series. It is the heaviest element that can be formed by neutron bombardment of lighter elements, and hence the last element that can be prepared in macroscopic quantities, although pure fermium metal has not yet been prepared.\",");
	    sb.append("            \"symbol\": \"Fm\",");
	    sb.append("            \"xpos\": 14,");
	    sb.append("            \"ypos\": 10,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                32,");
	    sb.append("                30,");
	    sb.append("                8,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Mendelevium\",");
	    sb.append("            \"appearance\": null,");
	    sb.append("            \"atomic_mass\": 258,");
	    sb.append("            \"boil\": null,");
	    sb.append("            \"category\": \"actinide\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": null,");
	    sb.append("            \"discovered_by\": \"Lawrence Berkeley National Laboratory\",");
	    sb.append("            \"melt\": 1100,");
	    sb.append("            \"molar_heat\": null,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 101,");
	    sb.append("            \"period\": 7,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Mendelevium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Mendelevium is a synthetic element with chemical symbol Md (formerly Mv) and atomic number 101. A metallic radioactive transuranic element in the actinide series, it is the first element that currently cannot be produced in macroscopic quantities through neutron bombardment of lighter elements. It is the antepenultimate actinide and the ninth transuranic element.\",");
	    sb.append("            \"symbol\": \"Md\",");
	    sb.append("            \"xpos\": 15,");
	    sb.append("            \"ypos\": 10,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                32,");
	    sb.append("                31,");
	    sb.append("                8,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Nobelium\",");
	    sb.append("            \"appearance\": null,");
	    sb.append("            \"atomic_mass\": 259,");
	    sb.append("            \"boil\": null,");
	    sb.append("            \"category\": \"actinide\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": null,");
	    sb.append("            \"discovered_by\": \"Joint Institute for Nuclear Research\",");
	    sb.append("            \"melt\": 1100,");
	    sb.append("            \"molar_heat\": null,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 102,");
	    sb.append("            \"period\": 7,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Nobelium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Nobelium is a synthetic chemical element with symbol No and atomic number 102. It is named in honor of Alfred Nobel, the inventor of dynamite and benefactor of science. A radioactive metal, it is the tenth transuranic element and is the penultimate member of the actinide series.\",");
	    sb.append("            \"symbol\": \"No\",");
	    sb.append("            \"xpos\": 16,");
	    sb.append("            \"ypos\": 10,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                32,");
	    sb.append("                32,");
	    sb.append("                8,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Lawrencium\",");
	    sb.append("            \"appearance\": null,");
	    sb.append("            \"atomic_mass\": 266,");
	    sb.append("            \"boil\": null,");
	    sb.append("            \"category\": \"actinide\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": null,");
	    sb.append("            \"discovered_by\": \"Lawrence Berkeley National Laboratory\",");
	    sb.append("            \"melt\": 1900,");
	    sb.append("            \"molar_heat\": null,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 103,");
	    sb.append("            \"period\": 7,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Lawrencium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Lawrencium is a synthetic chemical element with chemical symbol Lr (formerly Lw) and atomic number 103. It is named in honor of Ernest Lawrence, inventor of the cyclotron, a device that was used to discover many artificial radioactive elements. A radioactive metal, lawrencium is the eleventh transuranic element and is also the final member of the actinide series.\",");
	    sb.append("            \"symbol\": \"Lr\",");
	    sb.append("            \"xpos\": 17,");
	    sb.append("            \"ypos\": 10,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                32,");
	    sb.append("                32,");
	    sb.append("                8,");
	    sb.append("                3");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Rutherfordium\",");
	    sb.append("            \"appearance\": null,");
	    sb.append("            \"atomic_mass\": 267,");
	    sb.append("            \"boil\": 5800,");
	    sb.append("            \"category\": \"transition metal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 23.2,");
	    sb.append("            \"discovered_by\": \"Joint Institute for Nuclear Research\",");
	    sb.append("            \"melt\": 2400,");
	    sb.append("            \"molar_heat\": null,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 104,");
	    sb.append("            \"period\": 7,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Rutherfordium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Rutherfordium is a chemical element with symbol Rf and atomic number 104, named in honor of physicist Ernest Rutherford. It is a synthetic element (an element that can be created in a laboratory but is not found in nature) and radioactive; the most stable known isotope, 267Rf, has a half-life of approximately 1.3 hours. In the periodic table of the elements, it is a d - block element and the second of the fourth - row transition elements.\",");
	    sb.append("            \"symbol\": \"Rf\",");
	    sb.append("            \"xpos\": 4,");
	    sb.append("            \"ypos\": 7,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                32,");
	    sb.append("                32,");
	    sb.append("                10,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Dubnium\",");
	    sb.append("            \"appearance\": null,");
	    sb.append("            \"atomic_mass\": 268,");
	    sb.append("            \"boil\": null,");
	    sb.append("            \"category\": \"transition metal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 29.3,");
	    sb.append("            \"discovered_by\": \"Joint Institute for Nuclear Research\",");
	    sb.append("            \"melt\": null,");
	    sb.append("            \"molar_heat\": null,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 105,");
	    sb.append("            \"period\": 7,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Dubnium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Dubnium is a chemical element with symbol Db and atomic number 105. It is named after the town of Dubna in Russia (north of Moscow), where it was first produced. It is a synthetic element (an element that can be created in a laboratory but is not found in nature) and radioactive; the most stable known isotope, dubnium-268, has a half-life of approximately 28 hours.\",");
	    sb.append("            \"symbol\": \"Db\",");
	    sb.append("            \"xpos\": 5,");
	    sb.append("            \"ypos\": 7,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                32,");
	    sb.append("                32,");
	    sb.append("                11,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Seaborgium\",");
	    sb.append("            \"appearance\": null,");
	    sb.append("            \"atomic_mass\": 269,");
	    sb.append("            \"boil\": null,");
	    sb.append("            \"category\": \"transition metal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 35.0,");
	    sb.append("            \"discovered_by\": \"Lawrence Berkeley National Laboratory\",");
	    sb.append("            \"melt\": null,");
	    sb.append("            \"molar_heat\": null,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 106,");
	    sb.append("            \"period\": 7,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Seaborgium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Seaborgium is a synthetic element with symbol Sg and atomic number 106. Its most stable isotope 271Sg has a half-life of 1.9 minutes. A more recently discovered isotope 269Sg has a potentially slightly longer half-life (ca.\",");
	    sb.append("            \"symbol\": \"Sg\",");
	    sb.append("            \"xpos\": 6,");
	    sb.append("            \"ypos\": 7,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                32,");
	    sb.append("                32,");
	    sb.append("                12,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Bohrium\",");
	    sb.append("            \"appearance\": null,");
	    sb.append("            \"atomic_mass\": 270,");
	    sb.append("            \"boil\": null,");
	    sb.append("            \"category\": \"transition metal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 37.1,");
	    sb.append("            \"discovered_by\": \"Gesellschaft f\u00fcr Schwerionenforschung\",");
	    sb.append("            \"melt\": null,");
	    sb.append("            \"molar_heat\": null,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 107,");
	    sb.append("            \"period\": 7,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Bohrium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Bohrium is a chemical element with symbol Bh and atomic number 107. It is named after Danish physicist Niels Bohr. It is a synthetic element (an element that can be created in a laboratory but is not found in nature) and radioactive; the most stable known isotope, 270Bh, has a half-life of approximately 61 seconds.\",");
	    sb.append("            \"symbol\": \"Bh\",");
	    sb.append("            \"xpos\": 7,");
	    sb.append("            \"ypos\": 7,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                32,");
	    sb.append("                32,");
	    sb.append("                13,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Hassium\",");
	    sb.append("            \"appearance\": null,");
	    sb.append("            \"atomic_mass\": 269,");
	    sb.append("            \"boil\": null,");
	    sb.append("            \"category\": \"transition metal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 40.7,");
	    sb.append("            \"discovered_by\": \"Gesellschaft f\u00fcr Schwerionenforschung\",");
	    sb.append("            \"melt\": 126,");
	    sb.append("            \"molar_heat\": null,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 108,");
	    sb.append("            \"period\": 7,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Hassium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Hassium is a chemical element with symbol Hs and atomic number 108, named after the German state of Hesse. It is a synthetic element (an element that can be created in a laboratory but is not found in nature) and radioactive; the most stable known isotope, 269Hs, has a half-life of approximately 9.7 seconds, although an unconfirmed metastable state, 277mHs, may have a longer half-life of about 130 seconds. More than 100 atoms of hassium have been synthesized to date.\",");
	    sb.append("            \"symbol\": \"Hs\",");
	    sb.append("            \"xpos\": 8,");
	    sb.append("            \"ypos\": 7,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                32,");
	    sb.append("                32,");
	    sb.append("                14,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Meitnerium\",");
	    sb.append("            \"appearance\": null,");
	    sb.append("            \"atomic_mass\": 278,");
	    sb.append("            \"boil\": null,");
	    sb.append("            \"category\": \"unknown, probably transition metal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 37.4,");
	    sb.append("            \"discovered_by\": \"Gesellschaft f\u00fcr Schwerionenforschung\",");
	    sb.append("            \"melt\": null,");
	    sb.append("            \"molar_heat\": null,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 109,");
	    sb.append("            \"period\": 7,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Meitnerium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Meitnerium is a chemical element with symbol Mt and atomic number 109. It is an extremely radioactive synthetic element (an element not found in nature that can be created in a laboratory). The most stable known isotope, meitnerium-278, has a half-life of 7.6 seconds.\",");
	    sb.append("            \"symbol\": \"Mt\",");
	    sb.append("            \"xpos\": 9,");
	    sb.append("            \"ypos\": 7,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                32,");
	    sb.append("                32,");
	    sb.append("                15,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Darmstadtium\",");
	    sb.append("            \"appearance\": null,");
	    sb.append("            \"atomic_mass\": 281,");
	    sb.append("            \"boil\": null,");
	    sb.append("            \"category\": \"unknown, probably transition metal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 34.8,");
	    sb.append("            \"discovered_by\": \"Gesellschaft f\u00fcr Schwerionenforschung\",");
	    sb.append("            \"melt\": null,");
	    sb.append("            \"molar_heat\": null,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 110,");
	    sb.append("            \"period\": 7,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Darmstadtium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Darmstadtium is a chemical element with symbol Ds and atomic number 110. It is an extremely radioactive synthetic element. The most stable known isotope, darmstadtium-281, has a half-life of approximately 10 seconds.\",");
	    sb.append("            \"symbol\": \"Ds\",");
	    sb.append("            \"xpos\": 10,");
	    sb.append("            \"ypos\": 7,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                32,");
	    sb.append("                32,");
	    sb.append("                16,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Roentgenium\",");
	    sb.append("            \"appearance\": null,");
	    sb.append("            \"atomic_mass\": 282,");
	    sb.append("            \"boil\": null,");
	    sb.append("            \"category\": \"unknown, probably transition metal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 28.7,");
	    sb.append("            \"discovered_by\": \"Gesellschaft f\u00fcr Schwerionenforschung\",");
	    sb.append("            \"melt\": null,");
	    sb.append("            \"molar_heat\": null,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 111,");
	    sb.append("            \"period\": 7,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Roentgenium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Roentgenium is a chemical element with symbol Rg and atomic number 111. It is an extremely radioactive synthetic element (an element that can be created in a laboratory but is not found in nature); the most stable known isotope, roentgenium-282, has a half-life of 2.1 minutes. Roentgenium was first created in 1994 by the GSI Helmholtz Centre for Heavy Ion Research near Darmstadt, Germany.\",");
	    sb.append("            \"symbol\": \"Rg\",");
	    sb.append("            \"xpos\": 11,");
	    sb.append("            \"ypos\": 7,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                32,");
	    sb.append("                32,");
	    sb.append("                17,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Copernicium\",");
	    sb.append("            \"appearance\": null,");
	    sb.append("            \"atomic_mass\": 285,");
	    sb.append("            \"boil\": 3570,");
	    sb.append("            \"category\": \"transition metal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 23.7,");
	    sb.append("            \"discovered_by\": \"Gesellschaft f\u00fcr Schwerionenforschung\",");
	    sb.append("            \"melt\": null,");
	    sb.append("            \"molar_heat\": null,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 112,");
	    sb.append("            \"period\": 7,");
	    sb.append("            \"phase\": \"Gas\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Copernicium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Copernicium is a chemical element with symbol Cn and atomic number 112. It is an extremely radioactive synthetic element that can only be created in a laboratory. The most stable known isotope, copernicium-285, has a half-life of approximately 29 seconds, but it is possible that this copernicium isotope may have a nuclear isomer with a longer half-life, 8.9 min.\",");
	    sb.append("            \"symbol\": \"Cn\",");
	    sb.append("            \"xpos\": 12,");
	    sb.append("            \"ypos\": 7,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                32,");
	    sb.append("                32,");
	    sb.append("                18,");
	    sb.append("                2");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Nihonium\",");
	    sb.append("            \"appearance\": null,");
	    sb.append("            \"atomic_mass\": 286,");
	    sb.append("            \"boil\": 1430,");
	    sb.append("            \"category\": \"unknown, probably transition metal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 16,");
	    sb.append("            \"discovered_by\": \"RIKEN\",");
	    sb.append("            \"melt\": 700,");
	    sb.append("            \"molar_heat\": null,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 113,");
	    sb.append("            \"period\": 7,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Ununtrium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Nihonium is a chemical element with atomic number 113. It has a symbol Nh. It is a synthetic element (an element that can be created in a laboratory but is not found in nature) and is extremely radioactive; its most stable known isotope, nihonium-286, has a half-life of 20 seconds.\",");
	    sb.append("            \"symbol\": \"Nh\",");
	    sb.append("            \"xpos\": 13,");
	    sb.append("            \"ypos\": 7,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                32,");
	    sb.append("                32,");
	    sb.append("                18,");
	    sb.append("                3");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Flerovium\",");
	    sb.append("            \"appearance\": null,");
	    sb.append("            \"atomic_mass\": 289,");
	    sb.append("            \"boil\": 420,");
	    sb.append("            \"category\": \"post-transition metal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 14,");
	    sb.append("            \"discovered_by\": \"Joint Institute for Nuclear Research\",");
	    sb.append("            \"melt\": 340,");
	    sb.append("            \"molar_heat\": null,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 114,");
	    sb.append("            \"period\": 7,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Flerovium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Flerovium is a superheavy artificial chemical element with symbol Fl and atomic number 114. It is an extremely radioactive synthetic element. The element is named after the Flerov Laboratory of Nuclear Reactions of the Joint Institute for Nuclear Research in Dubna, Russia, where the element was discovered in 1998.\",");
	    sb.append("            \"symbol\": \"Fl\",");
	    sb.append("            \"xpos\": 14,");
	    sb.append("            \"ypos\": 7,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                32,");
	    sb.append("                32,");
	    sb.append("                18,");
	    sb.append("                4");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Moscovium\",");
	    sb.append("            \"appearance\": null,");
	    sb.append("            \"atomic_mass\": 289,");
	    sb.append("            \"boil\": 1400,");
	    sb.append("            \"category\": \"unknown, probably post-transition metal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 13.5,");
	    sb.append("            \"discovered_by\": \"Joint Institute for Nuclear Research\",");
	    sb.append("            \"melt\": 670,");
	    sb.append("            \"molar_heat\": null,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 115,");
	    sb.append("            \"period\": 7,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Ununpentium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Moscovium is the name of a synthetic superheavy element in the periodic table that has the symbol Mc and has the atomic number 115. It is an extremely radioactive element; its most stable known isotope, moscovium-289, has a half-life of only 220 milliseconds. It is also known as eka-bismuth or simply element 115.\",");
	    sb.append("            \"symbol\": \"Mc\",");
	    sb.append("            \"xpos\": 15,");
	    sb.append("            \"ypos\": 7,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                32,");
	    sb.append("                32,");
	    sb.append("                18,");
	    sb.append("                5");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Livermorium\",");
	    sb.append("            \"appearance\": null,");
	    sb.append("            \"atomic_mass\": 293,");
	    sb.append("            \"boil\": 1085,");
	    sb.append("            \"category\": \"unknown, probably post-transition metal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 12.9,");
	    sb.append("            \"discovered_by\": \"Joint Institute for Nuclear Research\",");
	    sb.append("            \"melt\": 709,");
	    sb.append("            \"molar_heat\": null,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 116,");
	    sb.append("            \"period\": 7,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Livermorium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Livermorium is a synthetic superheavy element with symbol Lv and atomic number 116. It is an extremely radioactive element that has only been created in the laboratory and has not been observed in nature. The element is named after the Lawrence Livermore National Laboratory in the United States, which collaborated with the Joint Institute for Nuclear Research in Dubna, Russia to discover livermorium in 2000.\",");
	    sb.append("            \"symbol\": \"Lv\",");
	    sb.append("            \"xpos\": 16,");
	    sb.append("            \"ypos\": 7,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                32,");
	    sb.append("                32,");
	    sb.append("                18,");
	    sb.append("                6");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Tennessine\",");
	    sb.append("            \"appearance\": null,");
	    sb.append("            \"atomic_mass\": 294,");
	    sb.append("            \"boil\": 883,");
	    sb.append("            \"category\": \"unknown, probably metalloid\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 7.17,");
	    sb.append("            \"discovered_by\": \"Joint Institute for Nuclear Research\",");
	    sb.append("            \"melt\": 723,");
	    sb.append("            \"molar_heat\": null,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 117,");
	    sb.append("            \"period\": 7,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Tennessine\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Tennessine is a superheavy artificial chemical element with an atomic number of 117 and a symbol of Ts. Also known as eka-astatine or element 117, it is the second-heaviest known element and penultimate element of the 7th period of the periodic table. As of 2016, fifteen tennessine atoms have been observed:six when it was first synthesized in 2010, seven in 2012, and two in 2014.\",");
	    sb.append("            \"symbol\": \"Ts\",");
	    sb.append("            \"xpos\": 17,");
	    sb.append("            \"ypos\": 7,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                32,");
	    sb.append("                32,");
	    sb.append("                18,");
	    sb.append("                7");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Oganesson\",");
	    sb.append("            \"appearance\": null,");
	    sb.append("            \"atomic_mass\": 294,");
	    sb.append("            \"boil\": 350,");
	    sb.append("            \"category\": \"unknown, predicted to be noble gas\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 4.95,");
	    sb.append("            \"discovered_by\": \"Joint Institute for Nuclear Research\",");
	    sb.append("            \"melt\": null,");
	    sb.append("            \"molar_heat\": null,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 118,");
	    sb.append("            \"period\": 7,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Oganesson\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Oganesson is IUPAC's name for the transactinide element with the atomic number 118 and element symbol Og. It is also known as eka-radon or element 118, and on the periodic table of the elements it is a p-block element and the last one of the 7th period. Oganesson is currently the only synthetic member of group 18.\",");
	    sb.append("            \"symbol\": \"Og\",");
	    sb.append("            \"xpos\": 18,");
	    sb.append("            \"ypos\": 7,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                32,");
	    sb.append("                32,");
	    sb.append("                18,");
	    sb.append("                8");
	    sb.append("            ]");
	    sb.append("        },");
	    sb.append("        {");
	    sb.append("            \"name\": \"Ununennium\",");
	    sb.append("            \"appearance\": null,");
	    sb.append("            \"atomic_mass\": 315,");
	    sb.append("            \"boil\": 630,");
	    sb.append("            \"category\": \"unknown, but predicted to be an alkali metal\",");
	    sb.append("            \"color\": null,");
	    sb.append("            \"density\": 3,");
	    sb.append("            \"discovered_by\": \"GSI Helmholtz Centre for Heavy Ion Research\",");
	    sb.append("            \"melt\": null,");
	    sb.append("            \"molar_heat\": null,");
	    sb.append("            \"named_by\": null,");
	    sb.append("            \"number\": 119,");
	    sb.append("            \"period\": 8,");
	    sb.append("            \"phase\": \"Solid\",");
	    sb.append("            \"source\": \"https://en.wikipedia.org/wiki/Ununennium\",");
	    sb.append("            \"spectral_img\": null,");
	    sb.append("            \"summary\": \"Ununennium, also known as eka-francium or simply element 119, is the hypothetical chemical element with symbol Uue and atomic number 119. Ununennium and Uue are the temporary systematic IUPAC name and symbol respectively, until a permanent name is decided upon. In the periodic table of the elements, it is expected to be an s-block element, an alkali metal, and the first element in the eighth period.\",");
	    sb.append("            \"symbol\": \"Uue\",");
	    sb.append("            \"xpos\": 1,");
	    sb.append("            \"ypos\": 8,");
	    sb.append("            \"shells\": [");
	    sb.append("                2,");
	    sb.append("                8,");
	    sb.append("                18,");
	    sb.append("                32,");
	    sb.append("                32,");
	    sb.append("                18,");
	    sb.append("                8,");
	    sb.append("                1");
	    sb.append("            ]");
	    sb.append("        }");
	    sb.append("    ]");
	    sb.append("}");
		return sb.toString();
	}
}
