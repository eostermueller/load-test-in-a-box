import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Workload } from '../model/workload';

@Injectable({
  providedIn: 'root'
})
export class WorkloadChangeService {

  private newWorkloadSource = new BehaviorSubject( new Workload () );
  currentWorkload = this.newWorkloadSource.asObservable();

  constructor() { }

  changeLaunchStatus(newWorkload: Workload) {
      this.newWorkloadSource.next(newWorkload);
  }
}
