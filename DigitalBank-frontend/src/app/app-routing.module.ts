import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {CustomersComponent} from "./customers/customers.component";
import {AccountsComponent} from "./accounts/accounts.component";
import {NewCustomerComponent} from "./new-customer/new-customer.component";
import {CustomerAccountsComponent} from "./customer-accounts/customer-accounts.component";
import {LoginComponent} from "./login/login.component";
import {AdminTemplateComponent} from "./admin-template/admin-template.component";
import {AuthenticationGuard} from "./guards/authentication.guard";
import {AuthorizationGuard} from "./guards/authorization.guard";
import {NotAuthorizedComponent} from "./not-authorized/not-authorized.component";
import {UsersComponent} from "./users/users.component";
import {NewUserComponent} from "./new-user/new-user.component";
import {UpdateProfileComponent} from "./update-profile/update-profile.component";
import {UpdateUserComponent} from "./update-user/update-user.component";

const routes: Routes = [

  {path:"login", component:LoginComponent},
  {path:"", redirectTo :"/login", pathMatch:"full"},
  {path:"admin", component:AdminTemplateComponent,canActivate:[AuthenticationGuard],
    children:[
      { path:"customers", component: CustomersComponent},
      { path:"accounts", component: AccountsComponent},
      {path:"newCustomer", component:NewCustomerComponent, canActivate:[AuthorizationGuard],data:{role:"ADMIN"}},
      {path:"customer-accounts/:id", component:CustomerAccountsComponent},
      {path:"notAuthorized", component:NotAuthorizedComponent},
      {path:"users", component:UsersComponent, canActivate:[AuthorizationGuard],data:{role: "ADMIN"}},
      {path:"newUser", component:NewUserComponent, canActivate:[AuthorizationGuard],data:{role: "ADMIN"}},
      {path:"updateUser/:userId", component:UpdateUserComponent, canActivate:[AuthorizationGuard],data:{role: "ADMIN"}},
      {path:"updateProfile", component:UpdateProfileComponent},
    ]}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
