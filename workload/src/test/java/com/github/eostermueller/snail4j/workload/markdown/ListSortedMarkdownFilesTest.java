package com.github.eostermueller.snail4j.workload.markdown;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.github.eostermueller.snail4j.workload.DefaultFactory;
import com.github.eostermueller.snail4j.workload.Snail4jWorkloadException;

/**
 * 
 * Do not add sort operations to this test, because we're trying to validate whether the
 * SUT performance sorting correctly. 
 * @author eoste
 *
 */
class ListSortedMarkdownFilesTest {
	protected static final String PATH_1 = "com/github/eostermueller/tjp2/md1";
	protected static final String PARENT_1 = PATH_1 + "/index.md";
	protected static final String PARENT_1_CONTENT = "H1 Hello Parent World!\n[meta]: # (sortOrder=1)";
	protected static final String CHILD_1 = PATH_1 + "/child.md";
	protected static final String CHILD_1_CONTENT = "H1 Hello Child World!";

	protected static final String PATH_2 = "com/github/eostermueller/tjp2/md2";
	protected static final String PARENT_2 = PATH_2 + "/index.md";
	protected static final String PARENT_2_CONTENT = "H1 Hello Parent 2 World!\n[meta]: # (sortOrder=2)";
	
	protected static final String CHILD_21 = PATH_2 + "/child21.md";
	protected static final String CHILD_21_CONTENT = "H1 Hello Child 2-1 World!\n[meta]: # (sortOrder=1)\n";
	
	protected static final String CHILD_22 = PATH_2 + "/child22.md";
	protected static final String CHILD_22_CONTENT = "H1 Hello Child 2-2 World!\n[meta]: # (sortOrder=2)\n";
	
	protected static final String CHILD_23 = PATH_2 + "/child23.md";
	protected static final String CHILD_23_CONTENT = "H1 Hello Child 2-3 World!\n[meta]: # (sortOrder=3)\n";
	
	Map<String,String> mapOfExpectedFileNames = new Hashtable<String,String>();
	
	@Test
	void canGetSortedListOfMarkdownFiles() throws IOException, Snail4jWorkloadException {
		
		MarkdownLoader loader = DefaultFactory.getFactory().createMarkdownLoader();
		
		loader.setLocator( getTrivialTestMarkdownLocator() );
		
		List<ParentMarkdownFile> allParentMdFiles = loader.getMarkdownFiles();
		
		assertEquals(2,allParentMdFiles.size());
		
		valdiateParent1( allParentMdFiles.get(0) );
		valdiateParent2( allParentMdFiles.get(1) );
			
	}

	private String normalizePath(String val) {
		return val.replace('\\', '/');
	}
	
	private void valdiateParent1(ParentMarkdownFile parentMarkdownFile) {
		assertEquals(normalizePath(parentMarkdownFile.getPath().getParent().toString()), PATH_1);
		
		assertEquals(parentMarkdownFile.getChildMarkdownFiles().size(),1);
		
		assertEquals(parentMarkdownFile.getChildMarkdownFiles().get(0).getFileName().toString(),"child.md" );
	}
	private void valdiateParent2(ParentMarkdownFile parentMarkdownFile) {
		
		assertEquals(normalizePath(parentMarkdownFile.getPath().getParent().toString()), PATH_2);
		assertEquals(parentMarkdownFile.getChildMarkdownFiles().size(),3);
		
		assertEquals(parentMarkdownFile.getChildMarkdownFiles().get(0).getFileName().toString(),"child21.md");
		assertEquals(parentMarkdownFile.getChildMarkdownFiles().get(1).getFileName().toString(),"child22.md");
		assertEquals(parentMarkdownFile.getChildMarkdownFiles().get(2).getFileName().toString(),"child23.md");
		
		String actualContent = parentMarkdownFile.getChildMarkdownFiles().get(0).getContent(); 
		assertEquals(CHILD_21_CONTENT.length(), actualContent.length() );
		
		assertEquals(CHILD_21_CONTENT, actualContent);
		
		
		assertEquals(CHILD_22_CONTENT,parentMarkdownFile.getChildMarkdownFiles().get(1).getContent());
		assertEquals(CHILD_23_CONTENT,parentMarkdownFile.getChildMarkdownFiles().get(2).getContent());
	}

	private MarkdownLocator getTrivialTestMarkdownLocator() {
		return new MarkdownLocator() {
			@Override
			public void loadParentFiles(MarkdownLoader loader) throws Snail4jWorkloadException {
				//will our SUT sort these correctly?  I put them out of order -- very sneaky!!!
				loader.loadMarkdownFile(Paths.get(PARENT_2), PARENT_2_CONTENT);
				loader.loadMarkdownFile(Paths.get(PARENT_1), PARENT_1_CONTENT);
			}
			@Override
			public void loadChildFiles(MarkdownLoader loader) throws Snail4jWorkloadException {
				loader.loadMarkdownFile(Paths.get(CHILD_1), CHILD_1_CONTENT);
				loader.loadMarkdownFile(Paths.get(CHILD_22), CHILD_22_CONTENT);
				loader.loadMarkdownFile(Paths.get(CHILD_23), CHILD_23_CONTENT);  // <<<<<  inserted this one out of order
				loader.loadMarkdownFile(Paths.get(CHILD_21), CHILD_21_CONTENT);
			}
		};
	}
}
