package com.github.eostermueller.snail4j.workload.markdown.reader;

import com.github.eostermueller.snail4j.workload.markdown.MarkdownFile;

public class MarkdownSyntaxException extends Exception {
	
	private static final int UNDEFINED = -1;
	int lineNumber = UNDEFINED;
	public int getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	private String message;
	@Override
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public MarkdownSyntaxException(String msg, int lineNumber2, MarkdownFile file2, String contextForError) {
		this.message = msg;
	}
	private String line = null;
	MarkdownFile file = null;
	public MarkdownFile getFile() {
		return file;
	}
	public void setFile(MarkdownFile file) {
		this.file = file;
	}
	public String getLine() {
		return line;
	}
	public void setLine(String line) {
		this.line = line;
	}

	public String humanReadable() {
		StringBuilder sb = new StringBuilder();
		sb.append(getMessage() ).append("\n");
		sb.append( String.format("lineNumber: %d\n", this.getLineNumber() ) );		
		sb.append( String.format("line: %s\n", this.getLine() ) );		
		sb.append( String.format("file %s\n", this.getFile().humanReadable() ) );		
		return sb.toString();
		
	}
}
