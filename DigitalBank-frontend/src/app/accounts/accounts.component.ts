import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {AccountsService} from "../services/accounts.service";
import {catchError, Observable, throwError} from "rxjs";
import {AccountDetails} from "../model/account.model";
import {AuthService} from "../services/auth.service";

@Component({
  selector: 'app-accounts',
  templateUrl: './accounts.component.html',
  styleUrl: './accounts.component.css'
})
export class AccountsComponent  implements OnInit{

  accountFormGroup!: FormGroup;
  currentPage : number = 0;
  pageSize:number = 5;

  accountObservable$! : Observable<AccountDetails>
  operationFormGroup!: FormGroup ;
  errorMessage!: string;

  constructor(public authService:AuthService,private fb: FormBuilder,private accountService : AccountsService) {
  }

  ngOnInit(): void {
    this.accountFormGroup = this.fb.group({
      accountId : this.fb.control(""),
    });
    this.operationFormGroup = this.fb.group({
      operationType : this.fb.control(""),
      amount : this.fb.control(0),
      description:this.fb.control(""),
      accountDestination : this.fb.control(null),
    })


  }

  handleSearchAccount() {
    let accountId : string = this.accountFormGroup.value.accountId;
    this.accountObservable$ = this.accountService.getAccount(accountId, this.currentPage, this.pageSize).pipe(
      catchError(err => {
        this.errorMessage = err.message;
        return throwError(err);
      })
    );
  }

  gotoPage(page: number) {
    this.currentPage = page;
    this.handleSearchAccount();
  }

  handleAccountOperation() {
    let accountId :string= this.accountFormGroup.value.accountId;
    let operationType :string = this.operationFormGroup.value.operationType;
    let amount:number = this.operationFormGroup.value.amount;
    let description:string = this.operationFormGroup.value.description;
    let accountDestination:string = this.operationFormGroup.value.accountDestination;
    if(operationType == 'DEBIT'){
      console.log("test")
      this.accountService.debit(accountId,amount,description).subscribe({
        next:data=>{
          alert("Debit Success");
          this.handleSearchAccount();
          this.operationFormGroup.reset();
        },
        error:err => {
          alert("Smth went wrong!")
          console.log(err);
        }
        }
      );
    }
    else if(operationType == 'CREDIT'){
      this.accountService.credit(accountId,amount,description).subscribe({
          next:data=>{
            alert("Credit Success");
            this.handleSearchAccount();
            this.operationFormGroup.reset();
          },
          error:err => {
            alert("Smth went wrong!")
            console.log(err);
          }
        }
      );
    }
    else if(operationType == 'TRANSFERT'){
      this.accountService.transfert(accountId,accountDestination,amount,description).subscribe({
          next:data=>{
            alert("Transfert Success");
            this.handleSearchAccount();
            this.operationFormGroup.reset();
          },
          error:err => {
            alert("Smth went wrong!")
            console.log(err);
          }
        }
      );
    }
  }
}
