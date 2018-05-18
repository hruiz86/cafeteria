import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Orden } from './orden.model';
import { OrdenPopupService } from './orden-popup.service';
import { OrdenService } from './orden.service';
import { Garzon, GarzonService } from '../garzon';
import { Product, ProductService } from '../product';

@Component({
    selector: 'jhi-orden-dialog',
    templateUrl: './orden-dialog.component.html'
})
export class OrdenDialogComponent implements OnInit {

    orden: Orden;
    isSaving: boolean;

    garzons: Garzon[];

    products: Product[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private ordenService: OrdenService,
        private garzonService: GarzonService,
        private productService: ProductService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.garzonService.query()
            .subscribe((res: HttpResponse<Garzon[]>) => { this.garzons = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.productService.query()
            .subscribe((res: HttpResponse<Product[]>) => { this.products = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.orden.id !== undefined) {
            this.subscribeToSaveResponse(
                this.ordenService.update(this.orden));
        } else {
            this.subscribeToSaveResponse(
                this.ordenService.create(this.orden));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Orden>>) {
        result.subscribe((res: HttpResponse<Orden>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Orden) {
        this.eventManager.broadcast({ name: 'ordenListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackGarzonById(index: number, item: Garzon) {
        return item.id;
    }

    trackProductById(index: number, item: Product) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-orden-popup',
    template: ''
})
export class OrdenPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ordenPopupService: OrdenPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.ordenPopupService
                    .open(OrdenDialogComponent as Component, params['id']);
            } else {
                this.ordenPopupService
                    .open(OrdenDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
