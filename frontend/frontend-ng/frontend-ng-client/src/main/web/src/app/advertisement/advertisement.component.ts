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

  constructor(private advertisementService: AdvertisementService) {}

  ngOnInit() {
    this.advertisementService.getAdvertisements().subscribe(
      advertisements => this.advertisements = advertisements
    );
  }

}
