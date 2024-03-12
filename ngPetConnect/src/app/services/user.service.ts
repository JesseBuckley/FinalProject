import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from '../../environments/environment';
import { AuthService } from './auth.service';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private url = environment.baseUrl + 'api/users';
  user: User = new User();
  constructor(
    private http: HttpClient,
    private auth: AuthService
  ) {}

  getHttpOptions() {
    let options = {
      headers: {
        Authorization: 'Basic ' + this.auth.getCredentials(),
        'X-Requested-With': 'XMLHttpRequest',
      },
    };
    return options;
  }

  findAllUsers(): Observable<User[]> {
    return this.http.get<User[]>(`${this.url}`, this.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.error('Error retrieving users', err);
        return throwError(() => new Error('UserService.findAllUsers(): error retrieving users: ' + err));
      })
    );
  }

  findUserById(userId: number): Observable<User> {
    return this.http.get<User>(`${this.url}/${userId}`, this.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.error('Error retrieving user', err);
        return throwError(() => new Error('UserService.findUserById(): error retrieving user: ' + err));
      })
    );
  }

  updateUser(userId: number, user: User): Observable<User> {
    return this.http.put<User>(`${this.url}/${userId}`, user, this.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.error('Error updating user', err);
        return throwError(() => new Error('UserService.updateUser(): error updating user: ' + err));
      })
    );
  }

  deleteUser(userId: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${userId}`, this.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.error('Error deleting user', err);
        return throwError(() => new Error('UserService.deleteUser(): error deleting user: ' + err));
      })
    );
  }

  enableUserAccount(userId: number): Observable<User> {
    return this.http.put<User>(`${this.url}/${userId}/enable`, {}, this.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.error('Error enabling user account', err);
        return throwError(() => new Error('UserService.enableUserAccount(): error enabling user account: ' + err));
      })
    );
  }

  disableUserAccount(userId: number): Observable<User> {
    return this.http.put<User>(`${this.url}/${userId}/disable`, {}, this.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.error('Error disabling user account', err);
        return throwError(() => new Error('UserService.disableUserAccount(): error disabling user account: ' + err));
      })
    );
  }

  getUser(userId: number): Observable<User> {
    return this.http.get<User>(`${this.url}/${userId}`, this.getHttpOptions());
  }

  getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.url, this.getHttpOptions());
  }

  followUser(userId: number): Observable<any> {
    const url = `${this.url}/${userId}/follow`;
    return this.http.post(url, null, this.getHttpOptions());
  }

  unfollowUser(userId: number): Observable<any> {
    const url = `${this.url}/${userId}/unfollow`;
    return this.http.delete(url, this.getHttpOptions());
  }
}
