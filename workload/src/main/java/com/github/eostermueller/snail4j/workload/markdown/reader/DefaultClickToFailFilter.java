package com.github.eostermueller.snail4j.workload.markdown.reader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * Discover occurrences of !{myalias} and replace them with 
 * <PRE>
 * 	<a href="javascript:void(0);" (click)="setWorkloadAlias('myalias')">myalias</a>
 * </PRE>
 * 
 * To insure the 'Marked' library converts to html correctly,
 * this code adds newline abefore and after the link.
 * Without these newlines, the markdown text shows up verbatim in the rendered html, instead of being translated into html.
 * @author eoste
 *
 */
public class DefaultClickToFailFilter extends AbstractFilter implements MarkdownReaderFilter {

	private static final String REGEX_ALIAS = "\\!\\{(?<alias>[A-Za-z0-9]+)\\}";
	
	private static final String REPLACEMENT_1 = "\n<p><a href=\"javascript:globalThis.markdownComponent.setWorkloadAlias('";
	//private static final String REPLACEMENT_1 = "\n<p><button (click)=\"\\$event.preventDefault(); setWorkloadAlias('";
	private static final String REPLACEMENT_2 = "');\">";
	private static final String REPLACEMENT_3 = "</a></p>\n";
	/**
	 * @stolenFrom: https://www.logicbig.com/tutorials/core-java-tutorial/java-regular-expressions/regex-capturing-groups.html
	 */
	@Override
	public String processLine(String line) {

        Pattern pattern = Pattern.compile(REGEX_ALIAS);
        Matcher matcher = pattern.matcher(line);
        String withReplacement = matcher.replaceAll(REPLACEMENT_1 + "${alias}" + REPLACEMENT_2 + "${alias}" + REPLACEMENT_3);
        return withReplacement;

	}



}
