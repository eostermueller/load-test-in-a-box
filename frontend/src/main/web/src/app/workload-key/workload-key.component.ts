import { Component, OnInit } from '@angular/core';
import {UseCaseService} from '../use-case.service';
import { Workload } from '../model/workload';
import { FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ConfigService} from '../services/config.service';
import {ConfigModel} from '../services/config.model';

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
  constructor(
    private useCaseService : UseCaseService, 
    private configService: ConfigService,
    private fb: FormBuilder    
  ) { 
    console.log("ctor for workload-key.components.tx");
    this.form = fb.group({

      workloadKey: ['', [Validators.required] ]


    })
  }
  private onFormValueChange(data) {
    this.workloadKeyString = this.form.get("workloadKey").value;
    console.log("in workload-key form, found changed data:" + this.workloadKeyString)
  }

  submit() {
    console.log("Submit!");
    this.updateWorkload();
  }
  private updateWorkload() {
    console.log("nnnnn about to parse selected workload:" + this.workloadKeyString);
    var workload:Workload = JSON.parse(this.workloadKeyString);
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
      //this.workloadKey = workloadObj;
      this.workloadKeyString = JSON.stringify(workloadObj);
      console.log("nnnnn workloadKey form valid?" + this.form.valid);
      console.log("nnnnn xGot new workload [" + this.workloadKeyString  + "]"); // prints 'object Object' without stringify
      //this.updateWorkload();

      this.form.controls['workloadKey'].setValue( this.workloadKeyString );
      //this.form.setValue([ this.workloadKeyString ]);
      
    }
    );
  }

}
