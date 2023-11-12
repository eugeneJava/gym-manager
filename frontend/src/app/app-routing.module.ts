import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {
  TableManagerContainerComponent
} from "./table-manager/containers/table-manager-container/table-manager-container.component";

const routes : Routes = [
  {
    path: 'tables',
    component: TableManagerContainerComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
