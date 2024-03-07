import { Routes } from '@angular/router';
import { MypetsComponent } from './component/mypets/mypets.component';
import { HomeComponent } from './component/home/home.component';

export const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'mypets', component: MypetsComponent },
  // { path: 'login', component: LoginComponent},
  // { path: 'login', component: LogoutComponent},

];
