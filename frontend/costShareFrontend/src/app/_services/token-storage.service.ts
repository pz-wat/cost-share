import { JsonpClientBackend } from '@angular/common/http';
import { Injectable } from '@angular/core';
const TOKEN_KEY = 'auth-token';
const USER_KEY = 'auth-user';
@Injectable({
  providedIn: 'root',
})
export class TokenStorageService {
  constructor() {}

  signOut(): void {
    window.sessionStorage.clear();
  }

  public saveToken(token: string): void {
    window.sessionStorage.removeItem(TOKEN_KEY);

    window.sessionStorage.setItem(TOKEN_KEY, token);
  }

  public getToken(): string {
    const item = sessionStorage.getItem(TOKEN_KEY);
    if (item) {
      return item;
    }
    return 'no item';
  }

  public saveUser(token: any): void {
    window.sessionStorage.removeItem(USER_KEY);
    let userInfo = {
      username: token.username,
      email: token.email,
      id: token.id,
    };

    window.sessionStorage.setItem(USER_KEY, JSON.stringify(userInfo));
  }

  public getUser(): any {
    const item = sessionStorage.getItem(USER_KEY);
    if (item) {
      return JSON.stringify(item);
    }
    return 'no item';
  }
}
