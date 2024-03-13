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
import { NgbCarouselModule } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-photos',
  standalone: true,
  imports: [HomeComponent, CommonModule, FormsModule, MypetsComponent, NgbCarouselModule],
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
  isAdminMode: boolean = false;

  images: string[] = [
    'https://images2.alphacoders.com/687/687623.jpg',
    'https://w0.peakpx.com/wallpaper/25/190/HD-wallpaper-camera-funny-animals-squirrels.jpg',
    'https://s1.1zoom.me/big0/846/322242-alexfas01.jpg',
    'https://img.freepik.com/free-photo/animal-lizard-nature-multi-colored-close-up-generative-ai_188544-9072.jpg?w=1060&t=st=1710318674~exp=1710319274~hmac=27ebfeb8f8cb0af57cc224d81d020c3b7a2bffd3bf80829e4e82f420ed23c082',
    'https://c.wallhere.com/photos/c2/5d/pictures_camera_wallpaper_dog_pet_pets_colour_dogs-849056.jpg!d',
    'https://wallpapers.com/images/high/baby-tortoise-hitching-a-ride-on-its-mom-s-head-06uzms83eu4ph2h6.webp'
  ]

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
      next: (currentUser) => {
        this.currentUser = currentUser;
        this.isAdminMode = currentUser && currentUser.role === 'admin';
      },
      error: (err) =>
        (this.errorMessage = `Error loading current user: ${err}`),
    });
  }
  toggleViewMode(): void {
    this.isAdminMode = !this.isAdminMode;
    this.loadPetPictures();
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
      if (this.isAdminMode) {
        this.petPictureService.index().subscribe({
          next: (petPictures) => {
            this.petPictures = petPictures;
          },
          error: (err) => {
            console.error('Error loading pet pictures:', err);
          },
        });
      } else {
        this.petPictureService.index().subscribe({
          next: (petPictures) => {
            this.petPictures = petPictures.filter(petPicture =>
              petPicture.pet && petPicture.pet.user && petPicture.pet.user.id === this.currentUser.id
            );
          },
          error: (err) => {
            console.error('Error loading pet pictures:', err);
          },
        });
      }
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
