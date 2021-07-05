package com.github.eostermueller.snail4j.workload.markdown;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ParentMarkdownFile extends MarkdownFile {

	/**
	 * In the given list, locate the children that have the same root path "this".
	 * @param manyChildren
	 */
	public void addChildren(List<String> manyChildren) {
		
		for(String possibleChild : manyChildren) {
			
			Path possibleChildPath = Paths.get(possibleChild);
			
			if (!possibleChildPath.getFileName().toString().equals("index.md") ) {
				if (possibleChildPath.getParent().equals(this.getPath().getParent()))				{
					MarkdownFile file = new MarkdownFile();
					file.setPath(possibleChildPath);
					this.getChildMarkdownFiles().add(file);
				}
				
			}
			
		}
		
	}
	List<MarkdownFile> files = new ArrayList<MarkdownFile>();

	public List<MarkdownFile> getChildMarkdownFiles() {
		return this.files;
	}

	public void add(MarkdownFile markdownFile) {
		this.getChildMarkdownFiles().add(markdownFile);
	}

}
