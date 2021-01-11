import {Component} from '@angular/core';
import { NotificationService } from './services/notification.service'

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  routes = [
    { path: 'workload', name: 'Worload' },
    { path: 'start-stop', name: 'starT-sTop' },
    { path: 'two', name: 'Two' },
    { path: 'three', name: 'Three' }
  ];

  /*
  routes = [
    { path: '/', name: 'Home' },
    { path: 'one', name: 'One' },
    { path: 'two', name: 'Two' },
    { path: 'three', name: 'Three' }
  ];
*/
  constructor(private notifyService : NotificationService) {
  }
  showToastrSuccess(){
    this.notifyService.showSuccess("Data shown successfully !!", "Performance Analysis Workbench")
  }

}
