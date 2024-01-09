import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Customer} from "../model/customer.model";
import {CustomerService} from "../services/customer.service";
import {Router} from "@angular/router";
import {UserService} from "../services/user.service";
import {Role, User} from "../model/user.model";
import {AuthService} from "../services/auth.service";

@Component({
  selector: 'app-update-profile',
  templateUrl: './update-profile.component.html',
  styleUrl: './update-profile.component.css'
})
export class UpdateProfileComponent implements OnInit{

  updateProfileFormGroup!: FormGroup
  username!:string
  userId!:string
  constructor(private fb : FormBuilder, private  userService : UserService,private authService:AuthService, private router:Router) {
  }

    ngOnInit(): void {
        this.username = this.authService.username;
        this.updateProfileFormGroup = this.fb.group({
            username: this.fb.control('', [Validators.required]),
            password: this.fb.control('', [Validators.required]),
            confirmPassword: this.fb.control('', [Validators.required]),
            email: this.fb.control('', [Validators.email, Validators.required]),
        });

        this.userService.findUserProfile(this.username).subscribe(
            (data: any) => {
                this.updateProfileFormGroup.patchValue({
                    username: data.username,
                    password: "",
                    confirmPassword: "",
                    email: data.email,
                });

                this.userId = data.userId;

                // Store the user's roles from the fetched data
                this.authService.roles = data.roles;
            },
            (error: any) => {
                console.log(error);
            }
        );
    }

    handleUpdateUser() {
        let user: User = this.updateProfileFormGroup.value;

        // Assign the stored roles from authService to user.roles
        user.roles = this.authService.roles;

        user.userId = this.userId;

        // Checking if password matches confirmPassword
        if (this.updateProfileFormGroup.value.password !== this.updateProfileFormGroup.value.confirmPassword) {
            alert("Password and Confirm Password do not match");
            return;
        }

        this.userService.updateUser(user).subscribe({
            next: data => {
                alert("Profile has been updated successfully");
                this.updateProfileFormGroup.reset();
                this.router.navigateByUrl("/admin/customers");
            },
            error: err => {
                console.log(err);
            }
        });
    }


    }

