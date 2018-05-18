package com.mono.app.service.dto;

import java.io.Serializable;
import com.mono.app.domain.enumeration.AttendanceType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import io.github.jhipster.service.filter.InstantFilter;




/**
 * Criteria class for the AttendanceRecord entity. This class is used in AttendanceRecordResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /attendance-records?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AttendanceRecordCriteria implements Serializable {
    /**
     * Class for filtering AttendanceType
     */
    public static class AttendanceTypeFilter extends Filter<AttendanceType> {
    }

    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private InstantFilter date;

    private AttendanceTypeFilter type;

    private LongFilter garzonId;

    public AttendanceRecordCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getDate() {
        return date;
    }

    public void setDate(InstantFilter date) {
        this.date = date;
    }

    public AttendanceTypeFilter getType() {
        return type;
    }

    public void setType(AttendanceTypeFilter type) {
        this.type = type;
    }

    public LongFilter getGarzonId() {
        return garzonId;
    }

    public void setGarzonId(LongFilter garzonId) {
        this.garzonId = garzonId;
    }

    @Override
    public String toString() {
        return "AttendanceRecordCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (date != null ? "date=" + date + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (garzonId != null ? "garzonId=" + garzonId + ", " : "") +
            "}";
    }

}
