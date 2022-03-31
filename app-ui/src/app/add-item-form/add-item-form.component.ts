import {Component, Input, OnInit} from '@angular/core';
import {ItemService} from "../_service/item.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ItemDto} from "../_dto/item.dto";
import {Utils} from "../_utils/app.util";

@Component({
  selector: 'app-add-item-form',
  templateUrl: './add-item-form.component.html',
  styleUrls: ['./add-item-form.component.scss']
})
export class AddItemFormComponent implements OnInit {

  @Input() parentItemId = undefined;

  constructor(private itemService: ItemService,
              private fb: FormBuilder,
              private _utils: Utils) {

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

    if (this.parentItemId == undefined) {
      this.itemService.addItem(dto)
        .then((res) => {
          window.location.reload();
        })
        .catch((errorResponse) => {
          this._utils.errorSnackBar(errorResponse)
          this.initForm();
        })
    } else {
      this.itemService.addSubItem(this.parentItemId, dto)
        .then((res) => {
          window.location.reload()
        })
        .catch((errorResponse) => {
          this._utils.errorSnackBar(errorResponse)
          this.initForm();
        })
    }
  }

}
