import { Pet } from './pet';

export class PetPicture {
  id: number;
  imageUrl: string;
  caption: string;
  pet: Pet;
  datePosted: string;

  constructor(
    id: number = 0,
    imageUrl: string = '',
    caption: string = '',
    pet: Pet = new Pet(),
    datePosted: string = ''
  ) {
    this.id = id;
    this.imageUrl = imageUrl;
    this.caption = caption;
    this.pet = pet;
    this.datePosted = datePosted;
  }
}
