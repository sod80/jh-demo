import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IGreeting } from 'app/shared/model/greeting/greeting.model';
import { GreetingService } from './greeting.service';

@Component({
    selector: 'jhi-greeting-update',
    templateUrl: './greeting-update.component.html'
})
export class GreetingUpdateComponent implements OnInit {
    private _greeting: IGreeting;
    isSaving: boolean;

    constructor(private greetingService: GreetingService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ greeting }) => {
            this.greeting = greeting;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.greeting.id !== undefined) {
            this.subscribeToSaveResponse(this.greetingService.update(this.greeting));
        } else {
            this.subscribeToSaveResponse(this.greetingService.create(this.greeting));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IGreeting>>) {
        result.subscribe((res: HttpResponse<IGreeting>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get greeting() {
        return this._greeting;
    }

    set greeting(greeting: IGreeting) {
        this._greeting = greeting;
    }
}
