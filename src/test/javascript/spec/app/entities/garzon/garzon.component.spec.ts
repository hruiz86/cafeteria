/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CafeteriaTestModule } from '../../../test.module';
import { GarzonComponent } from '../../../../../../main/webapp/app/entities/garzon/garzon.component';
import { GarzonService } from '../../../../../../main/webapp/app/entities/garzon/garzon.service';
import { Garzon } from '../../../../../../main/webapp/app/entities/garzon/garzon.model';

describe('Component Tests', () => {

    describe('Garzon Management Component', () => {
        let comp: GarzonComponent;
        let fixture: ComponentFixture<GarzonComponent>;
        let service: GarzonService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CafeteriaTestModule],
                declarations: [GarzonComponent],
                providers: [
                    GarzonService
                ]
            })
            .overrideTemplate(GarzonComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GarzonComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GarzonService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Garzon(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.garzons[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
