import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Bonos } from './bonos.model';
import { BonosPopupService } from './bonos-popup.service';
import { BonosService } from './bonos.service';

@Component({
    selector: 'jhi-bonos-delete-dialog',
    templateUrl: './bonos-delete-dialog.component.html'
})
export class BonosDeleteDialogComponent {

    bonos: Bonos;

    constructor(
        private bonosService: BonosService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.bonosService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'bonosListModification',
                content: 'Deleted an bonos'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-bonos-delete-popup',
    template: ''
})
export class BonosDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bonosPopupService: BonosPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.bonosPopupService
                .open(BonosDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
