import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {CustomerService} from "../services/customer.service";
import {catchError, map, Observable, throwError} from "rxjs";
import {Customer} from "../model/customer.model";
import {FormBuilder, FormGroup} from "@angular/forms";
import {Router} from "@angular/router";
import {User} from "../model/user.model";
import {UserService} from "../services/user.service";

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrl: './users.component.css'
})
export class UsersComponent implements OnInit{


  users! : Observable<Array<User>>;
  errorMessage! : string ;
  searchFormGroup :FormGroup | undefined;
  constructor(private userService:UserService, private fb : FormBuilder, private router:Router) {
  }

  ngOnInit(): void {
    this.searchFormGroup = this.fb.group(
      {
        keyword : this.fb.control(""),
      }
    );
    this.handleSearchUsers();
  }

  handleSearchUsers() {
    let kw = this.searchFormGroup?.value.keyword;
    this.users = this.userService.searchUsers(kw).pipe(
      catchError(err => {
        this.errorMessage = err.message;
        return throwError(err);
      })
    );
  }

  handleDeleteUser(u : User) {
    let conf = confirm("Are you sure?");
    if (!conf)return;
    this.userService.deleteUser(u.userId).subscribe({
      next: (resp) => {
        this.users = this.users.pipe(
          map(data =>{
            let index = data.indexOf(u);
            data.slice(index,1);
            return data;
          })
        );
      },
      error: err => {
        console.log(err);
      }
    })
  }


}
