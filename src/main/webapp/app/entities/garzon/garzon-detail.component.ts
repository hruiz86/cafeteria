import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Garzon } from './garzon.model';
import { GarzonService } from './garzon.service';

@Component({
    selector: 'jhi-garzon-detail',
    templateUrl: './garzon-detail.component.html'
})
export class GarzonDetailComponent implements OnInit, OnDestroy {

    garzon: Garzon;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private garzonService: GarzonService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInGarzons();
    }

    load(id) {
        this.garzonService.find(id)
            .subscribe((garzonResponse: HttpResponse<Garzon>) => {
                this.garzon = garzonResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInGarzons() {
        this.eventSubscriber = this.eventManager.subscribe(
            'garzonListModification',
            (response) => this.load(this.garzon.id)
        );
    }
}
