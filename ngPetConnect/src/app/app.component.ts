import { Component } from '@angular/core';
import { AuthService } from './services/auth.service';
import { HomeComponent } from './component/home/home.component';
import { MypetsComponent } from './component/mypets/mypets.component';
import { RouterLink, RouterModule } from '@angular/router';
import { NavigationComponent } from './component/navigation/navigation.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
  imports: [HomeComponent, MypetsComponent, NavigationComponent, RouterModule, RouterLink, CommonModule],
})
export class AppComponent {
  constructor(private auth: AuthService) {}

  ngOnInit() {
    this.tempTestDeleteMeLater(); // DELETE LATER!!!
  }

  tempTestDeleteMeLater() {
    this.auth.login('admin', 'test').subscribe({
      // change username to match DB
      next: (data) => {
        console.log('Logged in:');
        console.log(data);
      },
      error: (fail) => {
        console.error('Error authenticating:');
        console.error(fail);
      },
    });
  }
}
