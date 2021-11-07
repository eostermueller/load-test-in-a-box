import {WorkloadSelectionComponent} from './workload-selection/workload-selection.component';
import {TwoComponent} from './two/two.component';
import {ThreeComponent} from './three/three.component';
//import {WorkloadComponent} from './workload/workload.component';

import {Routes} from '@angular/router';

export const routes: Routes = [
  { path: 'one', component: WorkloadSelectionComponent },
  { path: 'two', component: TwoComponent },
  { path: 'three', component: ThreeComponent },
  { path: '', redirectTo: '/', pathMatch: 'full' },
];
