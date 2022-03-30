import {ErrorResponse} from "../_dto/error.dto";
import {SnackbarComponent} from "../snackbar/snackbar.component";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Injectable} from "@angular/core";

@Injectable({
  providedIn: 'root'
})

export class Utils {
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
      duration: 7000,
      horizontalPosition: "center",
      verticalPosition: "top"
    })
  }
}
