import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGreeting } from 'app/shared/model/greeting/greeting.model';

@Component({
    selector: 'jhi-greeting-detail',
    templateUrl: './greeting-detail.component.html'
})
export class GreetingDetailComponent implements OnInit {
    greeting: IGreeting;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ greeting }) => {
            this.greeting = greeting;
        });
    }

    previousState() {
        window.history.back();
    }
}
