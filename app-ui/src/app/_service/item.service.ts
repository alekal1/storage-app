import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {ItemDto} from "../_dto/item.dto";

const httpOptions = {
  headers: new HttpHeaders({
    "Content-type": 'application/json'
  }),
  resolveWithFullResponse: true
};

@Injectable({
  providedIn: 'root'
})

export class ItemService {
  readonly itemApi = "http://localhost:8080/api/item"

  constructor(private client: HttpClient) {
  }

  /**
   * Gets top items
   */

  public getTopItems(username: string): Observable<ItemDto[]> {
    return this.client.get<ItemDto[]>(`${this.itemApi}/${username}/items`);
  }

}
