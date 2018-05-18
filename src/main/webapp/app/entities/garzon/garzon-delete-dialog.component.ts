import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Garzon } from './garzon.model';
import { GarzonPopupService } from './garzon-popup.service';
import { GarzonService } from './garzon.service';

@Component({
    selector: 'jhi-garzon-delete-dialog',
    templateUrl: './garzon-delete-dialog.component.html'
})
export class GarzonDeleteDialogComponent {

    garzon: Garzon;

    constructor(
        private garzonService: GarzonService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.garzonService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'garzonListModification',
                content: 'Deleted an garzon'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-garzon-delete-popup',
    template: ''
})
export class GarzonDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private garzonPopupService: GarzonPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.garzonPopupService
                .open(GarzonDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
