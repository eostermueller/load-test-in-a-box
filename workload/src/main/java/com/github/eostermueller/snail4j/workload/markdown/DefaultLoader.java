package com.github.eostermueller.snail4j.workload.markdown;

import java.nio.file.Path;
import java.util.ArrayList;
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
		getLocator().loadChildFiles(this);
		
		
		parents.sort( new MarkdownFileComparator() );
		
		sortChildren();
		
		return parents;
	}


	private void sortChildren() {
		for(ParentMarkdownFile parent : parents)
			parent.sort();
	}

	@Override
	public void loadMarkdownFile(Path path, String rawFileContent) throws Snail4jWorkloadException  {
		
		MarkdownFile markdownFile = getReader().createMarkdownFile(path, rawFileContent);
		
		if (markdownFile instanceof ParentMarkdownFile) {
			parents.add((ParentMarkdownFile) markdownFile);
			LOGGER.debug(String.format("Just added parent to collection: %s", markdownFile.humanReadable() ));
		} else {
			ParentMarkdownFile parent = findParent(markdownFile);
			if (parent!=null)
				parent.add(markdownFile);
			else {
				LOGGER.warn(String.format("Ignoing markdown file [%s] path [%s].  To fix, put this file in a path with an index.md file.", 
						markdownFile.getFileName(),
						markdownFile.getPath()));
			}
		}
	}

	/**
	 * Find the parent that has the same path as the given markdown file.
	 * @param possibleChild
	 * @return
	 */
	public ParentMarkdownFile findParent(MarkdownFile possibleChild) {
		ParentMarkdownFile rc = null;
		
		for(ParentMarkdownFile parent : this.parents) {
			LOGGER.debug( String.format("Who is parent of %s? Is it %s?", 
					possibleChild.getPath().toString(), 
					parent.getPath().toString() ) );
			if (parent.getPath().getParent().toString().equals(possibleChild.getPath().getParent().toString())) {
				rc = parent;
				LOGGER.debug("YES!!!");
				break;
			}
		}
		return rc;
	}
}
