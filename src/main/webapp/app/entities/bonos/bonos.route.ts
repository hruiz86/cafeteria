import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { BonosComponent } from './bonos.component';
import { BonosDetailComponent } from './bonos-detail.component';
import { BonosPopupComponent } from './bonos-dialog.component';
import { BonosDeletePopupComponent } from './bonos-delete-dialog.component';

@Injectable()
export class BonosResolvePagingParams implements Resolve<any> {

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

export const bonosRoute: Routes = [
    {
        path: 'bonos',
        component: BonosComponent,
        resolve: {
            'pagingParams': BonosResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cafeteriaApp.bonos.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'bonos/:id',
        component: BonosDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cafeteriaApp.bonos.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bonosPopupRoute: Routes = [
    {
        path: 'bonos-new',
        component: BonosPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cafeteriaApp.bonos.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'bonos/:id/edit',
        component: BonosPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cafeteriaApp.bonos.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'bonos/:id/delete',
        component: BonosDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cafeteriaApp.bonos.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
