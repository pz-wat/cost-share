import { Component, OnInit } from '@angular/core';
import { asapScheduler } from 'rxjs';
import { TokenStorageService } from '../_services/token-storage.service';
@Component({
  selector: 'app-group-manager',
  templateUrl: './group-manager.component.html',
  styleUrls: ['./group-manager.component.scss'],
})
export class GroupManagerComponent implements OnInit {
  constructor(private tokenStorageService: TokenStorageService) {}

  groups: Group[] = [
    {
      id: 1,
      name: 'asd',
      accessCode: 'sdadsa',
      date: 'sadsad',
      isUserAdmin: true,
    },
    {
      id: 1,
      name: 'asd',
      accessCode: 'sdadsa',
      date: 'sadsad',
      isUserAdmin: true,
    },
    {
      id: 1,
      name: 'asd',
      accessCode: 'sdadsa',
      date: 'sadsad',
      isUserAdmin: true,
    },
  ];
  panelOpenState: boolean | undefined;
  ngOnInit(): void {}
  form: any = {};
  onSubmit() {
    console.log(this.form.groupName);
    var newGroup = {
      id: 1,
      name: this.form.groupName,
      accessCode: 'sdadsa',
      date: 'sadsad',
      isUserAdmin: true,
    };
    this.groups.push(newGroup);
  }
}

interface Group {
  id: number;
  name: string;
  accessCode: string;
  date: string;
  isUserAdmin: boolean;
}
