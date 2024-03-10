import { Post } from "./post";
import { User } from "./user";

export class Comment {
  id: number;
  content: string;
  enabled: boolean;
  createdAt: string;
  updatedAt: string;
  user: User;
  post: Post;
  replies: Comment [];

  constructor() {
    this.id = 0;
    this.content = '';
    this.enabled = false;
    this.createdAt = '';
    this.updatedAt = '';
    this.user = new User();
    this.post = new Post();
    this.replies = [];
  }
}
