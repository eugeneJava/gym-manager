import {Component, OnInit} from '@angular/core';
import {AbstractControl, AsyncValidatorFn, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ClientService} from '../services/client.service';
import {Client} from '../../../model/app.models';
import {NameService} from '../services/name.service';
import {iif, Observable, of} from 'rxjs';
import {debounceTime, distinctUntilChanged, map, switchMap} from 'rxjs/operators';
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'client-add',
  templateUrl: './client-add.component.html',
  styleUrls: ['./client-add.component.css']
})
export class ClientAddComponent implements OnInit {
  public form: FormGroup;

  constructor(private fb: FormBuilder,
              private toastr: ToastrService,
              private clientService: ClientService,
              private nameService: NameService) {
  }

  ngOnInit(): void {
    this.form = this.fb.group({
      name: [null, [Validators.required]],
      phone: ['+380'],
      secondName: null,
    });

    this.phone.setAsyncValidators(this.phoneValidator());
  }

  public addClient(): void {
    this.form.updateValueAndValidity();
    if (this.form.invalid) {
      return;
    }

    const client: Client = this.form.value as Client;
    this.clientService.addClient(client).subscribe((newClient) => {
      if (newClient) {
        this.resetForm();
        this.toastr.success(newClient.name + ' ' + (newClient.secondName ? newClient.secondName : '') + ' успешно добавлен!', '', {
          positionClass: 'toast-top-left'
        } );
      } else {
        this.toastr.warning('Клиент с номером ' + client.phone + ' уже существует');
      }
    });
  }

  public clear():void {
    this.resetForm();
  }

   private resetForm(): void {
     this.form.reset();
     this.phone.setValue('+380');
   }

  public get phone(): AbstractControl {
    return this.form.get('phone');
  }

  public get name(): AbstractControl {
    return this.form.get('name');
  }

  public isInvalid(control: AbstractControl): boolean {
    return control.dirty && control.invalid
  }

  search = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(200),
      distinctUntilChanged(),
      switchMap(term => iif(() => term.length >= 2, this.nameService.search(term), of([])))
    );

  phoneValidator(): AsyncValidatorFn {
    return (control: AbstractControl): Observable<{ [key: string]: any } | null> => {
      let phone: string = control.value;
      if (!phone) {
        return of({'invalidFormat': true})
      }

      if (!phone.match('^\\+380[0-9]{9}$')) {
        return of({'invalidFormat': true})
      }

      return this.clientService.getClient(phone)
        .pipe(
          map(nameExists => {
            if (nameExists) {
              return {'phoneExists': true}
            } else {
              return null;
            }
          })
        );
    };
  }

  public hasError(control: AbstractControl, errorName: string): boolean {
    return control.errors && control.errors.hasOwnProperty(errorName);
  }
}
