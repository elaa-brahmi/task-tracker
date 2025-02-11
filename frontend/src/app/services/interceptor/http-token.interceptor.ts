import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpHeaders
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { TokenService } from '../token/token.service';
import { Router } from '@angular/router';

@Injectable()
export class HttpTokenInterceptor implements HttpInterceptor {

  constructor(
    private tokenService:TokenService,private router:Router
  ) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const token=this.tokenService.token;
    if(token){
      if(this.tokenService.isTokenValid()){
      //inject my authorization header inside my request
      const authReq=request.clone({
        headers:new HttpHeaders({
          Authorization:'Bearer '+token
        })
      });
      return next.handle(authReq);


    }
    else{
      this.tokenService.clearToken();
      this.router.navigate(['/login']);
    }}
    return next.handle(request);
  }
}
