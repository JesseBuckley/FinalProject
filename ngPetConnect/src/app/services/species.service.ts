import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class SpeciesService {

  private url = environment.baseUrl + 'api/species';

  constructor(private http: HttpClient ,private auth: AuthService) {}

  getHttpOptions() {
    let options = {
      headers: {
        Authorization: 'Basic ' + this.auth.getCredentials(),
        'X-Requested-With': 'XMLHttpRequest',
      },
    };
    return options;
  }

  getAllSpecies(): Observable<any[]> {
    return this.http.get<any[]>(this.url, this.getHttpOptions());
  }
}
