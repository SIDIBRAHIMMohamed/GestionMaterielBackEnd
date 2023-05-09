import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {User} from 'src/app/models/user';
import { FormControl, FormGroup, NgForm, Validators } from '@angular/forms';
import { UserService } from 'src/app/services/user.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  public user?: User;
  email = ''
  password = ''
  isLoginPage = true;
  constructor(private userService:UserService,
              private router:Router
              ){ }
  formGroup: FormGroup= new FormGroup({
    email:new FormControl('',[Validators.required]),
    password:new FormControl('',[Validators.required])
  });
  ngOnInit():void {
    
  }
  loginProcess(form: NgForm){

      this.userService.login(this.email, this.password)
        .subscribe({
        next : (res) => { console.log(res);
          this.router.navigate(['user']);
        },
        error : (err) => { 
          console.log(err);
        }
        
      })
    }

}
