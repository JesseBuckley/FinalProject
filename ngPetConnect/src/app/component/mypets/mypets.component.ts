import { SpeciesService } from './../../services/species.service';
import { AuthService } from './../../services/auth.service';
import { PetService } from './../../services/pet.service';
import { Component, OnInit } from '@angular/core';
import { HomeComponent } from '../home/home.component';
import { FormsModule, NgModel } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Pet } from '../../models/pet';
import { Species } from '../../models/species';



@Component({
  selector: 'app-mypets',
  standalone: true,
  imports: [HomeComponent, FormsModule, CommonModule],
  templateUrl: './mypets.component.html',
  styleUrl: './mypets.component.css'
})
export class MypetsComponent implements OnInit {

  isEditing: boolean = false;
  newPet: any = new Pet();
  selected: Pet | null = null;
  editPet: Pet | null = null;
  pet: Pet[] = [];
  addPetClicked: boolean = false;
  speciesList: any[] = [];
  selectedSpeciesId: number | undefined;

  constructor(private petService: PetService, private authService: AuthService, private speciesService: SpeciesService) {}

cancelAdd() {
this.addPetClicked = false;
}

cancelEdit() {
this.isEditing = false;
this.editPet = null;
}

fetchSpecies() {
  this.speciesService.getAllSpecies().subscribe(
    (data: any[]) => {
      this.speciesList = data;
    },
    (error) => {
      console.error('error fetching species:', error);
    }
    );
}


  ngOnInit(): void {
    this.loadPets();
    this.fetchSpecies();
  }

  loadPets() {
    if(this.authService.checkLogin()) {
      this.petService.index().subscribe( {
        next: (petList) => {
          this.pet = petList;
          console.log(this.pet);
        },
        error: (err: any) => {
          console.error('PetListComponent.loadPet: error');
          console.error(err);
        }
      });
    }
}

addPets() {
  if(this.authService.checkLogin()) {
  this.petService.create(this.newPet).subscribe(
    (createdPet) => {
      this.pet.push(createdPet);
      this.newPet = new Pet();
      this.loadPets();
    },
    (error) => {
      console.error('Error creating pet:', error);
    }
  );
  }
}

deletePet(id: number) {
  if(this.authService.checkLogin()) {
    this.petService.destroy(id).subscribe(
      () => {
        this.pet = this.pet.filter((pet) => pet.id !== id);
        console.log('Delete of pet id: ' + id + ' successful')
    },
    (error) => {
      console.error('Error deleting pet:', error);
    }
  );
  }
}

updatePets() {
  if(this.authService.checkLogin()) {
  if (this.editPet) {
    this.petService.update(this.editPet).subscribe(
      () => {
        this.isEditing = false;
        this.editPet = null;
        this.loadPets();
      },
      (error) => {
        console.error('Error updating pets:', error);
      }
    );
  }
}
}

displayPets(item: Pet): void {
  this.selected = item;
}

displayTable(): void {
  this.selected = null;
}

setEditPets(pet: Pet) {
  this.isEditing = true;
  this.editPet = Object.assign({}, this.selected);
}
  toggleAddPet() {
    this.addPetClicked = !this.addPetClicked;
  }
}
