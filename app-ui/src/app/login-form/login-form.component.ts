import { Component, OnInit } from '@angular/core';
import {PersonService} from "../_service/person.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {PersonDto} from "../_dto/person.dto";
import {MatSnackBar} from '@angular/material/snack-bar';
import {ErrorResponse} from "../_dto/error.dto";
import {SnackbarComponent} from "../snackbar/snackbar.component";
import {Router} from "@angular/router";


@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.scss']
})
export class LoginFormComponent implements OnInit {

  constructor(private personService: PersonService,
              private fb: FormBuilder,
              private _router: Router,
              private _snackBar: MatSnackBar) {

  }

  form: FormGroup;

  ngOnInit(): void {
    this.initForm();
  }

  private initForm(): void {
    this.form = this.fb.group({
      name: ['', [Validators.required]],
      password: ['', [Validators.required]]
    });
  }

  loginPerson(): void {
    const personDto = new PersonDto(
      this.form.value.name,
      this.form.value.password
    );

    this.personService.loginPerson(personDto)
      .then((res) => {
        localStorage.setItem("person", res.username);
        localStorage.setItem("profileType", res.profileType);
        this._router.navigateByUrl("/dashboard").then(r => console.log("Redirect"));
      })
      .catch((errorResponse) => {
        const error = errorResponse.error;
        let errorObj = new ErrorResponse(
          error.uuid,
          error.code,
          error.message
        );
        console.log("Got error " + errorObj)
        this._snackBar.openFromComponent(SnackbarComponent, {
          data: errorObj,
          duration: 7000,
          horizontalPosition: "center",
          verticalPosition: "top"
        })
      })
  }
}
