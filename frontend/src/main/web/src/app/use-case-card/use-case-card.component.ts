import { Component, OnInit, Input } from '@angular/core';

/**
 * "ng update" to angular 9 (https://update.angular.io/#8.0:9.0l3) did not upgrade these imports
 * to include the component specific end to '@angular/material'
 */

import { MatCheckboxChange  } from '@angular/material/checkbox';
import { MatRadioButton } from '@angular/material/radio';
import { MatRadioChange } from '@angular/material/radio';

import { Output, EventEmitter } from '@angular/core';

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
  @Output() useCaseSelect = new EventEmitter<string>();
  @Output() useCaseDeSelect = new EventEmitter<string>();
  public selectedIndex:number = 0;
  public previousIndex:number = -1;
  @Input() useCase: any;
  useCaseObj: any;
  constructor() {
    this.IsChecked = false;
    this.IsIndeterminate = false;
    this.LabelAlign = 'after';
    this.IsDisabled = false;
    console.log('UseCaseCardComponent.ctor() selectedIndex a: ' + this.selectedIndex );
  }
  typeOf(obj:any) {
    return {}.toString.call(obj).split(' ')[1].slice(0, -1).toLowerCase();
  }
  public getName() : string {
    return this.useCase.name;
  }


  ngOnInit() {
  console.log('UseCaseCardComponent.ngOnInit() selectedIndex b: ' + this.selectedIndex );
  }

  getCurrentProcessingUnit() {
  //  console.log('selectedIndex c: ' + this.selectedIndex );

    return this.useCase.processingUnits[this.selectedIndex];
  }

  currentState() {
    console.log('selectedIndex d: ' + this.selectedIndex );

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
    this.updateSelectedFlags();
    // if (!this.IsChecked) {
    //   if (this.previousIndex!=-1) 
    //}
//    this.currentState();
    console.log('selectedIndex e: ' + this.selectedIndex );
      this.IsChecked = cbEvent.checked;
      console.log('cb checked ' + cbEvent.checked);
      console.log('cb name ' + cbEvent.source.name);
      console.log('cb value ' + cbEvent.source.value);
      console.log('cb id ' + cbEvent.source.id);
      console.log('cb inputId ' + cbEvent.source.inputId);
      if (cbEvent.checked) {
        this.useCaseSelect.emit(this.useCase);
      } else {
        this.useCaseDeSelect.emit(this.useCase);
      }

  }

  public setSelectionState(running:boolean, selectedNdx:number) {
    this.IsChecked = running;
    this.selectedIndex = selectedNdx;
    this.updateSelectedFlags();
  }
  updateSelectedFlags() {
    for(let i = 0; i < this.useCase.processingUnits.length; i++) {
      const pu = this.useCase.processingUnits[i];
      if (this.selectedIndex === i) {
        pu.selected = true;
      } else {
        pu.selected = false;
      }
    }
  }
  
  onRadioChange(mrChange: MatRadioChange) {
    //this.currentState();
    console.log('selectedIndex f: ' + this.selectedIndex );
    this.updateSelectedFlags();
    if (this.IsChecked) {

      this.previousIndex = this.selectedIndex;
    }
    console.log(mrChange.value);
    let mrButton: MatRadioButton = mrChange.source;
    console.log('r name ' + mrButton.name);
    console.log('r checked ' + mrButton.checked);
    console.log('r id ' + mrButton.id);
    console.log('r inputId ' + mrButton.inputId);
    if (this.IsChecked) {
      this.useCaseSelect.emit(this.useCase);
    }
    
    

 } 
 
}