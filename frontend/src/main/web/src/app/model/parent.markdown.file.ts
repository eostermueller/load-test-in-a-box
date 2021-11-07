import { ApiResponse } from './api.response';
import { MarkdownFile } from './markdown.file';

/**
 * @stolenFrom: https://stackoverflow.com/a/38688861/2377579
 */
export interface ParentMarkdownFile extends MarkdownFile {
    childMarkdownFiles : MarkdownFile[];
}
export class ParentMarkdownFileJsonUtil  {
    public static halfAssedParentFileDeserializeMarkdown(parentObj) : ParentMarkdownFile[] {
        let typedParentAry : ParentMarkdownFile[] = [];
        for(var i = 0; i < parentObj.length; i++) {

            /**
             * transform these from untyped outputs of JSON.parse() to 
             * interface-typed outputs from JSON.parse().
             */
            var parentJson : string = JSON.stringify(parentObj[i]);
            var parent : ParentMarkdownFile = JSON.parse(parentJson);
            typedParentAry.push(parent);
        }
        return typedParentAry;
    }
        public static halfAssedDeserializeApiResponseMarkdown(apiResponseMarkdownJson : string) : ApiResponse {
        let apiResponseTyped : ApiResponse = JSON.parse(apiResponseMarkdownJson);
  
        let typedParentAry : ParentMarkdownFile[] = [];
        for(var i = 0; i < apiResponseTyped.result.length; i++) {

            /**
             * transform these from untyped outputs of JSON.parse() to 
             * interface-typed outputs from JSON.parse().
             */
            var parentJson : string = JSON.stringify(apiResponseTyped.result[i]);
            var parent : ParentMarkdownFile = JSON.parse(parentJson);
            typedParentAry.push(parent);
        }

        apiResponseTyped.result  = typedParentAry;

        return apiResponseTyped;
    }
    public static halfAssedDeserialize(parentMarkdownFileJson : string) : ParentMarkdownFile {
    let parentMarkdownFileTyped : ParentMarkdownFile = JSON.parse(parentMarkdownFileJson);

    return parentMarkdownFileTyped;
    }
}