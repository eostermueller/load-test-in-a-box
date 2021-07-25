package com.github.eostermueller.snail4j.workload.markdown;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.github.eostermueller.snail4j.workload.DefaultFactory;
import com.github.eostermueller.snail4j.workload.Snail4jWorkloadException;
import com.github.eostermueller.snail4j.workload.markdown.reader.MarkdownReader;

class MarkdownCreationTest {

	private static final String PATH_1 = "com/github/eostermueller/tjp2/md1";
	private static final String PARENT_1 = PATH_1 + "/index.md";
	private static final String PARENT_1_CONTENT = "H1 Hello Parent World!\n";
	private static final String CLICK_TO_FAIL_MARKDOWN = "FOO !{katrina} BAR\n";
	private static final String TWO_CLICK_TO_FAIL_LINKS = "FOO !{katrina} BAR HELLO !{betty} WORLD\n";
	private static final String CLICK_TO_FAIL_HTML =  "FOO <a href=\"javascript:void(0);\" (click)=\"setWorkloadAlias('katrina')\">katrina</a> BAR\n";
	private static final String CLICK_TO_FAIL_HTML2 = "FOO <a href=\"javascript:void(0);\" (click)=\"setWorkloadAlias('katrina')\">katrina</a> BAR HELLO <a href=\"javascript:void(0);\" (click)=\"setWorkloadAlias('betty')\">betty</a> WORLD\n";
	
	@Test
	void canCreateTrivialFile() throws IOException, Snail4jWorkloadException {
		
		MarkdownReader reader = DefaultFactory.getFactory().createMarkdownReader();
		
		Path path = Paths.get(PARENT_1);
		
		MarkdownFile markdownFile = reader.createMarkdownFile(path, PARENT_1_CONTENT);
		
		assertEquals( PARENT_1_CONTENT, markdownFile.getContent() );
		assertEquals( "index.md", markdownFile.getFileName() );
		assertEquals( path.toString(), markdownFile.getPath().toString() );
		
		
	}
	@Test
	void canReadMarkdownSortOrderMetadata() throws Snail4jWorkloadException {
		
		MarkdownReader reader = DefaultFactory.getFactory().createMarkdownReader();
		
		Path path = Paths.get(PARENT_1);

		String content = "[meta]: # (sortOrder=7)";
		
		MarkdownFile markdownFile = reader.createMarkdownFile(path, content);
		
		assertEquals( 7, markdownFile.getSortOrder() );
		
	}
	@Test
	void canReadMarkdownDisplayNameMetadata() throws Snail4jWorkloadException {
		
		MarkdownReader reader = DefaultFactory.getFactory().createMarkdownReader();
		
		Path path = Paths.get(PARENT_1);

		String content = "[meta]: # (displayName=My Display Name)";
		
		MarkdownFile markdownFile = reader.createMarkdownFile(path, content);
		
		assertEquals( "My Display Name", markdownFile.getDisplayName() );
		
	}
	
	@Test
	void canMakeClickToFailLink() throws Snail4jWorkloadException {
		
		MarkdownReader reader = DefaultFactory.getFactory().createMarkdownReader();
		
		Path path = Paths.get(PARENT_1);
		
		MarkdownFile markdownFile = reader.createMarkdownFile(path, CLICK_TO_FAIL_MARKDOWN);
		
		assertEquals( CLICK_TO_FAIL_HTML, markdownFile.getContent() );
	}

	@Test
	void canTwoClickToFailLinksInSameLine() throws Snail4jWorkloadException {
		
		MarkdownReader reader = DefaultFactory.getFactory().createMarkdownReader();
		
		Path path = Paths.get(PARENT_1);
		
		MarkdownFile markdownFile = reader.createMarkdownFile(path, TWO_CLICK_TO_FAIL_LINKS);
		
		assertEquals( CLICK_TO_FAIL_HTML2, markdownFile.getContent() );
	}


}
