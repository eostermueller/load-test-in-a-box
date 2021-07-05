package com.github.eostermueller.snail4j.workload.markdown.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import com.github.eostermueller.snail4j.workload.markdown.MarkdownFile;

public abstract class AbstractFilter implements MarkdownReaderFilter {
	MarkdownFile file = null;
	@Override
	public MarkdownFile getFile() {
		return file;
	}

	@Override
	public void setFile(MarkdownFile file) {
		this.file = file;
	}

	List<MarkdownSyntaxException> exceptions = null;
	int lineNumber = 0;

	@Override
	public void readMarkdown(MarkdownFile val) throws IOException {
		file = val;
		
		StringBuilder replacementText = new StringBuilder();
		
		BufferedReader bufReader = new BufferedReader(new StringReader(val.getContent()));
		String line=null;
		while( (line=bufReader.readLine()) != null ) {
			lineNumber++;
			replacementText.append( processLine(line) + "\n" );
		}
		
		this.getFile().setContent(replacementText.toString());
	}
	@Override
	public String processLine(String line) {
		return line; //use the line exactly as it was specified.
	}

	@Override
	public MarkdownSyntaxException[] getSyntaxExceptions() {
		return exceptions.toArray( new MarkdownSyntaxException[this.exceptions.size()] );
	}

	@Override
	public void init() {
		exceptions = new ArrayList<MarkdownSyntaxException>();
		lineNumber = 0;
	}


}
