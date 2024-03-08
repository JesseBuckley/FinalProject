import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { User } from '../../models/user';
import { AuthService } from '../../services/auth.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-account',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './account.component.html',
  styleUrl: './account.component.css',
})
export class AccountComponent implements OnInit {
  user: User | any;
  Users: User[] = [];
  editUser: any;
  isAdmin: boolean = false;
  errorMessage: string | undefined;
  editAddress: any;
  allUsers: any;

  constructor(private userService: UserService, private auth: AuthService) {}

  ngOnInit(): void {
    this.fetchAuthenticatedUserDetails();
  }

  fetchAuthenticatedUserDetails(): void {
    if (this.auth.checkLogin()) {
      this.auth.getLoggedInUser().subscribe({
        next: (user) => {
          this.user = user;
          this.isAdmin = user.role === 'ADMIN';


          this.editUser = Object.assign({}, this.user);


          if (this.user.address) {
            this.editAddress = Object.assign({}, this.user.address);
          } else {
            this.editAddress = {};
          }
        },
        error: (err) => {
          console.error('Error fetching authenticated user details:', err);
          this.errorMessage = 'Error fetching user details';
        },
      });
    } else {
      this.errorMessage = 'User not logged in';
    }
  }


  fetchAllUsers(): void {
    this.userService.findAllUsers().subscribe({
      next: (users) => (this.Users = users),
      error: (err) => {
        this.errorMessage = 'Error fetching all users';
        console.error(err);
      },
    });
  }

  updateUser(userToUpdate: User): void {
    if (this.user) {
      userToUpdate.address = this.editAddress
      this.userService.updateUser(this.user.id, userToUpdate).subscribe({
        next: (updatedUser) => {
          this.user = updatedUser;
          alert('User updated successfully');
        },
        error: (err) => {
          this.errorMessage = 'Error updating user';
          console.error(err);
        },
      });
    }
  }
  handleImageUpload(
    event: any,
    field: 'profilePicture' | 'backgroundPicture'
  ): void {
    const file = event.target.files[0];
    if (file) {
      console.log(`Uploading ${field}:`, file.name);
    }
  }

  disableUserAccount(userId: number): void {
    this.userService.disableUserAccount(userId).subscribe({
      next: () => {
        alert('Account disabled successfully.');
        this.fetchAuthenticatedUserDetails();
      },
      error: (err) => {
        this.errorMessage = 'Error disabling account';
        console.error(err);
      },
    });
  }

  enableUserAccount(userId: number): void {
    this.userService.enableUserAccount(userId).subscribe({
      next: () => {
        alert('Account enabled successfully.');
        this.fetchAuthenticatedUserDetails();
      },
      error: (err) => {
        this.errorMessage = 'Error enabling account';
        console.error(err);
      },
    });
  }
}
