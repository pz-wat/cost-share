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
export class GroupsService {
  constructor(private http: HttpClient, private token: TokenStorageService) {}

  createGroup(groupName: string): Observable<any> {
    var userId = JSON.parse(JSON.parse(this.token.getUser())).id;
    var endpoint = API + '/user/' + userId + '/group';
    console.log(endpoint);
    return this.http.post(
      endpoint,
      {
        userId: userId,
        name: groupName,
      },
      httpOptions
    );
  }

  getUserGroups(): Observable<any> {
    var userId = JSON.parse(JSON.parse(this.token.getUser())).id;
    var endpoint = API + '/user/' + userId + '/group';
    return this.http.get(endpoint);
  }
  getGroupByAccess(accessCode: string): Observable<any> {
    var endpoint = API + '/group/' + accessCode;
    return this.http.get(endpoint);
  }
  joinGroup(groupId: string): Observable<any> {
    var userId = JSON.parse(JSON.parse(this.token.getUser())).id;
    var endpoint = API + '/user/' + userId + '/group/' + groupId;
    return this.http.post(endpoint, {});
  }
}
