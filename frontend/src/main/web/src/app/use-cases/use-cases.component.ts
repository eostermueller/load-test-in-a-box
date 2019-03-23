import { Component, OnInit, ViewChild, Input } from '@angular/core';
import {UseCaseService} from './../use-case.service';
import {PageEvent, MatPaginator} from '@angular/material';
import { ChangeDetectorRef } from '@angular/core';
import {DataSource} from '@angular/cdk/collections';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/observable/merge';
import { map } from 'rxjs/operators';
import {MatCheckboxModule} from '@angular/material/checkbox';

export class Database { // {{{
  /** Stream that emits whenever the data has been modified. If filter is applied on the data*/
  dataChange: BehaviorSubject<any[]> = new BehaviorSubject<any[]>([]);
  get data(): any[] { return this.dataChange.value; }

  constructor(data) {
    // Fill up the database .
    this.dataChange.next(data);
  }
  getChange(data){
    this.dataChange.next(data);
  }
}

@Component({
  selector: 'app-use-cases',
  templateUrl: './use-cases.component.html',
  styleUrls: ['./use-cases.component.scss']
})
export class UseCasesComponent implements OnInit {
  useCases : any[];
  length = 0;
    pageIndex = 0;
    pageSize = 5;
  database: Database;
    pageEvent: PageEvent;
  dataSource : MyDataSource;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(private useCaseService : UseCaseService, private cdRef:ChangeDetectorRef) {  }

    ngOnInit() {
        this.useCaseService.getUseCases().subscribe(data=>{
          console.log(data);
          this.useCases= data.useCases;
          this.length = this.useCases.length;
          this.database=new Database(this.useCases);
          this.dataSource = new MyDataSource(this.database, this.paginator);
        });

    this.useCaseService.getUseCases().subscribe(data=>{
      console.log(data);
      this.useCases= data.useCases;
      this.length = this.useCases.length;
      this.database=new Database(this.useCases);
      this.dataSource = new MyDataSource(this.database, this.paginator);
      this.cdRef.detectChanges();
    });
  }

}

export class MyDataSource extends DataSource<any> {
  /** Stream of data that is provided to the table. */
  constructor(private dataBase: Database,  private paginator: MatPaginator) {
      super();
    console.log('In constructor');
    console.log(dataBase);

  }
   /** Connect function called by the table to retrieve one stream containing the data to render. */
  connect(): Observable<any[]> {
    //return Observable.of(this.dataBase);
    const displayDataChanges = [
      //Observable.of(this.dataBase),
      this.dataBase.dataChange,
      this.paginator.page
    ];

    return Observable.merge(displayDataChanges).pipe(map(() => {
      let data;
      this.dataBase.dataChange.subscribe(xdata=>{
        // console.log(data.data);
        console.log(Object.values(xdata));
        data=Object.values(xdata);
        }
      );

      // const data = this.dataBase.data;//.slice();
      console.log('In merge');
      console.log(data);
      // // Grab the page's slice of data.
      const startIndex = this.paginator.pageIndex * this.paginator.pageSize;
      const finalData = data.splice(startIndex, this.paginator.pageSize);

      // console.log(finalData, 'finalData')
      return finalData;

    }));
  }

  disconnect() {}

}