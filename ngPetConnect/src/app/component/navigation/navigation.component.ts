import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router, RouterLink, RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { LoginComponent } from '../login/login.component';
import { LogoutComponent } from '../logout/logout.component';

@Component({
  selector: 'app-navigation',
  standalone: true,
  imports: [
    RouterLink,
    CommonModule,
    RouterModule,
    LoginComponent,
    LogoutComponent,
  ],
  templateUrl: './navigation.component.html',
  styleUrl: './navigation.component.css',
})
export class NavigationComponent {
  showHome: boolean = true;

  constructor(private auth: AuthService, private router: Router) {}

  loggedIn(): boolean {
    return this.auth.checkLogin();
  }

  logout(): void {
    console.log('logging out');
    this.auth.logout();
    this.router.navigateByUrl('/landing');
  }

}
