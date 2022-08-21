import { NestedTreeControl } from '@angular/cdk/tree';
import { MatTreeNestedDataSource } from '@angular/material/tree';
import { AfterViewInit, Component, OnInit } from '@angular/core';
import { ParentMarkdownFile } from '../model/parent.markdown.file';
import { ParentMarkdownFileJsonUtil } from '../model/parent.markdown.file';

import { MarkdownServiceWrapper } from '../services/markdown-service-wrapper';
import { ConfigService } from '../services/config.service';
import { ConfigModel} from '../services/config.model';
import { NotificationService }    from '../services/notification.service';
import { SutLaunchStatusService } from '../services/sut-launch-status.service';
import { LaunchStatus }           from '../services/LaunchStatus';

import { Output, EventEmitter } from '@angular/core';




/**
 * @title Tree with nested nodes
 */
@Component({
  selector: 'markdown-tree',
  templateUrl: 'markdown-tree.html',
  styleUrls: ['markdown-tree.css'],
})
//, AfterViewInit
export class MarkdownTree implements OnInit  {
  public markdownFiles_parent: ParentMarkdownFile[] = [];
  config: ConfigModel; 
  public visible: boolean = false;
  @Output() selectedHtmlEvent = new EventEmitter<string>();
  @Output() selectedMarkdownEvent = new EventEmitter<string>();
  private selectedMarkdown:string = "uninit";

  public notificationMessage:string = 'SUT Markdown Loaded';
  sutLaunchStatus:LaunchStatus;


  treeControl = new NestedTreeControl<ParentMarkdownFile>(node => node.childMarkdownFiles); //ETO
  dataSource = new MatTreeNestedDataSource<ParentMarkdownFile>();

  public clickEvent(node:ParentMarkdownFile): void{
  //  this.selectedMarkdownEvent.emit(node.content);
    console.log("Click displayName ! " + node.displayName);

    var mySelectedHtml = this.markdownService.convertMarkdownToHtml(node.content);
    this.selectedHtmlEvent.emit(mySelectedHtml);
    console.log("Click! " + node.displayName);
    console.log("Click content! #AAAAA " + node.content);
  console.log("Click content! [converted]" + mySelectedHtml);

  }
  ngOnInit(): void {
    this.sutLaunchStatusService.currentStatus.subscribe(status => {
      //debugger
      this.sutLaunchStatus = status;
      switch(this.sutLaunchStatus) {
        case LaunchStatus.Started:
          this.visible = true;
          this.getMarkdown();
          console.log('markdown.component.ts: just loaded markdown.' + status + ' change count:' + this.sutLaunchStatusService.statusChangeCount);
          break;
          case LaunchStatus.Stopped:
          case LaunchStatus.Stopping:
          case LaunchStatus.Starting:
            this.visible = false;
            break;
            }
      }
    );

    this.config = this.configService.config;


  }
  replacerFunc = () => {
    const visited = new WeakSet();
    return (key, value) => {
      if (typeof value === "object" && value !== null) {
        if (visited.has(value)) {
          return;
        }
        visited.add(value);
      }
      return value;
    };
  };

  public getMarkdown(): void {
    /**
     * uncomment to display without the http request.
    this.dataSource.data = ParentMarkdownFileJsonUtil.halfAssedParentFileDeserializeMarkdown( example_markdown_data );
    return;
     */
    this.markdownService.getMarkdown(
      this.config.sutAppHostname,
      this.config.sutAppPort).subscribe(
        restResp => {
          console.log("@@## return from getMarkdown(): " + JSON.stringify(restResp, this.replacerFunc() ) );
          if (restResp.status===100) {
//              this.notificationService.showSuccess(this.notificationMessage, 'Performance Analysis Workbench');
              this.dataSource.data = ParentMarkdownFileJsonUtil.halfAssedParentFileDeserializeMarkdown( restResp.result );
              //this.markdownTree.displayMarkdown(this.markdownFiles_parent);
          } else {
            this.notificationService.showError('Error loading SUT markdown.  Check browser developer tools console for details.', 'Performance Analysis Workbench');
          }
        },
        err => {
          console.error('@@## Oops:', err.message);
        },
        () => {
          console.log(`@@## ^tree-nested-overview-example.ts^ markdown is done here!`);

        }         

      );


  }

  constructor(
    private configService: ConfigService,   
    private markdownService: MarkdownServiceWrapper,
    private notificationService: NotificationService,
    private sutLaunchStatusService: SutLaunchStatusService,
  ) {
    //    this.dataSource.data = TREE_DATA;

//    this.setSelectedMarkdown("# in Constructor\nNothing selected yet.");

    // this.markdownFiles_parent = ParentMarkdownFileJsonUtil.halfAssedParentFileDeserializeMarkdown(files2); //ETO
    // console.log("Count of children: " + this.markdownFiles_parent.length)
    // this.dataSource.data = this.markdownFiles_parent;


  }

//  hasChild = (_: number, node: FoodNode) => !!node.children && node.children.length > 0;
hasChild = (_: number, node: ParentMarkdownFile) => !!node.childMarkdownFiles && node.childMarkdownFiles.length > 0;
}
const example_markdown_data: ParentMarkdownFile[] = [
  {
    path: "file:///C:/Users/eoste/.snail4j/sutApp/com/github/eostermueller/tjp2/markdown/part01/index.md",
    sortOrder: 1,
    displayName: "Static 2 Part I",
    content: "[meta]: # (sortOrder=1)\n[meta]: # (displayName=Part I)\n\n# The main deal\n\n## Still important\n\n\n### Detail 1\n\n### Detail 2\n\nThis is my  __first__  bit of text ooo love it.\n\n## Also a bit important\n",
    childMarkdownFiles: [
      {
        path: "file:///C:/Users/eoste/.snail4j/sutApp/com/github/eostermueller/tjp2/markdown/part01/firstPage.md",
        sortOrder: 1,
        displayName: "Static 2 First Page",
        content: "[meta]: # (sortOrder=1)\n[meta]: # (displayName=First Page)\n# First Problem\n\nTry <a href='javascript:void(0);' (click)='setWorkloadAlias(katrina)'>katrina</a>\n\n# Second Problem\n\nTry <p><a href='javascript:void(0);' (click)='setWorkloadAlias(julio)'>julio</a></p>\n"
      },
      {
        path: "file:///C:/Users/eoste/.snail4j/sutApp/com/github/eostermueller/tjp2/markdown/part01/secondPage.md",
        sortOrder: 2,
        displayName: "Static 2 Second Page",
        content: "[meta]: # (sortOrder=2)\n[meta]: # (displayName=Second Page)\n# First Problem\n\nTry <a href=\"javascript:void(0);\" (click)=\"setWorkloadAlias('quinta')\">quinta</a>\n\n# Second Problem\n\nTry <a href=\"javascript:void(0);\" (click)=\"setWorkloadAlias('omar')\">omar</a>\n"
      }
    ]
  },
  {
    path: "file:///C:/Users/eoste/.snail4j/sutApp/com/github/eostermueller/tjp2/markdown/part02/index.md",
    sortOrder: 2,
    displayName: "Static 2 Part II",
    content: "[meta]: # (sortOrder=2)\n[meta]: # (displayName=Part II)\n# The main deal\n\n## Still important\n\n\n### Detail 1\n\n### Detail 2\n\nThis is my  __first__  bit of text ooo love it.\n\n## Also a bit important\n",
    childMarkdownFiles: [
      {
        path: "file:///C:/Users/eoste/.snail4j/sutApp/com/github/eostermueller/tjp2/markdown/part02/thirdPage.md",
        sortOrder: 1,
        displayName: "Static 2 Third Page",
        content: "[meta]: # (sortOrder=1)\n[meta]: # (displayName=Third Page)\n# First Problem\n\nTry <a href=\"javascript:void(0);\" (click)=\"setWorkloadAlias('franklin')\">franklin</a>\n\n# Second Problem\n\nTry <a href=\"javascript:void(0);\" (click)=\"setWorkloadAlias('kanga')\">kanga</a>\n"
      },
      {
        path: "file:///C:/Users/eoste/.snail4j/sutApp/com/github/eostermueller/tjp2/markdown/part02/fourthPage.md",
        sortOrder: 2,
        displayName: "Static 2 Fourth Page",
        content: "[meta]: # (sortOrder=2)\n[meta]: # (displayName=Fourth Page)\n\n# First Problem\n\nTry <a href=\"javascript:void(0);\" (click)=\"setWorkloadAlias('bertha')\">bertha</a>\n\n# Second Problem\n\nTry <a href=\"javascript:void(0);\" (click)=\"setWorkloadAlias('stan')\">stan</a>\n"
      }
    ]
  }
]


/**  Copyright 2022 Google LLC. All Rights Reserved.
    Use of this source code is governed by an MIT-style license that
    can be found in the LICENSE file at https://angular.io/license */