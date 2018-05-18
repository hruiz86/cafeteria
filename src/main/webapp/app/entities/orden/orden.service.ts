import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Orden } from './orden.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Orden>;

@Injectable()
export class OrdenService {

    private resourceUrl =  SERVER_API_URL + 'api/ordens';

    constructor(private http: HttpClient) { }

    create(orden: Orden): Observable<EntityResponseType> {
        const copy = this.convert(orden);
        return this.http.post<Orden>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(orden: Orden): Observable<EntityResponseType> {
        const copy = this.convert(orden);
        return this.http.put<Orden>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Orden>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Orden[]>> {
        const options = createRequestOption(req);
        return this.http.get<Orden[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Orden[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Orden = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Orden[]>): HttpResponse<Orden[]> {
        const jsonResponse: Orden[] = res.body;
        const body: Orden[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Orden.
     */
    private convertItemFromServer(orden: Orden): Orden {
        const copy: Orden = Object.assign({}, orden);
        return copy;
    }

    /**
     * Convert a Orden to a JSON which can be sent to the server.
     */
    private convert(orden: Orden): Orden {
        const copy: Orden = Object.assign({}, orden);
        return copy;
    }
}
