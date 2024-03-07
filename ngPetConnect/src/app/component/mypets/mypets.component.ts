import { Component } from '@angular/core';
import { HomeComponent } from '../home/home.component';



@Component({
  selector: 'app-mypets',
  standalone: true,
  imports: [HomeComponent],
  templateUrl: './mypets.component.html',
  styleUrl: './mypets.component.css'
})
export class MypetsComponent {

}
