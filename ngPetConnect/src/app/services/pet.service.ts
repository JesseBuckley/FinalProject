import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { Pet } from '../models/pet';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { AuthService } from './auth.service';
import { UserService } from './user.service';

@Injectable({
  providedIn: 'root'
})
export class PetService {
  private url = environment.baseUrl + 'api/pets';

  constructor(
    private http: HttpClient,
    private auth: AuthService,
    private userService: UserService
  ) {
  }

  getHttpOptions() {
    let options = {
      headers: {
        Authorization: 'Basic ' + this.auth.getCredentials(),
        'X-Requested-With': 'XMLHttpRequest',
      },
    };
    return options;
  }

  index(): Observable<Pet[]> {
    return this.http.get<Pet[]>(this.url, this.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError(
          () => new Error('PetService.index(): error retrieving pet: ' + err)
        );
      })
    );
  }
  create(pet: Pet): Observable<Pet> {
    return this.http.post<Pet>(this.url, pet, this.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.error('PetService.create(): error creating pet', err);
        return throwError(
          () => new Error('Error creating pet: ' + err.message)
        );
      })
    );
  }

  update(pet: Pet): Observable<Pet> {
    const updateUrl = `${this.url}/${pet.id}/update`;
    return this.http.put<Pet>(updateUrl, pet, this.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.error('petService.update(): error updating pet', err);
        return throwError(
          () => new Error('Error updating pet: ' + err.message)
        );
      })
    );
  }

  destroy(id: number): Observable<any> {
    const deleteUrl = `${this.url}/${id}/delete`;
    return this.http.delete(deleteUrl, this.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.error('petService.destroy(): error deleting pet', err);
        return throwError(
          () => new Error('Error deleting pet: ' + err.message)
        );
      })
    );
  }
}
