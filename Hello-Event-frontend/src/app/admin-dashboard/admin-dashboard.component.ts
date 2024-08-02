import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth.service';
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit {
  userInfo: any;
  users: any[] = [];
  currentView: string = 'profile';

  constructor(private authService: AuthService) { }

  ngOnInit() {
    this.loadAdminInfo();
    this.loadUsers();
  }

  loadAdminInfo() {
    const username = this.authService.getUsername();
    if (username) {
      this.authService.getUserInfo(username).subscribe(
        info => {
          this.userInfo = info;
        },
        error => {
          console.error('Erreur lors de la récupération des informations utilisateur', error);
        }
      );
    }
  }

  loadUsers() {
    this.authService.getAllUsers().subscribe(
      users => {
        this.users = users;
      },
      error => {
        console.error('Erreur lors du chargement des utilisateurs', error);
      }
    );
  }
  updateUser(user: any) {
    this.authService.updateUser(user).subscribe(
      updatedUser => {
        console.log('Utilisateur mis à jour', updatedUser);
        this.loadUsers();
      },
      error => {
        console.error('Erreur lors de la mise à jour de l\'utilisateur', error);
      }
    );
  }
  deleteUser(id: number) {
    this.authService.deleteUser(id).subscribe(
      () => {
        console.log('Utilisateur supprimé');
        this.loadUsers();
      },
      error => {
        console.error('Erreur lors de la suppression de l\'utilisateur', error);
      }
    );
  }
  changeView(view: string) {
    this.currentView = view;
  }
}
