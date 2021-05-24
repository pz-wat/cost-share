import { Component, OnInit } from '@angular/core';
import { asapScheduler } from 'rxjs';
import { TokenStorageService } from '../_services/token-storage.service';
import { GroupsService } from '../_services/groups.service';
import { group } from '@angular/animations';
//
@Component({
  selector: 'app-group-manager',
  templateUrl: './group-manager.component.html',
  styleUrls: ['./group-manager.component.scss'],
})
export class GroupManagerComponent implements OnInit {
  constructor(
    private groupsService: GroupsService,
    private tokenStorageService: TokenStorageService //public dialog: MatDialog
  ) {}
  groups: Group[] = [];
  content: string | undefined;
  panelOpenState: boolean | undefined;
  ngOnInit(): void {
    this.groupsService.getUserGroups().subscribe((data) => {
      this.content = JSON.stringify(data);
      this.groups = JSON.parse(this.content);
    });
  }

  addGroupToggle() {
    const overlay = document.getElementById('overlay');
    const modal = document.getElementById('modal');
    overlay?.classList.toggle('active');
    modal?.classList.toggle('active');
  }
  joinGroupToggle() {
    const overlay = document.getElementById('overlay');
    const modal = document.getElementById('join-group');
    overlay?.classList.toggle('active');
    modal?.classList.toggle('active');
  }

  form: any = {};
  joinGroupForm: any = {};

  onSubmit() {
    console.log('data');
    this.groupsService.createGroup(this.form.groupName).subscribe((data) => {
      this.groupsService.getUserGroups().subscribe((data) => {
        this.content = JSON.stringify(data);
        this.groups = JSON.parse(this.content);
      });
    });
  }
  joinGroup() {
    var groupId = 'no';
    this.groupsService
      .getGroupByAccess(this.joinGroupForm.accessCode)
      .subscribe(async (data) => {
        console.log(data);
        groupId = JSON.parse(JSON.stringify(data)).id;
      });

    setTimeout(() => {
      this.groupsService.joinGroup(groupId).subscribe((data) => {
        console.log(data);
      });
    }, 500);
  }
  /*openDialog() {
    const dialogRef = this.dialog.open(AddGroupDialog);

    dialogRef.afterClosed().subscribe((result) => {
      console.log(`Dialog result: ${result}`);
    });
  }*/
}
/*@Component({
  selector: 'add-group-dialog',
  templateUrl: 'add-group-dialog.html',
})
export class AddGroupDialog {}*/

interface User {
  username: string;
}
interface Group {
  id: number;
  name: string;
  accessCode: string;
  date: string;
  isUserAdmin: boolean;
  groupUsers: User[];
}
