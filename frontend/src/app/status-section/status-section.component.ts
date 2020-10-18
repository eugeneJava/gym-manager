import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'status-section',
  templateUrl: './status-section.component.html',
  styleUrls: ['./status-section.component.css']
})
export class StatusSectionComponent implements OnInit {
  public currentDate: Date = new Date();
  public currentTime: Date;
  constructor() { }

  ngOnInit(): void {
    setInterval(() => {this.currentTime = new Date()}, 1);
  }

}
