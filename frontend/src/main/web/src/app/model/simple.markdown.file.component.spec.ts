import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { ApiResponse } from './api.response';
import { MarkdownFileJsonUtil } from './markdown.file';
import { MarkdownFile } from './markdown.file';

describe("deserialize markdown testing", function() {
  it("can deserialize jscmdon to ts objects", function() {

    var markdownFile : MarkdownFile = MarkdownFileJsonUtil.halfAssedDeserialize( getTestMarkdownString() );
    expect( markdownFile.displayName ).toBe('First Page');
    expect( markdownFile.sortOrder ).toBe(1);
    expect( markdownFile.path).toBe("file:///C:/Users/eoste/.snail4j/sutApp/com/github/eostermueller/tjp2/markdown/part01/firstPage.md");
    expect( markdownFile.content).toBe("[meta]: # (sortOrder=1)\n[meta]: # (displayName=First Page)\n# First Problem\n\nTry <a href=\"javascript:void(0);\" (click)=\"setWorkloadAlias('katrina')\">katrina</a>\n\n# Second Problem\n\nTry <a href=\"javascript:void(0);\" (click)=\"setWorkloadAlias('julio')\">julio</a>\n");
  });


});

function getTestMarkdownString(): string {
    let markdownFile  = {
                        "path": "file:///C:/Users/eoste/.snail4j/sutApp/com/github/eostermueller/tjp2/markdown/part01/firstPage.md",
                        "sortOrder": 1,
                        "displayName": "First Page",
                        "content": "[meta]: # (sortOrder=1)\n[meta]: # (displayName=First Page)\n# First Problem\n\nTry <a href=\"javascript:void(0);\" (click)=\"setWorkloadAlias('katrina')\">katrina</a>\n\n# Second Problem\n\nTry <a href=\"javascript:void(0);\" (click)=\"setWorkloadAlias('julio')\">julio</a>\n"
                    };
    return JSON.stringify(markdownFile);
}



