import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { User } from '../../models/user';
import { AuthService } from '../../services/auth.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink, RouterModule } from '@angular/router';
import { HomeComponent } from '../home/home.component';

@Component({
  selector: 'app-followers',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterModule, FormsModule, HomeComponent],
  templateUrl: './followers.component.html',
  styleUrl: './followers.component.css',
})
export class FollowersComponent implements OnInit {
  followers: User[] = [];
  followedUserIds: number[] = [];
  showUsers: boolean = false;
  searchKeyword: string = '';
  users: User[] = [];

  constructor(private userService: UserService, private authService: AuthService) {}

  ngOnInit(): void {
    this.loadFollowers();
  }

  loadFollowers(): void {
    if (this.authService.checkLogin()) {
      this.userService.getAllFollowers().subscribe(
        (data: User[]) => {
          this.followers = data;
          this.followedUserIds = this.followers.map(user => user.id);
          console.log(this.followedUserIds);
        },
        (error) => {
          console.error('Error fetching followers:', error);
        }
      );
    this.userService.getAllUsers().subscribe(
          (data: User[]) => {
            this.users = data;
          },
          (error) => {
            console.error('Error fetching followers:', error);
          }
        );
    }
  }


  toggleUsers(): void {
    this.showUsers = !this.showUsers;
    if (this.showUsers) {
      this.loadFollowers();
    }
  }

searchUsers(): void {
    if (this.authService.checkLogin()) {
      this.userService.searchUsersByUsername(this.searchKeyword).subscribe(
        (data: User[]) => {
          this.users = data;
          this.followedUserIds = this.users.map(user => user.id);
          console.log('Search results:', this.users);
          this.showUsers = true;
        },
        (error: any) => {
          console.error('Error searching users:', error);
        }
      );
    }
  }

  followUser(userId: number): void {
  if (this.authService.checkLogin()) {
    this.userService.followUser(userId).subscribe(
      () => {
        console.log('User followed successfully.');
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
        this.loadFollowers();
      },
      (error) => {
        console.error('Error unfollowing user:', error);
      }
    );
  }
}

  isUserFollowed(userId: number): boolean {

  if(this.followedUserIds.includes(userId)) {
    return true;
  } else {
    return false;
  }
  }
}
