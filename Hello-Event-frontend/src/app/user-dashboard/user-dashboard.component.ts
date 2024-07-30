import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth.service';

interface User {
  id: number;
  username: string;
  email: string;
}

@Component({
  selector: 'app-user-dashboard',
  templateUrl: './user-dashboard.component.html',
  styleUrls: ['./user-dashboard.component.css']
})
export class UserDashboardComponent implements OnInit {
  userInfo: any;
  updatedUserInfo: any = {};
  user: User = {
    id: 0,
    username: '',
    email: '',
  };
  constructor(private authService: AuthService) { }

  ngOnInit() {
    const username = this.authService.getUsername();
    if (username) {
      this.authService.getUserInfo(username).subscribe(
        info => {
          this.userInfo = info;
          this.updatedUserInfo = { ...info };
        },
        error => {
          console.error('Erreur lors de la récupération des informations utilisateur', error);
        }
      );
    }
  }

  updateUser() {
    this.authService.updateUser(this.user).subscribe(
      response => {
        console.log('User updated successfully', response);
      },
      error => {
        if (error.status === 403) {
          console.error('Access is forbidden: Check your token and user permissions.');
        } else {
          console.error('An error occurred:', error);
        }
      }
    );
  }



}
