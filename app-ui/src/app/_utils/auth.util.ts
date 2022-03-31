import {Injectable} from "@angular/core";

@Injectable({
  providedIn: 'root'
})

export class Auth {
  currentPerson: string;

  constructor() {
    this.currentPerson = localStorage.getItem('person')
  }
}
