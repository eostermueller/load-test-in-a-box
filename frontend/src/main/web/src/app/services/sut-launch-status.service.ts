import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { LaunchStatus } from './LaunchStatus'

@Injectable({
  providedIn: 'root'
})



export class SutLaunchStatusService {

  private previousStatus:LaunchStatus = LaunchStatus.Uninitialized;
  private lastStartedDate:Date;
  private lastStartingDate:Date;
  private lastStoppedDate:Date;
  private lastStoppingDate:Date;

  public uiRecentlyLaunched() : boolean {
    return (this.statusChangeCount < 10);
  }
  public sutHasBeenStoppedRecently() : boolean {
    var rc:boolean = false;

    if (this.lastStoppedDate ===null)
      rc = false;
    else {
      var oneSecondAgo:Date = new Date(new Date().getTime() - 1000);

      if (this.lastStoppedDate > oneSecondAgo) {
        rc = true;
      } 
    }
    return rc;
  }
  public sutHasBeenStartedRecently() : boolean {
    this.debugDates();
    var rc:boolean = false;

    if (this.lastStartedDate===null)
      rc = false;
      else {
        var oneSecondAgo:Date = new Date(new Date().getTime() - 1000);

        if (this.lastStartedDate > oneSecondAgo) {
          rc = true;
        }
      }
      console.log('sutHasBeenStartedRecently' + rc);      
      return rc;
  }
    public sutHasNotBeenStartedRecently() : boolean {
    var rc:boolean = false;

    if (
        (this.lastStartedDate === null) || (
         (this.lastStoppedDate != null && this.lastStoppedDate > this.lastStartedDate)
      && (this.lastStoppingDate != null && this.lastStoppingDate > this.lastStartedDate)
      && (this.lastStartingDate != null && this.lastStartingDate > this.lastStartedDate)        
      ) 
    ) {
      rc = true;
    }

//    console.log('suthasNotBeenStartedRecently' + rc);
      return rc;
  }
  private debugDates() : void {
    console.log( 'stopped: '+this.lastStoppedDate );
    console.log( 'starting: '+ this.lastStartingDate );
    console.log( 'started: '+ this.lastStartedDate );
    console.log( 'stopping: '+ this.lastStoppingDate );
    
  }
  public sutHasNotBeenStoppedRecently() : boolean {
    var rc:boolean = false;

//    this.debugDates();
    if ( 
        (this.lastStoppedDate === null) || 
        (
              (this.lastStoppedDate > this.lastStartingDate)
          &&  (this.lastStoppedDate > this.lastStartedDate)
          &&  (this.lastStoppedDate > this.lastStoppingDate)
        )
      ) {
        rc = true;
      }

  //    console.log('suthasNotBeenStoppedRecently' + rc);
      return rc;
  }

  public statusChangeCount : number = 0;
  private launchStatusSource = new BehaviorSubject(LaunchStatus.Uninitialized);
  currentStatus = this.launchStatusSource.asObservable();

  constructor() { }

  private updateDates(status:LaunchStatus):void {
    let current = new Date();

    console.log('Date' + current + ' Status: ' + status);

    switch(status) {
      case LaunchStatus.Stopped:
        this.lastStoppedDate = current;
        break;
      case LaunchStatus.Starting:
        this.lastStartingDate = current;
        break;
        case LaunchStatus.Started:
        this.lastStartedDate = current;
        break;
      case LaunchStatus.Stopping:
        this.lastStoppingDate = current;
        break;
        }
  }

  changeLaunchStatus(status: LaunchStatus):void {
    this.updateDates(status);
    this.statusChangeCount++;

    console.log('old:' + this.previousStatus + ' new:' + status + ' new-1:' + LaunchStatus.theStatusBefore(status) );


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
        console.log('sut-launch-status-service:  throwing away dup: ' + status);
        return;
      }
    }
 

    if (LaunchStatus.theStatusBefore(status)== this.previousStatus) {
      console.log('new status!!');
      this.launchStatusSource.next(status);
    }

    
    /*
    if (this.launchStatusSource.getValue() == LaunchStatus.Starting
      && status == LaunchStatus.Stopped) {
        //do nothing.  Give the poor thing a chance to complete the startup!
    } else if (this.launchStatusSource.getValue() == LaunchStatus.Stopping
      && status == LaunchStatus.Started) {
        //do nothing. Give the poor thing a chance to complete the shutdown!
    } else {
      this.launchStatusSource.next(status)
    }
    */


    this.previousStatus = status;

  }

}
