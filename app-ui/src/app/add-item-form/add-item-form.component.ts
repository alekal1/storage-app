import {Component, Input, OnInit} from '@angular/core';
import {ItemService} from "../_service/item.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ItemDto} from "../_dto/item.dto";
import {MatSnackBar} from "@angular/material/snack-bar";
import {ErrorResponse} from "../_dto/error.dto";
import {SnackbarComponent} from "../snackbar/snackbar.component";

@Component({
  selector: 'app-add-item-form',
  templateUrl: './add-item-form.component.html',
  styleUrls: ['./add-item-form.component.scss']
})
export class AddItemFormComponent implements OnInit {

  @Input() itemId = undefined;

  constructor(private itemService: ItemService,
              private _snackBar: MatSnackBar,
              private fb: FormBuilder) {

  }

  form: FormGroup;

  ngOnInit(): void {
    this.initForm();
  }

  private initForm(): void {
    this.form = this.fb.group({
      name: ['', [Validators.required]],
      color: ['', [Validators.required]],
      serialNumber: [''],
      picturePath: ['', [Validators.required]],
      size: [0, [Validators.required]]
    });
  }

  addItem(): void {
    let dto = new ItemDto(
      this.form.value.name,
      this.form.value.picturePath,
      this.form.value.serialNumber,
      this.form.value.color,
      this.form.value.size,
      new Date()
    )

    if (this.itemId == undefined) {
      this.itemService.addItem(localStorage.getItem("person"), dto)
        .then((res) => {
          window.location.reload();
        })
        .catch((errorResponse) => {
          this.errorSnackBar(errorResponse)
        })
    } else {
      this.itemService.addSubItem(this.itemId, localStorage.getItem("person"), dto)
        .then((res) => {
          window.location.reload()
        })
        .catch((errorResponse) => {
          this.errorSnackBar(errorResponse)
        })
    }
  }

  private errorSnackBar(errorResponse) {
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
  }

}
