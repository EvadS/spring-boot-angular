import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Image} from "../models/Image";


const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};
const API_URL = environment.apiUrl;
const imageUrlSearch = API_URL + '/v1/image/search?filter=';

@Injectable({
  providedIn: 'root'
})
export class ImageDataService {

  private imageUrl = API_URL + '/v1/image/search';filter

  constructor(private http: HttpClient) {
  }

  public findAll(): Observable<Image[]> {
    return this.http.get<Image[]>(this.imageUrl, httpOptions);
  }

  findByTitle(title: any): Observable<Image[]> {
    console.log("findByTitle" + title);
    return this.http.get<Image[]>(`${imageUrlSearch}/?filter=${title}`);
  }
}
