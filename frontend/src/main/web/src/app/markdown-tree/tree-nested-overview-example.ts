import { NestedTreeControl } from '@angular/cdk/tree';
import { MatTreeNestedDataSource } from '@angular/material/tree';
import { AfterViewInit, Component, OnInit } from '@angular/core';
import { ParentMarkdownFile } from '../model/parent.markdown.file';
import { ParentMarkdownFileJsonUtil } from '../model/parent.markdown.file';

import { MarkdownService } from '../services/markdown-service';
import { ConfigService } from '../services/config.service';
import { ConfigModel} from '../services/config.model';
import { NotificationService }    from '../services/notification.service';
import { SutLaunchStatusService } from '../services/sut-launch-status.service';
import { LaunchStatus }           from '../services/LaunchStatus';
/**
 * Food data with nested structure.
 * Each node has a name and an optional list of children.
 */
interface FoodNode {
  name: string;
  children?: FoodNode[];
}

const TREE_DATA: FoodNode[] = [
  {
    name: 'Fruit',
    children: [{ name: 'Apple' }, { name: 'Banana' }, { name: 'Fruit loops' }],
  },
  {
    name: 'Vegetables',
    children: [
      {
        name: 'Green',
        children: [{ name: 'Broccoli' }, { name: 'Brussels sprouts' }],
      },
      {
        name: 'Orange',
        children: [{ name: 'Pumpkins' }, { name: 'Carrots' }],
      },
    ],
  },
];

/** Example file/folder data. */
//export const files2 = [
const files2_ORIG: ParentMarkdownFile[] = [
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
]

const files2: ParentMarkdownFile[] = [
  {
    path: "file:///C:/Users/eoste/.snail4j/sutApp/com/github/eostermueller/tjp2/markdown/part01/index.md",
    sortOrder: 1,
    displayName: "Part I",
    content: "[meta]: # (sortOrder=1)\n[meta]: # (displayName=Part I)\n\n# The main deal\n\n## Still important\n\n\n### Detail 1\n\n### Detail 2\n\nThis is my  __first__  bit of text ooo love it.\n\n## Also a bit important\n",
    childMarkdownFiles: [
      {
        path: "file:///C:/Users/eoste/.snail4j/sutApp/com/github/eostermueller/tjp2/markdown/part01/firstPage.md",
        sortOrder: 1,
        displayName: "First Page",
        content: "[meta]: # (sortOrder=1)\n[meta]: # (displayName=First Page)\n# First Problem\n\nTry <a href=\"javascript:void(0);\" (click)=\"setWorkloadAlias('katrina')\">katrina</a>\n\n# Second Problem\n\nTry <a href=\"javascript:void(0);\" (click)=\"setWorkloadAlias('julio')\">julio</a>\n"
      },
      {
        path: "file:///C:/Users/eoste/.snail4j/sutApp/com/github/eostermueller/tjp2/markdown/part01/secondPage.md",
        sortOrder: 2,
        displayName: "Second Page",
        content: "[meta]: # (sortOrder=2)\n[meta]: # (displayName=Second Page)\n# First Problem\n\nTry <a href=\"javascript:void(0);\" (click)=\"setWorkloadAlias('quinta')\">quinta</a>\n\n# Second Problem\n\nTry <a href=\"javascript:void(0);\" (click)=\"setWorkloadAlias('omar')\">omar</a>\n"
      }
    ]
  },
  {
    path: "file:///C:/Users/eoste/.snail4j/sutApp/com/github/eostermueller/tjp2/markdown/part02/index.md",
    sortOrder: 2,
    displayName: "Part II",
    content: "[meta]: # (sortOrder=2)\n[meta]: # (displayName=Part II)\n# The main deal\n\n## Still important\n\n\n### Detail 1\n\n### Detail 2\n\nThis is my  __first__  bit of text ooo love it.\n\n## Also a bit important\n",
    childMarkdownFiles: [
      {
        path: "file:///C:/Users/eoste/.snail4j/sutApp/com/github/eostermueller/tjp2/markdown/part02/thirdPage.md",
        sortOrder: 1,
        displayName: "Third Page",
        content: "[meta]: # (sortOrder=1)\n[meta]: # (displayName=Third Page)\n# First Problem\n\nTry <a href=\"javascript:void(0);\" (click)=\"setWorkloadAlias('franklin')\">franklin</a>\n\n# Second Problem\n\nTry <a href=\"javascript:void(0);\" (click)=\"setWorkloadAlias('kanga')\">kanga</a>\n"
      },
      {
        path: "file:///C:/Users/eoste/.snail4j/sutApp/com/github/eostermueller/tjp2/markdown/part02/fourthPage.md",
        sortOrder: 2,
        displayName: "Fourth Page",
        content: "[meta]: # (sortOrder=2)\n[meta]: # (displayName=Fourth Page)\n\n# First Problem\n\nTry <a href=\"javascript:void(0);\" (click)=\"setWorkloadAlias('bertha')\">bertha</a>\n\n# Second Problem\n\nTry <a href=\"javascript:void(0);\" (click)=\"setWorkloadAlias('stan')\">stan</a>\n"
      }
    ]
  }
]


/**
 * @title Tree with nested nodes
 */
@Component({
  selector: 'tree-nested-overview-example',
  templateUrl: 'tree-nested-overview-example.html',
  styleUrls: ['tree-nested-overview-example.css'],
})
export class TreeNestedOverviewExample implements OnInit, AfterViewInit  {
  public markdownFiles_parent: ParentMarkdownFile[] = [];
  config: ConfigModel; 
  public visible: boolean = false;

  public notificationMessage:string = 'SUT Markdown Loaded';
  sutLaunchStatus:LaunchStatus;


//  treeControl = new NestedTreeControl<FoodNode>(node => node.children);
treeControl = new NestedTreeControl<ParentMarkdownFile>(node => node.childMarkdownFiles); //ETO
//  dataSource = new MatTreeNestedDataSource<FoodNode>();
  dataSource = new MatTreeNestedDataSource<ParentMarkdownFile>();
  ngAfterViewInit(): void {
    console.log('Method not implemented.');
  }

  ngOnInit(): void {
    this.sutLaunchStatusService.currentStatus.subscribe(status => {
      debugger
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
    this.markdownService.getMarkdown(
      this.config.sutAppHostname,
      this.config.sutAppPort).subscribe(
        restResp => {
          console.log("@@## return from getMarkdown(): " + JSON.stringify(restResp, this.replacerFunc() ) );
          if (restResp.status===100) {
//              this.notificationService.showSuccess(this.notificationMessage, 'Performance Analysis Workbench');
              this.markdownFiles_parent = ParentMarkdownFileJsonUtil.halfAssedParentFileDeserializeMarkdown( restResp.result );
              //this.markdownTree.displayMarkdown(this.markdownFiles_parent);
          } else {
            this.notificationService.showError('Error loading SUT markdown.  Check browser developer tools console for details.', 'Performance Analysis Workbench');
          }
        },
        err => {
          console.error('@@## Oops:', err.message);
        },
        () => {
          console.log(`@@## markdown is done here!`);
        }         

      );


  }

  constructor(
    private configService: ConfigService,   
    private markdownService: MarkdownService,
    private notificationService: NotificationService,
    private sutLaunchStatusService: SutLaunchStatusService,
  ) {
    //    this.dataSource.data = TREE_DATA;

    this.markdownFiles_parent = ParentMarkdownFileJsonUtil.halfAssedParentFileDeserializeMarkdown(files2); //ETO
    console.log("Count of children: " + this.markdownFiles_parent.length)
    this.dataSource.data = this.markdownFiles_parent;


  }

//  hasChild = (_: number, node: FoodNode) => !!node.children && node.children.length > 0;
hasChild = (_: number, node: ParentMarkdownFile) => !!node.childMarkdownFiles && node.childMarkdownFiles.length > 0;
}


/**  Copyright 2022 Google LLC. All Rights Reserved.
    Use of this source code is governed by an MIT-style license that
    can be found in the LICENSE file at https://angular.io/license */