import {OneComponent} from './one/one.component';
import {TwoComponent} from './two/two.component';
import {ThreeComponent} from './three/three.component';
//import {WorkloadComponent} from './workload/workload.component';

import {Routes} from '@angular/router';

export const routes: Routes = [
  { path: 'one', component: OneComponent },
  { path: 'two', component: TwoComponent },
  { path: 'three', component: ThreeComponent },
 // { path: 'workload', component: WorkloadComponent },
  { path: '', redirectTo: '/', pathMatch: 'full' },
];
