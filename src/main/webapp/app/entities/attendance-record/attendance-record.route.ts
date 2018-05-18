import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { AttendanceRecordComponent } from './attendance-record.component';
import { AttendanceRecordDetailComponent } from './attendance-record-detail.component';
import { AttendanceRecordPopupComponent } from './attendance-record-dialog.component';
import { AttendanceRecordDeletePopupComponent } from './attendance-record-delete-dialog.component';

@Injectable()
export class AttendanceRecordResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const attendanceRecordRoute: Routes = [
    {
        path: 'attendance-record',
        component: AttendanceRecordComponent,
        resolve: {
            'pagingParams': AttendanceRecordResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cafeteriaApp.attendanceRecord.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'attendance-record/:id',
        component: AttendanceRecordDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cafeteriaApp.attendanceRecord.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const attendanceRecordPopupRoute: Routes = [
    {
        path: 'attendance-record-new',
        component: AttendanceRecordPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cafeteriaApp.attendanceRecord.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'attendance-record/:id/edit',
        component: AttendanceRecordPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cafeteriaApp.attendanceRecord.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'attendance-record/:id/delete',
        component: AttendanceRecordDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cafeteriaApp.attendanceRecord.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
