package com.github.eostermueller.snail4j.workload.markdown.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.eostermueller.snail4j.workload.markdown.MarkdownFile;

public class InfoLoggerFilter extends AbstractFilter implements MarkdownReaderFilter {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Override
	public void readMarkdown(MarkdownFile val) {
		LOGGER.info(val.humanReadable());
	}


}
