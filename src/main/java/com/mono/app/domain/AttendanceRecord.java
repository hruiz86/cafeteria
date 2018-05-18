package com.mono.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import com.mono.app.domain.enumeration.AttendanceType;

/**
 * A AttendanceRecord.
 */
@Entity
@Table(name = "attendance_record")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AttendanceRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "jhi_date", nullable = false)
    private Instant date;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type", nullable = false)
    private AttendanceType type;

    @ManyToOne
    private Garzon garzon;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return date;
    }

    public AttendanceRecord date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public AttendanceType getType() {
        return type;
    }

    public AttendanceRecord type(AttendanceType type) {
        this.type = type;
        return this;
    }

    public void setType(AttendanceType type) {
        this.type = type;
    }

    public Garzon getGarzon() {
        return garzon;
    }

    public AttendanceRecord garzon(Garzon garzon) {
        this.garzon = garzon;
        return this;
    }

    public void setGarzon(Garzon garzon) {
        this.garzon = garzon;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AttendanceRecord attendanceRecord = (AttendanceRecord) o;
        if (attendanceRecord.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), attendanceRecord.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AttendanceRecord{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
