import { HomeComponent } from './../home/home.component';
import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { PetPicture } from '../../models/petpicture';
import { AuthService } from '../../services/auth.service';
import { PetPictureService } from '../../services/pet-picture.service';

@Component({
  selector: 'app-photos',
  standalone: true,
  imports: [HomeComponent, CommonModule, FormsModule],
  templateUrl: './photos.component.html',
  styleUrl: './photos.component.css'
})
export class PhotosComponent implements OnInit {
  petPictures: PetPicture[] = [];
  newPetPicture: PetPicture = new PetPicture();

  constructor(private authService: AuthService, private petPictureService: PetPictureService) { }

  ngOnInit(): void {
    this.loadPetPictures();
  }

  loadPetPictures(): void {
    if (this.authService.checkLogin()) {
      this.petPictureService.index().subscribe({
        next: (petPictures) => {
          this.petPictures = petPictures;
        },
        error: (err) => {
          console.error('Error loading pet pictures:', err);
        }
      });
    }
  }

  addPetPicture(): void {
    if (this.authService.checkLogin()) {
      this.petPictureService.create(this.newPetPicture).subscribe({
        next: (createdPetPicture) => {
          this.petPictures.push(createdPetPicture);
          this.newPetPicture = new PetPicture();
        },
        error: (err) => {
          console.error('Error adding pet picture:', err);
        }
      });
    }
  }

  deletePetPicture(id: number): void {
    if (this.authService.checkLogin()) {
      this.petPictureService.delete(id).subscribe({
        next: () => {
          this.petPictures = this.petPictures.filter((petPicture) => petPicture.id !== id);
        },
        error: (err) => {
          console.error('Error deleting pet picture:', err);
        }
      });
    }
  }

}

