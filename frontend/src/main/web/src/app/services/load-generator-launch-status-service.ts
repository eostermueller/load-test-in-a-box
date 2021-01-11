import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { LaunchStatus } from './LaunchStatus'

@Injectable({
  providedIn: 'root'
})
export class LoadGeneratorLaunchStatusService {
  previousStatus:LaunchStatus = LaunchStatus.Uninitialized;

  public uiRecentlyLaunched() : boolean {
    return (this.statusChangeCount < 10);
  }
  private launchStatusSource = new BehaviorSubject(LaunchStatus.Stopped);
  currentStatus = this.launchStatusSource.asObservable();
  private statusChangeCount : number = 0;

  constructor() { }
  changeLaunchStatus(status: LaunchStatus) {
    this.statusChangeCount++;
    if (this.previousStatus==LaunchStatus.Uninitialized) {
      console.log('sut-launch-status-service:  initializing with status: ' + status);
      console.log('    Uninitialized:' + LaunchStatus.Uninitialized);
      console.log('    Stopped     :'  + LaunchStatus.Stopped     );
      console.log('    Starting    :'  + LaunchStatus.Starting    );
      console.log('    Started     :'  + LaunchStatus.Started     );
      console.log('    Stopping    :'  + LaunchStatus.Stopping    );
            
      this.launchStatusSource.next(status);
      
    } else {  //throw duplicate notifications away.
      if (status==this.previousStatus) {
        console.log('load-generator-launch-status-service:  throwing away dup: ' + status);
        return;
      }
    }

    if (LaunchStatus.theStatusBefore(status)== this.previousStatus) {
      console.log('new status!!');
      this.launchStatusSource.next(status);
    }


    // if (this.launchStatusSource.getValue() == LaunchStatus.Starting
    //   && status == LaunchStatus.Stopped) {
    //     //do nothing. give the poor thing a chance to complete the startup!
    // } else if (this.launchStatusSource.getValue() == LaunchStatus.Stopping
    //   && status == LaunchStatus.Started) {
    //     //do nothing. give the poor thing a chance to complete the shutdown!
    // } else {
    //   this.launchStatusSource.next(status)
    // }
    this.previousStatus = status;

  }
}
