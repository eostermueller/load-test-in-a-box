import {WorkloadSelectionComponent} from './workload-selection/workload-selection.component';

import {Routes} from '@angular/router';

export const routes: Routes = [
  { path: 'one', component: WorkloadSelectionComponent },
  { path: '', redirectTo: '/', pathMatch: 'full' },
];
