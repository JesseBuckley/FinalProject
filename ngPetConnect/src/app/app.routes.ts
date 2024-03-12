import { Routes } from '@angular/router';
import { MypetsComponent } from './component/mypets/mypets.component';
import { HomeComponent } from './component/home/home.component';
import { RegisterComponent } from './component/register/register.component';
import { AccountComponent } from './component/account/account.component';
import { LoginComponent } from './component/login/login.component';
import { LogoutComponent } from './component/logout/logout.component';
import { NotfoundComponent } from './component/notfound/notfound.component';
import { LandingComponent } from './component/landing/landing.component';
import { FollowersComponent } from './component/followers/followers.component';
import { AboutComponent } from './component/about/about.component';
import { ResourceComponent } from './component/resource/resource.component';
import { PhotosComponent } from './component/photos/photos.component';

export const routes: Routes = [
  { path: '', redirectTo: '/landing', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'landing', component: LandingComponent },
  { path: 'mypets', component: MypetsComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'resource', component: ResourceComponent },
  { path: 'account', component: AccountComponent },
  { path: 'login', component: LoginComponent },
  { path: 'logout', component: LogoutComponent },
  { path: 'followers', component: FollowersComponent },
  { path: 'about', component: AboutComponent },
  { path: 'photos', component: PhotosComponent },
  { path: '**', component: NotfoundComponent },
];
