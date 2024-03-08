import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router, RouterLink } from '@angular/router';
import { User } from '../../models/user';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent {
  loginUser: User = new User();

  constructor(private authService: AuthService, private router: Router) {}

  login(user: User): void {
    console.log('Login attempt:', this.loginUser);
    this.authService.login(user.username, user.password).subscribe({
      next: (loggedInUser) => {
        this.router.navigateByUrl('/home');
      },
      error: (failedLogin) => {
        console.error('Login failed.');
        console.error(failedLogin);
      },
    });
  }
}
