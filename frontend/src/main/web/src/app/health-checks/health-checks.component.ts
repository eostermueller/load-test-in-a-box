import { Component, OnInit } from '@angular/core';
//import { AppModule } from '../../app/app.module';

import { HttpClient } from '@angular/common/http';
import { timer } from 'rxjs/observable/timer';
import { concatMap, map, tap } from 'rxjs/operators';
import { Observable } from 'rxjs/Observable';



@Component({
  selector: 'app-health-checks',
  templateUrl: './health-checks.component.html',
  styleUrls: ['./health-checks.component.scss']
})
export class HealthChecksComponent implements OnInit {

  actuatorHealthCheck$: Observable<string[]>;
  h2Health : boolean = false;
  sutAppHealth : boolean = false;
  wiremockHealth : boolean = false;
  jmeterLoad : boolean = false;

  constructor(private http: HttpClient) {
      
  }

  /**
   * @stolenFrom: https://stackoverflow.com/a/59146234/2377579
   */
  ngOnInit() {
      const unparsedActuatorResponse$ = this.http.get('/actuator/health');
      console.log('##@ top of ngOnInit')
      try {
        this.actuatorHealthCheck$ = timer(0, 1000).pipe(
          concatMap(_ => unparsedActuatorResponse$),
          map(
              (response:
                {[key: string]: any}) => {
                  const data = response.components;
                  this.sutAppHealth   = data['sutApp'    ].status === "UP" ? true : false;
                  this.wiremockHealth = data['wiremock'  ].status === "UP" ? true : false;
                  this.h2Health       = data['h2'        ].status === "UP" ? true : false;
                  this.jmeterLoad     = data['JMeterLoad'].status === "UP" ? true : false;
                  let temp: string[] = [];
                  // for(let key of ['sutApp', 'h2', 'wiremock']) {
                  //   temp.push(data[key].status);
                  // }
                  return temp;
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
        }
        else {
            // probably cannot recover...therefore, rethrow
            console.log('##@ unable to recover from error on /actuator/health')
            throw e;
        }
        console.log('##@ Execution continues!!!');
    }

  }

}
