import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

export enum LaunchStatus {
  Stopped,
  Starting,
  Started,
  Stopping,
}

@Injectable({
  providedIn: 'root'
})
export class SutLaunchStatusService {

  private launchStatusSource = new BehaviorSubject(LaunchStatus.Stopped);
  currentStatus = this.launchStatusSource.asObservable();

  constructor() { }

  changeLaunchStatus(status: LaunchStatus) {

    if (this.launchStatusSource.getValue() == LaunchStatus.Starting
      && status == LaunchStatus.Stopped) {
        //do nothing, give the poor thing a chance to complete the startup
    } else if (this.launchStatusSource.getValue() == LaunchStatus.Stopping
      && status == LaunchStatus.Started) {
        //do nothing, give the poo thing a chance to complete the shutdown
    } else {
      this.launchStatusSource.next(status)
    }

  }

}
