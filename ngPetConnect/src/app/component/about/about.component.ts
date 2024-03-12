import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { AuthService } from '../../services/auth.service';
import { User } from '../../models/user';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-about',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './about.component.html',
  styleUrls: ['./about.component.css'],
})
export class AboutComponent implements OnInit {
  user: User = new User();
  editUser: boolean = false;
  editedBiography: string = '';

  constructor(private userService: UserService, private authService: AuthService) {}

  ngOnInit(): void {
    this.authService.getLoggedInUser().subscribe(
      (user: User) => {
        this.user = user;
        console.log('Logging in as', user);
      },
      (error) => {
        console.error('Error fetching user:', error);
      }
    );
  }

  editBiography() {
    this.editUser = true;

  }
  saveChanges() {
    this.user.biography = this.editedBiography;
    // Call your service to update the user's biography in the backend
    this.userService.updateUser(this.user.id, this.user).subscribe(
      (updatedUser) => {
        console.log('Biography updated successfully:', updatedUser);
        this.editUser = false;
      },
      (error) => {
        console.error('Error updating biography:', error);
      }
    );
  }


  cancelEdit() {
    this.editUser = false;
  }
}
