import {Component, OnInit} from '@angular/core';
import { ChartOptions } from 'chart.js';
import {Account} from "../model/account.model";
import {AccountsService} from "../services/accounts.service";
import {CustomerService} from "../services/customer.service";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: [ './dashboard.component.css' ]
})
export class DashboardComponent implements OnInit {
  accounts!: Account[];
  accountsCount!:number;
  customersCount!:number;
  speed:number = 400;
  pieChartPlugins1 = [];
  pieChartLabels1: string[] = [];
  pieChartData1: number[] = [];
  pieChartType1: string = 'pie';
  pieChartDatasets1: any[] = [];
  pieChartLegend1: boolean = true;
  pieChartOptions1: any = {
    responsive: true,
    // Autres options de configuration du graphique
  };
  //""""""""""""""""""""
  pieChartPlugins = [];
  pieChartLabels: string[] = [];
  pieChartData: number[] = [];
  pieChartType: string = 'pie';
  pieChartDatasets: any[] = [];
  pieChartLegend: boolean = true;
  pieChartOptions: any = {
    responsive: true,
    // Autres options de configuration du graphique
  };


  constructor(private accountService: AccountsService,private customerService:CustomerService) {}

  ngOnInit(): void {
    this.customerService.getCustomers().subscribe({
      next:data=>{
        this.customersCount = data.length;

        this.increaseElementNumber("nbr2",this.customersCount)
      },
      error:err => {
        console.log(err)
      }
    })


    this.accountService.getAllAccounts().subscribe({
      next: (data) => {
        this.accounts = data;

        const savingsTotal = this.getTotalBalanceByType('SavingAccount');
        const currentTotal = this.getTotalBalanceByType('CurrentAccount');

        const totalAccounts = this.accounts.length;

        // Calculate counts for each account type
        const savingsCount = this.getAccountCountByType('SavingAccount');
        const currentCount = this.getAccountCountByType('CurrentAccount');

        // Calculate percentages
        const savingsPercentage = (savingsCount / totalAccounts) * 100;
        const currentPercentage = (currentCount / totalAccounts) * 100;
        // Update pie chart data with percentages
        this.pieChartLabels1 = ['Savings (' + savingsPercentage.toFixed(2) + '%)', 'Current (' + currentPercentage.toFixed(2) + '%)'];
        this.pieChartDatasets1 = [{
          data: [savingsCount, currentCount]
        }];

        // Update the pie chart data
        this.pieChartLabels = ['Savings Total', 'Current Total'];
        this.pieChartDatasets = [{
          data: [savingsTotal, currentTotal]
        }];

        this.accountsCount = this.accounts.length;
        this.increaseElementNumber('nbr',this.accountsCount);

      },
      error: (err) => {
        console.log(err);
      }
    });
  }

  getAccountCountByType(accountType: string): number {
    return this.accounts.filter((account) => account.type === accountType).length;
  }

  getTotalBalanceByType(accountType: string): number {
    return this.accounts
      ?.filter((account) => account.type === accountType)
      .reduce((total, account) => total + account.balance, 0) || 0;
  }

  increaseElementNumber(id: string, count:number) {
    const element = document.getElementById(id);
    const endNumber = count;
    this.increaseNumberRecursive(0, endNumber, element);
  }

  increaseNumberRecursive(i: number, endNumber: number, element: HTMLElement | null) {
    if (i <= endNumber) {
      if (element) {
        element.innerHTML = i.toString();
      }
      setTimeout(() => {
        this.increaseNumberRecursive(i + 1, endNumber, element);
      }, this.speed);
    }
  }

}
