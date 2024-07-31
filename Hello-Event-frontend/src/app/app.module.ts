import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module'; // Assurez-vous que cette ligne est présente


import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component';
import { UserDashboardComponent } from './user-dashboard/user-dashboard.component';
import { HomeComponent } from './home/home.component';
import { AuthInterceptor } from './auth.interceptor';
import {RouterLink, RouterOutlet} from "@angular/router";
import { ContactComponent } from './contact/contact.component';

@NgModule({
  bootstrap: [AppComponent],
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    AdminDashboardComponent,
    UserDashboardComponent,
    HomeComponent,
    ContactComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    RouterOutlet,
    RouterLink,
    AppRoutingModule

  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true}
  ]
})
export class AppModule { }
