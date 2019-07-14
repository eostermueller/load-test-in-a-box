import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-start-stop',
  templateUrl: './start-stop.component.html',
  styleUrls: ['./start-stop.component.scss']
})
export class StartStopComponent implements OnInit {
  panelOpenState = false;
  constructor() { }

  ngOnInit() {
  }

}
