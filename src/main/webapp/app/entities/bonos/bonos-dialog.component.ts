import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Bonos } from './bonos.model';
import { BonosPopupService } from './bonos-popup.service';
import { BonosService } from './bonos.service';

@Component({
    selector: 'jhi-bonos-dialog',
    templateUrl: './bonos-dialog.component.html'
})
export class BonosDialogComponent implements OnInit {

    bonos: Bonos;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private bonosService: BonosService,
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
