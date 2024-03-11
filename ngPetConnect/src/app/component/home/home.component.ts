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
  selector: 'app-home',
  standalone: true,
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
  imports: [MypetsComponent, FormsModule, CommonModule, PostCommentComponent, RouterLink, RouterModule],
})
export class HomeComponent implements OnInit {
  user: User = new User();
  users: any[] | undefined;

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
  }
