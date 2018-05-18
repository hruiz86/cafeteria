/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { CafeteriaTestModule } from '../../../test.module';
import { OrdenDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/orden/orden-delete-dialog.component';
import { OrdenService } from '../../../../../../main/webapp/app/entities/orden/orden.service';

describe('Component Tests', () => {

    describe('Orden Management Delete Component', () => {
        let comp: OrdenDeleteDialogComponent;
        let fixture: ComponentFixture<OrdenDeleteDialogComponent>;
        let service: OrdenService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CafeteriaTestModule],
                declarations: [OrdenDeleteDialogComponent],
                providers: [
                    OrdenService
                ]
            })
            .overrideTemplate(OrdenDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OrdenDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrdenService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
