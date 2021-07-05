package com.github.eostermueller.snail4j.workload.markdown.reader;

import java.io.IOException;

import com.github.eostermueller.snail4j.workload.markdown.MarkdownFile;

public class FilterManager {
	   FilterChain filterChain;

	   public FilterManager(){
	      filterChain = new FilterChain();
	   }
	   public void setFilter(MarkdownReaderFilter filter){
	      filterChain.addFilter(filter);
	   }

	   public void filterRequest(MarkdownFile val) throws IOException{
	      filterChain.processMarkdownFile(val);
	   }
	}