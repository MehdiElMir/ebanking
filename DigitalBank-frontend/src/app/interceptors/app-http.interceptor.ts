import {Injectable} from "@angular/core";
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {catchError, Observable, throwError} from "rxjs";
import {AuthService} from "../services/auth.service";
import {Router} from "@angular/router";

@Injectable()
export class AppHttpInterceptor implements HttpInterceptor{


  constructor(private authService : AuthService,private router:Router) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (!request.url.includes("/auth/login")){
      let newRequest = request.clone(
        {headers: request.headers.set('Authorization','Bearer '+this.authService.accessToken)}
      );

      return next.handle(newRequest).pipe(
          catchError(err => {
            if(err.status == 401 || err.status == 401){
              this.authService.logout();
            }
            return throwError(err);
          })
      );
    } else return next.handle(request);

  }

}
