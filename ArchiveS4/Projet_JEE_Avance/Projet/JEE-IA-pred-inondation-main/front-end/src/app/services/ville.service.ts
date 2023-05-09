import { Injectable } from '@angular/core';
import { Ville } from '../models/ville';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class VilleService {

  constructor(private http:HttpClient,) { }

  
  villes: Ville[]=[];
     
  getAllVilles():Observable<Ville[]>{
    return this.http.get<Ville[]>('http://localhost:3000/villes');
  }
}
