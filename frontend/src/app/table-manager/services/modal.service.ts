import {Injectable} from '@angular/core';
import {from, Observable} from 'rxjs';
import {NgbModal, NgbModalOptions} from '@ng-bootstrap/ng-bootstrap';
import {NgbModalRef} from '@ng-bootstrap/ng-bootstrap/modal/modal-ref';

@Injectable({
  providedIn: 'root'
})
export class ModalService {

  constructor(private modalService: NgbModal) { }

  public show(content: any, inputs?: any): Observable<any> {
    const options : NgbModalOptions = {
      backdrop : 'static',
      keyboard : false
    };

    const ngbModalRef : NgbModalRef = this.modalService.open(content, options);

    if (inputs) {
      for (const [key, value] of Object.entries(inputs)) {
        ngbModalRef.componentInstance[key] = value;
      }
    }

    return from(ngbModalRef.result);
  }
}
