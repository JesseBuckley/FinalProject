import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { environment } from '../../environments/environment';
import { PetPicture } from '../models/petpicture';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class PetPictureService {
  private url = environment.baseUrl + 'api/pet-pictures';

  constructor(
    private http: HttpClient,
    private auth: AuthService
  ) {}

  getHttpOptions() {
    return {
      headers: {
        Authorization: 'Basic ' + this.auth.getCredentials(),
        'X-Requested-With': 'XMLHttpRequest',
      },
    };
  }

  index(): Observable<PetPicture[]> {
    return this.http.get<PetPicture[]>(this.url, this.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.error('PetPictureService.index(): error retrieving pet pictures:', err);
        return throwError(() => new Error('PetPictureService.index(): error retrieving pet pictures'));
      })
    );
  }

  create(petPicture: PetPicture, petId: number): Observable<PetPicture> {
    return this.http.post<PetPicture>(`${this.url}/${petId}`, petPicture, this.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.error('PetPictureService.create(): error creating pet picture:', err);
        return throwError(() => new Error('PetPictureService.create(): error creating pet picture'));
      })
    );
  }

  delete(id: number): Observable<any> {
    const deleteUrl = `${this.url}/${id}/delete`;

    return this.http.delete(deleteUrl, this.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.error('PetPictureService.delete(): error deleting pet picture:', err);
        return throwError(() => new Error('PetPictureService.delete(): error deleting pet picture'));
      })
    );
  }
}
