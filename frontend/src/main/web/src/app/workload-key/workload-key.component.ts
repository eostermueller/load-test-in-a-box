import { Component, OnInit } from '@angular/core';
import {UseCaseService} from '../use-case.service';
import { Workload } from '../model/workload';
import { FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ConfigService} from '../services/config.service';
import {ConfigModel} from '../services/config.model';
import { Clipboard } from '@angular/cdk/clipboard';
import { NotificationService } from '../services/notification.service';

@Component({
  selector: 'app-workload-key',
  templateUrl: './workload-key.component.html',
  styleUrls: ['./workload-key.component.css']
})
export class WorkloadKeyComponent implements OnInit {
  config: ConfigModel = this.configService.config;
  form: FormGroup = new FormGroup({});
  workloadKey:Workload = null;
  workloadKeyString:string = null;  
  notificationMessage:string=null;
  
  copyClearTextWorkloadToClipboard() {
    this.useCaseService.getWorkload(
      this.config.sutAppHostname,
      this.config.sutAppPort,
    ).subscribe(data=>{
            this.clipboard.copy(JSON.stringify(data.result) );
    });
  }

  copyEncryptedWorkloadToClipboard() {
    this.useCaseService.getEncryptedWorkload(
        this.config.sutAppHostname,
        this.config.sutAppPort,
      ).subscribe(data=>{
              this.clipboard.copy(data.result);
      });
  }


  constructor(
    private useCaseService : UseCaseService, 
    private configService: ConfigService,
    private clipboard: Clipboard,
    private fb: FormBuilder,
    private notificationService : NotificationService, 
  ) { 
    console.log("ctor for workload-key.components.tx");
    this.form = fb.group({

      workloadKey: ['', [Validators.required] ]


    });
  }


  private onFormValueChange(data) {
    this.workloadKeyString = this.form.get("workloadKey").value;
    console.log("in workload-key form, found changed data:" + this.workloadKeyString)
  }

  submit() {
    console.log("Submit!");
    this.updateWorkload();

    //to show it has been applied successfully, zero out data that the user entered.
    this.form.controls['workloadKey'].setValue( '' );
  
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
  
  private updateWorkload() {
    console.log("nnnnn about to parse selected workload:" + this.workloadKeyString);
    var workload:Workload = new Workload();

    this.notificationMessage = '';
    if (this.workloadKeyString.length &&  this.workloadKeyString.length < 20) {
      workload.alias = this.workloadKeyString;
      console.log("#Workload alias = " + workload.alias);
      this.notificationMessage = 'Alias [' + workload.alias + '] successfully applied.  Workload is now encrypted.';
    } else if (workload.isBase64Ish_(this.workloadKeyString)) {

      workload.encryptedKey = this.workloadKeyString;
      this.notificationMessage = 'Encrypted workload [' + workload.getAbbreviatedEncryptedKey() + '] has been applied.';
      console.log("#Workload encrypted string = " + workload.encryptedKey);
    } else {
      workload = JSON.parse(this.workloadKeyString);
      console.log("#Workload clearText.  Use case count: = " + workload.useCases.length);
      this.notificationMessage = 'An unencrypted workload has been applied.';
    }
    workload.origin = 1;

    this.useCaseService.updateWorkload(
      this.config.sutAppHostname,
      this.config.sutAppPort,
      workload).subscribe(
        restResp => {
          console.log("@@## return from updateWorkload: " + JSON.stringify(restResp, this.replacerFunc() ) );
          if (restResp.status===100) {
              this.notificationService.showSuccess(this.notificationMessage, 'Performance Analysis Workbench');
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
  }

  public getWorkloadKeyJson() : string {
    return JSON.stringify(this.workloadKey);
  }
  ngOnInit(): void {
    this.form.valueChanges.subscribe(data => this.onFormValueChange(data));

    console.log("top of ngOnInit workload-key.component.ts [" + this.getWorkloadKeyJson()  + "]"); // prints 'object Object' without stringify

    this.useCaseService.currentWorkload.subscribe(workloadObj => {
//      this.workloadKeyString = JSON.stringify(workloadObj);

//      this.form.controls['workloadKey'].setValue( this.workloadKeyString );
      
    }
    );
  }

}
