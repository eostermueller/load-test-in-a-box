import { Component, OnInit } from '@angular/core';
import { ParentMarkdownFile, ParentMarkdownFileJsonUtil } from '../model/parent.markdown.file';
import { MarkdownFile } from '../model/markdown.file';
import { MarkdownService } from '../services/markdown-service';
import { ConfigService } from '../services/config.service';
import { ConfigModel} from '../services/config.model';
import { NotificationService }    from '../services/notification.service';
import { SutLaunchStatusService } from '../services/sut-launch-status.service';
import { LaunchStatus }           from '../services/LaunchStatus';
import { ApiResponse } from '../model/api.response';
import { MatSelectionListChange } from '@angular/material/list';

@Component({
  selector: 'app-markdown',
  templateUrl: './markdown.component.html',
  styleUrls: ['./markdown.component.css']
})
export class MarkdownComponent implements OnInit {
//  @ViewChild("userHtml", { static: false }) userHtml;
childMarkdownFileChanged(ob: MatSelectionListChange) {
  console.log("Child Selected Item: " + ob.source.selectedOptions.selected.length);
  console.log("Child Selected item2: " + ob.source );
}
parentMarkdownFileChanged(ob: MatSelectionListChange) {
  console.log("Parent Selected Item: " + ob.source.selectedOptions.selected.length);
  console.log("Parent Selected item2: " + ob.source );
}
  config: ConfigModel; 
  public visible: boolean = false;
  public markdownFiles: ParentMarkdownFile[] = [];
  public notificationMessage:string = 'SUT Markdown Loaded';
  sutLaunchStatus:LaunchStatus;

  constructor(
    private configService: ConfigService,   
    private markdownService: MarkdownService,
    private notificationService: NotificationService,
    private sutLaunchStatusService: SutLaunchStatusService,

  ) { }
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

  ngOnInit(): void {

    this.sutLaunchStatusService.currentStatus.subscribe(status => {
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

  public setMarkdown(markdownText:string):void {
    
  }

  public getMarkdown(): void {
    this.markdownService.getMarkdown(
      this.config.sutAppHostname,
      this.config.sutAppPort).subscribe(
        restResp => {
          console.log("@@## return from getMarkdown(): " + JSON.stringify(restResp, this.replacerFunc() ) );
          if (restResp.status===100) {
              this.notificationService.showSuccess(this.notificationMessage, 'Performance Analysis Workbench');
              this.markdownFiles = ParentMarkdownFileJsonUtil.halfAssedParentFileDeserializeMarkdown( restResp.result );
          } else {
            this.notificationService.showError('Error loading SUT markdown.  Check browser developer tools console for details.', 'Performance Analysis Workbench');
          }
        },
        err => {
          console.error('@@## Oops:', err.message);
        },
        () => {
          console.log(`@@## We're done here!`);
        }         

      );


  }

}
