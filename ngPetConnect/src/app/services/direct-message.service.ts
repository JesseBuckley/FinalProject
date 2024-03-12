import { UserService } from './user.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { AuthService } from './auth.service';
import { Observable, catchError, throwError } from 'rxjs';
import { DirectMessage } from '../models/directmessage';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root',
})
export class DirectMessageService {
  private url = environment.baseUrl;

  constructor(
    private http: HttpClient,
    private auth: AuthService,
    private userService: UserService
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

  getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>(`${this.url}/users`);
  }

  findAll(): Observable<DirectMessage[]> {
    return this.http
      .get<DirectMessage[]>(
        `${this.url}api/directMessages`,
        this.getHttpOptions()
      )
      .pipe(
        catchError((err: any) => {
          console.error('Error retrieving direct messages', err);
          return throwError(
            () =>
              new Error(
                'DirectMessageService.findAll(): error retrieving direct messages: ' +
                  err
              )
          );
        })
      );
  }

  findById(dmId: number): Observable<DirectMessage> {
    return this.http
      .get<DirectMessage>(
        `${this.url}api/directMessages/${dmId}`,
        this.getHttpOptions()
      )
      .pipe(
        catchError((err: any) => {
          console.error('Error retrieving direct message', err);
          return throwError(
            () =>
              new Error(
                'DirectMessageService.findById(): error retrieving direct message: ' +
                  err
              )
          );
        })
      );
  }

  create(
    directMessage: DirectMessage,
    recipientId: number
  ): Observable<DirectMessage> {
    return this.http
      .post<DirectMessage>(
        `${this.url}api/users/${recipientId}/directMessages`,
        directMessage,
        this.getHttpOptions()
      )
      .pipe(
        catchError((err: any) => {
          console.error('Error creating direct message', err);
          return throwError(
            () =>
              new Error(
                'DirectMessageService.create(): error creating direct message: ' +
                  err
              )
          );
        })
      );
  }

  update(
    dmId: number,
    directMessage: DirectMessage
  ): Observable<DirectMessage> {
    return this.http
      .put<DirectMessage>(
        `${this.url}api/users/${dmId}/directMessages`,
        directMessage,
        this.getHttpOptions()
      )
      .pipe(
        catchError((err: any) => {
          console.error('Error updating direct message', err);
          return throwError(
            () =>
              new Error(
                'DirectMessageService.update(): error updating direct message: ' +
                  err
              )
          );
        })
      );
  }

  deleteById(dmId: number): Observable<void> {
    return this.http
      .delete<void>(
        `${this.url}api/users/${dmId}/directMessages`,
        this.getHttpOptions()
      )
      .pipe(
        catchError((err: any) => {
          console.error('Error deleting direct message', err);
          return throwError(
            () =>
              new Error(
                'DirectMessageService.deleteById(): error deleting direct message: ' +
                  err
              )
          );
        })
      );
  }
}
