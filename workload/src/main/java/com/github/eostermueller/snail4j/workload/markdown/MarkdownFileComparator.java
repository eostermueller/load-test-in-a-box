package com.github.eostermueller.snail4j.workload.markdown;

import java.util.Comparator;

public class MarkdownFileComparator implements Comparator<MarkdownFile> {
	@Override
	public int compare(MarkdownFile o1, MarkdownFile o2) {
		return (o1.getSortOrder() - o2.getSortOrder() );
	}
}