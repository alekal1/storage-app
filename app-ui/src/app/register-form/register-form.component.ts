import { Component, OnInit } from '@angular/core';
import {PersonService} from "../_service/person.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MatSnackBar} from "@angular/material/snack-bar";
import {ErrorResponse} from "../_dto/error.dto";
import {SnackbarComponent} from "../snackbar/snackbar.component";
import {PersonDto} from "../_dto/person.dto";

@Component({
  selector: 'app-register-form',
  templateUrl: './register-form.component.html',
  styleUrls: ['./register-form.component.scss']
})
export class RegisterFormComponent implements OnInit {

  constructor(private personService: PersonService,
              private fb: FormBuilder,
              private _snackBar: MatSnackBar) {

  }

  form: FormGroup;
  profileType: string = undefined;
  needsRepresentative: boolean = false;

  ngOnInit(): void {
    this.initForm();
  }

  private initForm(): void {
    this.form = this.fb.group({
      name: ['', [Validators.required]],
      password: ['', Validators.required],
      confirmPassword: ['', Validators.required],
      representative: [''],
    })
  }

  handleProfileType(): void {
    if ("business" == this.profileType) {
      this.needsRepresentative = true;
    } else {
      this.form.value.representative = '';
      this.needsRepresentative = false;
    }
  }

  registerPerson(): void {
    if (this.form.value.password != this.form.value.confirmPassword) {
      let errorObj = new ErrorResponse(
        "password-uuid",
        "400",
        "Passwords not matching!"
      );
      this._snackBar.openFromComponent(SnackbarComponent, {
        data: errorObj,
        duration: 5000
      });
      this.initForm();
    } else {
      const personDto = new PersonDto(
        this.form.value.name,
        this.form.value.password,
        this.form.value.representative,
        this.profileType
      );

      this.personService.registerPerson(personDto)
        .then((res) => {
          alert("You were registered!")
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
}
