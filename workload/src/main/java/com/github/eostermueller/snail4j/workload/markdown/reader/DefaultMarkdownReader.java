package com.github.eostermueller.snail4j.workload.markdown.reader;

import java.io.IOException;
import java.nio.file.Path;

import com.github.eostermueller.snail4j.workload.Snail4jWorkloadException;
import com.github.eostermueller.snail4j.workload.markdown.MarkdownFile;
import com.github.eostermueller.snail4j.workload.markdown.ParentMarkdownFile;

public class DefaultMarkdownReader implements MarkdownReader {


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
