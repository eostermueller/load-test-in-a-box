import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {ApiResponse} from './model/api.response';
import {Workload} from './model/workload';
import { ApiResponseInterface } from './model/api.response.interface';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UseCaseService {
  private newWorkloadSource = new BehaviorSubject( new Workload () );
  currentWorkload = this.newWorkloadSource.asObservable();

  constructor(private http: HttpClient) {
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
    getBaseUrl(host:string, port:number) :string{
      var baseUrl:string = 'http://' + host + ':' + port;
      console.log('Base url for useCases [' + baseUrl + ']');
      return baseUrl;
    }
      getUseCases(host:string, port:number,searchCriteria:string) :Observable<any> {
        console.log( 'oct 19: 02 getUseCases');

        return this.http.get( this.getBaseUrl(host,port) + '/traffic/useCases?useCaseSearchCriteria='+searchCriteria);
          // }));
      }

      getWorkload(host:string,port:number) :Observable<any> {
        return this.http.get(this.getBaseUrl(host,port) + '/traffic/workload');
      }

      updateWorkload(host:string,port:number,workload:Workload): Observable<ApiResponseInterface> {
        console.log( '... 01 updateWorkload oct 20: ' + JSON.stringify(workload));
        this.fireWorkloadChangeEvent(workload);
        return this.http.put<ApiResponseInterface>(this.getBaseUrl(host,port) + '/traffic/workload', workload);        
      }
      updateEncryptedWorkload(host:string,port:number,workload:Workload): Observable<ApiResponseInterface> {
        console.log( '... 01 updateWorkload oct 20: ' + JSON.stringify(workload));
        this.fireWorkloadChangeEvent(workload);
        return this.http.put<ApiResponseInterface>(this.getBaseUrl(host,port) + '/traffic/workload', workload);        
      }

      fireWorkloadChangeEvent(newWorkload: Workload) {
          this.newWorkloadSource.next(newWorkload);
      }
    
}
