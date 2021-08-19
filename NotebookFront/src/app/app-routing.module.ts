import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import {SearchComponent} from './search/search.component'
const routes: Routes = [ 
    {path: 'home', component: SearchComponent},
  { path: '**', redirectTo: 'home', pathMatch: 'full'} ];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
