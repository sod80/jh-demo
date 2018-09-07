import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IGreeting } from 'app/shared/model/greeting/greeting.model';

type EntityResponseType = HttpResponse<IGreeting>;
type EntityArrayResponseType = HttpResponse<IGreeting[]>;

@Injectable({ providedIn: 'root' })
export class GreetingService {
    private resourceUrl = SERVER_API_URL + 'greeting/api/greetings';

    constructor(private http: HttpClient) {}

    create(greeting: IGreeting): Observable<EntityResponseType> {
        return this.http.post<IGreeting>(this.resourceUrl, greeting, { observe: 'response' });
    }

    update(greeting: IGreeting): Observable<EntityResponseType> {
        return this.http.put<IGreeting>(this.resourceUrl, greeting, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IGreeting>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IGreeting[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
