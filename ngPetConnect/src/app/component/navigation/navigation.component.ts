import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import {
  NavigationStart,
  Router,
  RouterLink,
  RouterModule,
} from '@angular/router';

@Component({
  selector: 'app-navigation',
  standalone: true,
  imports: [RouterLink, CommonModule, RouterModule],
  templateUrl: './navigation.component.html',
  styleUrl: './navigation.component.css',
})
export class NavigationComponent {
  showHome: boolean = true;

  constructor(private router: Router) {
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationStart) {
        if (event.url === '/mypets') {
          this.showHome = false;
          console.log('Hiding Home, Showing my pets ' + this.showHome);
        } else if (event.url === '/home') {
          this.showHome = true;
          console.log('Showing Home, hiding my pets ' + this.showHome);
        }
      }
    });
  }
}
