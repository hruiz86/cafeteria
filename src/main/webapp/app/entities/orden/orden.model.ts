import { BaseEntity } from './../../shared';

export const enum State {
    'PENDING_APPROVAL',
    'APROVED',
    'CANCEL'
}

export class Orden implements BaseEntity {
    constructor(
        public id?: number,
        public state?: State,
        public total?: number,
        public garzons?: BaseEntity[],
        public products?: BaseEntity[],
    ) {
    }
}
