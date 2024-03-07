import { User } from './user';
import { Resource } from './resource';

export class Address {
  id: number;
  street: string;
  city: string;
  state: string;
  zip: string;
  users: User[];
  resources: Resource[];

  constructor(
    id: number = 0,
    street: string = '',
    city: string = '',
    state: string = '',
    zip: string = '',
    users: User[] = [],
    resources: Resource[] = []
  ) {
    this.id = id;
    this.street = street;
    this.city = city;
    this.state = state;
    this.zip = zip;
    this.users = users;
    this.resources = resources;
  }
}
