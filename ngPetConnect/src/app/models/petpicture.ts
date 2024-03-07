import { Pet } from './pet';

export class PetPicture {
  id: number;
  imageUrl: string;
  caption: string;
  pet: Pet;

  constructor() {
    this.id = 0;
    this.imageUrl = '';
    this.caption = '';
    this.pet = new Pet();
  }
}
