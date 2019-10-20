import { Injectable } from '@angular/core';
import { Subject } from 'rxjs/Subject';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {ApiResponse} from './model/api.response';
import {Workload} from './model/workload';
import { ApiResponseInterface } from './model/api.response.interface';

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
        console.log( 'oct 19: 01 getUseCases');
        this.http.get('http://www.google.com' );
        console.log( 'oct 19: 02 getUseCases');
        return this.http.get('http://localhost:8080/traffic/useCases');
          // }));
      }

      updateWorkload(workload:Workload): Observable<ApiResponseInterface> {
        console.log( '... 01 updateWorkload oct 20: ' + JSON.stringify(workload));
        return this.http.put<ApiResponseInterface>('http://localhost:8080/traffic/workload', workload);
        //return this.http.put<ApiResponse>('http://localhost:8080/traffic/workload/', workload.useCases);
        //return this.http.put<ApiResponse>('http://localhost:8080/traffic/workload/', JSON.stringify(workload) );
        //this.http.get('http://www.google.com' );
        // console.log( '... 02 updateWorkload oct 19: ');
        // var f00 = '{"useCases":[{"processingUnits":[{"description":{"en_US":"busy - table-based Random - 10 items, 10 iterations"},"useCaseName":"busyOptimizedUuid","selected":true,"methodWrapper":{"parameters":[],"declaringClassName":"com.github.eostermueller.tjp2.BusyProcessor","methodName":"randomTableInt_10_10_optimizedUuid"}},{"description":{"en_US":"busy - reuse Random - 10 items, 10 iterations"},"useCaseName":"busyOptimizedUuid","selected":false,"methodWrapper":{"parameters":[],"declaringClassName":"com.github.eostermueller.tjp2.BusyProcessor","methodName":"randomNextInt_10_10_optimizedUuid"}},{"description":{"en_US":"busy - table-based Random - 1000 items, 1000 iterations"},"useCaseName":"busyOptimizedUuid","selected":false,"methodWrapper":{"parameters":[],"declaringClassName":"com.github.eostermueller.tjp2.BusyProcessor","methodName":"randomTableInt_1000_1000_optimizedUuid"}},{"description":{"en_US":"busy - threadLocal Random - 1000 items, 1000 iterations"},"useCaseName":"busyOptimizedUuid","selected":false,"methodWrapper":{"parameters":[],"declaringClassName":"com.github.eostermueller.tjp2.BusyProcessor","methodName":"randomThreadLocalInt_1000_1000_optimizedUuid"}},{"description":{"en_US":"busy - reuse Random - 1000 items, 1000 iterations"},"useCaseName":"busyOptimizedUuid","selected":false,"methodWrapper":{"parameters":[],"declaringClassName":"com.github.eostermueller.tjp2.BusyProcessor","methodName":"randomNextInt_1000_1000_optimizedUuid"}},{"description":{"en_US":"busy - threadLocal Random - 10 items, 10 iterations"},"useCaseName":"busyOptimizedUuid","selected":false,"methodWrapper":{"parameters":[],"declaringClassName":"com.github.eostermueller.tjp2.BusyProcessor","methodName":"randomThreadLocalInt_10_10_optimizedUuid"}}],"name":"busyOptimizedUuid"},{"processingUnits":[{"description":{"en_US":"busy - threadLocal Random - 10 items, 10 iterations"},"useCaseName":"busySlowUuid","selected":true,"methodWrapper":{"parameters":[],"declaringClassName":"com.github.eostermueller.tjp2.BusyProcessor","methodName":"randomThreadLocalInt_10_10"}},{"description":{"en_US":"busy - threadLocal Random - 1000 items, 1000 iterations"},"useCaseName":"busySlowUuid","selected":false,"methodWrapper":{"parameters":[],"declaringClassName":"com.github.eostermueller.tjp2.BusyProcessor","methodName":"randomThreadLocalInt_1000_1000"}},{"description":{"en_US":"busy - table-based Random - 10 items, 10 iterations"},"useCaseName":"busySlowUuid","selected":false,"methodWrapper":{"parameters":[],"declaringClassName":"com.github.eostermueller.tjp2.BusyProcessor","methodName":"randomTableInt_10_10"}},{"description":{"en_US":"busy - reuse Random - 10 items, 10 iterations"},"useCaseName":"busySlowUuid","selected":false,"methodWrapper":{"parameters":[],"declaringClassName":"com.github.eostermueller.tjp2.BusyProcessor","methodName":"randomNextInt_10_10"}},{"description":{"en_US":"busy - reuse Random - 1000 items, 1000 iterations"},"useCaseName":"busySlowUuid","selected":false,"methodWrapper":{"parameters":[],"declaringClassName":"com.github.eostermueller.tjp2.BusyProcessor","methodName":"randomNextInt_1000_1000"}},{"description":{"en_US":"busy - table-based Random - 1000 items, 1000 iterations"},"useCaseName":"busySlowUuid","selected":false,"methodWrapper":{"parameters":[],"declaringClassName":"com.github.eostermueller.tjp2.BusyProcessor","methodName":"randomTableInt_1000_1000"}}],"name":"busySlowUuid"}]}';
        // //this.http.put('http://localhost:8080/traffic/workload', f00 );
        // this.http.get('http://localhost:8090/snail4j/getWl' );
        // this.http.put('http://localhost:8090/snail4j/wl', f00 );
        // console.log( 'foo ' +  f00 );
        // console.log( '... 03 updateWorkload oct 19: ');
        // return null;
        
      }


}
