import { Resource } from './resource';
import { Post } from './post';

export class Category {
  id: number;
  type: string;
  resources: Resource[];
  posts: Post[];


  constructor(id: number = 0, type: string = '', resources: Resource[] = [], posts: Post[] = []) {
    this.id = id;
    this.type = type;
    this.resources = resources;
    this.posts = posts;
  }
}
