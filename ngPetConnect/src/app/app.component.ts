import { Component } from '@angular/core';
import { HomeComponent } from './component/home/home.component';
import { MypetsComponent } from './component/mypets/mypets.component';
import { RouterLink, RouterModule } from '@angular/router';
import { NavigationComponent } from './component/navigation/navigation.component';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { LogoutComponent } from './component/logout/logout.component';
import { ngbCarouselTransitionIn } from '@ng-bootstrap/ng-bootstrap/carousel/carousel-transition';
import { NgbModal, NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { GoogleMapsModule } from '@angular/google-maps';


@Component({
  selector: 'app-root',
  standalone: true,
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
  imports: [
    HomeComponent,
    MypetsComponent,
    NavigationComponent,
    RouterModule,
    RouterLink,
    LogoutComponent,
    NgbModule,
    CommonModule,
    FormsModule,
    GoogleMapsModule
  ],
})
export class AppComponent {}
