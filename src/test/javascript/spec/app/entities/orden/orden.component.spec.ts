/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CafeteriaTestModule } from '../../../test.module';
import { OrdenComponent } from '../../../../../../main/webapp/app/entities/orden/orden.component';
import { OrdenService } from '../../../../../../main/webapp/app/entities/orden/orden.service';
import { Orden } from '../../../../../../main/webapp/app/entities/orden/orden.model';

describe('Component Tests', () => {

    describe('Orden Management Component', () => {
        let comp: OrdenComponent;
        let fixture: ComponentFixture<OrdenComponent>;
        let service: OrdenService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CafeteriaTestModule],
                declarations: [OrdenComponent],
                providers: [
                    OrdenService
                ]
            })
            .overrideTemplate(OrdenComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OrdenComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrdenService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Orden(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.ordens[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
