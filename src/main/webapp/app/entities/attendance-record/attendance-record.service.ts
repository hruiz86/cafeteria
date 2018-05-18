import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { AttendanceRecord } from './attendance-record.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<AttendanceRecord>;

@Injectable()
export class AttendanceRecordService {

    private resourceUrl =  SERVER_API_URL + 'api/attendance-records';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(attendanceRecord: AttendanceRecord): Observable<EntityResponseType> {
        const copy = this.convert(attendanceRecord);
        return this.http.post<AttendanceRecord>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(attendanceRecord: AttendanceRecord): Observable<EntityResponseType> {
        const copy = this.convert(attendanceRecord);
        return this.http.put<AttendanceRecord>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<AttendanceRecord>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<AttendanceRecord[]>> {
        const options = createRequestOption(req);
        return this.http.get<AttendanceRecord[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<AttendanceRecord[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: AttendanceRecord = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<AttendanceRecord[]>): HttpResponse<AttendanceRecord[]> {
        const jsonResponse: AttendanceRecord[] = res.body;
        const body: AttendanceRecord[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to AttendanceRecord.
     */
    private convertItemFromServer(attendanceRecord: AttendanceRecord): AttendanceRecord {
        const copy: AttendanceRecord = Object.assign({}, attendanceRecord);
        copy.date = this.dateUtils
            .convertDateTimeFromServer(attendanceRecord.date);
        return copy;
    }

    /**
     * Convert a AttendanceRecord to a JSON which can be sent to the server.
     */
    private convert(attendanceRecord: AttendanceRecord): AttendanceRecord {
        const copy: AttendanceRecord = Object.assign({}, attendanceRecord);

        copy.date = this.dateUtils.toDate(attendanceRecord.date);
        return copy;
    }
}
