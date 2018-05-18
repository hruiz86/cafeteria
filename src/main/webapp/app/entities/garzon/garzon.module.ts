import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CafeteriaSharedModule } from '../../shared';
import {
    GarzonService,
    GarzonPopupService,
    GarzonComponent,
    GarzonDetailComponent,
    GarzonDialogComponent,
    GarzonPopupComponent,
    GarzonDeletePopupComponent,
    GarzonDeleteDialogComponent,
    garzonRoute,
    garzonPopupRoute,
    GarzonResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...garzonRoute,
    ...garzonPopupRoute,
];

@NgModule({
    imports: [
        CafeteriaSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        GarzonComponent,
        GarzonDetailComponent,
        GarzonDialogComponent,
        GarzonDeleteDialogComponent,
        GarzonPopupComponent,
        GarzonDeletePopupComponent,
    ],
    entryComponents: [
        GarzonComponent,
        GarzonDialogComponent,
        GarzonPopupComponent,
        GarzonDeleteDialogComponent,
        GarzonDeletePopupComponent,
    ],
    providers: [
        GarzonService,
        GarzonPopupService,
        GarzonResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CafeteriaGarzonModule {}
