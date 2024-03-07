import { PetPicture } from "./petpicture";
import { Species } from "./species";
import { User } from "./user";

export class Pet {
  id: number;
  name: string;
  dateOfBirth: string;
  breed: string;
  profilePicture: string;
  description: string;
  enabled: boolean;
  user: User;
  petPictures: PetPicture[];
  species: Species;

  constructor() {
    this.id = 0;
    this.name = '';
    this.dateOfBirth = '';
    this.breed = '';
    this.profilePicture = '';
    this.description = '';
    this.enabled = false;
    this.user = new User();
    this.petPictures = [];
    this.species = new Species();
  }
}
