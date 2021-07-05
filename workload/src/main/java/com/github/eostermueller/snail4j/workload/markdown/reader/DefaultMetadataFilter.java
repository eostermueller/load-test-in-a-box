package com.github.eostermueller.snail4j.workload.markdown.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.eostermueller.snail4j.workload.DefaultFactory;
import com.github.eostermueller.snail4j.workload.markdown.MarkdownFile;

/**
 * @stolenFrom: https://stackoverflow.com/questions/44215896/markdown-metadata-format
 * 1) Locate variables that look like this in the given markdown file.
 * 2) Read values and store them in variables, like setSortOrder() and setDisplayName()
 * 
 * <pre>
 * </pre>
 * @author eoste
 *
 */
public class DefaultMetadataFilter extends AbstractFilter implements MarkdownReaderFilter {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	private static final String METADATA_PREFIX = "[meta]: # (";
	private static final String SORT_ORDER_LOWER_CASE = "sortorder";
	private static final String DISPLAY_NAME_LOWER_CASE = "displayname";

	
	@Override
	public String processLine(String line) {
		if (line.trim().startsWith(METADATA_PREFIX)) {
			String remainder = line.substring(METADATA_PREFIX.length());
			String[] parts = remainder.trim().split("[=)]");
			if (parts.length==2) {
				readNameValuePair(parts[0],parts[1],line, lineNumber);
			}
		}
		return line;
	}

	private void readNameValuePair(String name, String value, String contextForError, int lineNumber) {
		
		if (name==null || "".equals(name) ) {
			MarkdownSyntaxException e = new MarkdownSyntaxException("'name' in metadata mardown was null/blank",lineNumber,file,contextForError);
			exceptions.add(e);
		}
		
		if (value==null || "".equals(value) ) {
			MarkdownSyntaxException e = new MarkdownSyntaxException("'value' in metadata mardown was null/blank",lineNumber,file,contextForError);
			exceptions.add(e);
		}

		switch(name.toLowerCase()) {
		case SORT_ORDER_LOWER_CASE:
			int sortOrder = Integer.valueOf(value.trim());
			try {
				sortOrder = Integer.valueOf(value.trim());
				file.setSortOrder(sortOrder);
			} catch (NumberFormatException nfe) {
				MarkdownSyntaxException e = new MarkdownSyntaxException("integer not found, was expecting: " + METADATA_PREFIX + "sortOrder=<integer>)",lineNumber,file,contextForError);
				exceptions.add(e);
			}
			break;
		case DISPLAY_NAME_LOWER_CASE:
			file.setDisplayName(value.trim() );
			break;
		default:
			MarkdownSyntaxException e = new MarkdownSyntaxException(
					String.format("Invalid metadata 'name' [%s], Was expecting either %s or %s.", name,SORT_ORDER_LOWER_CASE,DISPLAY_NAME_LOWER_CASE),
					lineNumber,file,contextForError);
			exceptions.add(e);
		}
		
	}



}
