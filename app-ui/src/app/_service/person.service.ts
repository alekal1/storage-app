import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {PersonDto} from "../_dto/person.dto";

const httpOptions = {
  headers: new HttpHeaders({
    "Content-type": 'application/json'
  }),
  resolveWithFullResponse: true
};

@Injectable({
  providedIn: 'root'
})

export class PersonService {
  readonly personApi = "http://localhost:8080/api/person"

  constructor(private client: HttpClient) {
  }

  /**
   * Check user can login
   */
  public async loginUser(personDto: PersonDto): Promise<PersonDto> {
    return this.client.post<PersonDto>(`${this.personApi}/login`, personDto, httpOptions).toPromise();
  }
}
