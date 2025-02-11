import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TokenService {
  private _token:string | null = null;
  get token(): string | null {
    return this._token;
  }

  set token(value: string | null) {
    this._token = value;
    if (value) {
      localStorage.setItem('token', value);
    } else {
      localStorage.removeItem('token');
    }
  }

  loadToken(): void {
    this._token = localStorage.getItem('token');
  }

  clearToken(): void {
    this._token = null;
    localStorage.removeItem('token');
  }


  /* set token(token:string){
    localStorage.setItem('token',token);
  }
  get token():{
    return localStorage.getItem('token') as string;
  } */

}
