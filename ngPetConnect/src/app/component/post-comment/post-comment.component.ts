import { Component, OnInit } from '@angular/core';
import { PostService } from '../../services/post.service';
import { CommentService } from '../../services/comment.service';
import { Post } from '../../models/post';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Comment } from '../../models/comment';
import { CategoryService } from '../../services/category.service';
import { Category } from '../../models/category';

@Component({
  selector: 'app-post-comment',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './post-comment.component.html',
  styleUrl: './post-comment.component.css',
})
export class PostCommentComponent implements OnInit {
  posts: Post[] = [];
  errorMessage: string | undefined;
  categories: Category[] = [];

  constructor(
    private postService: PostService,
    private comService: CommentService,
    private categoryService: CategoryService
  ) {}

  ngOnInit(): void {
    this.loadAllPosts();
    this.loadCategories();
  }

  loadCommentsForPosts(): void {
    this.posts.forEach((post) => {
      this.comService.getAllCommentsForPost(post.id).subscribe({
        next: (comments) => (post.comments = comments),
        error: (err) =>
          console.error(`Error loading comments for post ${post.id}:`, err),
      });
    });
  }

  loadAllPosts(): void {
    this.postService.findAllPosts().subscribe({
      next: (posts) => {
        this.posts = posts;
        // Load comments for each post
        this.posts.forEach((post) => {
          this.comService.getAllCommentsForPost(post.id).subscribe({
            next: (comments) => (post.comments = comments),
            error: (err) =>
              console.error(`Error loading comments for post ${post.id}:`, err),
          });
        });
      },
      error: (err) => (this.errorMessage = `Error loading posts: ${err}`),
    });
  }

  addComment(postId: number, content: string): void {
    let newComment = new Comment();
    newComment.content = content;

    this.comService.addComment(postId, newComment).subscribe(
      (comment) => {
        const post = this.posts.find((p) => p.id === postId);
        if (post) {
          if (!post.comments) {
            post.comments = [];
          }
          post.comments.push(comment);
        }
      },
      (error) => console.error('Error adding comment:', error)
    );
  }

  addReply(postId: number, parentCommentId: number, content: string): void {
    const replyComment = new Comment();
    replyComment.content = content;
    this.comService.addReply(postId, parentCommentId, replyComment).subscribe(
      (comment) => {
        const post = this.posts.find((p) => p.id === postId);
        if (post) {
          const parentComment = post.comments.find(
            (c) => c.id === parentCommentId
          );
          if (parentComment) {
            if (parentComment.replies) {
              parentComment.replies.push(comment);
            } else {
              parentComment.replies = [comment];
            }
          }
        }
      },
      (error) => console.error('Error adding reply:', error)
    );
  }

  updateComment(postId: number, commentId: number, content: string): void {
    const updatedComment = new Comment();
    updatedComment.content = content;
    this.comService.updateComment(postId, commentId, updatedComment).subscribe(
      (comment) => {
        const post = this.posts.find((p) => p.id === postId);
        if (post) {
          const index = post.comments.findIndex((c) => c.id === commentId);
          if (index !== -1) {
            post.comments[index] = comment;
          }
        }
      },
      (error) => console.error('Error updating comment:', error)
    );
  }

  deleteComment(postId: number, commentId: number): void {
    this.comService.deleteComment(postId, commentId).subscribe(
      () => {
        const post = this.posts.find((p) => p.id === postId);
        if (post) {
          const index = post.comments.findIndex((c) => c.id === commentId);
          if (index !== -1) {
            post.comments.splice(index, 1);
          }
        }
      },
      (error) => console.error('Error deleting comment:', error)
    );
  }

  createPost(content: string): void {
    const newPost = new Post();
    newPost.content = content;
    this.postService.createPost(newPost).subscribe(
      (post) => {
        this.posts.push(post);
      },
      (error) => console.error('Error creating post:', error)
    );
  }

  updatePost(postId: number, content: string): void {
    const updatedPost = new Post();
    updatedPost.content = content;
    this.postService.updatePost(postId, updatedPost).subscribe(
      (post) => {
        const index = this.posts.findIndex((p) => p.id === postId);
        if (index !== -1) {
          this.posts[index] = post;
        }
      },
      (error) => console.error('Error updating post:', error)
    );
  }

  deletePost(postId: number): void {
    this.postService.deletePost(postId).subscribe(
      () => {
        this.posts = this.posts.filter((p) => p.id !== postId);
      },
      (error) => console.error('Error deleting post:', error)
    );
  }

  loadCategories(): void {
    this.categoryService.getAllCategories().subscribe({
      next: (categories) => {
        this.categories = categories;
      },
      error: (err) => {
        this.errorMessage = `Error loading categories: ${err}`;
      },
    });
  }

  searchByKeyword(keyword: string): void {
    this.postService.searchByKeyword(keyword).subscribe({
      next: (posts) => {
        this.posts = posts;
        this.loadCommentsForPosts();
      },
      error: (err) =>
        (this.errorMessage = `Error searching posts by keyword: ${err}`),
    });
  }

  getPostsByUsersCity(city: string): void {
    this.postService.getPostsByUsersCity(city).subscribe({
      next: (posts) => {
        this.setPostsAndLoadComments(posts);
      },
      error: (err) => {
        console.error(`Error retrieving posts by city: ${err}`);
        this.errorMessage = `Error retrieving posts by city: ${err}`;
      },
    });
  }
  getPostsByUsersState(state: string): void {
    this.postService.getPostsByUsersState(state).subscribe({
      next: (posts) => {
        this.setPostsAndLoadComments(posts);
      },
      error: (err) => {
        console.error(`Error retrieving posts by state: ${err}`);
        this.errorMessage = `Error retrieving posts by state: ${err}`;
      },
    });
  }
  getPostsByUsersZip(zip: string): void {
    this.postService.getPostsByUsersZip(zip).subscribe({
      next: (posts) => {
        this.setPostsAndLoadComments(posts);
      },
      error: (err) => {
        console.error(`Error retrieving posts by zip: ${err}`);
        this.errorMessage = `Error retrieving posts by zip: ${err}`;
      },
    });
  }
  getPostsByUsername(username: string): void {
    this.postService.getPostsByUsername(username).subscribe({
      next: (posts) => {
        this.setPostsAndLoadComments(posts);
      },
      error: (err) => {
        console.error(`Error retrieving posts by username: ${err}`);
        this.errorMessage = `Error retrieving posts by username: ${err}`;
      },
    });
  }

  filterByLocation(location: string): void {
    if (location.length === 5 && !isNaN(+location)) {
      this.getPostsByUsersZip(location);
    } else if (location.length === 2) {
      this.getPostsByUsersState(location);
    } else {
      this.getPostsByUsersCity(location);
    }
    this.posts.forEach((post) => {
      this.comService.getAllCommentsForPost(post.id).subscribe({
        next: (comments) => (post.comments = comments),
        error: (err) =>
          console.error(`Error loading comments for post ${post.id}:`, err),
      });
    });
  }

  findPostsByCategory(catId: string): void {
    const categoryId = Number(catId); // Convert the selected category ID to a number
    if (!isNaN(categoryId)) {
      this.postService.findPostsByCategory(categoryId).subscribe({
        next: (posts) => {
          this.posts = posts;
          this.posts.forEach((post) => {
            this.comService.getAllCommentsForPost(post.id).subscribe({
              next: (comments) => (post.comments = comments),
              error: (err) =>
                console.error(
                  `Error loading comments for post ${post.id}:`,
                  err
                ),
            });
          });
        },
        error: (err) => {
          console.error(
            `Error retrieving posts by category ${categoryId}:`,
            err
          );
          this.errorMessage = `Error retrieving posts by category: ${err}`;
        },
      });
    } else {
      console.error('Invalid category ID:', catId);
      this.errorMessage = 'Invalid category ID.';
    }
  }
  setPostsAndLoadComments(posts: Post[]): void {
    this.posts = posts;
    this.loadCommentsForPosts();
  }
}
