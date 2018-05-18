import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { AttendanceRecord } from './attendance-record.model';
import { AttendanceRecordService } from './attendance-record.service';

@Component({
    selector: 'jhi-attendance-record-detail',
    templateUrl: './attendance-record-detail.component.html'
})
export class AttendanceRecordDetailComponent implements OnInit, OnDestroy {

    attendanceRecord: AttendanceRecord;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private attendanceRecordService: AttendanceRecordService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAttendanceRecords();
    }

    load(id) {
        this.attendanceRecordService.find(id)
            .subscribe((attendanceRecordResponse: HttpResponse<AttendanceRecord>) => {
                this.attendanceRecord = attendanceRecordResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAttendanceRecords() {
        this.eventSubscriber = this.eventManager.subscribe(
            'attendanceRecordListModification',
            (response) => this.load(this.attendanceRecord.id)
        );
    }
}
