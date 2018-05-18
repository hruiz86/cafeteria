package com.mono.app.service.dto;

import java.io.Serializable;
import com.mono.app.domain.enumeration.BonoType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the Bonos entity. This class is used in BonosResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /bonos?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class BonosCriteria implements Serializable {
    /**
     * Class for filtering BonoType
     */
    public static class BonoTypeFilter extends Filter<BonoType> {
    }

    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private BonoTypeFilter type;

    private DoubleFilter amount;

    private LongFilter garzonId;

    public BonosCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BonoTypeFilter getType() {
        return type;
    }

    public void setType(BonoTypeFilter type) {
        this.type = type;
    }

    public DoubleFilter getAmount() {
        return amount;
    }

    public void setAmount(DoubleFilter amount) {
        this.amount = amount;
    }

    public LongFilter getGarzonId() {
        return garzonId;
    }

    public void setGarzonId(LongFilter garzonId) {
        this.garzonId = garzonId;
    }

    @Override
    public String toString() {
        return "BonosCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (amount != null ? "amount=" + amount + ", " : "") +
                (garzonId != null ? "garzonId=" + garzonId + ", " : "") +
            "}";
    }

}
