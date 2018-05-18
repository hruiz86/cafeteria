/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { CafeteriaTestModule } from '../../../test.module';
import { AttendanceRecordDetailComponent } from '../../../../../../main/webapp/app/entities/attendance-record/attendance-record-detail.component';
import { AttendanceRecordService } from '../../../../../../main/webapp/app/entities/attendance-record/attendance-record.service';
import { AttendanceRecord } from '../../../../../../main/webapp/app/entities/attendance-record/attendance-record.model';

describe('Component Tests', () => {

    describe('AttendanceRecord Management Detail Component', () => {
        let comp: AttendanceRecordDetailComponent;
        let fixture: ComponentFixture<AttendanceRecordDetailComponent>;
        let service: AttendanceRecordService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CafeteriaTestModule],
                declarations: [AttendanceRecordDetailComponent],
                providers: [
                    AttendanceRecordService
                ]
            })
            .overrideTemplate(AttendanceRecordDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AttendanceRecordDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AttendanceRecordService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new AttendanceRecord(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.attendanceRecord).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
