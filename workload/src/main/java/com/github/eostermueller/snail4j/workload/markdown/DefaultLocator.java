package com.github.eostermueller.snail4j.workload.markdown;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.github.eostermueller.snail4j.workload.Snail4jWorkloadException;
import com.github.eostermueller.snail4j.workload.markdown.reader.MarkdownReader;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.Resource;
import io.github.classgraph.ScanResult;

public class DefaultLocator implements MarkdownLocator {

	@Override
	public void loadParentFiles(MarkdownLoader loader) {
		try (ScanResult scanResult = new ClassGraph().scan()) {
		    scanResult.getResourcesWithLeafName(MarkdownReader.INDEX_MD)
		              .forEachByteArray((Resource res, byte[] content) -> 
		              {
		            	  Path markdownFilePath = Paths.get(res.getPath());
		            	  try {
							loader.loadMarkdownFile(markdownFilePath, new String(content, StandardCharsets.UTF_8));
						} catch (Snail4jWorkloadException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		              });
		}			
	}
	
	@Override
	public void loadChildFiles(MarkdownLoader loader) {
		try (ScanResult scanResult = new ClassGraph().scan()) {
		    scanResult.getResourcesWithExtension("md")
		              .forEachByteArray((Resource res, byte[] content) -> {
		          		Path markdownFilePath = Paths.get(res.getPath());
		          		
		          	/** exclude parents, b/c they've been loaded using loadParentFiles()
		          	 * 
		          	 */
		          		if (!markdownFilePath.getFileName().equals(MarkdownReader.INDEX_MD))
							try {
								loader.loadMarkdownFile(markdownFilePath, new String(content, StandardCharsets.UTF_8));
							} catch (Snail4jWorkloadException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
		              });
		}			
	}

}
