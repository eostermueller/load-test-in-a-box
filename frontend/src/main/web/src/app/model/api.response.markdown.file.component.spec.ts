import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { ApiResponse } from './api.response';
import { ParentMarkdownFileJsonUtil } from './parent.markdown.file';
import { MarkdownFile } from './markdown.file';
import { ParentMarkdownFile } from './parent.markdown.file';

describe("deserialize markdown testing", function() {
  it("can deserialize parent markdownfiles with children to ts objects", function() {

    let apiResponse : ApiResponse;
    console.log("entering test 'deserialize markdown testing' 0000000000000000000");    
    apiResponse = ParentMarkdownFileJsonUtil.halfAssedDeserializeApiResponseMarkdown(getTestMarkdownString());
    
    console.log("entering test 'deserialize markdown testing' 333333333333333333333333");    


    expect( apiResponse.nanoStart ).toBe(239811361560700);
    expect( apiResponse.nanoStop ).toBe(239811661481800);
    expect( apiResponse.status ).toBe(100);
    expect( apiResponse.message ).toBe(null);

    let parents : ParentMarkdownFile[] = apiResponse.result;
    console.log("TYpe of result: " + parents.constructor.name);

    expect( parents.length ).toBe(2);
    let parentMarkdownFile_00:ParentMarkdownFile = parents[0];
    expect( parentMarkdownFile_00.childMarkdownFiles.length ).toBe(2);

    let parentMarkdownFile_01:ParentMarkdownFile = parents[1];
    expect( parentMarkdownFile_01.childMarkdownFiles.length ).toBe(2);

  });


});

function getTestMarkdownString(): string {
  var markdowns = {
      "nanoStart": 239811361560700,
      "nanoStop": 239811661481800,
      "status": 100,
      "message": null,
      "result": [
          {
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
          },
          {
              "path": "file:///C:/Users/eoste/.snail4j/sutApp/com/github/eostermueller/tjp2/markdown/part02/index.md",
              "sortOrder": 2,
              "displayName": "Part II",
              "content": "[meta]: # (sortOrder=2)\n[meta]: # (displayName=Part II)\n# The main deal\n\n## Still important\n\n\n### Detail 1\n\n### Detail 2\n\nThis is my  __first__  bit of text ooo love it.\n\n## Also a bit important\n",
              "childMarkdownFiles": [
                  {
                      "path": "file:///C:/Users/eoste/.snail4j/sutApp/com/github/eostermueller/tjp2/markdown/part02/thirdPage.md",
                      "sortOrder": 1,
                      "displayName": "Third Page",
                      "content": "[meta]: # (sortOrder=1)\n[meta]: # (displayName=Third Page)\n# First Problem\n\nTry <a href=\"javascript:void(0);\" (click)=\"setWorkloadAlias('franklin')\">franklin</a>\n\n# Second Problem\n\nTry <a href=\"javascript:void(0);\" (click)=\"setWorkloadAlias('kanga')\">kanga</a>\n"
                  },
                  {
                      "path": "file:///C:/Users/eoste/.snail4j/sutApp/com/github/eostermueller/tjp2/markdown/part02/fourthPage.md",
                      "sortOrder": 2,
                      "displayName": "Fourth Page",
                      "content": "[meta]: # (sortOrder=2)\n[meta]: # (displayName=Fourth Page)\n\n# First Problem\n\nTry <a href=\"javascript:void(0);\" (click)=\"setWorkloadAlias('bertha')\">bertha</a>\n\n# Second Problem\n\nTry <a href=\"javascript:void(0);\" (click)=\"setWorkloadAlias('stan')\">stan</a>\n"
                  }
              ]
          }
      ],
      "failure": false
  };
  return JSON.stringify(markdowns);
}

