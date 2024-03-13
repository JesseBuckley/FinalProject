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
import { PostService } from '../../services/post.service';
import { Post } from '../../models/post';

@Component({
  selector: 'app-photos',
  standalone: true,
  imports: [HomeComponent, CommonModule, FormsModule, MypetsComponent],
  templateUrl: './photos.component.html',
  styleUrl: './photos.component.css',
})
export class PhotosComponent implements OnInit {
  petPictures: PetPicture[] = [];
  newPetPicture: PetPicture = new PetPicture();
  newPetId: number | any;
  myPets: Pet[] = [];
  newCaption: string | any;
  currentUser: any;
  errorMessage: string | undefined;
  newPost: Post = new Post();
  posts: Post[] = [];

  constructor(
    private authService: AuthService,
    private petPictureService: PetPictureService,
    private petService: PetService,
    private postService: PostService
  ) {}

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
    if (this.authService.checkLogin()) {
      this.petService.index().subscribe({
        next: (petList) => {
          this.myPets = petList;
        },
        error: (err: any) => {
          console.error('PetListComponent.loadPet: error');
          console.error(err);
        },
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
        },
      });
    }
  }

  addPetPicture(petPicture: PetPicture): void {
    this.petPictureService.create(petPicture, petPicture.pet.id).subscribe({
      next: (createdPetPicture) => {
        const defaultTitle = petPicture.caption ? `Pet picture: ${petPicture.caption}` : 'New pet picture post';
        this.petPictures.push(createdPetPicture);
        const newPostData: Partial<Post> = {
          title: defaultTitle,
          content: `Check out my pet: ${petPicture.caption}`,
          imageUrl: createdPetPicture.imageUrl,
        };

        this.postService.createPicturePost(newPostData).subscribe({
          next: (createdPost) => {
            console.log('Post created with pet picture', createdPost);

          },
          error: (err) => console.error('Error creating post with pet picture:', err)
        });
      },
      error: (err) => console.error('Error adding pet picture:', err)
    });
  }


  deletePetPicture(id: number): void {
    if (this.authService.checkLogin()) {
      this.petPictureService.delete(id).subscribe({
        next: () => {
          this.petPictures = this.petPictures.filter(
            (petPicture) => petPicture.id !== id
          );
        },
        error: (err) => {
          console.error('Error deleting pet picture:', err);
        },
      });
    }
  }

  getPetName(petId: number): string {
    const pet = this.myPets.find((pet) => pet.id === petId);
    return pet ? pet.name : 'Unknown';
  }
}
