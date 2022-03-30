import { Component, OnInit } from '@angular/core';
import {PersonService} from "../_service/person.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {PersonDto} from "../_dto/person.dto";
import {Router} from "@angular/router";
import {Utils} from "../_utils/app.util";


@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.scss']
})
export class LoginFormComponent implements OnInit {

  constructor(private personService: PersonService,
              private fb: FormBuilder,
              private router: Router,
              private _util: Utils) {

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
        this.router.navigateByUrl("/dashboard").then(r => console.log("Redirect"));
      })
      .catch((errorResponse) => {
        this._util.errorSnackBar(errorResponse);
      })
  }
}
