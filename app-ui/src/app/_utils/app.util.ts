import {ErrorResponse} from "../_dto/error.dto";
import {SnackbarComponent} from "../snackbar/snackbar.component";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Injectable} from "@angular/core";
import {PersonDto} from "../_dto/person.dto";

@Injectable({
  providedIn: 'root'
})

export class Utils {
  displayedColumns: string[] =
    ['id', 'picture', 'name', 'serialNumber',
      'color', 'size', 'lastAccessedOn',
      'subItems', 'addSubItems', 'removeItem']

  constructor(private _snackBar: MatSnackBar) {
  }

  errorSnackBar(errorResponse) {
    const error = errorResponse.error;
    let errorObj = new ErrorResponse(
      error.uuid,
      error.code,
      error.message
    );
    console.log("Got error " + errorObj)
    this._snackBar.openFromComponent(SnackbarComponent, {
      data: errorObj,
      duration: 6000,
      horizontalPosition: "center",
      verticalPosition: "top"
    })
  }

  saveToLocalStorage(personDto: PersonDto): void {
    localStorage.setItem("person", personDto.username);
    localStorage.setItem("profileType", personDto.profileType);
  }
}
