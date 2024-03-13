import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Pet } from '../../models/pet';
import { AuthService } from '../../services/auth.service';
import { PetPictureService } from '../../services/pet-picture.service';
import { MypetsComponent } from '../mypets/mypets.component';
import { PetPicture } from './../../models/petpicture';
import { PetService } from './../../services/pet.service';
import { HomeComponent } from './../home/home.component';

@Component({
  selector: 'app-photos',
  standalone: true,
  imports: [HomeComponent, CommonModule, FormsModule, MypetsComponent],
  templateUrl: './photos.component.html',
  styleUrl: './photos.component.css'
})
export class PhotosComponent implements OnInit {
  petPictures: PetPicture[] = [];
  newPetPicture: PetPicture = new PetPicture();
  newPetId: number | any;
  myPets: Pet[] = [];
  newCaption: string | any;
  currentUser: any;
  errorMessage: string | undefined;

  constructor(private authService: AuthService, private petPictureService: PetPictureService, private petService: PetService) { }

  ngOnInit(): void {
    this.loadPetPictures();
    this.loadPets();
    this.getLoggedInUser();
  }


  getLoggedInUser() {
    this.authService.getLoggedInUser().subscribe({
      next: (currentUser) => (this.currentUser = currentUser),
      error: (err) =>
        (this.errorMessage = `Error loading current user: ${err}`),
    });
  }

  loadPets() {
    if(this.authService.checkLogin()) {
      this.petService.index().subscribe( {
        next: (petList) => {
          this.myPets = petList;
        },
        error: (err: any) => {
          console.error('PetListComponent.loadPet: error');
          console.error(err);
        }
      });
    }
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

  addPetPicture(petPicture: PetPicture): void {
    this.petPictureService.create(petPicture, petPicture.pet.id).subscribe({
      next: (createdPetPicture) => {
        this.loadPetPictures();
        this.newPetPicture = new PetPicture();
        },
        error: (err) => {
          console.error('Error adding pet picture:', err);
        }
      });

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

  getPetName(petId: number): string {
    const pet = this.myPets.find(pet => pet.id === petId);
    return pet ? pet.name : 'Unknown';
  }


}

