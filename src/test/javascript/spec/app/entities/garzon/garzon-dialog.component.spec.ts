/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { CafeteriaTestModule } from '../../../test.module';
import { GarzonDialogComponent } from '../../../../../../main/webapp/app/entities/garzon/garzon-dialog.component';
import { GarzonService } from '../../../../../../main/webapp/app/entities/garzon/garzon.service';
import { Garzon } from '../../../../../../main/webapp/app/entities/garzon/garzon.model';
import { OrdenService } from '../../../../../../main/webapp/app/entities/orden';
import { BonosService } from '../../../../../../main/webapp/app/entities/bonos';
import { AttendanceRecordService } from '../../../../../../main/webapp/app/entities/attendance-record';

describe('Component Tests', () => {

    describe('Garzon Management Dialog Component', () => {
        let comp: GarzonDialogComponent;
        let fixture: ComponentFixture<GarzonDialogComponent>;
        let service: GarzonService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CafeteriaTestModule],
                declarations: [GarzonDialogComponent],
                providers: [
                    OrdenService,
                    BonosService,
                    AttendanceRecordService,
                    GarzonService
                ]
            })
            .overrideTemplate(GarzonDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GarzonDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GarzonService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Garzon(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.garzon = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'garzonListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Garzon();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.garzon = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'garzonListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
