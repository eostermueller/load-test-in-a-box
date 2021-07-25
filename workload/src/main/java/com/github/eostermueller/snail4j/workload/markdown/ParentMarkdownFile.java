package com.github.eostermueller.snail4j.workload.markdown;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ParentMarkdownFile extends MarkdownFile {

//	private static final String INDEX_MD = "index.md";

//	/**
//	 * In the given list, locate the children that have the same root path "this".
//	 * @param manyChildren
//	 */
//	public void addChildren(List<String> manyChildren) {
//		
//		for(String possibleChild : manyChildren) {
//			
//			Path possibleChildPath = Paths.get(possibleChild);
//			
//			if (!possibleChildPath.getFileName().toString().equals(INDEX_MD )) {
//				if (possibleChildPath.getParent().equals(this.getPath().getParent()))				{
//					MarkdownFile file = new MarkdownFile();
//					file.setPath(possibleChildPath);
//					this.add(file);
//				}
//				
//			}
//			
//		}
//		
//	}
	List<MarkdownFile> children = new ArrayList<MarkdownFile>();

	public List<MarkdownFile> getChildMarkdownFiles() {
		sort();
		return this.children;
	}

	public void sort() {
		this.children.sort( new MarkdownFileComparator() );
	}

	public void add(MarkdownFile markdownFile) {
		this.children.add(markdownFile);
	}

}
