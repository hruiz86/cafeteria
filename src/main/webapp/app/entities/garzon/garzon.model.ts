import { BaseEntity } from './../../shared';

export class Garzon implements BaseEntity {
    constructor(
        public id?: number,
        public userId?: string,
        public picture?: string,
        public name?: string,
        public lastName?: string,
        public orden?: BaseEntity,
        public bonos?: BaseEntity,
        public attendanceRecord?: BaseEntity,
    ) {
    }
}
