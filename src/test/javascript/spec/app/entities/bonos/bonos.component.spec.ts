/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CafeteriaTestModule } from '../../../test.module';
import { BonosComponent } from '../../../../../../main/webapp/app/entities/bonos/bonos.component';
import { BonosService } from '../../../../../../main/webapp/app/entities/bonos/bonos.service';
import { Bonos } from '../../../../../../main/webapp/app/entities/bonos/bonos.model';

describe('Component Tests', () => {

    describe('Bonos Management Component', () => {
        let comp: BonosComponent;
        let fixture: ComponentFixture<BonosComponent>;
        let service: BonosService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CafeteriaTestModule],
                declarations: [BonosComponent],
                providers: [
                    BonosService
                ]
            })
            .overrideTemplate(BonosComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BonosComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BonosService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Bonos(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.bonos[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
