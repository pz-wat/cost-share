import { Component, OnInit } from '@angular/core';
import { GroupsService } from '../_services/groups.service';
import { CostService } from '../_services/cost.service';
import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';
import { TokenStorageService } from '../_services/token-storage.service';
@Component({
  selector: 'app-group',
  templateUrl: './group.component.html',
  styleUrls: ['./group.component.scss'],
})
export class GroupComponent implements OnInit {
  constructor(
    private groupsService: GroupsService,
    private route: ActivatedRoute,
    private router: Router,
    private costService: CostService,
    private tokenStorageService: TokenStorageService
  ) {}

  changeColor() {
    var container = document.getElementById('container');
    if (container) {
      if (this.white) {
        container.style.color = 'lightblue';
        this.white = !this.white;
      } else {
        container.style.color = 'white';
        this.white = !this.white;
      }
    }
  }
  deleteGroupModal() {
    const overlay = document.getElementById('overlay');
    const modal = document.getElementById('confirmation');
    overlay?.classList.toggle('active');
    modal?.classList.toggle('active');
  }
  addExpenseToggle() {
    const overlay = document.getElementById('overlay');
    const modal = document.getElementById('expense');
    overlay?.classList.toggle('active');
    modal?.classList.toggle('active');
  }
  removeUserModal() {
    const overlay = document.getElementById('overlay');
    const modal = document.getElementById('modal');
    overlay?.classList.toggle('active');
    modal?.classList.toggle('active');
  }
  deleteGroup() {
    this.groupsService.deleteGroup(this.group.id).subscribe();
    this.router.navigate(['/groups']);
  }
  removeUser(userId: number) {
    console.log(userId);
    this.groupsService
      .getGroupByUser(this.group.id, userId)
      .subscribe((data) => {
        console.log(data);
      });
    this.groupsService.removeUser(this.group.id, userId).subscribe((data) => {
      console.log(data);
    });
    this.ngOnInit();
  }
  onSubmit() {
    var id = JSON.parse(JSON.parse(this.tokenStorageService.getUser())).id;
    let userarray: number[] = [];
    this.group.groupUsers.forEach((element) => {
      if (id != element.id) userarray.push(element.id);
    });
    this.costService
      .createExpense(
        this.group.id,
        this.form.expenseName,
        this.form.amount,
        userarray
      )
      .subscribe();
    this.ngOnInit();
  }

  form: any = {};
  group: Group;
  expenses: Expense[] = [];
  white = true;

  content: string | undefined;
  ngOnInit(): void {
    let accessCode = this.route.snapshot.paramMap.get('code');
    if (accessCode) {
      this.groupsService.getGroupByAccess(accessCode).subscribe((data) => {
        this.content = JSON.stringify(data);
        this.group = JSON.parse(this.content);
        console.log(data);
      });

      setTimeout(() => {
        this.costService.getGroupExpenses(this.group.id).subscribe((data) => {
          this.expenses = JSON.parse(JSON.stringify(data));
          console.log(data);
        });
      }, 100);
    }
  }
}
interface User {
  id: number;
  username: string;
  owedAmount: number;
  paid: number;
  settled: boolean;
}
interface Group {
  id: string;
  name: string;
  accessCode: string;
  date: string;
  userAdmin: boolean;
  groupUsers: User[];
}
interface Expense {
  id: number;
  name: string;
  amount: number;
  dateCreated: string;
  groupId: number;
  users: User[];
}
