package com.github.eostermueller.snail4j.workload.model.json;

import java.util.List;

import com.github.eostermueller.snail4j.workload.Snail4jWorkloadException;
import com.github.eostermueller.snail4j.workload.markdown.ParentMarkdownFile;
import com.github.eostermueller.snail4j.workload.model.UseCases;
import com.github.eostermueller.snail4j.workload.model.WorkloadSpecRq;

public interface SerializaionUtil {
	UseCases unmmarshalUseCases(String json) throws Snail4jWorkloadException;
	String marshalUseCases(UseCases useCases) throws Snail4jWorkloadException;
	WorkloadSpecRq unmmarshalWorkloadUpdateRq(String json) throws Snail4jWorkloadException;
	String marshalWorkloadSpecRq(WorkloadSpecRq rq) throws Snail4jWorkloadException;
	
	/**
	 * [
    {
        "name": "Crime and Punishment",
        "markdownFiles": [
            {
                "fileName": "Chapter1.txt",
                "path": null,
                "sortOrder": 0,
                "displayName": null,
                "lastModified": null
            },
            {
                "fileName": "Chapter2.txt",
                "path": null,
                "sortOrder": 0,
                "displayName": null,
                "lastModified": null
            },
        ]
    },
    {
        "name": "Catch 22",
        "markdownFiles": [
            {
                "fileName": "ChapterA.txt",
                "path": null,
                "sortOrder": 0,
                "displayName": null,
                "lastModified": null
            },
       ]
    }
]
	 * @param parentFiles
	 * @return
	 * @throws Snail4jWorkloadException
	 */
	String marshalMarkdownFileGroups(List<ParentMarkdownFile> parentFiles) throws Snail4jWorkloadException;
	List<ParentMarkdownFile> unMarshalMarkdownFileGroups(String json) throws Snail4jWorkloadException;

}
