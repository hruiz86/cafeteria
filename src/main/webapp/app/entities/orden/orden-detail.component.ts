import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Orden } from './orden.model';
import { OrdenService } from './orden.service';

@Component({
    selector: 'jhi-orden-detail',
    templateUrl: './orden-detail.component.html'
})
export class OrdenDetailComponent implements OnInit, OnDestroy {

    orden: Orden;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private ordenService: OrdenService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInOrdens();
    }

    load(id) {
        this.ordenService.find(id)
            .subscribe((ordenResponse: HttpResponse<Orden>) => {
                this.orden = ordenResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInOrdens() {
        this.eventSubscriber = this.eventManager.subscribe(
            'ordenListModification',
            (response) => this.load(this.orden.id)
        );
    }
}
