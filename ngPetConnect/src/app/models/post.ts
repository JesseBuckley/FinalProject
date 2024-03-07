import { Comment } from './comment';
import { Category } from "./category";
import { User } from "./user";

export class Post {
  id: number;
  content: string;
  imageUrl: string;
  createdAt: string;
  updatedAt: string;
  enabled: boolean;
  title: string;
  pinned: boolean;
  user: User;
  comments: Comment[];
  categories: Category[];

  constructor() {
    this.id = 0;
    this.content = '';
    this.imageUrl = '';
    this.createdAt = '';
    this.updatedAt = '';
    this.enabled = false;
    this.title = '';
    this.pinned = false;
    this.user = new User();
    this.comments = [];
    this.categories = [];
  }
}
