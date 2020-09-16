import { Component, OnInit } from '@angular/core';
import {UseCaseService} from '../use-case.service';
import { Workload } from '../model/workload';
import { FormBuilder, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-workload-key',
  templateUrl: './workload-key.component.html',
  styleUrls: ['./workload-key.component.css']
})
export class WorkloadKeyComponent implements OnInit {
  form: FormGroup = new FormGroup({});
  workloadKey:Workload = null;
  constructor(
    private useCaseService : UseCaseService, 
    private fb: FormBuilder    
  ) { 

    this.form = fb.group({

      workloadKey: ['', [Validators.required]]

    })

    
  }

  submit() {
    console.log("Submit!");
  }

  getWorkloadKeyJson() : string {
    return JSON.stringify(this.workloadKey);
  }
  ngOnInit(): void {
    console.log("top of ngOnInit workload-key.component.ts [" + this.getWorkloadKeyJson()  + "]"); // prints 'object Object' without stringify

    this.useCaseService.currentWorkload.subscribe(workloadObj => {
      this.workloadKey = workloadObj;
      console.log("workloadKey form valid?" + this.form.valid);
      console.log("xGot new workload [" + this.getWorkloadKeyJson()  + "]"); // prints 'object Object' without stringify
    }
    );
  }

}
