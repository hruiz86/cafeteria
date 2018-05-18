import { BaseEntity } from './../../shared';

export const enum AttendanceType {
    'ON',
    'OFF'
}

export class AttendanceRecord implements BaseEntity {
    constructor(
        public id?: number,
        public date?: any,
        public type?: AttendanceType,
        public garzons?: BaseEntity[],
    ) {
    }
}
