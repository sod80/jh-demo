/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BlogTestModule } from '../../../../test.module';
import { GreetingDeleteDialogComponent } from 'app/entities/greeting/greeting/greeting-delete-dialog.component';
import { GreetingService } from 'app/entities/greeting/greeting/greeting.service';

describe('Component Tests', () => {
    describe('Greeting Management Delete Component', () => {
        let comp: GreetingDeleteDialogComponent;
        let fixture: ComponentFixture<GreetingDeleteDialogComponent>;
        let service: GreetingService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BlogTestModule],
                declarations: [GreetingDeleteDialogComponent]
            })
                .overrideTemplate(GreetingDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(GreetingDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GreetingService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
