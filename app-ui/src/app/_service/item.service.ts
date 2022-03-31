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

  /**
   * Add item
   */
  public addItem(username: string, itemDto: ItemDto): Promise<ItemDto> {
    return this.client.post<ItemDto>(`${this.itemApi}/${username}`, itemDto, httpOptions).toPromise();
  }

  /**
   * Add sub item
   */
  public addSubItem(itemId: number, username: string, itemDto: ItemDto): Promise<ItemDto> {
    return this.client.post<ItemDto>(`${this.itemApi}/${itemId}/${username}`, itemDto, httpOptions).toPromise();
  }

  /**
   * Get sub items
   */
  public getSubItems(itemId: number): Observable<ItemDto[]> {
    return this.client.get<ItemDto[]>(`${this.itemApi}/${itemId}/subItems`);
  }

  /**
   * Get item size
   */
  public getItemSize(itemId: number): Observable<number> {
    return this.client.get<number>(`${this.itemApi}/${itemId}`);
  }

  /**
   * Search for an item
   */
  public searchForAnItem(username: string, searchQuery: string) {
    return this.client.get<string[]>(`${this.itemApi}/${username}/${searchQuery}`);
  }

  /**
   * Remove item
   */
  public removeItem(itemId: number, username: string) {
    return this.client.delete(`${this.itemApi}/${itemId}/${username}`);
  }
}
