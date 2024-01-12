import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Customer} from "../model/customer.model";
import {Role, User} from "../model/user.model";
import {UserService} from "../services/user.service";
import {FormArray, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../services/auth.service";

@Component({
  selector: 'app-update-user',
  templateUrl: './update-user.component.html',
  styleUrl: './update-user.component.css'
})
export class UpdateUserComponent implements OnInit{
  updateUserFormGroup!:FormGroup;
  userId!:string;
  user!:User;
  roles!:any;
  constructor(private route :ActivatedRoute,private router:Router,private userService:UserService,private fb:FormBuilder,private authService:AuthService) {
    this.user = this.router.getCurrentNavigation()?.extras.state as User;
  }

  ngOnInit(): void {
    this.userId = this.route.snapshot.params["userId"];

    this.userService.findUserById(this.userId).subscribe(
        (data: any) => {
          this.user = data
          this.userId = data.userId;
        },
        (error: any) => {
          console.log(error);
        }
    );
  }

  // Méthode pour vérifier si l'utilisateur a un rôle spécifique
  userHasRole(role: string): boolean {
    return this.user.roles.some(r => r.role === role);
  }

  AddRoleUser() {
    this.userService.addRoleToUser(this.user.username,"USER").subscribe(
        {
          next:data =>{
            alert("USER role added successfully");
            this.router.navigateByUrl("/admin/users");
          },
          error:err => {
            console.log(err);
          }
        }
    );
  }

  AddRoleAdmin() {
    this.userService.addRoleToUser(this.user.username,"ADMIN").subscribe(
        {
          next:data =>{
            alert("ADMIN role added successfully");
            this.router.navigateByUrl("/admin/users");
          },
          error:err => {
            console.log(err);
          }
        }
    );
  }

  removeRoleUser() {
    this.userService.removeRoleToUser(this.user.username,"USER").subscribe(
        {
          next:data =>{
            alert("USER role removed successfully");
            this.router.navigateByUrl("/admin/users");
          },
          error:err => {
            console.log(err);
          }
        }
    );
  }

  removeRoleAdmin() {
    this.userService.removeRoleToUser(this.user.username,"ADMIN").subscribe(
        {
          next:data =>{
            alert("ADMIN role removed successfully");
            this.router.navigateByUrl("/admin/users");
          },
          error:err => {
            console.log(err);
          }
        }
    );
  }
}
