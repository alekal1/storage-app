import { Component, OnInit } from '@angular/core';
import {PersonService} from "../_service/person.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ErrorResponse} from "../_dto/error.dto";
import {PersonDto} from "../_dto/person.dto";
import {Utils} from "../_utils/app.util";

@Component({
  selector: 'app-register-form',
  templateUrl: './register-form.component.html',
  styleUrls: ['./register-form.component.scss']
})
export class RegisterFormComponent implements OnInit {

  constructor(private personService: PersonService,
              private fb: FormBuilder,
              private _utils: Utils) {

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
      this._utils.errorSnackBar(errorObj);
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
          this.initForm();
        })
        .catch((errorResponse) => {
          this._utils.errorSnackBar(errorResponse)
        })
    }
  }
}
