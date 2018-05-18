import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { AttendanceRecord } from './attendance-record.model';
import { AttendanceRecordService } from './attendance-record.service';

@Injectable()
export class AttendanceRecordPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private attendanceRecordService: AttendanceRecordService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.attendanceRecordService.find(id)
                    .subscribe((attendanceRecordResponse: HttpResponse<AttendanceRecord>) => {
                        const attendanceRecord: AttendanceRecord = attendanceRecordResponse.body;
                        attendanceRecord.date = this.datePipe
                            .transform(attendanceRecord.date, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.attendanceRecordModalRef(component, attendanceRecord);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.attendanceRecordModalRef(component, new AttendanceRecord());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    attendanceRecordModalRef(component: Component, attendanceRecord: AttendanceRecord): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.attendanceRecord = attendanceRecord;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
