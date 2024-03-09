import { PetPicture } from "./petpicture";
import { Species } from "./species";
import { User } from "./user";

export class Pet {
  id: number = 0;
  name: string = '';
  dateOfBirth: string = '';
  breed: string = '';
  profilePicture: string = '';
  description: string = '';
  enabled: boolean = true;
  user: User = new User();
  petPictures: PetPicture[] = [];
  species: Species = new Species();

  constructor(
    id: number = 0, name: string = '', dateOfBirth: string = '', breed: string = '',
    profilePicture: string = '', description: string = '', enabled: boolean = true,
    user: User = new User(), petPictures: PetPicture[] = [], species: Species = new Species()) {
    this.id = this.id;
    this.name = this.name;
    this.dateOfBirth = this.dateOfBirth;
    this.breed = this.breed;
    this.profilePicture = this.profilePicture;
    this.description = this.description;
    this.enabled = this.enabled;
    this.user = this.user;
    this.petPictures = this.petPictures;
    this.species = this.species;
  }
}
