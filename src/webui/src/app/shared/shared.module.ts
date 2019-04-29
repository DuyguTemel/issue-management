import {NgModule} from '@angular/core';
import {TranslateModule} from "@ngx-translate/core";
import {BsModalRef, ModalModule} from "ngx-bootstrap";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {ConfirmationComponent} from './confirmation/confirmation.component';
import {CommonModule} from "@angular/common";

@NgModule({
  declarations: [ConfirmationComponent],
  entryComponents: [
    ConfirmationComponent
  ],
  imports: [FormsModule, ModalModule.forRoot(), CommonModule],
  providers: [
    BsModalRef
  ],
  exports: [
    TranslateModule,
    ModalModule,
    ReactiveFormsModule,
    ConfirmationComponent
  ]
})
export class SharedModule {
}
