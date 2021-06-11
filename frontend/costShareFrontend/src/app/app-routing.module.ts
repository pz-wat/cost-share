import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LandingPageComponent } from './landing-page/landing-page.component';
import { LoginPageComponent } from './login-page/login-page.component';
import { RegisterComponent } from './register/register.component';
import { GroupManagerComponent } from './group-manager/group-manager.component';
import { GroupComponent } from './group/group.component';

const routes: Routes = [
  { path: 'home', component: LandingPageComponent },
  { path: '', component: LandingPageComponent },
  { path: 'login', component: LoginPageComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'groups', component: GroupManagerComponent },
  { path: 'groups/:code', component: GroupComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
