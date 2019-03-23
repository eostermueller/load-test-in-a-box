import { Component, OnInit, Input } from '@angular/core';


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
  @Input() useCase: any;

  constructor() {
    this.IsChecked = false;
    this.IsIndeterminate = false;
    this.LabelAlign = 'after';
    this.IsDisabled = false;
  }

  ngOnInit() {
  }

}