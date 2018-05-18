/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CafeteriaTestModule } from '../../../test.module';
import { AttendanceRecordComponent } from '../../../../../../main/webapp/app/entities/attendance-record/attendance-record.component';
import { AttendanceRecordService } from '../../../../../../main/webapp/app/entities/attendance-record/attendance-record.service';
import { AttendanceRecord } from '../../../../../../main/webapp/app/entities/attendance-record/attendance-record.model';

describe('Component Tests', () => {

    describe('AttendanceRecord Management Component', () => {
        let comp: AttendanceRecordComponent;
        let fixture: ComponentFixture<AttendanceRecordComponent>;
        let service: AttendanceRecordService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CafeteriaTestModule],
                declarations: [AttendanceRecordComponent],
                providers: [
                    AttendanceRecordService
                ]
            })
            .overrideTemplate(AttendanceRecordComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AttendanceRecordComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AttendanceRecordService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new AttendanceRecord(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.attendanceRecords[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
