package com.github.eostermueller.snail4j.workload.markdown;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.eostermueller.snail4j.workload.DefaultFactory;
import com.github.eostermueller.snail4j.workload.Snail4jWorkloadException;
import com.github.eostermueller.snail4j.workload.markdown.reader.MarkdownReader;


public class DefaultLoader implements MarkdownLoader {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	MarkdownReader reader = null;
	MarkdownLocator locator = null;
	@Override
	public MarkdownReader getReader() {
		return reader;
	}

	@Override
	public void setReader(MarkdownReader reader) {
		this.reader = reader;
	}

	@Override
	public MarkdownLocator getLocator() {
		return locator;
	}

	@Override
	public void setLocator(MarkdownLocator locator) {
		this.locator = locator;
	}

	private List<ParentMarkdownFile> parents = new ArrayList<ParentMarkdownFile>();
	

	public DefaultLoader() throws Snail4jWorkloadException {
		setReader( DefaultFactory.getFactory().createMarkdownReader() );
		setLocator( DefaultFactory.getFactory().createMarkdownLocator() );
	}

	@Override
	public List<ParentMarkdownFile> getMarkdownFiles() throws Snail4jWorkloadException {
		
		
		getLocator().loadParentFiles(this);
		parents.sort( new SortOrderComparator() );
		

		getLocator().loadChildFiles(this);
		for(ParentMarkdownFile pmf : parents ) {
			pmf.getChildMarkdownFiles().sort( new SortOrderComparator() );
		}
		
		return parents;
	}


	@Override
	public void loadMarkdownFile(Path path, String rawFileContent) throws Snail4jWorkloadException  {
		
		MarkdownFile markdownFile = getReader().createMarkdownFile(path, rawFileContent);
		
		if (markdownFile instanceof ParentMarkdownFile)
			parents.add((ParentMarkdownFile) markdownFile);
		else {
			ParentMarkdownFile parent = findParent(markdownFile);
			if (parent!=null)
				parent.getChildMarkdownFiles().add(markdownFile);
			else {
				LOGGER.warn(String.format("Ignoing markdown file [%s] path [%s].  To fix, put this file in a path with an index.md file.", 
						markdownFile.getFileName(),
						markdownFile.getPath()));
			}
		}
	}

	/**
	 * Find the parent that has the same path as the given markdown file.
	 * @param markdownFile
	 * @return
	 */
	private ParentMarkdownFile findParent(MarkdownFile markdownFile) {
		ParentMarkdownFile rc = null;
		
		for(ParentMarkdownFile parent : this.parents) {
			if (parent.getPath().getParent().toString().equals(markdownFile.getPath().getParent().toString()))
				rc = parent;
		}
		return rc;
	}
}
class SortOrderComparator implements Comparator<MarkdownFile> {
	@Override
	public int compare(MarkdownFile o1, MarkdownFile o2) {
		return (o1.getSortOrder() - o2.getSortOrder() );
	}
}