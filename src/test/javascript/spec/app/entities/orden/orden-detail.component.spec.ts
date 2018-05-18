/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { CafeteriaTestModule } from '../../../test.module';
import { OrdenDetailComponent } from '../../../../../../main/webapp/app/entities/orden/orden-detail.component';
import { OrdenService } from '../../../../../../main/webapp/app/entities/orden/orden.service';
import { Orden } from '../../../../../../main/webapp/app/entities/orden/orden.model';

describe('Component Tests', () => {

    describe('Orden Management Detail Component', () => {
        let comp: OrdenDetailComponent;
        let fixture: ComponentFixture<OrdenDetailComponent>;
        let service: OrdenService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CafeteriaTestModule],
                declarations: [OrdenDetailComponent],
                providers: [
                    OrdenService
                ]
            })
            .overrideTemplate(OrdenDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OrdenDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrdenService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Orden(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.orden).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
