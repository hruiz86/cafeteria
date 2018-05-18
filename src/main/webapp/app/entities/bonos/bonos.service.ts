import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Bonos } from './bonos.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Bonos>;

@Injectable()
export class BonosService {

    private resourceUrl =  SERVER_API_URL + 'api/bonos';

    constructor(private http: HttpClient) { }

    create(bonos: Bonos): Observable<EntityResponseType> {
        const copy = this.convert(bonos);
        return this.http.post<Bonos>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(bonos: Bonos): Observable<EntityResponseType> {
        const copy = this.convert(bonos);
        return this.http.put<Bonos>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Bonos>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Bonos[]>> {
        const options = createRequestOption(req);
        return this.http.get<Bonos[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Bonos[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Bonos = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Bonos[]>): HttpResponse<Bonos[]> {
        const jsonResponse: Bonos[] = res.body;
        const body: Bonos[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Bonos.
     */
    private convertItemFromServer(bonos: Bonos): Bonos {
        const copy: Bonos = Object.assign({}, bonos);
        return copy;
    }

    /**
     * Convert a Bonos to a JSON which can be sent to the server.
     */
    private convert(bonos: Bonos): Bonos {
        const copy: Bonos = Object.assign({}, bonos);
        return copy;
    }
}
