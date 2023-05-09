import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor() { }

isLoggedIn(): boolean {
  // code pour vérifier si l'utilisateur est connecté
  if (localStorage.getItem('auth-token') === null) {
    return false
  } else {
    return true;
  }
 
}

}