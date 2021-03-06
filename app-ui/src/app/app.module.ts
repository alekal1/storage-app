import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import {MatCardModule} from '@angular/material/card';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSnackBarModule } from "@angular/material/snack-bar";
import { MatSelectModule } from '@angular/material/select';
import { MatTableModule } from '@angular/material/table';
import {HttpClientModule} from '@angular/common/http';

import { LoginFormComponent } from './login-form/login-form.component';
import { SnackbarComponent } from './snackbar/snackbar.component';
import { RegisterFormComponent } from './register-form/register-form.component';
import { GreetingComponent } from './greeting/greeting.component';
import { ItemDashboardComponent } from './item-dashboard/item-dashboard.component';
import { AddItemFormComponent } from './add-item-form/add-item-form.component';
import { SubItemDashboardComponent } from './sub-item-dashboard/sub-item-dashboard.component';
import { SearchComponent } from './search/search.component';
import {MatIconModule} from "@angular/material/icon";

@NgModule({
  declarations: [
    AppComponent,
    LoginFormComponent,
    SnackbarComponent,
    RegisterFormComponent,
    GreetingComponent,
    ItemDashboardComponent,
    AddItemFormComponent,
    SubItemDashboardComponent,
    SearchComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatCardModule,
    MatSnackBarModule,
    MatSelectModule,
    MatFormFieldModule,
    MatTableModule,
    MatIconModule,
    FormsModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatInputModule,
    HttpClientModule,
    BrowserAnimationsModule
  ],
  providers: [AppRoutingModule],
  bootstrap: [AppComponent]
})
export class AppModule { }
