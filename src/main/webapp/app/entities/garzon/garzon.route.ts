import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { GarzonComponent } from './garzon.component';
import { GarzonDetailComponent } from './garzon-detail.component';
import { GarzonPopupComponent } from './garzon-dialog.component';
import { GarzonDeletePopupComponent } from './garzon-delete-dialog.component';

@Injectable()
export class GarzonResolvePagingParams implements Resolve<any> {

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

export const garzonRoute: Routes = [
    {
        path: 'garzon',
        component: GarzonComponent,
        resolve: {
            'pagingParams': GarzonResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cafeteriaApp.garzon.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'garzon/:id',
        component: GarzonDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cafeteriaApp.garzon.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const garzonPopupRoute: Routes = [
    {
        path: 'garzon-new',
        component: GarzonPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cafeteriaApp.garzon.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'garzon/:id/edit',
        component: GarzonPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cafeteriaApp.garzon.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'garzon/:id/delete',
        component: GarzonDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cafeteriaApp.garzon.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
