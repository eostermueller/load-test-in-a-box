import { Injectable } from '@angular/core';
import { Subject } from 'rxjs/Subject';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {ApiResponse} from '../model/api.response';
import { ApiResponseInterface } from '../model/api.response.interface';

@Injectable({
  providedIn: 'root'
})
export class LoadGeneratorLauncherService {

  constructor(private http: HttpClient) {}
  
  startLg() : Observable<ApiResponseInterface> {
    var result : Observable<ApiResponseInterface>;
    console.log('b4 try block to call http startLg');
    try {
      console.log("before http call to startLg");
      result = this.http.get<ApiResponseInterface>('http://localhost:8090/snail4j/startLg');
      console.log("after http call to startLg");
      console.log('after startLg');
    } catch(e) {
      if(e instanceof Error) {
          // IDE type hinting now available
          // properly handle Error e
          console.log('##@ error on /snail4j/startLg ' + e.name);
          console.log('##@ error on /snail4j/startLg ' + e.message);
          console.log('##@ error on /snail4j/startLg ' + e.stack);
      } else {
          // probably cannot recover...therefore, rethrow
          console.log('##@ unable to recover from error on /snail4j/startLg')
          throw e;
      }

    }
    return result;
  }
  stopLg() :Observable<any> {
    //    return this.http.get('/snail4j/stopSut');
    var result : Observable<ApiResponseInterface>;
    console.log('b4 try block to call http stopLg');
    try {
      console.log("before http call to stopLg");
      result = this.http.get<ApiResponseInterface>('http://localhost:8090/snail4j/stopLg');
      console.log("after http call to stopLg");
      console.log('after stopLg');
    } catch(e) {
      if(e instanceof Error) {
          // IDE type hinting now available
          // properly handle Error e
          console.log('##@ error on /snail4j/stopLg ' + e.name);
          console.log('##@ error on /snail4j/stopLg ' + e.message);
          console.log('##@ error on /snail4j/stopLg ' + e.stack);
      } else {
          // probably cannot recover...therefore, rethrow
          console.log('##@ unable to recover from error on /snail4j/stopLg')
          throw e;
      }
    }
    return result;
  }
}
