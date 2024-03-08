import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { User } from '../../models/user';
import { Address } from '../../models/address';
import { AuthService } from '../../services/auth.service';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { LoginComponent } from '../login/login.component';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule, CommonModule, RouterModule],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent {
  newUser: User = new User();
  newAddress: Address = new Address();

  constructor(private auth: AuthService, private router: Router) {}

  register(user: User): void {
    this.newUser.address = this.newAddress;

    console.log('Registering user:');
    console.log(this.newUser);
    this.auth.register(this.newUser).subscribe({
      next: (registeredUser) => {
        this.auth
          .login(this.newUser.username, this.newUser.password)
          .subscribe({
            next: (loggedInUser) => {
              this.router.navigateByUrl('/home');
            },
            error: (problem) => {
              console.error(
                'RegisterComponent.register(): Error logging in user:'
              );
              console.error(problem);
            },
          });
      },
      error: (fail) => {
        console.error(
          'RegisterComponent.register(): Error registering account'
        );
        console.error(fail);
      },
    });
  }

  handleImageUpload(event: any, field: 'profilePicture' | 'backgroundPicture') {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = (e: any) => {
        this.newUser[field] = e.target.result;
      };
      reader.readAsDataURL(file);
    }
  }
}
