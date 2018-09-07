/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BlogTestModule } from '../../../../test.module';
import { GreetingDetailComponent } from 'app/entities/greeting/greeting/greeting-detail.component';
import { Greeting } from 'app/shared/model/greeting/greeting.model';

describe('Component Tests', () => {
    describe('Greeting Management Detail Component', () => {
        let comp: GreetingDetailComponent;
        let fixture: ComponentFixture<GreetingDetailComponent>;
        const route = ({ data: of({ greeting: new Greeting(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BlogTestModule],
                declarations: [GreetingDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(GreetingDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(GreetingDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.greeting).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
