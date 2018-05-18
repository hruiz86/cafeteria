import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Bonos } from './bonos.model';
import { BonosPopupService } from './bonos-popup.service';
import { BonosService } from './bonos.service';
import { Garzon, GarzonService } from '../garzon';

@Component({
    selector: 'jhi-bonos-dialog',
    templateUrl: './bonos-dialog.component.html'
})
export class BonosDialogComponent implements OnInit {

    bonos: Bonos;
    isSaving: boolean;

    garzons: Garzon[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private bonosService: BonosService,
        private garzonService: GarzonService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.garzonService.query()
            .subscribe((res: HttpResponse<Garzon[]>) => { this.garzons = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.bonos.id !== undefined) {
            this.subscribeToSaveResponse(
                this.bonosService.update(this.bonos));
        } else {
            this.subscribeToSaveResponse(
                this.bonosService.create(this.bonos));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Bonos>>) {
        result.subscribe((res: HttpResponse<Bonos>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Bonos) {
        this.eventManager.broadcast({ name: 'bonosListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-bonos-popup',
    template: ''
})
export class BonosPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bonosPopupService: BonosPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.bonosPopupService
                    .open(BonosDialogComponent as Component, params['id']);
            } else {
                this.bonosPopupService
                    .open(BonosDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
