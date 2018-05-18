import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CafeteriaSharedModule } from '../../shared';
import {
    BonosService,
    BonosPopupService,
    BonosComponent,
    BonosDetailComponent,
    BonosDialogComponent,
    BonosPopupComponent,
    BonosDeletePopupComponent,
    BonosDeleteDialogComponent,
    bonosRoute,
    bonosPopupRoute,
    BonosResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...bonosRoute,
    ...bonosPopupRoute,
];

@NgModule({
    imports: [
        CafeteriaSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        BonosComponent,
        BonosDetailComponent,
        BonosDialogComponent,
        BonosDeleteDialogComponent,
        BonosPopupComponent,
        BonosDeletePopupComponent,
    ],
    entryComponents: [
        BonosComponent,
        BonosDialogComponent,
        BonosPopupComponent,
        BonosDeleteDialogComponent,
        BonosDeletePopupComponent,
    ],
    providers: [
        BonosService,
        BonosPopupService,
        BonosResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CafeteriaBonosModule {}
