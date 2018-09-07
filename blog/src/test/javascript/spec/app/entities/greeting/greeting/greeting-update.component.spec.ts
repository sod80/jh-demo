/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { BlogTestModule } from '../../../../test.module';
import { GreetingUpdateComponent } from 'app/entities/greeting/greeting/greeting-update.component';
import { GreetingService } from 'app/entities/greeting/greeting/greeting.service';
import { Greeting } from 'app/shared/model/greeting/greeting.model';

describe('Component Tests', () => {
    describe('Greeting Management Update Component', () => {
        let comp: GreetingUpdateComponent;
        let fixture: ComponentFixture<GreetingUpdateComponent>;
        let service: GreetingService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BlogTestModule],
                declarations: [GreetingUpdateComponent]
            })
                .overrideTemplate(GreetingUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(GreetingUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GreetingService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Greeting(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.greeting = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Greeting();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.greeting = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
