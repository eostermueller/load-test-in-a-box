import {Component} from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  routes = [
    { path: '/', name: 'Home' },
    { path: 'one', name: 'One' },
    { path: 'two', name: 'Two' },
    { path: 'three', name: 'Three' }
  ];

  constructor() {
  }

}
