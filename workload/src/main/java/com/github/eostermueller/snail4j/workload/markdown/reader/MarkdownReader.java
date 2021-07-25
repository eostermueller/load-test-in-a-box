package com.github.eostermueller.snail4j.workload.markdown.reader;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;

import com.github.eostermueller.snail4j.workload.Snail4jWorkloadException;
import com.github.eostermueller.snail4j.workload.markdown.MarkdownFile;

public interface MarkdownReader {
	static final String INDEX_MD = "index.md";
	
	MarkdownFile createMarkdownFile(Path path, String rawFileContent) throws Snail4jWorkloadException;



}
