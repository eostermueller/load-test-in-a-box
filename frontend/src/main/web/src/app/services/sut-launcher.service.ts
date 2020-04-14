import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {ApiResponse} from '../model/api.response';
import { ApiResponseInterface } from '../model/api.response.interface';

@Injectable({
  providedIn: 'root'
})
export class SutLauncherService {

  constructor(private http: HttpClient) {}
  
  startSut() : Observable<ApiResponseInterface> {
    var result : Observable<ApiResponseInterface>;
    console.log('b4 try block to call http startSut');
    try {
      console.log("before http call to startSut");
      result = this.http.get<ApiResponseInterface>('/snail4j/startSut');
      console.log("after http call to startSut");
      console.log('after startSut');
    } catch(e) {
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

    }
    return result;
  }
  stopSut() :Observable<any> {
    return this.http.get('/snail4j/stopSut');
  }

}

