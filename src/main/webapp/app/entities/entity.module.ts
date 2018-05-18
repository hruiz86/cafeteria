import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { CafeteriaProductModule } from './product/product.module';
import { CafeteriaGarzonModule } from './garzon/garzon.module';
import { CafeteriaOrdenModule } from './orden/orden.module';
import { CafeteriaBonosModule } from './bonos/bonos.module';
import { CafeteriaAttendanceRecordModule } from './attendance-record/attendance-record.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        CafeteriaProductModule,
        CafeteriaGarzonModule,
        CafeteriaOrdenModule,
        CafeteriaBonosModule,
        CafeteriaAttendanceRecordModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CafeteriaEntityModule {}
