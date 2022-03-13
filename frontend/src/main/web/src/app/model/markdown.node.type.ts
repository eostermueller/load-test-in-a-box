export enum MarkdownNodeType {
    folder,
    file
  }
  
  /**
   * @stolenFrom:  https://stackoverflow.com/a/28151986
   * 
   */
export namespace MarkdownNodeType {

  export function toValue(myType:string):MarkdownNodeType {
    var rc:MarkdownNodeType = null;

    if (myType=='folder')
      rc = MarkdownNodeType.folder;
    else if (myType=='file')
     rc = MarkdownNodeType.file;

    return rc;
  }

  export function toString(myType:MarkdownNodeType):string {
    var rc:string = "unknownMarkdownType";

    if (myType==MarkdownNodeType.folder)
      rc = 'folder';
    else if (myType==MarkdownNodeType.file)
     rc = 'file';

    return rc;
  }

}