import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from '../_services/token-storage.service';
@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent implements OnInit {
  constructor(private tokenStorageService: TokenStorageService) {}
  isLoggedIn = false;
  username = '';
  ngOnInit(): void {
    if (this.tokenStorageService.getToken() != 'no item') {
      this.isLoggedIn = true;
      this.username = JSON.parse(
        JSON.parse(this.tokenStorageService.getUser())
      ).username;
    }
  }
  signOut() {
    this.tokenStorageService.signOut();
    window.location.reload();
  }
}
