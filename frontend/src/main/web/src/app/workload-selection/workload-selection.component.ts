import { Component, OnInit } from '@angular/core';

import { LoadGeneratorLaunchStatusService } from '../services/load-generator-launch-status-service';
import { LoadGeneratorLauncherService }     from '../services/load-generator-launcher.service';

import { SutLaunchStatusService } from '../services/sut-launch-status.service';
import { SutLauncherService }     from '../services/sut-launcher.service';

import { LaunchStatus }           from '../services/LaunchStatus';
import { NotificationService }    from '../services/notification.service';
import {ConfigService} from '../services/config.service';
import {ConfigModel} from '../services/config.model';
import { Workload } from '../model/workload';

/**
 * "ng update" to angular 9 (https://update.angular.io/#8.0:9.0l3) did not upgrade these imports
 * to include the component specific end to '@angular/material'
 */
import { MatCheckboxChange } from '@angular/material/checkbox';
import { MatCheckbox } from '@angular/material/checkbox';

import { ViewChild } from '@angular/core'; 
import { Observable } from 'rxjs';
import { ApiResponseInterface } from '../model/api.response.interface';
import { stringify } from 'querystring';
import { refCount } from 'rxjs/operators';
import { UseCasesComponent } from '../use-cases/use-cases.component';
import { UseCaseService } from '../use-case.service';

@Component({
  selector: 'app-one',
  templateUrl: './workload-selection.html',
  styleUrls: ['./workload-selection.scss']
})
export class WorkloadSelectionComponent implements OnInit {
  config: ConfigModel = this.configService.config;

  @ViewChild('sutCheckbox',{static: false}) private sutCheckbox: MatCheckbox;
  @ViewChild('loadGeneratorCheckbox',{static: false}) private loadGeneratorCheckbox: MatCheckbox;
  
  @ViewChild(UseCasesComponent,{static: false}) useCases : UseCasesComponent;
  /** Example of how to disable tab:
   * https://stackblitz.com/edit/angular-zqe112
   */
  workloadTabDisabled : boolean = false;
  sutLaunchStatus:LaunchStatus;
  loadGeneratorLaunchStatus:LaunchStatus;

  constructor(
    private sutLaunchStatusService: SutLaunchStatusService,
    private sutLauncherService: SutLauncherService,
    private loadGeneratorLaunchStatusService: LoadGeneratorLaunchStatusService,
    private loadGeneratorLauncherService: LoadGeneratorLauncherService,
    private notificationService: NotificationService,
    private useCaseService: UseCaseService,
    private configService: ConfigService,   
    ) {

    } 
  launchSutCheckboxChange(cbEvent: MatCheckboxChange) {
    console.log( "launchSutCheckboxChange:"+ cbEvent.checked );
    if (cbEvent.checked) {
      this.sutLaunchStatusService.changeLaunchStatus(LaunchStatus.Starting);
      this.sutLauncherService.startSut().subscribe();
    } else {
      this.stopLoadGenerator();
      this.sutLaunchStatusService.changeLaunchStatus(LaunchStatus.Stopping);
      this.sutLauncherService.stopSut().subscribe();
    }
  }
  stopLoadGenerator() {
    this.loadGeneratorCheckbox.checked = false;
    this.loadGeneratorLaunchStatusService.changeLaunchStatus(LaunchStatus.Stopping);
    this.loadGeneratorLauncherService.stopLg().subscribe();

  }
  launchLoadGeneratorCheckboxChange(cbEvent: MatCheckboxChange) {
    console.log( "launchLoadGeneratorCheckboxChange:"+ cbEvent.checked );
    if (cbEvent.checked) {
      this.loadGeneratorLaunchStatusService.changeLaunchStatus(LaunchStatus.Starting);
      this.loadGeneratorLauncherService.startLg().subscribe();
    } else {
      this.loadGeneratorLaunchStatusService.changeLaunchStatus(LaunchStatus.Stopping);
      this.loadGeneratorLauncherService.stopLg().subscribe();
    }
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
/**
   * While the LoadGenerator in the process of coming up or going down,
   * keep the user from messing around with the check box that starts/stops the LoadGenerator.
   */
  public isLoadGeneratorInProcess() : boolean {
    var rc : boolean = false;
    switch(this.loadGeneratorLaunchStatus) {
      case LaunchStatus.Starting:
      case LaunchStatus.Stopping:
              rc = true;
        break;
    }

      return rc;
  }

  /**
   * TODO: i18n
   */
  public getLoadGeneratorTextStatus() : string {
    var rc : string = "<unknown>";
    switch (this.loadGeneratorLaunchStatus) {
      case LaunchStatus.Started:
        rc = "Applying Load";
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
   * TODO: i18n
   */
  public getSutTextStatus() : string {
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
  tabSelectionChanged(tabIndex:number) {
    var start = Date.now();

    console.log("tabSelectionChange index [" + tabIndex + "]")
    console.log('index => ', tabIndex);
    if (tabIndex==1) {

      var begin = Date.now();
      console.log("useCases.load():" + begin/1000+"secs");
      this.useCases.load();
      var end = Date.now();
      var timeSpent=(end-begin)/1000+"secs";
      console.log("useCases.load():" + timeSpent);
    }
  }
  public setWorkloadAlias(alias:string) {
    console.log("nnnnn about to parse selected workload:" + alias);
    var workload:Workload = new Workload();

    var notificationMessage:string = '';
    workload.alias = alias;
    console.log("#Workload alias = " + workload.alias);
    notificationMessage = 'Alias [' + workload.alias + '] successfully applied.  Workload is now encrypted.';

    workload.origin = 1;

    this.useCaseService.updateWorkload(
      this.config.sutAppHostname,
      this.config.sutAppPort,
      workload).subscribe(
        restResp => {
          console.log("@@## return from updateWorkload: " + JSON.stringify(restResp, this.replacerFunc() ) );
          if (restResp.status===100) {
              this.notificationService.showSuccess(notificationMessage, 'Performance Analysis Workbench');
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
  public setDefaultWorkload() {
    this.useCaseService.setDefaultWorkload(
      this.config.sutAppHostname,
      this.config.sutAppPort).subscribe(
        restResp => {
          console.log("@@## return from setDefaultWorkload(): " + JSON.stringify(restResp, this.replacerFunc() ) );
          if (restResp.status===100) {
              //this.notificationService.showSuccess('Default Java Workload now running.', 'Performance Analysis Workbench');
          } else {
            this.notificationService.showError('Error setting Default Workload.  Check browser developer tools console for details.', 'Performance Analysis Workbench');
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
 
  public updateLoadGenStatus():void {
    switch (this.loadGeneratorLaunchStatus) {
      case LaunchStatus.Stopped:
        this.notificationService.showSuccess('Relax, the SUT is not under load.', 'Performance Analysis Workbench');
        break;
      case LaunchStatus.Started:
        this.notificationService.showSuccess('Attention: SUT is now under load!', 'Performance Analysis Workbench');
        break;
      }


    if (this.loadGeneratorLaunchStatusService.uiRecentlyLaunched() ) {
      if (this.loadGeneratorLaunchStatus == LaunchStatus.Started) {
        this.loadGeneratorCheckbox.checked = true;
      }
    }

  }
  /**
   * If the JVM where the workload is running is unavailable,
   * then keep the user from changing the workload selection by disabling the workload tab
   * when the JVM is down.
   */
  public updateSutStatus() {
//    console.log('updateTabStatus this.sutLaunchStatusService.statusChangeCount:' + this.sutLaunchStatusService.statusChangeCount + ' this.sutLaunchStatus: ' + this.sutLaunchStatus);
    switch(this.sutLaunchStatus) {
      case LaunchStatus.Started:
        this.workloadTabDisabled = false;
        break;
      default:
        this.workloadTabDisabled = true;
        break;
    }

      if (this.sutLaunchStatus == LaunchStatus.Started) {
        this.notificationService.showSuccess('Java SUT started.', 'Performance Analysis Workbench');

        this.useCaseService.getWorkload(
          this.config.sutAppHostname,
          this.config.sutAppPort,
        ).subscribe(data=>{
                var zeroUseCasesSetAtBrowserStartup:boolean = true; //an assumption
                if ( (data.result) &&(data.result.useCases) && data.result.useCases[0]) {
                  zeroUseCasesSetAtBrowserStartup = false; //1 or more use cases are set.
                }

                if (zeroUseCasesSetAtBrowserStartup) {
                  //Set the default workload (one with 10 second wait time) so any load generation doesn't
                  //make a massive number of 1-ish-ms requests to tjp2, and thus eating up
                  //all the end user's CPU.
                  this.setDefaultWorkload();
                }
                  

        });
    

        
      }
      if (this.sutLaunchStatus == LaunchStatus.Stopped) {
        this.notificationService.showSuccess('Java SUT stopped.', 'Performance Analysis Workbench');
      }

      /**
       *  If you shut down your browser and navigate to snail4j,
       *  enable the check box if the sut and lg are started.
       */

      if (this.sutLaunchStatusService.uiRecentlyLaunched() ) {
        if (this.sutLaunchStatus == LaunchStatus.Started) {
          this.sutCheckbox.checked = true;
        }
      }

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
  
  ngOnInit() {
    console.log('ngOnInit this.sutLaunchStatusService.statusChangeCount:' + this.sutLaunchStatusService.statusChangeCount);
    this.sutLaunchStatusService.currentStatus.subscribe(status => {
        this.sutLaunchStatus = status;
        console.log('workload-selection.component.ts: sutLaunchStatusService.currentStatus.subscribe status: ' + status + ' change count:' + this.sutLaunchStatusService.statusChangeCount);
        this.updateSutStatus();
        }
    );
    this.loadGeneratorLaunchStatusService.currentStatus.subscribe(status => {
      this.loadGeneratorLaunchStatus = status;

  //    console.log('loadGeneratorLaunchStatusService.currentStatus.subscribe this.sutLaunchStatusService.statusChangeCount:' + this.sutLaunchStatusService.statusChangeCount);
      this.updateLoadGenStatus();
    }
  );
  }
}
