import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/models/user';
import { UserService } from 'src/app/services/user.service';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
  user: User = {};

  constructor( private userService: UserService,private router: Router) {}

  ngOnInit(): void {}

  onSubmit(form: NgForm) {

    this.userService.signUp(this.user).subscribe({
      next : () => { this.router.navigate(['/login']); },
    });
  }
}


