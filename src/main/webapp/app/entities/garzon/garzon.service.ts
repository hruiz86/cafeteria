import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Garzon } from './garzon.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Garzon>;

@Injectable()
export class GarzonService {

    private resourceUrl =  SERVER_API_URL + 'api/garzons';

    constructor(private http: HttpClient) { }

    create(garzon: Garzon): Observable<EntityResponseType> {
        const copy = this.convert(garzon);
        return this.http.post<Garzon>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(garzon: Garzon): Observable<EntityResponseType> {
        const copy = this.convert(garzon);
        return this.http.put<Garzon>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Garzon>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Garzon[]>> {
        const options = createRequestOption(req);
        return this.http.get<Garzon[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Garzon[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Garzon = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Garzon[]>): HttpResponse<Garzon[]> {
        const jsonResponse: Garzon[] = res.body;
        const body: Garzon[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Garzon.
     */
    private convertItemFromServer(garzon: Garzon): Garzon {
        const copy: Garzon = Object.assign({}, garzon);
        return copy;
    }

    /**
     * Convert a Garzon to a JSON which can be sent to the server.
     */
    private convert(garzon: Garzon): Garzon {
        const copy: Garzon = Object.assign({}, garzon);
        return copy;
    }
}
