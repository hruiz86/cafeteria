import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AttendanceRecord } from './attendance-record.model';
import { AttendanceRecordPopupService } from './attendance-record-popup.service';
import { AttendanceRecordService } from './attendance-record.service';

@Component({
    selector: 'jhi-attendance-record-dialog',
    templateUrl: './attendance-record-dialog.component.html'
})
export class AttendanceRecordDialogComponent implements OnInit {

    attendanceRecord: AttendanceRecord;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private attendanceRecordService: AttendanceRecordService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.attendanceRecord.id !== undefined) {
            this.subscribeToSaveResponse(
                this.attendanceRecordService.update(this.attendanceRecord));
        } else {
            this.subscribeToSaveResponse(
                this.attendanceRecordService.create(this.attendanceRecord));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<AttendanceRecord>>) {
        result.subscribe((res: HttpResponse<AttendanceRecord>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: AttendanceRecord) {
        this.eventManager.broadcast({ name: 'attendanceRecordListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-attendance-record-popup',
    template: ''
})
export class AttendanceRecordPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private attendanceRecordPopupService: AttendanceRecordPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.attendanceRecordPopupService
                    .open(AttendanceRecordDialogComponent as Component, params['id']);
            } else {
                this.attendanceRecordPopupService
                    .open(AttendanceRecordDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
