package com.github.eostermueller.snail4j.workload.markdown;
import static java.util.Optional.ofNullable;
import java.net.URL;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Base64.Encoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class MarkdownFile {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	Path path;

	int sortOrder;
	String displayName;
	String content;

	public String getContent() {
		return content;
	}
	@JsonIgnore
	public String getBase64Content() {
		return ( Base64.getEncoder().encodeToString( getContent().getBytes() ) );
	}

	public void setContent(String content) {
		this.content = content;
	}

	@JsonIgnore
	public String getFileName() {
		return this.getPath().getFileName().toString();
	}

	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		LOGGER.error( String.format("MarkdownFile path [%s]", ofNullable(path.toString()).orElse("null Path")) );
		this.path = path;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public String humanReadable() {
		StringBuilder sb = new StringBuilder();
		try {
			sb.append( String.format("\nfileName: %s\n", getFileName() ) );
			sb.append( String.format("sortOrder: %d\n", getSortOrder() ) );
			sb.append( String.format("displayName: %s\n", getDisplayName() ) );
			sb.append( String.format("path: %s\n", getPath().toString() ) );
			sb.append( String.format("parent: %b\n", (this instanceof ParentMarkdownFile) ) );
		} catch (Exception e) {
			sb.append("Exception:").append(e.getClass().getName()).append(e.getMessage());
			for(StackTraceElement f : e.getStackTrace() ) {
				sb.append(f.toString());
			}
			
		}
		
		return sb.toString();
	}

}
