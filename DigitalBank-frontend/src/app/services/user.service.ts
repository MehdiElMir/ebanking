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

  public findUserProfile(username : string):Observable<User>{
    let par = new HttpParams().set('username',username)
    return this.http.get<User>(this.backendHost+"/auth/users/find", {params : par});
  }
  public updateUser(user : User):Observable<User>{
    return this.http.put<User>(this.backendHost+"/auth/updateProfile",user);
  }

  public findUserById(userId : string):Observable<User>{
    return this.http.get<User>(this.backendHost+"/auth/users/"+userId);
  }

  public addRoleToUser(username : string, role:string):Observable<User>{
    let params = new HttpParams()
        .set("username",username).set("role",role);
    return this.http.put<User>(this.backendHost+"/auth/users/addRole",params);
  }

  public removeRoleToUser(username : string, role:string):Observable<User>{
    let params = new HttpParams()
        .set("username",username).set("role",role);
    return this.http.put<User>(this.backendHost+"/auth/users/removeRole",params);
  }






}
