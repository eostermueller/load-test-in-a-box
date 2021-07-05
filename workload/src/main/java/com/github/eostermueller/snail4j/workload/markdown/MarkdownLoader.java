package com.github.eostermueller.snail4j.workload.markdown;

import java.nio.file.Path;
import java.util.List;

import com.github.eostermueller.snail4j.workload.Snail4jWorkloadException;
import com.github.eostermueller.snail4j.workload.markdown.reader.MarkdownReader;

public interface MarkdownLoader {

	void loadMarkdownFile(Path path, String rawFileContent) throws Snail4jWorkloadException;

	void setLocator(MarkdownLocator locator);

	MarkdownLocator getLocator();

	void setReader(MarkdownReader reader);

	MarkdownReader getReader();

	List<ParentMarkdownFile> getMarkdownFiles() throws Snail4jWorkloadException;

}
