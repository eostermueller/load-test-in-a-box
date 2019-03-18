import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-demo-card',
  templateUrl: './demo-card.component.html',
  styleUrls: ['./demo-card.component.scss']
})
export class DemoCardComponent implements OnInit {
  @Input() element: any;
  constructor() { }

  ngOnInit() {
  }

}
