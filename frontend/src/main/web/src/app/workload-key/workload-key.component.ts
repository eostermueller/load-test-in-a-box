import { Component, OnInit } from '@angular/core';
import {UseCaseService} from '../use-case.service';
import { Workload } from '../model/workload';
import { FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ConfigService} from '../services/config.service';
import {ConfigModel} from '../services/config.model';
import { Clipboard } from '@angular/cdk/clipboard';

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
    private fb: FormBuilder
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

    /** TODO:  add error checking to confirm new workload key was successfully applied  */
    this.form.controls['workloadKey'].setValue( 'Applied workload key.' );
  
  }
  private updateWorkload() {
    console.log("nnnnn about to parse selected workload:" + this.workloadKeyString);
    var workload:Workload = new Workload();
    if (workload.isBase64Ish_(this.workloadKeyString)) {
      workload.encryptedKey = this.workloadKeyString;
    } else {
      workload = JSON.parse(this.workloadKeyString);
    }

    workload.origin = 1;
    this.useCaseService.updateWorkload(
      this.config.sutAppHostname,
      this.config.sutAppPort,
      workload).subscribe();
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
