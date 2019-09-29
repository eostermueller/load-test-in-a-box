package com.github.eostermueller.havoc.launcher;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* Parse a string, and separate all variables into separate "split buckets".
* A "variable" looks like this:  #{myVariable}
* All the other splilt tokens must be stitched back together by the user of this class!!!!!
* This class pays no attention to delimeters (like the space character) used to separate command line arguments.
 * @st0lenFr0m: https://stackoverflow.com/a/2206432/2377579
 * Helpful one-liners for understanding regex used in this class:
 * <pre>
 * System.out.println(Arrays.toString("a;b;c;d".split("((?<=;)|(?=;))")));
 * System.out.println(java.util.Arrays.toString("#{abc}f00 bar#{c}baz#{d}".split("((?<=\\#)|(?=\\#))|((?<=\\})|(?=\\}))|((?<=\\{)|(?=\\{))")));
 * </pre>
 * @author erikostermueller
 *
 */
public class VariableTokenizer {
	private static final String VAR_SUFFIX = "}";
	public static final String VAR_MARKER = "#"; //   #{myVariable}
	public static final String VAR_PREFIX = VAR_MARKER+"{"; //   #{myVariable}
	
	Pattern validJavaNamePattern = Pattern.compile("[a-zA-Z$_][a-zA-Z0-9$_]*");

	private String str = null;

	private List<String> warnings = new ArrayList<String>();
	
	public boolean isVariableNameWithPunctuation(String val) {
		boolean rc = false;
		if (val != null && this.hasCorrectPunctuation(val)) {
			String varName = this.getVariableNameWithoutPunctuation(val);
			if (this.validJavaName(varName))
				rc = true;
		}
		return rc;
	}
	public boolean hasCorrectPunctuation(String val) {
		boolean rc = false;
		if (
				   val != null
				&& val.startsWith(VAR_PREFIX)
				&& val.endsWith(VAR_SUFFIX) ) {
			rc = true;
		}
		return rc;
	}
	
	/**
	 * This regex looks super complicated, but its not. 
	 * It splits up a string with 3 different delimiters: {}#
	 * See the three different parts, divided by | ?
	 * It keeps each delimiter as a single token.
	 * Input:  abc#{def}ghi
	 * 0utput: 
	 * 		abc
	 * 		#
	 * 		{
	 * 		def
	 * 		}
	 * 		ghi
	 */
	private static final String REGEX = "((?<=\\" + VAR_MARKER + ")|(?=\\" + VAR_MARKER + "))|((?<=\\})|(?=\\}))|((?<=\\{)|(?=\\{))";
	
	public VariableTokenizer(String val) {
		this.str = val;
	}
	public boolean validJavaName(String val) {
		boolean rc = false;
		
		if (val !=null) {
			Matcher m = validJavaNamePattern.matcher(val);
			rc =  m.matches();
		}
		
		return rc;
	}
	
	public List<String> split() {
		List<String> rc = new ArrayList<String>();
		String[] wordOrDelimeter =  this.str.split(REGEX);
		
		for(int i = 0; i < wordOrDelimeter.length; i++) {
			
			if (
				   (wordOrDelimeter[i+0].equals(VAR_MARKER) ) 
				&& (wordOrDelimeter[i+1].equals("{") )
				&& (wordOrDelimeter[i+3].equals(VAR_SUFFIX) ) 
				) {
				rc.add(VAR_MARKER + "{" + wordOrDelimeter[i+2] + VAR_SUFFIX );
				
				if (!validJavaName(wordOrDelimeter[i+2])) {
					addWarning("The text [" + wordOrDelimeter[i+2] + "] looks like a variable ( #{myVar} ) but violate java's method name rules.");
				}
				i+=3;
			} else {
				rc.add(wordOrDelimeter[i] );
			}
		}
		return rc;
	}
	private void addWarning(String string) {
		this.warnings.add(string);
	}
	public String getVariableNameWithoutPunctuation(String val) {
		String rc = null;
		if (val != null && val.length() > 3) {
			rc = val.substring(2, val.length()-1);
		}
		return rc;
	}
	
}