import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from './auth/guards/auth.guard';
import { LoginComponent } from './auth/components/login/login.component';
import {ImageComponent} from "./images-component/image.component";

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  {
    path: '**',
    redirectTo: 'images'
  },
  { canActivate: [AuthGuard], path: 'images', component: ImageComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
