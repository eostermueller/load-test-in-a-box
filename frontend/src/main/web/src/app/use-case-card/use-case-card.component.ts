import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-use-case-card',
  templateUrl: './use-case-card.component.html',
  styleUrls: ['./use-case-card.component.scss']
})
export class UseCaseCardComponent implements OnInit {
  @Input() useCase: any;

  constructor() { }

  ngOnInit() {
  }

}