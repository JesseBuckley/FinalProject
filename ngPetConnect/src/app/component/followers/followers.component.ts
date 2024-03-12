import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { HomeComponent } from '../home/home.component';
import { HttpClient } from '@angular/common/http';
import { User } from '../../models/user';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-followers',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterModule, FormsModule, HomeComponent],
  templateUrl: './followers.component.html',
  styleUrl: './followers.component.css',
})
export class FollowersComponent implements OnInit {
  followers: User[] = [];

  constructor(
    private userService: UserService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.loadFollowers();
  }

  loadFollowers(): void {
    if (this.authService.checkLogin()) {
      this.userService.getAllUsers().subscribe(
        (data: User[]) => {
          this.followers = data;
          console.log(this.followers);
        },
        (error) => {
          console.error('Error fetching followers:', error);
        }
      );
    }
  }

  followUser(userId: number): void {
    if (this.authService.checkLogin()) {
      this.userService.followUser(userId).subscribe(
        () => {
          console.log('User followed successfully.');
          // Refresh the list of followers
          this.loadFollowers();
        },
        (error) => {
          console.error('Error following user:', error);
        }
      );
    }
  }

  unfollowUser(userId: number): void {
    if (this.authService.checkLogin()) {
      this.userService.unfollowUser(userId).subscribe(
        () => {
          console.log('User unfollowed successfully.');
          // Refresh the list of followers
          this.loadFollowers();
        },
        (error) => {
          console.error('Error unfollowing user:', error);
        }
      );
    }
  }
}
