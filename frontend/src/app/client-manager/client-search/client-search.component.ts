import {Component, OnInit} from '@angular/core';
import {FormBuilder} from "@angular/forms";
import {ToastrService} from "ngx-toastr";
import {ClientService} from "../client-add/services/client.service";
import {iif, Observable, of} from "rxjs";
import {debounceTime, distinctUntilChanged, switchMap} from "rxjs/operators";
import {Client} from "../../model/app.models";

@Component({
  selector: 'client-search',
  templateUrl: './client-search.component.html',
  styleUrls: ['./client-search.component.css']
})
export class ClientSearchComponent implements OnInit {

  public client: Client;

  constructor(private fb: FormBuilder,
              private toastr: ToastrService,
              private clientService: ClientService) { }

  ngOnInit(): void {

  }

  search = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(450),
      distinctUntilChanged(),
      switchMap(term => iif(() => term.length >= 2, this.clientService.search(term), of([])))
    )

  formatter = (x: {name: string, secondName: string}) => x.name + ' ' + x.secondName;

}
