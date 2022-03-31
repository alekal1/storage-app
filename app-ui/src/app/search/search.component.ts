import { Component, OnInit } from '@angular/core';
import {ItemService} from "../_service/item.service";

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss']
})
export class SearchComponent implements OnInit {

  username: string = localStorage.getItem("person")
  searchQuery: string = '';
  searchResult: string[] = [];

  constructor(private itemService: ItemService) { }

  ngOnInit(): void {
  }

  searchForAnItem(): void {
    this.itemService.searchForAnItem(this.username, this.searchQuery)
      .subscribe(res => {
        this.searchResult = res;
      })
  }

  clearSearch(): void {
    this.searchQuery = '';
    this.searchResult = [];
  }

}
