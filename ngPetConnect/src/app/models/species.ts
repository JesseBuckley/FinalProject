import { Pet } from './pet';

export class Species {
  id: number;
  species: string;
  imageUrl: string;
  pets: Pet[];

  constructor(
    id: number = 0,
    species: string = '',
    imageUrl: string = '',
    pets: Pet[] = []
  ) {
    this.id = id;
    this.species = species;
    this.imageUrl = imageUrl;
    this.pets = pets;
  }
}
