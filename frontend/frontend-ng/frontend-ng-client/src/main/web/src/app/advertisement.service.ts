import {Injectable} from '@angular/core';

import {Observable, of} from 'rxjs';

import {Advertisement} from "./advertisement";
import {ADVERTISEMENTS} from "./mock-advertisements";


@Injectable({
  providedIn: 'root'
})
export class AdvertisementService {

  constructor() { }

  getAdvertisements(): Observable<Advertisement[]> {
    return of(ADVERTISEMENTS);
  }
}
