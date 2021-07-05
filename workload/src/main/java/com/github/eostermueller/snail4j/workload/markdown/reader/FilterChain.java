package com.github.eostermueller.snail4j.workload.markdown.reader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.eostermueller.snail4j.workload.markdown.MarkdownFile;

public class FilterChain {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
   private List<MarkdownReaderFilter> filters = new ArrayList<MarkdownReaderFilter>();

   public void addFilter(MarkdownReaderFilter filter){
      filters.add(filter);
   }

   public void processMarkdownFile(MarkdownFile val) throws IOException{
      
      for (MarkdownReaderFilter filter : filters) {
    	  
    	  filter.init();
    	  filter.readMarkdown(val);
    	  
    	  if (filter.getSyntaxExceptions() !=null && filter.getSyntaxExceptions().length > 0) {
    	  
    		  LOGGER.warn( 
    				  String.format("Found %d exceptions for filter %s .", filter.getSyntaxExceptions().length, filter.getClass().getName() ) 
    				  ); 
   		  		
	    	  for(MarkdownSyntaxException me : filter.getSyntaxExceptions() )
	    		  LOGGER.warn( me.humanReadable() );
    	  }
      }
      
   }

}