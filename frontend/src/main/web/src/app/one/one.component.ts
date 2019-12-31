import { Component, OnInit } from '@angular/core';
import { SutLaunchStatusService } from '../services/sut-launch-status.service';
import { LaunchStatus }           from '../services/sut-launch-status.service';
import { SutLauncherService }     from '../services/sut-launcher.service';
import { MatCheckboxChange } from '@angular/material';
import { Observable } from 'rxjs';
import { ApiResponseInterface } from '../model/api.response.interface';
import { stringify } from 'querystring';
import { refCount } from 'rxjs/operators';

@Component({
  selector: 'app-one',
  templateUrl: './one.component.html',
  styleUrls: ['./one.component.scss']
})
export class OneComponent implements OnInit {

  /** Example of how to disable tab:
   * https://stackblitz.com/edit/angular-zqe112
   */
  workloadTabDisabled : boolean = false;
  sutLaunchStatus:LaunchStatus;

  constructor(
    private sutLaunchStatusService: SutLaunchStatusService,
    private sutLauncherService: SutLauncherService) { }
    onEnableCheckboxChange(cbEvent: MatCheckboxChange) {
      console.log( "onEnableCheckboxChange:"+ cbEvent.checked );
      if (cbEvent.checked) {
        this.sutLaunchStatusService.changeLaunchStatus(LaunchStatus.Starting);
        this.sutLauncherService.startSut().subscribe();
      } else {
        this.sutLaunchStatusService.changeLaunchStatus(LaunchStatus.Stopping);
        this.sutLauncherService.stopSut().subscribe();
      }

      //this.useCaseChange.emit('uc change 1');
  }
  /**
   * While the SUT is in the process of coming up or going down,
   * keep the user from messing around with the check box that starts/stops the SUT.
   */
  public isSutInProcess() : boolean {
    var rc : boolean = false;
    switch(this.sutLaunchStatus) {
      case LaunchStatus.Starting:
      case LaunchStatus.Stopping:
              rc = true;
        break;
    }

      return rc;
  }

  public getTextStatus() : string {
    var rc : string = "<unknown>";
    switch(this.sutLaunchStatus) {
      case LaunchStatus.Started:
        rc = "Started";
        break;
      case LaunchStatus.Starting:
        rc = "Starting";  
        break;
      case LaunchStatus.Stopped:
        rc = "Stopped";  
        break;
      case LaunchStatus.Stopping:
        rc = "Stopping";  
        break;
        }    
    return rc;
  }

  /**
   * If the JVM where the workload is running is unavailable,
   * then keep the user from changing the workload selection by disabling the workload tab
   * when the JVM is down.
   */
  public updateTabStatus() {
    switch(this.sutLaunchStatus) {
      case LaunchStatus.Started:
        this.workloadTabDisabled = false;
        break;
      default:
        this.workloadTabDisabled = true;
        break;
    }
  }
  ngOnInit() {
    this.sutLaunchStatusService.currentStatus.subscribe(status => {
        this.sutLaunchStatus = status;
        this.updateTabStatus();
        }
    );
  }

}
