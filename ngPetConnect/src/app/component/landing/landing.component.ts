import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MypetsComponent } from '../mypets/mypets.component';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-landing',
  standalone: true,
  imports: [FormsModule, CommonModule, MypetsComponent, NgbModule],
  templateUrl: './landing.component.html',
  styleUrl: './landing.component.css'
})
export class LandingComponent {
  constructor(private activatedRoute: ActivatedRoute, private router: Router) {}


  images: string[] = [
    'assets/images/dog.png',
    'assets/images/Cat.png',
    'assets/images/Fish.png',
  ];
}
