import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {routes} from './app.routes';

@NgModule({
  imports: [ RouterModule.forRoot(routes, { relativeLinkResolution: 'legacy' }) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {
}
