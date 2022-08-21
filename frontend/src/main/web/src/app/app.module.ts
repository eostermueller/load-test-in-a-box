import { HttpClientModule } from '@angular/common/http';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { ClipboardModule } from '@angular/cdk/clipboard';

import { SanitizeHtml } from './markdown/sanitize-html'
import { SecurityContext } from '@angular/core';

import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';

import { CoreModule } from './core/core.module';
import { NavComponent } from './nav/nav.component';
import { LayoutModule } from '@angular/cdk/layout';

import { MatExpansionModule} from '@angular/material/expansion';

import {MatFormFieldModule, MatFormFieldControl} from '@angular/material/form-field';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';

import { MatTooltipModule } from '@angular/material/tooltip';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { ConfigService } from './services/config.service';
import { APP_INITIALIZER } from '@angular/core';

import { MarkdownModule } from 'ngx-markdown';
import { SafeHtmlPipe } from './markdown/markdown.component';
import { SafeUrlPipe } from './markdown/markdown.component';
import { AngularSplitModule } from 'angular-split';
/**
 * "ng update" to angular 9 (https://update.angular.io/#8.0:9.0l3) did not upgrade these imports
 * to include the component specific end to '@angular/material'
 */
import {  MatToolbarModule } from '@angular/material/toolbar';
import {  MatButtonModule } from '@angular/material/button';
import {  MatIconModule } from '@angular/material/icon';
import {  MatCardModule } from '@angular/material/card';
import {  MatSidenavModule } from '@angular/material/sidenav';
import {  MatListModule } from '@angular/material/list';
import {  MatPaginatorModule } from '@angular/material/paginator';
import {  MatTabsModule } from '@angular/material/tabs';
import {  MatInputModule } from '@angular/material/input';

import { WorkloadSelectionComponent } from './workload-selection/workload-selection.component';

import { RouterModule, Routes } from '@angular/router';
import { LoadGenMetricsComponent } from './load-gen-metrics/load-gen-metrics.component';
import { TrafficJvmParametersComponent } from './traffic-jvm-parameters/traffic-jvm-parameters.component';
import { DeploymentComponent } from './deployment/deployment.component';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatRadioModule} from '@angular/material/radio';

import {FormsModule, ReactiveFormsModule} from '@angular/forms';

//import { MatPaginationDemoComponent } from './mat-pagination-demo/mat-pagination-demo.component';
import { DemoCardComponent } from './demo-card/demo-card.component';

import { UseCasesComponent } from './use-cases/use-cases.component';
import { UseCaseCardComponent } from './use-case-card/use-case-card.component';
import { StartStopComponent } from './start-stop/start-stop.component';
import { HealthChecksComponent } from './health-checks/health-checks.component';
import { UseCaseService } from './use-case.service';
import { WorkloadKeyComponent } from './workload-key/workload-key.component';
import { MarkdownComponent } from './markdown/markdown.component';
import { MarkdownTree } from './markdown-tree/markdown-tree';
//import { TreeComponent } from './tree/tree.component';
//import { TreeComponent } from './src/app/tree/tree.component';

import { MatTreeModule } from '@angular/material/tree';
//import { TreeComponent } from './tree/tree.component';
//import { MatSelectionListChange } from '@angular/material/list';

  const appRoutes: Routes = [
    /**
     * technique for setting the default route:
     * @st0lenFr0m:  https://stackoverflow.com/a/42156970/2377579
     */
    {
      path: '',
      redirectTo: "/snail4j",
      pathMatch: 'full'
    },
    { path: 'snail4j', component: WorkloadSelectionComponent },      
    //{ path: 'workload', component: WorkloadComponent },
    { path: 'start-stop', component: StartStopComponent },
    { path: 'metrics', component: LoadGenMetricsComponent },
    { path: 'jvm-parameters', component: TrafficJvmParametersComponent },
    { path: 'deployment', component: DeploymentComponent },
  ];


@NgModule({
  declarations: [
    AppComponent,
    NavComponent,
    WorkloadSelectionComponent,
    LoadGenMetricsComponent,
    TrafficJvmParametersComponent,
    UseCasesComponent,
    UseCaseCardComponent,
    DeploymentComponent,
//    MatPaginationDemoComponent,
    DemoCardComponent,
    StartStopComponent,
    HealthChecksComponent,
    WorkloadKeyComponent,
    MarkdownComponent,
    MarkdownTree,
    SanitizeHtml,
    SafeHtmlPipe,
    SafeUrlPipe,
  ],
  imports: [
    HttpClientModule,
    RouterModule.forRoot(appRoutes, { relativeLinkResolution: 'legacy' }),
    CoreModule,
    BrowserModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot(),
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
    MatTooltipModule,
    MatProgressSpinnerModule,
    ClipboardModule,
//    // enable default sanitization
//    MarkdownModule.forRoot(),
// turn off sanitization
    MarkdownModule.forRoot({
      sanitize: SecurityContext.NONE
    }),
    AngularSplitModule,
    MatTreeModule,
//    TreeNestedOverviewExample
//    MatSelectionListChange
    // ErrorHandler,
    // GlobalErrorHandler,
    // Injectable
  ],
  exports: [
  ],
  // providers: [
  //   {
  //     provide: ErrorHandler, 
  //     useClass: GlobalErrorHandler
  //   }    
  // ],
//  entryComponents: [UseCaseService],
//  bootstrap: [AppComponent, UseCasesComponent],
  bootstrap: [AppComponent], // https://github.com/angular/components/issues/5899
providers: [ConfigService,
    {
      provide: APP_INITIALIZER,
      // useFactory: (configService: ConfigService) => function () { return configService.load(); },
      useFactory: (configService: ConfigService) => configService.loader,
      deps: [ConfigService],
      multi: true
    }]
 
})
export class AppModule {
}
