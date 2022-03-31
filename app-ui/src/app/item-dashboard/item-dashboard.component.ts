import { Component, OnInit } from '@angular/core';
import {ItemService} from "../_service/item.service";
import {ItemDto} from "../_dto/item.dto";
import {Router} from "@angular/router";
import {Utils} from "../_utils/app.util";
import {Auth} from "../_utils/auth.util";

@Component({
  selector: 'app-item-dashboard',
  templateUrl: './item-dashboard.component.html',
  styleUrls: ['./item-dashboard.component.scss']
})
export class ItemDashboardComponent implements OnInit {

  person: string;
  itemId: number = undefined;
  topItems: ItemDto[];
  displayedColumns: string[];

  constructor(private itemService: ItemService,
              private router: Router,
              private _utils: Utils,
              private _auth: Auth) {
    this.person = _auth.currentPerson;
    this.displayedColumns = _utils.displayedColumns;
  }

  ngOnInit(): void {
    this.getTopItems();
  }

  private getTopItems(): void {
    this.itemService.getTopItems().subscribe(
      res => {
        this.topItems = res;
      }
    )
  }

  addSubItem(id: number): void {
    this.itemId = id;
  }

  removeItem(id: number): void {
    this.itemService.removeItem(id)
      .toPromise()
      .then((res) => window.location.reload())
      .catch((errorResponse) => {
        this._utils.errorSnackBar(errorResponse);
      })
  }

  logOut(): void {
    localStorage.clear();
    this.router.navigateByUrl("/login").then(r => console.log("Log out"))
  }
}
