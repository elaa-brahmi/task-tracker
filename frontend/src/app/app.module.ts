import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatFormFieldModule} from '@angular/material/form-field';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { AngularFontAwesomeComponent, AngularFontAwesomeModule, AngularFontAwesomeService } from 'angular-font-awesome';
import {HTTP_INTERCEPTORS, HttpClient, HttpClientModule} from "@angular/common/http";
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { ActivateAccountComponent } from './pages/activate-account/activate-account.component';
import {CodeInputModule} from "angular-code-input";
import { HttpTokenInterceptor } from './services/interceptor/http-token.interceptor';
import { TasksModule } from './modules/tasks/tasks.module';
import { ToastrModule } from 'ngx-toastr';
import {MatProgressBarModule} from '@angular/material/progress-bar';
@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    ActivateAccountComponent

  ],
  imports: [
    MatDatepickerModule,
    MatNativeDateModule,
    ToastrModule.forRoot(),
     // ToastrModule added
    MatFormFieldModule,
    MatProgressBarModule,
    ReactiveFormsModule,
    BrowserModule,
    HttpClientModule, // super important
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    TasksModule,
    CodeInputModule
  ],
  providers:[
    HttpClient, // super important
    {
      provide: HTTP_INTERCEPTORS,
      useClass:HttpTokenInterceptor,
      multi:true //this is not the only interceptor we have
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
