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
import { stringify } from '@angular/compiler/src/util';
import {Workload} from '../model/workload';

import { SutLaunchStatusService } from '../services/sut-launch-status.service';

import { LaunchStatus }           from '../services/LaunchStatus';

export class Database { // {{{

  /** Stream that emits whenever the data has been modified. If filter is applied on the data*/
  dataChange: BehaviorSubject<any[]> = new BehaviorSubject<any[]>([]);
  get data(): any[] { return this.dataChange.value; }

  constructor(
    data : any[],     
    ) {
    // Fill up the database .
    this.dataChange.next(data);
    //debugger
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

  private forceHttpWorkloadRq : boolean = true;
  private sutLaunchStatus:LaunchStatus = LaunchStatus.Stopped;
  private sutLaunchStatusPrevious:LaunchStatus = LaunchStatus.Stopped;

  useCases : any[];
  length = 0;
  pageIndex = 0;
  pageSize = 5;
  database: Database;
  pageEvent: PageEvent;
  dataSource : MyDataSource;
  useCaseSelection: Map<string, any>; //one day, I'll add value objects and replace any with UseCase
  @ViewChild(MatPaginator, {static: true} ) paginator: MatPaginator;

constructor(
  private useCaseService : UseCaseService, 
  private sutLaunchStatusService: SutLaunchStatusService,
  private cdRef:ChangeDetectorRef) { 
  debugger
 }

public getKey(useCase: any): string {
  return useCase.name;
}

/**
 * 
 * @param $event The type here shows string, but I've confirmed its an object.
 * this was req'd: //console.log( 'useCase changed [' + JSON.stringify($event) + ']');
 */
public useCaseSelectionListener($event:string) {
    const myKey:string = this.getKey($event);
    this.useCaseSelection.set( myKey, $event);
    this.dispUseCases('upsert');
    this.updateWorkload();
}
public updateWorkload() {
    let myArray = Array.from( this.useCaseSelection );

    const workload = new Workload();
    
    //each entry is an array of 2 values:  0=key, 1=value
     for (let entry of myArray) {
        workload.useCases.push( entry[1] )
     }
     this.useCaseService.updateWorkload(workload).subscribe();
   
}
public useCaseDeSelectionListener($event:string) {
  const myKey:string = this.getKey($event);
  this.useCaseSelection.delete( myKey );
  this.dispUseCases('delete');
  this.updateWorkload();
}

dispUseCases(ctx:string) {
  console.log('My ctx:' + ctx);
  for (let [key, value] of this.useCaseSelection) {
    console.log(key, value);
  }
}
  public load() {
    console.log("UseCasesComponents.load()");
//    if (this.sutLaunchStatus == LaunchStatus.Started 
//      && this.forceHttpWorkloadRq == true) {

        this.useCaseService.getUseCases().subscribe(data=>{
          console.log(data);
          this.useCases= data.useCases;
          this.length = this.useCases.length;
          this.database=new Database(this.useCases);
          this.dataSource = new MyDataSource(this.database, this.paginator);
          this.useCaseSelection = new Map<string,any>();
          this.cdRef.detectChanges();
          this.forceHttpWorkloadRq = false;
        });

//      }
  }
    ngOnInit() {

      this.sutLaunchStatusService.currentStatus.subscribe(
        status => {
          this.sutLaunchStatusPrevious = this.sutLaunchStatus;
          this.sutLaunchStatus = status;

          /**
           * When restart is first complete,
           * query newly started JVM for code annotated
           * with @Load
           */
          if (this.sutLaunchStatus != this.sutLaunchStatusPrevious) {
            if (this.sutLaunchStatus == LaunchStatus.Started) {
              this.forceHttpWorkloadRq = true;
            }
          }
        }
    );


    // this.useCaseService.getUseCases().subscribe(data=>{
    //   console.log(data);
    //   this.useCases= data.useCases;
    //   this.length = this.useCases.length;
    //   this.database=new Database(this.useCases);
    //   this.dataSource = new MyDataSource(this.database, this.paginator);
    // });
  }
}

export class MyDataSource extends DataSource<any> {
  /** Stream of data that is provided to the table. */
  constructor(private dataBase: Database,  private paginator: MatPaginator) {
      super();
    console.log('In constructor');
    console.log(dataBase);
    //debugger
  }
   /** Connect function called by the table to retrieve one stream containing the data to render. */
  connect(): Observable<any[]> {
    const displayDataChanges = [
      this.dataBase.dataChange,
      this.paginator.page
    ];

    return Observable.merge(displayDataChanges).pipe(map(() => {
      let data : any[];
      this.dataBase.dataChange.subscribe(xdata=>{
        // console.log(data.data);
        data=Object.values(xdata);
        }
      );

      // // Grab the page's slice of data.
      const startIndex = this.paginator.pageIndex * this.paginator.pageSize;
      const finalData = data.splice(startIndex, this.paginator.pageSize);

      // console.log(finalData, 'finalData')
      return finalData;

    }));
  }

  disconnect() {}

}