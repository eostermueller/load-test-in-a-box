import { Injectable } from '@angular/core';
import { Subject } from 'rxjs/Subject';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {ApiResponse} from './model/api.response';
import {Workload} from './model/workload';
import {ConfigService} from './services/config.service';
import { ApiResponseInterface } from './model/api.response.interface';

@Injectable({
  providedIn: 'root'
})
export class UseCaseService {
  config = this.configService.config;
  sutHostAndPort : string;

  constructor(private http: HttpClient, private configService: ConfigService) {
    console.log('UseCaseService.constructor() before:'  + this.sutHostAndPort);
    this.sutHostAndPort = 'http://localhost:' + this.config.sutAppPort;
    console.log('after:'  + this.sutHostAndPort);
  }
  /**
   * Alternative approach.  See how the Race class
   * provides a cast during the json deserialization?
   * https://blog.ninja-squad.com/2016/03/15/ninja-tips-2-type-your-json-with-typescript/
   * 
   * 
   * 
    getRaceById(id): Observable<Race> {
        return this._http.get(`/api/races/${id}`)
                         .map(response => response.json());
    }
   * 
   */

      getUseCases() :Observable<any> {
        console.log( 'oct 19: 02 getUseCases');

        return this.http.get(this.sutHostAndPort + '/traffic/useCases');
          // }));
      }

      getWorkload() :Observable<any> {
        return this.http.get(this.sutHostAndPort + '/traffic/workload');
      }

      updateWorkload(workload:Workload): Observable<ApiResponseInterface> {
        console.log( '... 01 updateWorkload oct 20: ' + JSON.stringify(workload));
        return this.http.put<ApiResponseInterface>(this.sutHostAndPort + '/traffic/workload', workload);        
      }
}
