import { Component, OnInit } from '@angular/core';
//import { AppModule } from '../../app/app.module';

import { HttpClient } from '@angular/common/http';
import { timer } from 'rxjs';

import { concatMap, map, tap } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { SutLaunchStatusService } from '../services/sut-launch-status.service';
import { LoadGeneratorLaunchStatusService } from '../services/load-generator-launch-status-service';
import { LaunchStatus }           from '../services/LaunchStatus';
import { LoadGeneratorLauncherService } from '../services/load-generator-launcher.service';


@Component({
  selector: 'app-health-checks',
  templateUrl: './health-checks.component.html',
  styleUrls: ['./health-checks.component.scss']
})
export class HealthChecksComponent implements OnInit {
  sutLaunchStatus: LaunchStatus;
  loadGeneratorLaunchStatus: LaunchStatus;
  actuatorHealthCheck$: Observable<string[]>;
  h2Health : boolean = false;
  sutAppHealth : boolean = false;
  wiremockHealth : boolean = false;
  jmeterLoad : boolean = false;
  polledBitcoin$ : Observable<number>;
  theAnswer : number = -1;

  constructor(
    private http: HttpClient, 
    private sutLaunchStatusService: SutLaunchStatusService,
    private loadGeneratorLaunchStatusService: LoadGeneratorLaunchStatusService
    ) {      
  }

  /**
   * @stolenFrom: https://stackoverflow.com/a/59146234/2377579
   * @stolenFrom: https://stackblitz.com/edit/angular-abcqen
   */
  ngOnInit() {
    this.sutLaunchStatusService.currentStatus.subscribe(sutLaunchStatus => this.sutLaunchStatus = sutLaunchStatus);
    this.loadGeneratorLaunchStatusService.currentStatus.subscribe(loadGeneratorLaunchStatus => this.loadGeneratorLaunchStatus = loadGeneratorLaunchStatus);

      const unparsedActuatorResponse$ = this.http.get('/actuator/health');
//      console.log('##@ top of ngOnInit')
      try {
//        console.log('##@ top of ngOnInit2')
        this.actuatorHealthCheck$ = timer(0, 5000).pipe(
          concatMap(_ => unparsedActuatorResponse$),
          map(
              (response:
                {[key: string]: any}) => {
//                  console.log("b4 health check parse");
                  const data = response.components;
                  this.sutAppHealth   = data['sutApp'    ].status === "UP" ? true : false;
                  this.wiremockHealth = data['wiremock'  ].status === "UP" ? true : false;
                  this.h2Health       = data['h2'        ].status === "UP" ? true : false;
                  this.jmeterLoad     = data['JMeterLoad'].status === "UP" ? true : false;

                  if (this.sutLaunchStatus!=LaunchStatus.Stopping) {
                        /* Sometimes after initiating a stop, there is zero progress for multiple seconds.  Unfortnately slow, but true.
                           So we we're stopping, skip the check for started status....because we know it will stop in just a few seconds :-D
                         **/

                    if (this.sutAppHealth && this.wiremockHealth && this.h2Health) {
                      console.log('before health-check setting to started date:' + new Date() );
                      this.sutLaunchStatusService.changeLaunchStatus(LaunchStatus.Started);
                      console.log('after health-check setting to started date:' + new Date() );
                    }
                  } 

                  if (this.sutLaunchStatus!=LaunchStatus.Starting) {  
                        /* Sometimes after initiating a start, there is zero progress for multiple seconds.  Unfortnately slow, but true.
                           So if we're starting, skip the check for stopped status....because we know it will start in just a few seconds :-D
                         **/
                      if (!this.sutAppHealth && !this.wiremockHealth && !this.h2Health) {
                        console.log('before HC setting to stopped date:' + new Date() );
                        this.sutLaunchStatusService.changeLaunchStatus(LaunchStatus.Stopped)
                        console.log('after HC setting to stopped date:' + new Date() );
                      }                    
                  }

                  if (this.loadGeneratorLaunchStatus != LaunchStatus.Stopping) {
                        /* Sometimes after initiating a stop, there is zero progress for multiple seconds.  Unfortnately slow, but true.
                           So we we're stopping, skip the check for started status....because we know it will stop in just a few seconds :-D
                         **/
                        if (this.jmeterLoad)
                      this.loadGeneratorLaunchStatusService.changeLaunchStatus(LaunchStatus.Started);
                  }

                  if (this.loadGeneratorLaunchStatus != LaunchStatus.Starting) {
                        /* Sometimes after initiating a start, there is zero progress for multiple seconds.  Unfortnately slow, but true.
                           So if we're starting, skip the check for stopped status....because we know it will start in just a few seconds :-D
                         **/
                        if (!this.jmeterLoad)
                          this.loadGeneratorLaunchStatusService.changeLaunchStatus(LaunchStatus.Stopped);
                  }



                  let neverUsed: string[] = [];
                  return neverUsed;
                } 
            ),
        );

      }
    catch(e) {
        if(e instanceof Error) {
            // IDE type hinting now available
            // properly handle Error e
            console.log('##@ error on /actuator/health ' + e.name);
            console.log('##@ error on /actuator/health ' + e.message);
            console.log('##@ error on /actuator/health ' + e.stack);
        } else {
            // probably cannot recover...therefore, rethrow
            console.log('##@ unable to recover from error on /actuator/health')
            throw e;
        }
        console.log('##@ Execution continues!!!');
    }

  }
}
