import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component';
import { UserDashboardComponent } from './user-dashboard/user-dashboard.component';
import {ContactComponent} from "./contact/contact.component";
import {AboutComponent} from "./about/about.component";
import {EventDetailsComponent} from "./event-details/event-details.component";
import {EventSearchComponent} from "./event-search/event-search.component";
//import { EventCreateComponent } from './create-event/create-event.component';
import { CreateEventComponent } from './create-event/create-event.component';
import { ListEventComponent } from './list-event/list-event.component';

const routes: Routes = [
  { path: 'create-event', component: CreateEventComponent },
  //{ path: 'events', component: EventListComponent },
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'admin-dashboard', component: AdminDashboardComponent },
  { path: 'user-dashboard', component: UserDashboardComponent },
  { path: 'search', component: EventSearchComponent },
  { path: 'event/:id', component: EventDetailsComponent },
  { path: 'about', component: AboutComponent },
  { path: 'contact', component: ContactComponent },
  { path: 'events', component: ListEventComponent },
  //{ path: 'create-event', component: EventCreateComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
