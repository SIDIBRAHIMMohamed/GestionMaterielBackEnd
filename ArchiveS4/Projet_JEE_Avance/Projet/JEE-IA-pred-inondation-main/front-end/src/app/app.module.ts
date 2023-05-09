import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SignupComponent } from './components/signup/signup.component';
import { LoginComponent } from './components/login/login.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { UserComponent } from './components/user/user.component';
import { AdminComponent } from './components/admin/admin.component';
import { HeaderComponent } from './components/header/header.component';
import { VisualiserComponent } from './components/visualiser/visualiser.component';
import { GraphComponent } from './components/graph/graph.component';
import { MatTableModule } from '@angular/material/table';
import { CdkTableModule } from '@angular/cdk/table';

@NgModule({
  declarations: [
    AppComponent,
    SignupComponent,
    LoginComponent,
    UserComponent,
    AdminComponent,
    HeaderComponent,
    VisualiserComponent,
    GraphComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    MatTableModule,
    CdkTableModule
  ],
  exports: [
    MatTableModule,
    CdkTableModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
