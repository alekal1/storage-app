import { Component, OnInit } from '@angular/core';
import {ItemDto} from "../_dto/item.dto";
import {ActivatedRoute, ParamMap, Router} from "@angular/router";
import {ItemService} from "../_service/item.service";
import { Location } from '@angular/common';
import {switchMap} from "rxjs/operators";
import {Utils} from "../_utils/app.util";

@Component({
  selector: 'app-sub-item-dashboard',
  templateUrl: './sub-item-dashboard.component.html',
  styleUrls: ['./sub-item-dashboard.component.scss']
})
export class SubItemDashboardComponent implements OnInit {

  subItems: ItemDto[];
  size: number = undefined;
  parentItemId: number = undefined;
  currentItemId: number = undefined;

  displayedColumns: string[];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private itemService: ItemService,
    private location: Location,
    private _utils: Utils
  ) {
    this.displayedColumns = _utils.displayedColumns;
  }

  ngOnInit(): void {
    this.getSubItems();
  }

  private getSubItems(): void {
    this.route.paramMap
      .pipe(switchMap((params: ParamMap) =>  this.itemService.getSubItems(Number(params.get("id")))))
      .subscribe((res) => this.subItems = res)
    this.currentItemId = Number(this.route.snapshot.paramMap.get('id'))
    this.getItemSize();
  }

  private getItemSize(): void {
    this.itemService.getItemSize(this.currentItemId).subscribe(
      res => {
        this.size = res;
      }
    )
  }

  goBack(): void {
    this.location.back();
  }

  addSubItem(id: number) {
    this.parentItemId = id;
  }

  removeItem(id: number): void {
    this.itemService.removeItem(id)
      .toPromise()
      .then((res) => window.location.reload())
      .catch((errorResponse) => {
        this._utils.errorSnackBar(errorResponse);
      })
  }
}
