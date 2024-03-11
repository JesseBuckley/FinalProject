import { Router } from '@angular/router';
import { AuthService } from './../../services/auth.service';
import { Component } from '@angular/core';

@Component({
  selector: 'app-logout',
  standalone: true,
  imports: [],
  templateUrl: './logout.component.html',
  styleUrl: './logout.component.css',
})
export class LogoutComponent {
  constructor(private auth: AuthService, private router: Router) {}

  logout(): void {
    console.log('logging out');
    this.auth.logout();
    this.router.navigateByUrl('/landing');
  }
}
