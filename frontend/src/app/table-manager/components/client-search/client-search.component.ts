import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {FormBuilder} from '@angular/forms';
import {iif, Observable, of} from 'rxjs';
import {debounceTime, distinctUntilChanged, switchMap} from 'rxjs/operators';
import {ClientService} from '../../services/client.service';
import {NgbTypeaheadSelectItemEvent} from '@ng-bootstrap/ng-bootstrap';
import {Client} from '../../model/model';

@Component({
  selector: 'app-client-search',
  templateUrl: './client-search.component.html',
  styleUrls: ['./client-search.component.css']
})
export class ClientSearchComponent implements OnInit {

  @Output() clientSelected: EventEmitter<Client> = new EventEmitter<Client>();

  constructor(private fb: FormBuilder,
              private clientService: ClientService) { }

  ngOnInit(): void {

  }

  search = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(450),
      distinctUntilChanged(),
      switchMap(term => iif(() => term.length >= 2, this.clientService.search(term), of([])))
    )

  formatter = (x: {name: string, nick: string}) => x.name + ' ' + x.nick;

  public handleSelectedClient($event: NgbTypeaheadSelectItemEvent<Client>) {
    $event.preventDefault();
    this.clientSelected.emit($event.item);
  }
}
