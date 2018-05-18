import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Orden } from './orden.model';
import { OrdenPopupService } from './orden-popup.service';
import { OrdenService } from './orden.service';

@Component({
    selector: 'jhi-orden-delete-dialog',
    templateUrl: './orden-delete-dialog.component.html'
})
export class OrdenDeleteDialogComponent {

    orden: Orden;

    constructor(
        private ordenService: OrdenService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.ordenService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'ordenListModification',
                content: 'Deleted an orden'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-orden-delete-popup',
    template: ''
})
export class OrdenDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ordenPopupService: OrdenPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.ordenPopupService
                .open(OrdenDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
