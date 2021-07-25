package com.github.eostermueller.snail4j.workload.markdown.reader;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.eostermueller.snail4j.workload.Snail4jWorkloadException;
import com.github.eostermueller.snail4j.workload.markdown.MarkdownFile;
import com.github.eostermueller.snail4j.workload.markdown.ParentMarkdownFile;

public class DefaultMarkdownReader implements MarkdownReader {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());


	@Override
	public MarkdownFile createMarkdownFile(Path path, String rawFileContent) throws Snail4jWorkloadException  {
		
	    MarkdownFile markdownFile = new MarkdownFile();
		if (path.getFileName().toString().equals(INDEX_MD))
			markdownFile = new ParentMarkdownFile();
		else
			markdownFile = new MarkdownFile();
		
		markdownFile.setPath(path);
		markdownFile.setContent(rawFileContent);
		
	    FilterManager filterManager = getFilterManager();
	    
	    try {
			filterManager.filterRequest(markdownFile);
		} catch (IOException e) {
			throw new Snail4jWorkloadException(e);
		}  
		LOGGER.warn( String.format("just created %s", markdownFile.humanReadable() ));
		return markdownFile;
	}

	protected FilterManager getFilterManager() {
		FilterManager filterManager = new FilterManager();
	    filterManager.setFilter(new DefaultMetadataFilter() );
	    filterManager.setFilter(new DefaultClickToFailFilter() );
	    filterManager.setFilter(new InfoLoggerFilter());
	    return filterManager;
	}

}
