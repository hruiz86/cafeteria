import { BaseEntity } from './../../shared';

export const enum BonoType {
    'DAYLY',
    'MONTHLY'
}

export class Bonos implements BaseEntity {
    constructor(
        public id?: number,
        public type?: BonoType,
        public amount?: number,
        public garzons?: BaseEntity[],
    ) {
    }
}
