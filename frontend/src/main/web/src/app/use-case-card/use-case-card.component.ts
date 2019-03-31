import { Component, OnInit, Input } from '@angular/core';
import { MatCheckboxChange, MatRadioButton, MatRadioChange } from '@angular/material';


@Component({
  selector: 'app-use-case-card',
  templateUrl: './use-case-card.component.html',
  styleUrls: ['./use-case-card.component.scss']
})
export class UseCaseCardComponent implements OnInit {
  IsChecked:boolean;
  IsIndeterminate:boolean;
  LabelAlign:string;
  IsDisabled:boolean;

  public selectedIndex:number = 0;
  public previousIndex:number = -1;
  @Input() useCase: any;
  useCaseObj: any;
  constructor() {
    this.IsChecked = false;
    this.IsIndeterminate = false;
    this.LabelAlign = 'after';
    this.IsDisabled = false;
  }
  typeOf(obj:any) {
    return {}.toString.call(obj).split(' ')[1].slice(0, -1).toLowerCase();
  }

  ngOnInit() {
  }

  getCurrentProcessingUnit() {
    return this.useCase.processingUnits[this.selectedIndex];
  }

  currentState() {
    console.log('useCase pr0ps: ' + Object.getOwnPropertyNames(this.useCase));
    console.log('useCase type a: ' + this.typeOf(this.useCase) );
    console.log('useCase type b: ' + this.typeOf(this.useCase.name) );
    console.log('useCase type c: ' + this.typeOf(this.useCase.processingUnits) );

    console.log('Enabled ' + this.IsChecked);
    console.log( 'UseCase: ' + this.useCase.name );
    console.log( 'UseCase dump: ' + JSON.stringify(this.useCase) );
    console.log( 'UseCase pi length: ' + this.useCase.processingUnits.length );
    const processingUnit = this.getCurrentProcessingUnit();

    console.log( 'Selected processing impl method name: ' 
    + processingUnit.method.name);

    console.log( 'Selected processing class name: ' 
    + processingUnit.method.declaringClassName );
   }
  onEnableCheckboxChange(cbEvent: MatCheckboxChange) {
    // if (!this.IsChecked) {
    //   if (this.previousIndex!=-1) 
    //}
//    this.currentState();

      console.log('cb checked ' + cbEvent.checked);
      console.log('cb name ' + cbEvent.source.name);
      console.log('cb value ' + cbEvent.source.value);
      console.log('cb id ' + cbEvent.source.id);
      console.log('cb inputId ' + cbEvent.source.inputId);
  }
  onRadioChange(mrChange: MatRadioChange) {
    //this.currentState();

    if (this.IsChecked) {

      this.previousIndex = this.selectedIndex;
    }
    console.log(mrChange.value);
    let mrButton: MatRadioButton = mrChange.source;
    console.log('r name ' + mrButton.name);
    console.log('r checked ' + mrButton.checked);
    console.log('r id ' + mrButton.id);
    console.log('r inputId ' + mrButton.inputId);

 } 
 
}