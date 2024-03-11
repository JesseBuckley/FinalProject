import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from '../../environments/environment';
import { AuthService } from './auth.service';
import { Post } from '../models/post';

@Injectable({
  providedIn: 'root',
})
export class PostService {

  private url = environment.baseUrl;

  constructor(
    private http: HttpClient,
    private auth: AuthService
  ) {}

  getHttpOptions() {
    let options = {
      headers: new HttpHeaders({
        Authorization: 'Basic ' + this.auth.getCredentials(),
        'X-Requested-With': 'XMLHttpRequest',
      }),
    };
    return options;
  }

  findAllPosts(): Observable<Post[]> {
    return this.http.get<Post[]>(`${this.url}api/posts`, this.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.error('Error retrieving posts', err);
        return throwError(() => new Error('PostService.findAllPosts(): error retrieving posts: ' + err));
      })
    );
  }

  findPostById(postId: number): Observable<Post> {
    return this.http.get<Post>(`${this.url}api/posts/${postId}`, this.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.error('Error retrieving post', err);
        return throwError(() => new Error('PostService.findPostById(): error retrieving post: ' + err));
      })
    );
  }

  createPost(post: Post): Observable<Post> {
    return this.http.post<Post>(`${this.url}api/posts`, post, this.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.error('Error creating post', err);
        return throwError(() => new Error('PostService.createPost(): error creating post: ' + err));
      })
    );
  }

  createPostWithFormData(formData: FormData): Observable<Post> {
    return this.http.post<Post>(this.url, formData, this.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.error('Error creating post with FormData', err);
        return throwError(() => new Error('PostService.createPostWithFormData(): error creating post with FormData: ' + err));
      })
    );
  }
  updatePost(postId: number, post: Post): Observable<Post> {
    return this.http.put<Post>(`${this.url}api/posts/${postId}`, post, this.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.error('Error updating post', err);
        return throwError(() => new Error('PostService.updatePost(): error updating post: ' + err));
      })
    );
  }

  deletePost(postId: number): Observable<void> {
    return this.http.delete<void>(`${this.url}api/posts/${postId}`, this.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.error('Error deleting post', err);
        return throwError(() => new Error('PostService.deletePost(): error deleting post: ' + err));
      })
    );
  }

  findPostsByCategory(catId: number): Observable<Post[]> {
    return this.http.get<Post[]>(`${this.url}api/categories/${catId}/posts`, this.getHttpOptions()).pipe(
      catchError((err: any) => throwError(() => new Error('Error retrieving posts by category: ' + err)))
    );
  }

  searchByKeyword(keyword: string): Observable<Post[]> {
    return this.http.get<Post[]>(`${this.url}api/posts/search/${keyword}`, this.getHttpOptions()).pipe(
      catchError((err: any) => throwError(() => new Error('Error searching posts by keyword: ' + err)))
    );
  }

  getPostsByUsersCity(city: string): Observable<Post[]> {
    return this.http.get<Post[]>(`${this.url}api/posts/city/${city}`, this.getHttpOptions()).pipe(
      catchError((err: any) => throwError(() => new Error('Error retrieving posts by city: ' + err)))
    );
  }

  getPostsByUsersState(state: string): Observable<Post[]> {
    return this.http.get<Post[]>(`${this.url}api/posts/state/${state}`, this.getHttpOptions()).pipe(
      catchError((err: any) => throwError(() => new Error('Error retrieving posts by state: ' + err)))
    );
  }

  getPostsByUsersZip(zip: string): Observable<Post[]> {
    return this.http.get<Post[]>(`${this.url}api/posts/zip/${zip}`, this.getHttpOptions()).pipe(
      catchError((err: any) => throwError(() => new Error('Error retrieving posts by zip: ' + err)))
    );
  }

  getPostsByUsername(username: string): Observable<Post[]> {
    return this.http.get<Post[]>(`${this.url}api/posts/username/${username}`, this.getHttpOptions()).pipe(
      catchError((err: any) => throwError(() => new Error('Error retrieving posts by username: ' + err)))
    );
  }

}
