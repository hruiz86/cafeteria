import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CafeteriaSharedModule } from '../../shared';
import {
    AttendanceRecordService,
    AttendanceRecordPopupService,
    AttendanceRecordComponent,
    AttendanceRecordDetailComponent,
    AttendanceRecordDialogComponent,
    AttendanceRecordPopupComponent,
    AttendanceRecordDeletePopupComponent,
    AttendanceRecordDeleteDialogComponent,
    attendanceRecordRoute,
    attendanceRecordPopupRoute,
    AttendanceRecordResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...attendanceRecordRoute,
    ...attendanceRecordPopupRoute,
];

@NgModule({
    imports: [
        CafeteriaSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        AttendanceRecordComponent,
        AttendanceRecordDetailComponent,
        AttendanceRecordDialogComponent,
        AttendanceRecordDeleteDialogComponent,
        AttendanceRecordPopupComponent,
        AttendanceRecordDeletePopupComponent,
    ],
    entryComponents: [
        AttendanceRecordComponent,
        AttendanceRecordDialogComponent,
        AttendanceRecordPopupComponent,
        AttendanceRecordDeleteDialogComponent,
        AttendanceRecordDeletePopupComponent,
    ],
    providers: [
        AttendanceRecordService,
        AttendanceRecordPopupService,
        AttendanceRecordResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CafeteriaAttendanceRecordModule {}
