import { User } from './../../models/user';
import { Component, OnInit } from '@angular/core';
import { PostService } from '../../services/post.service';
import { CommentService } from '../../services/comment.service';
import { Post } from '../../models/post';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Comment } from '../../models/comment';
import { CategoryService } from '../../services/category.service';
import { Category } from '../../models/category';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-post-comment',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './post-comment.component.html',
  styleUrl: './post-comment.component.css',
})
export class PostCommentComponent implements OnInit {
  editCommentId: number | null = null;
  posts: Post[] = [];
  errorMessage: string | undefined;
  categories: Category[] = [];
  editingPost: { [key: number]: boolean } = {};
  editingComment: { [key: number]: boolean } = {};
  currentUser: any;
  categoryId: any;

  constructor(
    private postService: PostService,
    private comService: CommentService,
    private categoryService: CategoryService,
    private auth: AuthService
  ) {}

  ngOnInit(): void {
    this.loadAllPosts();
    this.loadCategories();
    this.getLoggedInUser();
  }
  //****************************************** Loading ..... ******************************************
  getLoggedInUser() {
    this.auth.getLoggedInUser().subscribe({
      next: (currentUser) => (this.currentUser = currentUser),
      error: (err) =>
        (this.errorMessage = `Error loading current user: ${err}`),
    });
  }

  loadCategories(): void {
    this.categoryService.getAllCategories().subscribe({
      next: (categories) => (this.categories = categories),
      error: (err) => (this.errorMessage = `Error loading categories: ${err}`),
    });
  }

  loadAllPosts(): void {
    this.postService.findAllPosts().subscribe({
      next: (posts) => {
        this.posts = posts.sort(
          (a, b) =>
            new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime()
        );
        this.posts.forEach((post) => {
          post.comments = post.comments.sort(
            (a, b) =>
              new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime()
          );
        });
      },
      error: (err) => {
        this.errorMessage = `Error loading posts: ${err}`;
      },
    });
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
  setPostsAndLoadComments(posts: Post[]): void {
    this.posts = posts;
    this.loadCommentsForPosts();
  }
  //****************************************** Post CRUD ******************************************
  createPost(
    content: string,
    imageUrl: string,
    categoryId: string,
    postTextarea: HTMLTextAreaElement,
    imageUrlInput: HTMLInputElement,
    categorySelect: HTMLSelectElement
  ): void {
    if (!content.trim()) return;
    const newPost = new Post();
    newPost.content = content;
    newPost.imageUrl = imageUrl;
    // Convert categoryId to a number if necessary
    newPost.categories = this.categoryId;

    this.postService.createPost(newPost).subscribe(
      (post) => {
        this.posts.unshift(post); // Add the new post at the beginning of the list
        postTextarea.value = ''; // Clear the post content textarea
        imageUrlInput.value = ''; // Clear the image URL input
        categorySelect.value = ''; // Reset the category select to its default state
      },
      (error) => console.error('Error creating post:', error)
    );
  }

  startEditPost(postId: number): void {
    this.editingPost[postId] = !this.editingPost[postId];
  }
  updatePost(postId: number, updatedPost: Post): void {
    this.postService.updatePost(postId, updatedPost).subscribe({
      next: (post) => {
        const index = this.posts.findIndex((p) => p.id === postId);
        if (index !== -1) {
          this.posts[index] = post;
        }
      },
      error: (error) => console.error('Error updating post:', error),
    });
  }
  finishEditPost(postId: number, content: string): void {
    const originalPost = this.posts.find((p) => p.id === postId);
    if (!originalPost || !this.editingPost[postId]) return;

    const updatedPost: Post = {
      ...originalPost,
      content: content,
      id: originalPost.id,
    };

    this.postService.updatePost(postId, updatedPost).subscribe({
      next: () => {
        this.editingPost[postId] = false;
        this.loadAllPosts();
      },
      error: (error) => console.error('Error updating post:', error),
    });
  }
  deletePost(postId: number): void {
    this.postService.deletePost(postId).subscribe(
      () => {
        this.posts = this.posts.filter((p) => p.id !== postId);
      },
      (error) => console.error('Error deleting post:', error)
    );
  }
  //****************************************** Comment CRUD ******************************************
  addComment(postId: number, content: string, input: HTMLInputElement): void {
    let newComment = new Comment();
    newComment.content = content;
    this.comService.addComment(postId, newComment).subscribe(
      (comment) => {
        this.loadCommentsForPosts();
        input.value = '';
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

  startEditComment(postId: number, commentId: number): void {
    this.editingComment[commentId] = !this.editingComment[commentId];
  }

  updateComment(
    postId: number,
    commentId: number,
    updatedContent: string
  ): void {
    let updatedComment = new Comment();
    updatedComment.content = updatedContent;

    this.comService.updateComment(postId, commentId, updatedComment).subscribe({
      next: (response) => {
        console.log('Comment updated successfully');
        this.loadCommentsForPosts();
      },
      error: (error) => console.error('Error updating comment:', error),
    });
  }

  finishEditComment(postId: number, commentId: number, content: string): void {
    if (!this.editingComment[commentId]) return;

    const updatedComment = new Comment();
    updatedComment.content = content;
    this.comService.updateComment(postId, commentId, updatedComment).subscribe({
      next: () => {
        this.editingComment[commentId] = false;
        this.loadAllPosts();
      },
      error: (error) => console.error('Error updating comment:', error),
    });
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
  //****************************************** Filtering Posts ******************************************
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
    const categoryId = Number(catId);
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
  //****************************************** Helpers ******************************************
  isUserPost(post: Post): boolean {
    return this.currentUser && post.user.id === this.currentUser.id;
  }

  isUserComment(comment: Comment): boolean {
    return this.currentUser && comment.user.id === this.currentUser.id;
  }
}
