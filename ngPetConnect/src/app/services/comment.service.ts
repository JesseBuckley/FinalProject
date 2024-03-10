import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from '../../environments/environment';
import { AuthService } from './auth.service';
import { Comment } from '../models/comment';

@Injectable({
  providedIn: 'root',
})
export class CommentService {
  private baseUrl = environment.baseUrl;

  constructor(
    private http: HttpClient,
    private auth: AuthService
  ) {}

  getHttpOptions() {
    return {
      headers: new HttpHeaders({
        Authorization: 'Basic ' + this.auth.getCredentials(),
        'X-Requested-With': 'XMLHttpRequest',
      }),
    };
  }

  getAllCommentsForPost(postId: number): Observable<Comment[]> {
    return this.http.get<Comment[]>(`${this.baseUrl}api/${postId}/comments`, this.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.error('Error retrieving comments for post', err);
        return throwError(() => new Error('CommentService.getAllCommentsForPost(): error retrieving comments: ' + err));
      })
    );
  }

  addComment(postId: number, comment: Comment): Observable<Comment> {
    return this.http.post<Comment>(`${this.baseUrl}api/${postId}/comments`, comment, this.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.error('Error adding comment', err);
        return throwError(() => new Error('CommentService.addComment(): error adding comment: ' + err));
      })
    );
  }

  addReply(postId: number, parentCommentId: number, replyComment: Comment): Observable<Comment> {
    return this.http.post<Comment>(`${this.baseUrl}api/${postId}/comments/${parentCommentId}/reply`, replyComment, this.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.error('Error adding reply', err);
        return throwError(() => new Error('CommentService.addReply(): error adding reply: ' + err));
      })
    );
  }

  updateComment(postId: number, commentId: number, comment: Comment): Observable<Comment> {
    return this.http.put<Comment>(`${this.baseUrl}api/${postId}/comments/${commentId}`, comment, this.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.error('Error updating comment', err);
        return throwError(() => new Error('CommentService.updateComment(): error updating comment: ' + err));
      })
    );
  }

  deleteComment(postId: number, commentId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}api/${postId}/comments/${commentId}`, this.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.error('Error deleting comment', err);
        return throwError(() => new Error('CommentService.deleteComment(): error deleting comment: ' + err));
      })
    );
  }
}
