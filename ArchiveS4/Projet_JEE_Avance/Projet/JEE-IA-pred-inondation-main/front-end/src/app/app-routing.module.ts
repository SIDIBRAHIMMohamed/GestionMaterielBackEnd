import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SignupComponent } from './components/signup/signup.component';
import { LoginComponent } from './components/login/login.component';
import { UserComponent } from './components/user/user.component';
import { AdminComponent } from './components/admin/admin.component';
import { CommonModule } from '@angular/common';
import { VisualiserComponent } from './components/visualiser/visualiser.component';
import { GraphComponent } from './components/graph/graph.component';
import { AuthGuard } from './auth.guard';

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  {path: 'signup', component: SignupComponent},
  { path: 'user', component: UserComponent, },
  { path: 'visualiser', component: VisualiserComponent },
  {path:'admin', component :AdminComponent},
  {path:'graph', component :GraphComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule,CommonModule],
  
})
export class AppRoutingModule { }
