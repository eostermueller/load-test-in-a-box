package com.github.eostermueller.snail4j.workload.markdown;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.github.eostermueller.snail4j.workload.DefaultFactory;
import com.github.eostermueller.snail4j.workload.Snail4jWorkloadException;

class ListTrivialMarkdownFilesTest {
	protected static final String PATH_1 = "com/github/eostermueller/tjp2/md1";
	protected static final String PARENT_1 = PATH_1 + "/index.md";
	protected static final String PARENT_1_CONTENT = "H1 Hello Parent World!\n";
	protected static final String CHILD_1 = PATH_1 + "/child.md";
	protected static final String CHILD_1_CONTENT = "H1 Hello Child World!\n";
	Map<String,String> mapOfExpectedFileNames = new Hashtable<String,String>();
	
	@Test
	void canGetListOfMarkdownFiles() throws IOException, Snail4jWorkloadException {
		
		MarkdownLoader loader = DefaultFactory.getFactory().createMarkdownLoader();
		
		loader.setLocator( getTrivialMarkdownLocator() );
		Map<String,String> mapOfExpectedFileNames = new Hashtable<String,String>();
		mapOfExpectedFileNames.put(PARENT_1, "index.md");
		mapOfExpectedFileNames.put(CHILD_1, "child.md");
		
		List<ParentMarkdownFile> allParentMdFiles = loader.getMarkdownFiles();
		int childCounter = 0;
		int parentCounter = 0;
		for(ParentMarkdownFile parent : allParentMdFiles) {
			parentCounter++;
			
			if (!expectedFile(mapOfExpectedFileNames,parent,"index.md", PARENT_1_CONTENT)) 
				fail("Could not find index.md");
			List<MarkdownFile> files = parent.getChildMarkdownFiles();
			for (MarkdownFile file : files ) {
				if (!expectedFile(mapOfExpectedFileNames,file,"child.md", CHILD_1_CONTENT)) 
					fail("Could not find child.md");
				childCounter++;
			}
		}
		assertEquals(1,childCounter);
		assertEquals(1,parentCounter);
	}

	private MarkdownLocator getTrivialMarkdownLocator() {
		return new MarkdownLocator() {

			@Override
			public void loadParentFiles(MarkdownLoader loader) throws Snail4jWorkloadException {
				loader.loadMarkdownFile( Paths.get(PARENT_1), PARENT_1_CONTENT);
			}

			@Override
			public void loadChildFiles(MarkdownLoader loader) throws Snail4jWorkloadException {
				loader.loadMarkdownFile( Paths.get(CHILD_1), CHILD_1_CONTENT);
			}
			
		};
	}

	private boolean expectedFile(Map<String, String> mapOfExpectedFileNames, MarkdownFile file, String criteria, String content) {
		String key = file.getPath().toString();
		
		/**
		 * do all comparisons using unix path separator
		 */
		key = key.replace('\\', '/');
		String valueFromMap = mapOfExpectedFileNames.get(key);
		
		boolean ynFound = false;
		if (valueFromMap.equals(criteria)) {
			
			if (file.getContent().equals(content))
				ynFound = true;
		}
		
		return ynFound;
	}

}
