export interface MarkdownFile {
    path : string;
    sortOrder : number;
    displayName : string;
    content : string;
}
export class MarkdownFileJsonUtil  {

    public static halfAssedDeserialize(markdownFileJson : string) : MarkdownFile {
      let markdownFileTyped : MarkdownFile = JSON.parse(markdownFileJson);

      return markdownFileTyped;
      }
  

}