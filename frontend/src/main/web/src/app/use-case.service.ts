import { Injectable } from '@angular/core';
import { Subject } from 'rxjs/Subject';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {ApiResponse} from './model/api.response';
import {Workload} from './model/workload';

@Injectable({
  providedIn: 'root'
})
export class UseCaseService {

// dataList = new Subject<any[]>();

  constructor(private http: HttpClient) {}
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
        //havocAgent/useCases
        //return this.http.get('/havocAgent/useCases');
        return this.http.get('http://localhost:8080/traffic/useCases');
          // }));
      }

      updateWorkload(workload:Workload): Observable<ApiResponse> {
        console.log( '... updateWorkload request: ' + JSON.stringify(workload));
        return this.http.put<ApiResponse>('http://localhost:8080/traffic/workload/', workload.useCases);
      }


}
