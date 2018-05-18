/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { CafeteriaTestModule } from '../../../test.module';
import { OrdenDialogComponent } from '../../../../../../main/webapp/app/entities/orden/orden-dialog.component';
import { OrdenService } from '../../../../../../main/webapp/app/entities/orden/orden.service';
import { Orden } from '../../../../../../main/webapp/app/entities/orden/orden.model';
import { GarzonService } from '../../../../../../main/webapp/app/entities/garzon';
import { ProductService } from '../../../../../../main/webapp/app/entities/product';

describe('Component Tests', () => {

    describe('Orden Management Dialog Component', () => {
        let comp: OrdenDialogComponent;
        let fixture: ComponentFixture<OrdenDialogComponent>;
        let service: OrdenService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CafeteriaTestModule],
                declarations: [OrdenDialogComponent],
                providers: [
                    GarzonService,
                    ProductService,
                    OrdenService
                ]
            })
            .overrideTemplate(OrdenDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OrdenDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrdenService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Orden(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.orden = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'ordenListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Orden();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.orden = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'ordenListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
