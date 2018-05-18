/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { CafeteriaTestModule } from '../../../test.module';
import { AttendanceRecordDialogComponent } from '../../../../../../main/webapp/app/entities/attendance-record/attendance-record-dialog.component';
import { AttendanceRecordService } from '../../../../../../main/webapp/app/entities/attendance-record/attendance-record.service';
import { AttendanceRecord } from '../../../../../../main/webapp/app/entities/attendance-record/attendance-record.model';
import { GarzonService } from '../../../../../../main/webapp/app/entities/garzon';

describe('Component Tests', () => {

    describe('AttendanceRecord Management Dialog Component', () => {
        let comp: AttendanceRecordDialogComponent;
        let fixture: ComponentFixture<AttendanceRecordDialogComponent>;
        let service: AttendanceRecordService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CafeteriaTestModule],
                declarations: [AttendanceRecordDialogComponent],
                providers: [
                    GarzonService,
                    AttendanceRecordService
                ]
            })
            .overrideTemplate(AttendanceRecordDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AttendanceRecordDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AttendanceRecordService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new AttendanceRecord(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.attendanceRecord = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'attendanceRecordListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new AttendanceRecord();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.attendanceRecord = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'attendanceRecordListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
