import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { LaunchStatus } from './LaunchStatus'

@Injectable({
  providedIn: 'root'
})
export class LoadGeneratorLaunchStatusService {
  private launchStatusSource = new BehaviorSubject(LaunchStatus.Stopped);
  currentStatus = this.launchStatusSource.asObservable();

  constructor() { }
  changeLaunchStatus(status: LaunchStatus) {

    if (this.launchStatusSource.getValue() == LaunchStatus.Starting
      && status == LaunchStatus.Stopped) {
        //do nothing. give the poor thing a chance to complete the startup!
    } else if (this.launchStatusSource.getValue() == LaunchStatus.Stopping
      && status == LaunchStatus.Started) {
        //do nothing. give the poor thing a chance to complete the shutdown!
    } else {
      this.launchStatusSource.next(status)
    }
  }
}
