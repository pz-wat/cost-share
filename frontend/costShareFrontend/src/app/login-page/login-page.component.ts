import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from '../_services/token-storage.service';
import { AuthService } from '../_services/auth.service';
import jwt_decode from 'jwt-decode';
@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss'],
})
export class LoginPageComponent implements OnInit {
  constructor(
    private authService: AuthService,
    private tokenStorageService: TokenStorageService
  ) {}

  form: any = {};
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  user: any;

  overlay = document.getElementById('overlay');
  modal = document.getElementById('modal');

  ngOnInit(): void {
    if (this.tokenStorageService.getToken() != 'no item') {
      this.isLoggedIn = true;

      this.user = JSON.parse(
        JSON.parse(this.tokenStorageService.getUser())
      ).username;
    }
  }

  modalToggle() {
    this.overlay?.classList.toggle('active');
    this.modal?.classList.toggle('active');
  }

  onSubmit(): void {
    this.authService.login(this.form).subscribe(
      (data) => {
        console.log(data);
        this.tokenStorageService.saveToken(data.token);
        this.tokenStorageService.saveUser(data);

        this.isLoginFailed = false;
        this.isLoggedIn = true;

        this.reloadPage();
      },
      (err) => {
        this.errorMessage = err.error.message;
        this.isLoginFailed = true;
      }
    );
  }
  reloadPage(): void {
    window.location.reload();
  }
}
