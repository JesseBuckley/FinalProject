import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink, RouterModule } from '@angular/router';
import { MypetsComponent } from '../mypets/mypets.component';
import { CommonModule } from '@angular/common';
import { AppComponent } from '../../app.component';

@Component({
    selector: 'app-home',
    standalone: true,
    templateUrl: './home.component.html',
    styleUrl: './home.component.css',
    imports: [MypetsComponent, FormsModule, CommonModule,]
})
export class HomeComponent {


}
