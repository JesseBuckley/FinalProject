import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { AuthService } from './auth.service';
import { Observable, catchError, throwError } from 'rxjs';
import { Resource } from '../models/resource';

@Injectable({
  providedIn: 'root',
})
export class ResourceService {
  private url = environment.baseUrl;

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

  getAllResources(): Observable<Resource[]> {
    return this.http.get<Resource[]>(`${this.url}api/resources`, this.getHttpOptions()).pipe(
      catchError((err: any) => throwError(() => new Error('ResourceService.getAllResources(): error retrieving resources: ' + err)))
    );
  }

  getResourceById(resourceId: number): Observable<Resource> {
    return this.http.get<Resource>(`${this.url}api/resources/${resourceId}`, this.getHttpOptions()).pipe(
      catchError((err: any) => throwError(() => new Error('ResourceService.getResourceById(): error retrieving resource: ' + err)))
    );
  }

  createResource(resource: Resource): Observable<Resource> {
    return this.http.post<Resource>(`${this.url}api/resources`, resource, this.getHttpOptions()).pipe(
      catchError((err: any) => throwError(() => new Error('ResourceService.createResource(): error creating resource: ' + err)))
    );
  }

  updateResource(resourceId: number, resource: Resource): Observable<Resource> {
    return this.http.put<Resource>(`${this.url}api/resources/${resourceId}`, resource, this.getHttpOptions()).pipe(
      catchError((err: any) => throwError(() => new Error('ResourceService.updateResource(): error updating resource: ' + err)))
    );
  }

  deleteResource(resourceId: number): Observable<void> {
    return this.http.delete<void>(`${this.url}api/resources/${resourceId}`, this.getHttpOptions()).pipe(
      catchError((err: any) => throwError(() => new Error('ResourceService.deleteResource(): error deleting resource: ' + err)))
    );
  }

  getResourcesByCategory(categoryId: number): Observable<Resource[]> {
    return this.http.get<Resource[]>(`${this.url}api/resources/category/${categoryId}`, this.getHttpOptions()).pipe(
      catchError((err: any) => throwError(() => new Error('Error retrieving resources by category: ' + err)))
    );
  }

  searchResourcesByKeyword(keyword: string): Observable<Resource[]> {
    return this.http.get<Resource[]>(`${this.url}api/resources/search/${keyword}`, this.getHttpOptions()).pipe(
      catchError((err: any) => throwError(() => new Error('Error searching resources by keyword: ' + err)))
    );
  }

  findResourcesByCity(city: string): Observable<Resource[]> {
    return this.http.get<Resource[]>(`${this.url}api/resources/city/${city}`, this.getHttpOptions()).pipe(
      catchError((err: any) => throwError(() => new Error('Error retrieving resources by city: ' + err)))
    );
  }

  findResourcesByState(state: string): Observable<Resource[]> {
    return this.http.get<Resource[]>(`${this.url}api/resources/state/${state}`, this.getHttpOptions()).pipe(
      catchError((err: any) => throwError(() => new Error('Error retrieving resources by state: ' + err)))
    );
  }

  findResourcesByZip(zip: string): Observable<Resource[]> {
    return this.http.get<Resource[]>(`${this.url}api/resources/zip/${zip}`, this.getHttpOptions()).pipe(
      catchError((err: any) => throwError(() => new Error('Error retrieving resources by zip: ' + err)))
    );
  }


}
