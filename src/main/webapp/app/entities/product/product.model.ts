import { BaseEntity } from './../../shared';

export class Product implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public stock?: number,
        public price?: number,
        public ordens?: BaseEntity[],
    ) {
    }
}
