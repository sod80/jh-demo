import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Greeting } from 'app/shared/model/greeting/greeting.model';
import { GreetingService } from './greeting.service';
import { GreetingComponent } from './greeting.component';
import { GreetingDetailComponent } from './greeting-detail.component';
import { GreetingUpdateComponent } from './greeting-update.component';
import { GreetingDeletePopupComponent } from './greeting-delete-dialog.component';
import { IGreeting } from 'app/shared/model/greeting/greeting.model';

@Injectable({ providedIn: 'root' })
export class GreetingResolve implements Resolve<IGreeting> {
    constructor(private service: GreetingService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((greeting: HttpResponse<Greeting>) => greeting.body));
        }
        return of(new Greeting());
    }
}

export const greetingRoute: Routes = [
    {
        path: 'greeting',
        component: GreetingComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Greetings'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'greeting/:id/view',
        component: GreetingDetailComponent,
        resolve: {
            greeting: GreetingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Greetings'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'greeting/new',
        component: GreetingUpdateComponent,
        resolve: {
            greeting: GreetingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Greetings'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'greeting/:id/edit',
        component: GreetingUpdateComponent,
        resolve: {
            greeting: GreetingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Greetings'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const greetingPopupRoute: Routes = [
    {
        path: 'greeting/:id/delete',
        component: GreetingDeletePopupComponent,
        resolve: {
            greeting: GreetingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Greetings'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
