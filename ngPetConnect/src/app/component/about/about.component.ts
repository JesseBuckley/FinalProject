import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink, RouterModule } from '@angular/router';
import { MypetsComponent } from '../mypets/mypets.component';
import { CommonModule } from '@angular/common';
import { AppComponent } from '../../app.component';
import { PostCommentComponent } from '../post-comment/post-comment.component';
import { UserService } from '../../services/user.service';
import { User } from '../../models/user';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-about',
  standalone: true,
  imports: [MypetsComponent, FormsModule, CommonModule],
  templateUrl: './about.component.html',
  styleUrls: ['./about.component.css'],
})
export class AboutComponent implements OnInit {
  user: User = new User();
  Users: User[] = [];
  editUser: any;
  editBiography: any;
  errorMessage: string | undefined;
  allUsers: any;

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
    )
  }

  updateUser(userToUpdate: User): void {
    if (this.user) {
      userToUpdate.address = this.editBiography;
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
}
