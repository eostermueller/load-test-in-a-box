import { HttpClientModule } from '@angular/common/http';

import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { CoreModule } from './core/core.module';
import { NavComponent } from './nav/nav.component';
import { LayoutModule } from '@angular/cdk/layout';

import { MatExpansionModule} from '@angular/material/expansion';

import {MatFormFieldModule, MatFormFieldControl} from '@angular/material/form-field';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';

import { MatToolbarModule,
          MatButtonModule,
          MatIconModule,
          MatCardModule,
          MatSidenavModule,
          MatListModule,
          MatPaginatorModule,
          MatTabsModule,
          MatInputModule,
           } from '@angular/material';
import { WorkloadComponent } from './workload/workload.component';

import { RouterModule, Routes } from '@angular/router';
import { LoadGenMetricsComponent } from './load-gen-metrics/load-gen-metrics.component';
import { TrafficJvmParametersComponent } from './traffic-jvm-parameters/traffic-jvm-parameters.component';
import { WorkloadSelectionTypeComponent } from './workload-selection-type/workload-selection-type.component';
import { DeploymentComponent } from './deployment/deployment.component';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatRadioModule} from '@angular/material/radio';

import {FormsModule, ReactiveFormsModule} from '@angular/forms';

import { MatPaginationDemoComponent } from './mat-pagination-demo/mat-pagination-demo.component';
import { DemoCardComponent } from './demo-card/demo-card.component';

import { UseCasesComponent } from './use-cases/use-cases.component';
import { UseCaseCardComponent } from './use-case-card/use-case-card.component';
import { StartStopComponent } from './start-stop/start-stop.component';

  const appRoutes: Routes = [
    { path: 'workload', component: WorkloadComponent },
    { path: 'start-stop', component: StartStopComponent },
    { path: 'metrics', component: LoadGenMetricsComponent },
    { path: 'jvm-parameters', component: TrafficJvmParametersComponent },
    { path: 'deployment', component: DeploymentComponent },
  ];


@NgModule({
  declarations: [
    AppComponent,
    NavComponent,
    WorkloadComponent,
    LoadGenMetricsComponent,
    TrafficJvmParametersComponent,
    WorkloadSelectionTypeComponent,
    UseCasesComponent,
    UseCaseCardComponent,
    DeploymentComponent,
    MatPaginationDemoComponent,
    DemoCardComponent,
    StartStopComponent,
  ],
  imports: [
    RouterModule.forRoot(appRoutes),
    CoreModule,
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    LayoutModule,
    MatCardModule,
    MatPaginatorModule,
    MatToolbarModule,
    MatButtonModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule,
    MatTabsModule,
    MatCheckboxModule,
    MatRadioModule,
    FormsModule,
    ReactiveFormsModule,
    MatExpansionModule,
    MatFormFieldModule,
    MatInputModule,
    MatSlideToggleModule,    
  ],
  exports: [
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
