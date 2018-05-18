import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AttendanceRecord } from './attendance-record.model';
import { AttendanceRecordPopupService } from './attendance-record-popup.service';
import { AttendanceRecordService } from './attendance-record.service';

@Component({
    selector: 'jhi-attendance-record-delete-dialog',
    templateUrl: './attendance-record-delete-dialog.component.html'
})
export class AttendanceRecordDeleteDialogComponent {

    attendanceRecord: AttendanceRecord;

    constructor(
        private attendanceRecordService: AttendanceRecordService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.attendanceRecordService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'attendanceRecordListModification',
                content: 'Deleted an attendanceRecord'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-attendance-record-delete-popup',
    template: ''
})
export class AttendanceRecordDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private attendanceRecordPopupService: AttendanceRecordPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.attendanceRecordPopupService
                .open(AttendanceRecordDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
