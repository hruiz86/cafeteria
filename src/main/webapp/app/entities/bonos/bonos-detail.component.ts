import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Bonos } from './bonos.model';
import { BonosService } from './bonos.service';

@Component({
    selector: 'jhi-bonos-detail',
    templateUrl: './bonos-detail.component.html'
})
export class BonosDetailComponent implements OnInit, OnDestroy {

    bonos: Bonos;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private bonosService: BonosService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBonos();
    }

    load(id) {
        this.bonosService.find(id)
            .subscribe((bonosResponse: HttpResponse<Bonos>) => {
                this.bonos = bonosResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBonos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'bonosListModification',
            (response) => this.load(this.bonos.id)
        );
    }
}
