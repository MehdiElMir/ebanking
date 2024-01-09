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
  constructor(private route :ActivatedRoute,private router:Router,private userService:UserService,private fb:FormBuilder,private authService:AuthService) {
    this.user = this.router.getCurrentNavigation()?.extras.state as User;
  }

  ngOnInit(): void {
    this.userId = this.route.snapshot.params["userId"];


    this.updateUserFormGroup = this.fb.group({
      username: this.fb.control('', [Validators.required]),
      email: this.fb.control('', [Validators.email, Validators.required]),

    });

    this.userService.findUserById(this.userId).subscribe(
        (data: any) => {
          this.updateUserFormGroup.patchValue({
            username: data.username,
            email: data.email,
            roles : data.roles
          });

          this.userId = data.userId;

        },
        (error: any) => {
          console.log(error);
        }
    );
  }

  handleUpdateUser() {

  }
}
