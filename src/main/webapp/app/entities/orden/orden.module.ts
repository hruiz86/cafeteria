import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CafeteriaSharedModule } from '../../shared';
import {
    OrdenService,
    OrdenPopupService,
    OrdenComponent,
    OrdenDetailComponent,
    OrdenDialogComponent,
    OrdenPopupComponent,
    OrdenDeletePopupComponent,
    OrdenDeleteDialogComponent,
    ordenRoute,
    ordenPopupRoute,
    OrdenResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...ordenRoute,
    ...ordenPopupRoute,
];

@NgModule({
    imports: [
        CafeteriaSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        OrdenComponent,
        OrdenDetailComponent,
        OrdenDialogComponent,
        OrdenDeleteDialogComponent,
        OrdenPopupComponent,
        OrdenDeletePopupComponent,
    ],
    entryComponents: [
        OrdenComponent,
        OrdenDialogComponent,
        OrdenPopupComponent,
        OrdenDeleteDialogComponent,
        OrdenDeletePopupComponent,
    ],
    providers: [
        OrdenService,
        OrdenPopupService,
        OrdenResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CafeteriaOrdenModule {}
