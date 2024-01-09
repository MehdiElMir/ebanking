import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Customer} from "../model/customer.model";
import {CustomerService} from "../services/customer.service";
import {Router} from "@angular/router";
import {UserService} from "../services/user.service";
import {User} from "../model/user.model";

@Component({
  selector: 'app-new-user',
  templateUrl: './new-user.component.html',
  styleUrl: './new-user.component.css'
})
export class NewUserComponent implements OnInit{

  newUserFormGroup!: FormGroup


  constructor(private fb : FormBuilder, private  userService : UserService, private router:Router) {
  }

  ngOnInit(): void {
    this.newUserFormGroup = this.fb.group({
      username : this.fb.control("", [Validators.required]),
      password : this.fb.control("", [Validators.required]),
      confirmPassword : this.fb.control("", [Validators.required]),
      email: this.fb.control("",[Validators.email, Validators.required]),
    })
  }

  handleSaveUser() {
    let user:User = this.newUserFormGroup.value;
    console.log(this.newUserFormGroup.value);
    this.userService.saveUser(user).subscribe({
        next : data => {
          alert("User has been saved successfully");
          this.newUserFormGroup.reset();
          this.router.navigateByUrl("/admin/users");
        },
        error:err => {
          console.log(err)
        }
      }
    );
  }
}
