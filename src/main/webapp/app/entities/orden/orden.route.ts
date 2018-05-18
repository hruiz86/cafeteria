import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { OrdenComponent } from './orden.component';
import { OrdenDetailComponent } from './orden-detail.component';
import { OrdenPopupComponent } from './orden-dialog.component';
import { OrdenDeletePopupComponent } from './orden-delete-dialog.component';

@Injectable()
export class OrdenResolvePagingParams implements Resolve<any> {

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

export const ordenRoute: Routes = [
    {
        path: 'orden',
        component: OrdenComponent,
        resolve: {
            'pagingParams': OrdenResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cafeteriaApp.orden.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'orden/:id',
        component: OrdenDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cafeteriaApp.orden.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const ordenPopupRoute: Routes = [
    {
        path: 'orden-new',
        component: OrdenPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cafeteriaApp.orden.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'orden/:id/edit',
        component: OrdenPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cafeteriaApp.orden.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'orden/:id/delete',
        component: OrdenDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cafeteriaApp.orden.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
