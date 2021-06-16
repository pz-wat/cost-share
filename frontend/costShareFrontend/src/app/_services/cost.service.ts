import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TokenStorageService } from './token-storage.service';
const API = 'http://localhost:8080/api';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
};

@Injectable({
  providedIn: 'root',
})
export class CostService {
  constructor(private http: HttpClient, private token: TokenStorageService) {}

  createExpense(
    groupId: string,
    expenseName: string,
    amount: number,
    userIds: number[]
  ): Observable<any> {
    var userId = JSON.parse(JSON.parse(this.token.getUser())).id;
    var endpoint = API + '/group/' + groupId + '/user/' + userId + '/expense';

    return this.http.post(
      endpoint,
      {
        groupId: groupId,
        name: expenseName,
        amount: amount,
        userIds: userIds,
      },
      httpOptions
    );
  }
  getGroupExpenses(groupId: string) {
    var endpoint = API + '/group/' + groupId + '/expense';
    return this.http.get(endpoint, httpOptions);
  }
  settleExpense(expenseId: number, userId: number) {
    var endpoint =
      API + '/expense/' + expenseId + '/user/' + userId + '/settle';
    return this.http.put(endpoint, httpOptions);
  }
}
