import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {MainComponent} from "./main/main.component";

/**
 *  Defined Routes
 */
const routes: Routes = [
  {path: 'main', component: MainComponent},
  {path: '**', redirectTo: '/main', pathMatch: 'full'},
];

/**
 * AppRoutingModule
 */
@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule],
  providers: []
})
export class AppRoutingModule {
}
