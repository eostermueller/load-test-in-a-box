package com.github.eostermueller.snail4j.workload.markdown.reader;

import java.io.IOException;

import com.github.eostermueller.snail4j.workload.markdown.MarkdownFile;

public interface MarkdownReaderFilter {

	public void readMarkdown(MarkdownFile val) throws IOException;
	public MarkdownSyntaxException[] getSyntaxExceptions();
	void init();
	String processLine(String line);
	MarkdownFile getFile();
	void setFile(MarkdownFile file);
}
