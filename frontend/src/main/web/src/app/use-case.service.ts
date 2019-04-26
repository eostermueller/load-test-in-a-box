import { Injectable } from '@angular/core';
import { Subject } from 'rxjs/Subject';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class UseCaseService {

// dataList = new Subject<any[]>();

  constructor(private http: HttpClient) {}

      getUseCases() :Observable<any> {
        //havocAgent/useCases
        return this.http.get('/havocAgent/useCases');
        //return this.http.get('http://localhost:8080/workload/useCases');
          // }));
      }


}
