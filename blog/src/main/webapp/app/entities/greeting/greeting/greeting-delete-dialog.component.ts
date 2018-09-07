import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGreeting } from 'app/shared/model/greeting/greeting.model';
import { GreetingService } from './greeting.service';

@Component({
    selector: 'jhi-greeting-delete-dialog',
    templateUrl: './greeting-delete-dialog.component.html'
})
export class GreetingDeleteDialogComponent {
    greeting: IGreeting;

    constructor(private greetingService: GreetingService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.greetingService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'greetingListModification',
                content: 'Deleted an greeting'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-greeting-delete-popup',
    template: ''
})
export class GreetingDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ greeting }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(GreetingDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.greeting = greeting;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
