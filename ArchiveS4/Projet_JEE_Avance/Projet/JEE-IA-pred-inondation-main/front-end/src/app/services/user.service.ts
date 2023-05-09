import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { User } from '../models/user';
import { Token } from '../models/token';


@Injectable({
  providedIn: 'root'
})
export class UserService {


  constructor(
    private http: HttpClient,
    ) { }

  
  signUp(user : User){ 
    return this.http.post('api/signup', user);
  }
  
  login(email:any, password:any):Observable<Token>{

    localStorage.clear()
    let params = new HttpParams().set('email', email).set('password', password);

    return this.http.post<Token>('api/authenticate', params)
    .pipe(
      tap(res => localStorage.setItem('auth-token', res.accessToken)),
          );
  }


}
