import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { LabelRoutingModule } from './label-routing.module';
import {MessageService} from "primeng/api";


@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    LabelRoutingModule
  ],
    providers : [
        MessageService
    ]
})
export class LabelModule { }
