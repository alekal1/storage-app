import { Component, OnInit } from '@angular/core';
import {ItemService} from "../_service/item.service";
import {ItemDto} from "../_dto/item.dto";

@Component({
  selector: 'app-item-dashboard',
  templateUrl: './item-dashboard.component.html',
  styleUrls: ['./item-dashboard.component.scss']
})
export class ItemDashboardComponent implements OnInit {

  person: string = localStorage.getItem("person")
  itemId: number = undefined;

  constructor(private itemService: ItemService) { }

  topItems: ItemDto[];
  displayedColumns: string[] =
    ['id', 'picture', 'name', 'serialNumber',
      'color', 'size', 'lastAccessedOn',
      'subItems', 'addSubItems']

  ngOnInit(): void {
    this.getTopItems();
  }

  private getTopItems() {
    this.itemService.getTopItems(this.person).subscribe(
      res => {
        this.topItems = res;
      }
    )
  }

  addSubItem(id: number) {
    this.itemId = id;
  }
}
