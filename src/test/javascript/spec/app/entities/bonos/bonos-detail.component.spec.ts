/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { CafeteriaTestModule } from '../../../test.module';
import { BonosDetailComponent } from '../../../../../../main/webapp/app/entities/bonos/bonos-detail.component';
import { BonosService } from '../../../../../../main/webapp/app/entities/bonos/bonos.service';
import { Bonos } from '../../../../../../main/webapp/app/entities/bonos/bonos.model';

describe('Component Tests', () => {

    describe('Bonos Management Detail Component', () => {
        let comp: BonosDetailComponent;
        let fixture: ComponentFixture<BonosDetailComponent>;
        let service: BonosService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CafeteriaTestModule],
                declarations: [BonosDetailComponent],
                providers: [
                    BonosService
                ]
            })
            .overrideTemplate(BonosDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BonosDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BonosService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Bonos(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.bonos).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
