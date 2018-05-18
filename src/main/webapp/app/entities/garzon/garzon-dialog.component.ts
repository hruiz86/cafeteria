import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Garzon } from './garzon.model';
import { GarzonPopupService } from './garzon-popup.service';
import { GarzonService } from './garzon.service';

@Component({
    selector: 'jhi-garzon-dialog',
    templateUrl: './garzon-dialog.component.html'
})
export class GarzonDialogComponent implements OnInit {

    garzon: Garzon;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private garzonService: GarzonService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.garzon.id !== undefined) {
            this.subscribeToSaveResponse(
                this.garzonService.update(this.garzon));
        } else {
            this.subscribeToSaveResponse(
                this.garzonService.create(this.garzon));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Garzon>>) {
        result.subscribe((res: HttpResponse<Garzon>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Garzon) {
        this.eventManager.broadcast({ name: 'garzonListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-garzon-popup',
    template: ''
})
export class GarzonPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private garzonPopupService: GarzonPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.garzonPopupService
                    .open(GarzonDialogComponent as Component, params['id']);
            } else {
                this.garzonPopupService
                    .open(GarzonDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
