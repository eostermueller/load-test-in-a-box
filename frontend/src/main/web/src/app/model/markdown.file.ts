export interface MarkdownFile {
    path : string;
    sortOrder : number;
    displayName : string;
    content : string;
}
export class MarkdownFileJsonUtil  {

    public static createFromJson(markdownFileJson : string) : MarkdownFile {
        let markdownFileTyped : MarkdownFile = JSON.parse(markdownFileJson);
        return markdownFileTyped;
        }
  
    public static assignJsonTo(markdownFileTyped : MarkdownFile, markdownFileJson : string) : MarkdownFile {
      markdownFileTyped = JSON.parse(markdownFileJson);

      return markdownFileTyped;
      }
  

}