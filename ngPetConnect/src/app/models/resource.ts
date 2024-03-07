import { Category } from './category';
import { Address } from './address';

export class Resource {
  id: number;
  name: string;
  description: string;
  imageUrl: string;
  category: Category;
  address: Address;

  constructor(
    id: number = 0,
    name: string = '',
    description: string = '',
    imageUrl: string = '',
    category: Category = new Category(),
    address: Address = new Address()
  ) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.imageUrl = imageUrl;
    this.category = category;
    this.address = address;
  }
}
