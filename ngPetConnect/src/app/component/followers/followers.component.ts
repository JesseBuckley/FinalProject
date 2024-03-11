import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { HomeComponent } from '../home/home.component';

@Component({
  selector: 'app-followers',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterModule, FormsModule, HomeComponent],
  templateUrl: './followers.component.html',
  styleUrl: './followers.component.css'
})
export class FollowersComponent implements OnInit {
  followers: any[] | undefined;

  constructor(private userService: UserService){}

  ngOnInit(): void {

    this.userService.findAllUsers().subscribe(
      (data: any[]) => {
        this.followers = data;
        console.log(this.followers);
      },
      (error) => {
        console.error('Error fetching users:', error);
      }
    );  }

  getFollowers() {
    this.userService.findAllUsers().subscribe((data: any[]) => {
      this.followers = data;
      console.log(this.followers);
    });
  }
}
