import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';
import { environment } from '../../environments/environment';
import { Category } from '../models/category'; // Ensure you have a Category model
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root',
})
export class CategoryService {
  private url = environment.baseUrl + 'api/categories';

  constructor(private http: HttpClient, private auth: AuthService) {}

  getHttpOptions() {
    let options = {
      headers: new HttpHeaders({
        Authorization: 'Basic ' + this.auth.getCredentials(),
        'X-Requested-With': 'XMLHttpRequest',
      }),
    };
    return options;
  }

  getAllCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(`${this.url}`, this.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.error('Error retrieving categories', err);
        return throwError(
          () =>
            new Error(
              'CategoryService.getAllCategories(): error retrieving categories: ' +
                err
            )
        );
      })
    );
  }
}
