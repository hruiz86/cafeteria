package com.mono.app.service.dto;

import java.io.Serializable;
import com.mono.app.domain.enumeration.State;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the Orden entity. This class is used in OrdenResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /ordens?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OrdenCriteria implements Serializable {
    /**
     * Class for filtering State
     */
    public static class StateFilter extends Filter<State> {
    }

    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StateFilter state;

    private DoubleFilter total;

    private LongFilter garzonId;

    private LongFilter productId;

    public OrdenCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StateFilter getState() {
        return state;
    }

    public void setState(StateFilter state) {
        this.state = state;
    }

    public DoubleFilter getTotal() {
        return total;
    }

    public void setTotal(DoubleFilter total) {
        this.total = total;
    }

    public LongFilter getGarzonId() {
        return garzonId;
    }

    public void setGarzonId(LongFilter garzonId) {
        this.garzonId = garzonId;
    }

    public LongFilter getProductId() {
        return productId;
    }

    public void setProductId(LongFilter productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "OrdenCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (state != null ? "state=" + state + ", " : "") +
                (total != null ? "total=" + total + ", " : "") +
                (garzonId != null ? "garzonId=" + garzonId + ", " : "") +
                (productId != null ? "productId=" + productId + ", " : "") +
            "}";
    }

}
