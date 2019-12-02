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

  polledBitcoin$: Observable<string[]>;

  constructor(private http: HttpClient) {
      
  }

  ngOnInit() {
      const bitcoin$ = this.http.get('/actuator/health');


    this.polledBitcoin$ = timer(0, 1000).pipe(
        concatMap(_ => bitcoin$),
        map(
            (response:
              {[key: string]: any}) => {
                const data = response.details;
                let temp: string[] = [];
                for(let key of ['h2', 'sutApp', 'wiremock']) {
                  temp.push(data[key].status);
                }
                return temp;
              } 
              ),
      );
  }

}
