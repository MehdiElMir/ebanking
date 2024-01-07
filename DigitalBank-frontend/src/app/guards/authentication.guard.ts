import {
  ActivatedRouteSnapshot,
  CanActivate,
  CanActivateFn,
  Router,
  RouterStateSnapshot,
  UrlTree
} from '@angular/router';
import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {AuthService} from "../services/auth.service";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationGuard implements CanActivate{
  constructor(private authService:AuthService,private router:Router) {
  }
  canActivate(route: ActivatedRouteSnapshot,
              state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if (this.authService.isAuthenticated){
      console.log(this.authService.isAuthenticated)
      return true;
    }
    else {
      this.router.navigateByUrl("/login")
      return false;
    }
  }

}
