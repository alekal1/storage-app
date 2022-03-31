import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {ItemDto} from "../_dto/item.dto";
import {Auth} from "../_utils/auth.util";

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
  currentPerson: string;

  constructor(private client: HttpClient,
              private _auth: Auth) {
    this.currentPerson = _auth.currentPerson;
  }

  /**
   * Gets top items
   */

  public getTopItems(): Observable<ItemDto[]> {
    return this.client.get<ItemDto[]>(`${this.itemApi}/${this.currentPerson}/items`);
  }

  /**
   * Add item
   */
  public addItem(itemDto: ItemDto): Promise<ItemDto> {
    return this.client.post<ItemDto>(`${this.itemApi}/${this.currentPerson}`, itemDto, httpOptions).toPromise();
  }

  /**
   * Add sub item
   */
  public addSubItem(itemId: number, itemDto: ItemDto): Promise<ItemDto> {
    return this.client.post<ItemDto>(`${this.itemApi}/${itemId}/${this.currentPerson}`, itemDto, httpOptions).toPromise();
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
  public searchForAnItem(searchQuery: string) {
    return this.client.get<string[]>(`${this.itemApi}/${this.currentPerson}/${searchQuery}`);
  }

  /**
   * Remove item
   */
  public removeItem(itemId: number) {
    return this.client.delete(`${this.itemApi}/${itemId}/${this.currentPerson}`);
  }
}
