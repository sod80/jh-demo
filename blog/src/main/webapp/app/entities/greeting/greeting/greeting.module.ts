import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BlogSharedModule } from 'app/shared';
import {
    GreetingComponent,
    GreetingDetailComponent,
    GreetingUpdateComponent,
    GreetingDeletePopupComponent,
    GreetingDeleteDialogComponent,
    greetingRoute,
    greetingPopupRoute
} from './';

const ENTITY_STATES = [...greetingRoute, ...greetingPopupRoute];

@NgModule({
    imports: [BlogSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        GreetingComponent,
        GreetingDetailComponent,
        GreetingUpdateComponent,
        GreetingDeleteDialogComponent,
        GreetingDeletePopupComponent
    ],
    entryComponents: [GreetingComponent, GreetingUpdateComponent, GreetingDeleteDialogComponent, GreetingDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BlogGreetingModule {}
