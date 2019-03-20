import { Injectable } from '@angular/core';
import { Subject } from 'rxjs/Subject';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class MatDemoService {

// dataList = new Subject<any[]>();

  constructor(private http: HttpClient) {}

      getElements() :Observable<any> {
        return this.http.get('havocAgent/elements.json');
          // }));
      }


}
