package com.github.eostermueller.snail4j.workload.markdown;

import com.github.eostermueller.snail4j.workload.Snail4jWorkloadException;

public interface MarkdownLocator {

	void loadParentFiles(MarkdownLoader loader) throws Snail4jWorkloadException;

	void loadChildFiles(MarkdownLoader loader) throws Snail4jWorkloadException;

}
