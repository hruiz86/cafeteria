package com.mono.app.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the Garzon entity. This class is used in GarzonResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /garzons?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class GarzonCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter userId;

    private StringFilter picture;

    private StringFilter name;

    private StringFilter lastName;

    private LongFilter ordenId;

    private LongFilter bonosId;

    private LongFilter attendanceRecordId;

    public GarzonCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getUserId() {
        return userId;
    }

    public void setUserId(StringFilter userId) {
        this.userId = userId;
    }

    public StringFilter getPicture() {
        return picture;
    }

    public void setPicture(StringFilter picture) {
        this.picture = picture;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getLastName() {
        return lastName;
    }

    public void setLastName(StringFilter lastName) {
        this.lastName = lastName;
    }

    public LongFilter getOrdenId() {
        return ordenId;
    }

    public void setOrdenId(LongFilter ordenId) {
        this.ordenId = ordenId;
    }

    public LongFilter getBonosId() {
        return bonosId;
    }

    public void setBonosId(LongFilter bonosId) {
        this.bonosId = bonosId;
    }

    public LongFilter getAttendanceRecordId() {
        return attendanceRecordId;
    }

    public void setAttendanceRecordId(LongFilter attendanceRecordId) {
        this.attendanceRecordId = attendanceRecordId;
    }

    @Override
    public String toString() {
        return "GarzonCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (picture != null ? "picture=" + picture + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (lastName != null ? "lastName=" + lastName + ", " : "") +
                (ordenId != null ? "ordenId=" + ordenId + ", " : "") +
                (bonosId != null ? "bonosId=" + bonosId + ", " : "") +
                (attendanceRecordId != null ? "attendanceRecordId=" + attendanceRecordId + ", " : "") +
            "}";
    }

}
