import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {Customer} from "../model/customer.model";
import {environment} from "../../environments/environment";
import {User} from "../model/user.model";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http:HttpClient) { }
  backendHost : string = "http://localhost:8085"
  public getUsers():Observable<Array<User>>{
    return this.http.get<Array<User>>(this.backendHost+"/auth/users")
  }

  public searchUsers(keyword : String):Observable<Array<User>>{
    return this.http.get<Array<User>>(this.backendHost+"/auth/users/search?keyword="+keyword)
  }
  public saveUser(user : User):Observable<User>{

    let data = {
      "username":user.username,
      "email":user.email,
      "password":user.password,
      "confirmPassword":user.confirmPassword,
    }

    return this.http.post<User>(this.backendHost+"/auth/addUser",data);
  }
  public deleteUser(id : string){
    return this.http.delete(this.backendHost+"/auth/delete/"+id);
  }
}
