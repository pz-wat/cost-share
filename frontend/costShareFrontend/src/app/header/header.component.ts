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
  ngOnInit(): void {
    if (this.tokenStorageService.getToken() != 'no item') {
      this.isLoggedIn = true;
    }
  }
  signOut() {
    this.tokenStorageService.signOut();
    window.location.reload();
  }
}
