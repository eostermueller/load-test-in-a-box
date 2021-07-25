import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { ApiResponse } from './api.response';
import { ParentMarkdownFileJsonUtil } from './parent.markdown.file';
import { MarkdownFile } from './markdown.file';
import { ParentMarkdownFile } from './parent.markdown.file';

describe("deserialize markdown testing", function() {
  it("can deserialize parent markdownfiles with children to ts objects", function() {

    var markdownFile : ParentMarkdownFile = ParentMarkdownFileJsonUtil.halfAssedDeserialize( getTestMarkdownString() );
    expect( markdownFile.displayName ).toBe('Part I');
    expect( markdownFile.sortOrder ).toBe(1);
    expect( markdownFile.path).toBe("file:///C:/Users/eoste/.snail4j/sutApp/com/github/eostermueller/tjp2/markdown/part01/index.md");
    expect( markdownFile.content).toBe("[meta]: # (sortOrder=1)\n[meta]: # (displayName=Part I)\n\n# The main deal\n\n## Still important\n\n\n### Detail 1\n\n### Detail 2\n\nThis is my  __first__  bit of text ooo love it.\n\n## Also a bit important\n");

    expect( markdownFile.childMarkdownFiles[0].displayName).toBe("First Page");
    expect( markdownFile.childMarkdownFiles[0].sortOrder).toBe(1);
    expect( markdownFile.childMarkdownFiles[0].path).toBe("file:///C:/Users/eoste/.snail4j/sutApp/com/github/eostermueller/tjp2/markdown/part01/firstPage.md");
    expect( markdownFile.childMarkdownFiles[0].content).toBe("[meta]: # (sortOrder=1)\n[meta]: # (displayName=First Page)\n# First Problem\n\nTry <a href=\"javascript:void(0);\" (click)=\"setWorkloadAlias('katrina')\">katrina</a>\n\n# Second Problem\n\nTry <a href=\"javascript:void(0);\" (click)=\"setWorkloadAlias('julio')\">julio</a>\n");


    expect( markdownFile.childMarkdownFiles[1].displayName).toBe("Second Page");
    expect( markdownFile.childMarkdownFiles[1].sortOrder).toBe(2);
    expect( markdownFile.childMarkdownFiles[1].path).toBe("file:///C:/Users/eoste/.snail4j/sutApp/com/github/eostermueller/tjp2/markdown/part01/secondPage.md");
    expect( markdownFile.childMarkdownFiles[1].content).toBe("[meta]: # (sortOrder=2)\n[meta]: # (displayName=Second Page)\n# First Problem\n\nTry <a href=\"javascript:void(0);\" (click)=\"setWorkloadAlias('quinta')\">quinta</a>\n\n# Second Problem\n\nTry <a href=\"javascript:void(0);\" (click)=\"setWorkloadAlias('omar')\">omar</a>\n");

  });


});

function getTestMarkdownString(): string {
    let parentMarkdownFile  = {
      "path": "file:///C:/Users/eoste/.snail4j/sutApp/com/github/eostermueller/tjp2/markdown/part01/index.md",
      "sortOrder": 1,
      "displayName": "Part I",
      "content": "[meta]: # (sortOrder=1)\n[meta]: # (displayName=Part I)\n\n# The main deal\n\n## Still important\n\n\n### Detail 1\n\n### Detail 2\n\nThis is my  __first__  bit of text ooo love it.\n\n## Also a bit important\n",
      "childMarkdownFiles": [
          {
              "path": "file:///C:/Users/eoste/.snail4j/sutApp/com/github/eostermueller/tjp2/markdown/part01/firstPage.md",
              "sortOrder": 1,
              "displayName": "First Page",
              "content": "[meta]: # (sortOrder=1)\n[meta]: # (displayName=First Page)\n# First Problem\n\nTry <a href=\"javascript:void(0);\" (click)=\"setWorkloadAlias('katrina')\">katrina</a>\n\n# Second Problem\n\nTry <a href=\"javascript:void(0);\" (click)=\"setWorkloadAlias('julio')\">julio</a>\n"
          },
          {
              "path": "file:///C:/Users/eoste/.snail4j/sutApp/com/github/eostermueller/tjp2/markdown/part01/secondPage.md",
              "sortOrder": 2,
              "displayName": "Second Page",
              "content": "[meta]: # (sortOrder=2)\n[meta]: # (displayName=Second Page)\n# First Problem\n\nTry <a href=\"javascript:void(0);\" (click)=\"setWorkloadAlias('quinta')\">quinta</a>\n\n# Second Problem\n\nTry <a href=\"javascript:void(0);\" (click)=\"setWorkloadAlias('omar')\">omar</a>\n"
          }
      ]
  };
    return JSON.stringify(parentMarkdownFile);
}