import {Component, OnInit} from '@angular/core';

import {AdvertisementService} from '../advertisement.service';
import {Advertisement} from "../advertisement";

@Component({
  selector: 'app-advertisement',
  templateUrl: './advertisement.component.html',
  styleUrls: ['./advertisement.component.css']
})
export class AdvertisementComponent implements OnInit {

  advertisements: Advertisement[];

  constructor(private advertisementService: AdvertisementService) {
  }

  ngOnInit() {
  }

  onSearchButtonClick() {
    this.advertisements = [];
    let source = new EventSource('http://localhost:8080/simpleSearch/britax%20romer%20vajicko');
    // let source = new EventSource('http://localhost:8080/stub');

    source.addEventListener('advertisement', advertisement => {
      let parsedAdt = <Advertisement>JSON.parse(advertisement['data']);
      this.advertisements.push(parsedAdt);
    });

    source.onerror = error => {
      if (source.readyState === 0) {
        console.log('The stream has been closed by the server.');
        source.close();
      } else {
        console.error('EventSource error: ' + error);
      }
    };

    // this.advertisementService.getAdvertisements().subscribe(
    //   advertisements => this.advertisements = advertisements
    // );
  }


}
