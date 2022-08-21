import { AfterViewInit, Component, OnInit } from '@angular/core';
import { ViewEncapsulation }  from '@angular/core';


import { ParentMarkdownFile, ParentMarkdownFileJsonUtil } from '../model/parent.markdown.file';
import { MarkdownTree } from '../markdown-tree/markdown-tree';
import { ConfigModel} from '../services/config.model';
import { LaunchStatus }           from '../services/LaunchStatus';
import { ViewChild} from '@angular/core';

import { Pipe, PipeTransform, } from "@angular/core";
import { DomSanitizer } from "@angular/platform-browser";

/** invoking workload */
import {ConfigService} from '../services/config.service';
import { UseCaseService } from '../use-case.service';
import { Workload } from '../model/workload';
import { NotificationService }    from '../services/notification.service';

@Pipe({ name: 'safeUrl' })
export class SafeUrlPipe implements PipeTransform {
    constructor(private sanitizer: DomSanitizer) {}
    transform(url) {
        return this.sanitizer.bypassSecurityTrustResourceUrl(url);
    }
}
@Pipe({ name: "safeHtml" })
export class SafeHtmlPipe implements PipeTransform {
  constructor(private sanitizer: DomSanitizer) {}

  transform(value) {
    console.log('Skipping DomSanitizer, using: ' + value);
    return this.sanitizer.bypassSecurityTrustHtml(value);
  }
}


@Component({
  selector: 'app-markdown',
  templateUrl: './markdown.component.html',
  styleUrls: ['./markdown.component.css'],
  encapsulation: ViewEncapsulation.None,
})
export class MarkdownComponent implements OnInit, AfterViewInit {
  config: ConfigModel = this.configService.config;
  @ViewChild("fooStuff", { static: false }) fooStuff:string = "my fooStuff";
  @ViewChild("userMarkdown", { static: false }) userMarkdown:string;
  @ViewChild("TreeNestedOverviewExample",{ static: false }) markdownTree: MarkdownTree;
  public localHtmlForSelectedMarkdown:string="init hello!";
  public selectedMarkdown:string="[meta]: # (sortOrder=1) \n[meta]: # (displayName=First Page)\n# First Problem\nTry <p><a href='javascript:void(0);' (click)='setWorkloadAlias(katrina)'>katrina</a></p>\n# Second Problem\n\nTry <p><a href='javascript:void(0);' (click)='setWorkloadAlias(julio)'>julio</a></p>";

public selectHtml(myHtml:string) {
  console.log("#AAAAA inside selectHtml: " + myHtml);
  this.localHtmlForSelectedMarkdown = myHtml;
  this.fooStuff = myHtml;

}
public selectMarkdown(myMarkdown:string) {
  myMarkdown = "<h1>Hello World!</h1>";
  console.log("#BBBBBB inside selectMarkdown: " + myMarkdown);
  this.fooStuff = myMarkdown;
  this.selectedMarkdown = myMarkdown;
}

// childMarkdownFileChanged(ob: MatSelectionListChange) {
//   console.log("Child Selected Item: " + ob.source.selectedOptions.selected.length);
//   console.log("Child Selected item2: " + ob.source );
// }
// parentMarkdownFileChanged(ob: MatSelectionListChange) {
//   console.log("Parent Selected Item: " + ob.source.selectedOptions.selected.length);
//   console.log("Parent Selected item2: " + ob.source );
// }
  public visible: boolean = false;
  public markdownFiles_parent: ParentMarkdownFile[] = [];
  public notificationMessage:string = 'SUT Markdown Loaded';
  sutLaunchStatus:LaunchStatus;

  constructor(
    private useCaseService: UseCaseService,
    private configService: ConfigService,   
    private notificationService: NotificationService,   
  ) { }
  ngAfterViewInit(): void {
    console.log('ngAfterViewInit() not much here -- ETO.');
    globalThis.markdownComponent = this;
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

  public setWorkloadAlias(alias:string) {
    console.log("June 26: nnnnn about to parse selected workload:" + alias);
    var workload:Workload = new Workload();

    var notificationMessage:string = '';
    workload.alias = alias;
    console.log("#Workload alias = " + workload.alias);
    notificationMessage = 'Alias [' + workload.alias + '] successfully applied.  Workload is now encrypted.';

    workload.origin = 1;

    this.useCaseService.updateWorkload(
      this.config.sutAppHostname,
      this.config.sutAppPort,
      workload).subscribe(
        restResp => {
          console.log("@@## return from updateWorkload: " + JSON.stringify(restResp, this.replacerFunc() ) );
          if (restResp.status===100) {
              this.notificationService.showSuccess(notificationMessage, 'Performance Analysis Workbench');
          } else {
            this.notificationService.showError('Error changing Workload.  Check browser developer tools console for details.', 'Performance Analysis Workbench');
          }
        },
        err => {
          console.error('@@## Oops:', err.message);
        },
        () => {
          console.log(`@@## We're done here!`);
        }         

      );
      return false;    
  }

  // replacerFunc = () => {
  //   const visited = new WeakSet();
  //   return (key, value) => {
  //     if (typeof value === "object" && value !== null) {
  //       if (visited.has(value)) {
  //         return;
  //       }
  //       visited.add(value);
  //     }
  //     return value;
  //   };
  // };

  ngOnInit(): void {

    // this.sutLaunchStatusService.currentStatus.subscribe(status => {
    //   debugger
    //   this.sutLaunchStatus = status;
    //   switch(this.sutLaunchStatus) {
    //     case LaunchStatus.Started:
    //       this.visible = true;
    //       this.getMarkdown();
    //       console.log('markdown.component.ts: just loaded markdown.' + status + ' change count:' + this.sutLaunchStatusService.statusChangeCount);
    //       break;
    //       case LaunchStatus.Stopped:
    //       case LaunchStatus.Stopping:
    //       case LaunchStatus.Starting:
    //         this.visible = false;
    //         break;
    //         }
    //   }
    // );

    // this.config = this.configService.config;


  }

 
//   public getMarkdown(): void {
//     this.markdownService.getMarkdown(
//       this.config.sutAppHostname,
//       this.config.sutAppPort).subscribe(
//         restResp => {
//           console.log("@@## return from getMarkdown(): " + JSON.stringify(restResp, this.replacerFunc() ) );
//           if (restResp.status===100) {
// //              this.notificationService.showSuccess(this.notificationMessage, 'Performance Analysis Workbench');
//               this.markdownFiles_parent = ParentMarkdownFileJsonUtil.halfAssedParentFileDeserializeMarkdown( restResp.result );
// //              this.markdownTree.displayMarkdown(this.markdownFiles_parent);
//           } else {
//             this.notificationService.showError('Error loading SUT markdown.  Check browser developer tools console for details.', 'Performance Analysis Workbench');
//           }
//         },
//         err => {
//           console.error('@@## Oops:', err.message);
//         },
//         () => {
//           console.log(`@@## ^markdown.component.ts^ markdown is done here!`);
//         }         

//       );


//   }

}
