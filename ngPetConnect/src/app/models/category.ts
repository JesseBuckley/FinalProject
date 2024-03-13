import { Resource } from './resource';
import { Post } from './post';

export class Category {
  id: number;
  type: string;
  resources: Resource[];
  posts: Post[];
  selected: boolean;


  constructor(id: number = 0, type: string = '', resources: Resource[] = [], posts: Post[] = [], selected: boolean = false) {
    this.id = id;
    this.type = type;
    this.resources = resources;
    this.posts = posts;
    this.selected = selected;
  }
}
